package com.project.skoolio.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.skoolio.screens.LoginScreen.LoginScreen
import com.project.skoolio.screens.SelectAccountTypeScreen.SelectAccountTypeScreen
import com.project.skoolio.screens.SplashScreen
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun AppNavigation(viewModelProvider: ViewModelProvider) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name ){
        composable(AppScreens.SplashScreen.name){
            SplashScreen(navController)
        }
        composable(AppScreens.LoginScreen.name){
            LoginScreen(navController, viewModelProvider)
        }
        composable(AppScreens.SelectAccountTypeScreen.name){
            SelectAccountTypeScreen(navController, viewModelProvider)
        }
    }
}