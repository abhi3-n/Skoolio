package com.project.skoolio.screens.LoginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.AppLogoText
import com.project.skoolio.components.CircularProgressIndicatorCustom
import com.project.skoolio.components.ForgotPasswordButton
import com.project.skoolio.components.NewAccountText
import com.project.skoolio.components.UserLoginForm
import com.project.skoolio.utils.ExitApp
import com.project.skoolio.viewModels.ViewModelProvider


@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider
) {
    val loginViewModel = viewModelProvider.getLoginViewModel()
//    if(loginViewModel.isLoggedIn.value){
//        navController.navigate(AppScreens.HomeScreen.name+"/${loginViewModel.userType.value}")
//    }
    ExitApp(navController = navController, context = LocalContext.current)

    val signUpLoading = rememberSaveable { mutableStateOf(false) }
    Surface(Modifier.fillMaxSize(), color = Color.White) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppLogoText()
            Spacer(modifier = Modifier.height(15.dp))
            UserLoginForm(loginViewModel, navController)
            ForgotPasswordButton(navController)
            NewAccountText(navController, viewModelProvider, signUpLoading)
            if(signUpLoading.value){
                CircularProgressIndicatorCustom()
            }
            Spacer(modifier = Modifier.height(15.dp))
//            Button(onClick = {
//                navController.navigate(AppScreens.TestScreen.name)
//            }) {
//                Text(text = "Test Screen")
//            }
        }
    }

}

