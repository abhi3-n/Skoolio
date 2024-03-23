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
import com.project.skoolio.model.RegisterResponse
import com.project.skoolio.model.userDetailSingleton.studentDetails
import com.project.skoolio.model.userDetailSingleton.teacherDetails
import com.project.skoolio.model.userDetailSingleton.userDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.network.Backend
import com.project.skoolio.repositories.RegistrationScreenRepository
import com.project.skoolio.utils.CityList
import com.project.skoolio.utils.SchoolList
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class RegistrationScreenViewModel @Inject constructor(
    private val registrationScreenRepository: RegistrationScreenRepository,
    private val backend: Backend
    )
    :ViewModel() {
    private var _registrationResponse: MutableState<DataOrException<RegisterResponse, Boolean, Exception>> =
        mutableStateOf<DataOrException<RegisterResponse, Boolean, Exception>>(
            DataOrException(RegisterResponse(), false, null)
        )
    val registrationResponse: State<DataOrException<RegisterResponse, Boolean, Exception>> = _registrationResponse

    var selectedSchoolCity:MutableState<String> = mutableStateOf("")

    val isClassIdAndNameListReady:MutableState<Boolean> = mutableStateOf(false)

//    fun registerStudent(onRegisterFailure: (Context) -> Unit, context: Context):Unit{
//        viewModelScope.launch {
//            _registrationResponse.value.loading = true
//            _registrationResponse.value = registrationScreenRepository.registerStudent(registerStudent.getStudent())
//            if(_registrationResponse.value.data.applicationId.isNotEmpty() == true){
//                //registration has succeeded
//                _registrationResponse.value.loading = false
//            }
//            else if(_registrationResponse.value.exception!=null){
//                //registration has not succeeded
//                _registrationResponse.value.loading = false
//                onRegisterFailure(context)
//            }
//        }
//    }
//
//    fun registerTeacher(onRegisterFailure: (Context) -> Unit, context: Context) {
//        viewModelScope.launch {
//            _registrationResponse.value.loading = true
//            _registrationResponse.value = registrationScreenRepository.registerTeacher(registerTeacher.getTeacher())
//            if(_registrationResponse.value.data.applicationId.isNotEmpty() == true){
//                //registration has succeeded
//                _registrationResponse.value.loading = false
//            }
//            else if(_registrationResponse.value.exception!=null){
//                //registration has not succeeded
//                _registrationResponse.value.loading = false
//                onRegisterFailure(context)
//            }
//        }
//    }

    fun getCitySchools(
        context: Context,
        goToFormScreen: (NavHostController, MutableState<String>) -> Unit,
        navController: NavHostController,
        selectedAccountType: MutableState<String>,
        loading: MutableState<Boolean>
    ) {
        viewModelScope.launch {
            try {
                loading.value = true
                SchoolList.listOfSchools = backend.getCitySchools(selectedSchoolCity.value)
                SchoolList.listForCity = selectedSchoolCity.value
                loading.value = false
                goToFormScreen(navController,selectedAccountType)
            }
            catch (e:Exception){
                loading.value = false
                Toast.makeText(context,"Some error occured while fetching school list.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun register(
        onRegisterFailure: (Context) -> Unit,
        context: Context,
        userDetails: userDetails,
        registrationDone: MutableState<Boolean>
    ) {
        viewModelScope.launch {
            _registrationResponse.value.loading = true
            if(userDetails is studentDetails) {
                _registrationResponse.value =
                    registrationScreenRepository.registerStudent(studentDetails.getStudent())
            }
            else if(userDetails is teacherDetails){
                _registrationResponse.value =
                    registrationScreenRepository.registerTeacher(teacherDetails.getTeacher())
            }
            if(_registrationResponse.value.data.applicationId.isNotEmpty() == true){
                registrationDone.value = true
                //registration has succeeded
                _registrationResponse.value.loading = false
            }
            else if(_registrationResponse.value.exception!=null){
                //registration has not succeeded
                _registrationResponse.value.loading = false
                onRegisterFailure(context)
            }
        }
    }

    fun getCitiesList(
        navController: NavHostController,
        signUpLoading: MutableState<Boolean>,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                signUpLoading.value = true
                CityList.listOfCities = backend.getCitiesList()
                signUpLoading.value = false
                navController.navigate(AppScreens.SelectAccountTypeScreen.name)
            }
            catch (e:Exception){
                signUpLoading.value = false
                Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getClassNameListForSchool(
        schoolId: Int,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                isClassIdAndNameListReady.value = false
                val list = backend.getClassNameListForSchool(schoolId.toString())
                SchoolList.setClassInfoOnSchool(schoolId, list)
                isClassIdAndNameListReady.value = true
//                Toast.makeText(context,"Got class list successfully for school.", Toast.LENGTH_SHORT).show()
            }
            catch (e: HttpException){
                if(e.message() == "Not Found")
                    Toast.makeText(context, "No classes found for this school.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}