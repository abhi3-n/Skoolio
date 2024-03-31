package com.project.skoolio.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen.RegistrationFormScreen
import com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen.SelectAccountTypeScreen
import com.project.skoolio.screens.AdminListScreen.AdminListScreen
import com.project.skoolio.screens.ClassListScreen.ClassListScreen
import com.project.skoolio.screens.ClassStudentsAttendanceScreen.ClassStudentsAttendanceScreen
import com.project.skoolio.screens.HomeScreen.HomeScreen
import com.project.skoolio.screens.LoginScreen.LoginScreen
import com.project.skoolio.screens.OtpValidationScreen.OtpValidationScreen
import com.project.skoolio.screens.PendingApprovalsScreen.PendingApprovalsScreen
import com.project.skoolio.screens.SchoolInformationScreen.SchoolInformationScreen
import com.project.skoolio.screens.SetPasswordScreen.SetPasswordScreen
import com.project.skoolio.screens.SettingsScreen.SettingsScreen
import com.project.skoolio.screens.SplashScreen
import com.project.skoolio.screens.TakeAttendanceScreen.TakeAttendanceScreen
import com.project.skoolio.screens.TeacherListScreen.TeacherListScreen
import com.project.skoolio.screens.TestScreen.TestScreen
import com.project.skoolio.viewModels.ViewModelProvider

@SuppressLint("NewApi")
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
        composable(AppScreens.RegistrationFormScreen.name+"/{userType}",
            arguments = listOf(navArgument("userType"){
                type = NavType.StringType
            })){navBack->
            navBack.arguments?.getString("userType").let { userType ->
                RegistrationFormScreen(navController,viewModelProvider,userType)
            }
        }
        composable(AppScreens.OtpValidationScreen.name){
            OtpValidationScreen(navController, viewModelProvider)
        }
        composable(AppScreens.SetPasswordScreen.name+"/{userType}",
            arguments = listOf(navArgument("userType"){
                type = NavType.StringType
            })){navBack->
            navBack.arguments?.getString("userType").let { userType ->
                SetPasswordScreen(navController, viewModelProvider, userType)
            }
        }
        composable(AppScreens.HomeScreen.name+"/{userType}",
            arguments = listOf(navArgument("userType"){
                type = NavType.StringType
            })){navBack->
            navBack.arguments?.getString("userType").let { userType ->
                HomeScreen(navController, viewModelProvider, userType)
            }
        }
        composable(AppScreens.SettingsScreen.name+"/{userType}",
            arguments = listOf(navArgument("userType"){
                type = NavType.StringType
            })){navBack->
            navBack.arguments?.getString("userType").let { userType ->
                SettingsScreen(navController, viewModelProvider, userType)
            }
        }
        composable(AppScreens.TakeAttendanceScreen.name+"/{userType}",
            arguments = listOf(navArgument("userType"){
                type = NavType.StringType
            })){navBack->
            navBack.arguments?.getString("userType").let { userType ->
                TakeAttendanceScreen(navController, viewModelProvider, userType)
            }
        }
        composable(AppScreens.SchoolInformationScreen.name){
                SchoolInformationScreen(navController, viewModelProvider)
        }
        composable(AppScreens.PendingApprovalsScreen.name){
                PendingApprovalsScreen(navController, viewModelProvider)
        }
        composable(AppScreens.ClassStudentsAttendanceScreen.name+"/{userType}",
            arguments = listOf(navArgument("userType"){
                type = NavType.StringType
            })){navBack->
            navBack.arguments?.getString("userType").let { userType ->
                ClassStudentsAttendanceScreen(navController, viewModelProvider, userType)
            }
        }
        composable(AppScreens.TestScreen.name){
            TestScreen()
        }
        composable(AppScreens.AdminListScreen.name){
            AdminListScreen(navController, viewModelProvider)
        }
        composable(AppScreens.TeacherListScreen.name){
            TeacherListScreen(navController, viewModelProvider)
        }
        composable(AppScreens.ClassListScreen.name){
            ClassListScreen(navController, viewModelProvider)
        }
    }
}