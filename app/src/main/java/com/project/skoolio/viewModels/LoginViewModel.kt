package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.project.skoolio.model.login.LoginRequest
import com.project.skoolio.model.login.LoginResponse
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.network.Backend
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginViewModel @Inject constructor(private val backend: Backend):ViewModel() {
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
            val loginResponse:LoginResponse =
                if(userType.value == "Student") backend.studentLogin(LoginRequest(email,password))
                else if(userType.value == "Teacher") backend.teacherLogin(LoginRequest(email,password))
                else backend.adminLogin(LoginRequest(email,password))
            loading.value = false
            if(loginResponse.status == "Approved"){
                navController.navigate(AppScreens.HomeScreen.name+"/${userType.value}")
            }
            else{
                Toast.makeText(context,loginResponse.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}