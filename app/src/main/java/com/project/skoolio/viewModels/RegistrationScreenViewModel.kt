package com.project.skoolio.viewModels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.RegisterResponse
import com.project.skoolio.model.registerSingleton.registerStudent
import com.project.skoolio.model.registerSingleton.registerTeacher
import com.project.skoolio.repositories.RegistrationScreenRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationScreenViewModel @Inject constructor(private val registrationScreenRepository: RegistrationScreenRepository)
    :ViewModel() {
    private var _registrationResponse: MutableState<DataOrException<RegisterResponse, Boolean, Exception>> =
        mutableStateOf<DataOrException<RegisterResponse, Boolean, Exception>>(
            DataOrException(RegisterResponse(), false, null)
        )
    val registrationResponse: State<DataOrException<RegisterResponse, Boolean, Exception>> = _registrationResponse

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
}