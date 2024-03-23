package com.project.skoolio.viewModels

import android.content.Context
import android.util.Log
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
import com.project.skoolio.model.userDetailSingleton.adminDetails
import com.project.skoolio.model.userDetailSingleton.studentDetails
import com.project.skoolio.model.userDetailSingleton.teacherDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.network.Backend
import com.project.skoolio.utils.capitalize
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

    val userType:MutableState<String> = mutableStateOf("Student")
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
            Log.d("Admin Login","1.)$userType")
            _loginResponse.value.data =
                if(userType.value == "Student") backend.studentLogin(LoginRequest(email,password))
                else if(userType.value == "Teacher") backend.teacherLogin(LoginRequest(email,password))
                else  backend.adminLogin(LoginRequest(email,password))

                Log.d("Admin Login","2.)$userType")
            _loginResponse.value.loading = false
            loading.value = false
            if(_loginResponse.value.data.status == "Approved") {
                _isLoggedIn.value = true
                if (userType.value == "Student") {
                    studentDetails.schoolName.value = capitalize(backend.getSchoolNameForSchoolId((_loginResponse.value.data as StudentLoginResponse).student.studentSchoolDetails.schoolId.toString()).schoolName) //From backend, we get only schoolId for student, But we also want schoolName against that schoolId. So we make another request to fetch schoolName.
                    studentDetails.populateStudentDetails((_loginResponse.value.data as StudentLoginResponse).student)
                }
                else if (userType.value == "Teacher"){
                    teacherDetails.employingSchoolName.value = capitalize(backend.getSchoolNameForSchoolId((_loginResponse.value.data as TeacherLoginResponse).teacher.teacherSchoolDetails.schoolId.toString()).schoolName) //From backend, we get only schoolId for teacher, But we also want schoolName against that schoolId. So we make another request to fetch schoolName.
                    teacherDetails.populateTeacherDetails((_loginResponse.value.data as TeacherLoginResponse).teacher)
                }
              else{
                    Log.d("Admin Login","3.)${(_loginResponse.value.data as AdminLoginResponse).schoolAdministrator.schoolId}")

                    adminDetails.schoolName.value = capitalize(backend.getSchoolNameForSchoolId((_loginResponse.value.data as AdminLoginResponse).schoolAdministrator.schoolId.schoolId.toString()).schoolName) //From backend, we get only schoolId for admin, But we also want schoolName against that schoolId. So we make another request to fetch schoolName.
                    adminDetails.populateAdminDetails((_loginResponse.value.data as AdminLoginResponse).schoolAdministrator)
                }
                navController.navigate(AppScreens.HomeScreen.name + "/${userType.value}")
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