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
import com.project.skoolio.model.registerSingleton.registerStudent
import com.project.skoolio.model.registerSingleton.registerTeacher
import com.project.skoolio.network.Backend
import com.project.skoolio.repositories.RegistrationScreenRepository
import com.project.skoolio.utils.SchoolList
import kotlinx.coroutines.launch
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

    fun registerStudent(onRegisterFailure: (Context) -> Unit, context: Context):Unit{
        viewModelScope.launch {
            _registrationResponse.value.loading = true
            _registrationResponse.value = registrationScreenRepository.registerStudent(registerStudent.getStudent())
            if(_registrationResponse.value.data.applicationId.isNotEmpty() == true){
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

    fun registerTeacher(onRegisterFailure: (Context) -> Unit, context: Context) {
        viewModelScope.launch {
            _registrationResponse.value.loading = true
            _registrationResponse.value = registrationScreenRepository.registerTeacher(registerTeacher.getTeacher())
            if(_registrationResponse.value.data.applicationId.isNotEmpty() == true){
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
                Toast.makeText(context,"Some error occured while fetching school list.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}