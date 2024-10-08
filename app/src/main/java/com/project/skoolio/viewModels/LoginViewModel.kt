package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.login.AdminLoginResponse
import com.project.skoolio.model.login.LoginRequest
import com.project.skoolio.model.login.LoginResponse
import com.project.skoolio.model.login.StudentLoginResponse
import com.project.skoolio.model.login.TeacherLoginResponse
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.model.singletonObject.teacherDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.network.Backend
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.loginUserType
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


class LoginViewModel @Inject constructor(private val backend: Backend):ViewModel() {
    private var _loginResponse: MutableState<DataOrException<LoginResponse, Boolean, Exception>> =
        mutableStateOf<DataOrException<LoginResponse, Boolean, Exception>>(
            DataOrException(LoginResponse(), false, null)
        )
    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    val userType:MutableState<String> = mutableStateOf("Student") //TODO: This better be global so that it can be accessed without having to rely on loginViewModel
    val email:MutableState<String> = mutableStateOf("")
    val password:MutableState<String> = mutableStateOf("")

    fun loginRequest(
        email: String,
        password: String,
        loading: MutableState<Boolean>,
        context: Context,
        navController: NavHostController
    ):Unit{
        viewModelScope.launch {
            loading.value = true
            _loginResponse.value.loading = true
            try {
            _loginResponse.value.data =
                if(loginUserType.value == "Student") backend.studentLogin(LoginRequest(email,password))
                else if(loginUserType.value == "Teacher") backend.teacherLogin(LoginRequest(email,password))
                else  backend.adminLogin(LoginRequest(email,password))

            _loginResponse.value.loading = false
            loading.value = false
            if(_loginResponse.value.data.status == "Approved") {
                _isLoggedIn.value = true
                if (loginUserType.value == "Student") {
                    studentDetails.schoolName.value = capitalize(backend.getSchoolNameForSchoolId((_loginResponse.value.data as StudentLoginResponse).student.studentSchoolDetails.schoolId.toString()).schoolName) //From backend, we get only schoolId for student, But we also want schoolName against that schoolId. So we make another request to fetch schoolName.
                    studentDetails.populateStudentDetails((_loginResponse.value.data as StudentLoginResponse).student)
                }
                else if (loginUserType.value == "Teacher"){
                    teacherDetails.employingSchoolName.value = capitalize(backend.getSchoolNameForSchoolId((_loginResponse.value.data as TeacherLoginResponse).teacher.teacherSchoolDetails.schoolId.toString()).schoolName) //From backend, we get only schoolId for teacher, But we also want schoolName against that schoolId. So we make another request to fetch schoolName.
                    teacherDetails.populateTeacherDetails((_loginResponse.value.data as TeacherLoginResponse).teacher)
                }
              else{
                    adminDetails.schoolName.value = capitalize(backend.getSchoolNameForSchoolId((_loginResponse.value.data as AdminLoginResponse).schoolAdministrator.schoolId.schoolId.toString()).schoolName) //From backend, we get only schoolId for admin, But we also want schoolName against that schoolId. So we make another request to fetch schoolName.
                    adminDetails.populateAdminDetails((_loginResponse.value.data as AdminLoginResponse).schoolAdministrator)
                }
                navController.navigate(AppScreens.HomeScreen.name + "/${loginUserType.value}")
                }
            }
            catch (e:HttpException){
                _loginResponse.value.loading = false
                loading.value = false
                if(e.message() == "Not Found")
                    Toast.makeText(context, "No user found with this email.", Toast.LENGTH_SHORT).show()
                else if(e.message() == "Unauthorized")
                    Toast.makeText(context, "Wrong Password.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}