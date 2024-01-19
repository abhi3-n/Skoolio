package com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.skoolio.components.ShowToast
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun RegistrationFormScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
    userType: String?
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        if(userType == "Student"){
            ShowStudentRegistrationForm()
        }
        else if(userType == "Teacher"){
            ShowTeacherRegistrationForm()
        }
        else{
            ShowAdminRegistrationForm()
        }
    }
}


@Composable
fun ShowStudentRegistrationForm() {
    ShowToast(LocalContext.current,"Student Registration Form")
}

@Composable
fun ShowTeacherRegistrationForm() {
    ShowToast(LocalContext.current,"Teacher Registration Form")
}

@Composable
fun ShowAdminRegistrationForm() {
    ShowToast(LocalContext.current,"Admin Registration Form")
}
