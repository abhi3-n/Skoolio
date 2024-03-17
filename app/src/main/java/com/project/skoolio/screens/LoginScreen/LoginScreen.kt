package com.project.skoolio.screens.LoginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.AppLogoText
import com.project.skoolio.components.ForgotPasswordText
import com.project.skoolio.components.NewAccountText
import com.project.skoolio.components.UserLoginForm
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.ExitApp
import com.project.skoolio.viewModels.ViewModelProvider


@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider
) {
    val loginViewModel = viewModelProvider.getLoginViewModel()
    if(loginViewModel.isLoggedIn.value){
        navController.navigate(AppScreens.HomeScreen.name+"/${loginViewModel.userType.value}")
    }
    ExitApp(navController = navController, context = LocalContext.current)

    Surface(Modifier.fillMaxSize(), color = Color.White) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppLogoText()
            Spacer(modifier = Modifier.height(15.dp))
            UserLoginForm(loginViewModel, navController)
            ForgotPasswordText()
            NewAccountText(navController)
        }
    }

}

