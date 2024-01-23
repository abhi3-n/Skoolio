package com.project.skoolio.viewModels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelProvider @Inject constructor(
    private val loginViewModel: LoginViewModel,
    private val registrationScreenViewModel: RegistrationScreenViewModel
):ViewModel(){

    fun getLoginViewModel() = loginViewModel
    fun getRegistrationScreenViewModel() = registrationScreenViewModel

}