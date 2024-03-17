package com.project.skoolio.screens.HomeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.skoolio.utils.ExitApp
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModelProvider: ViewModelProvider,
    userType: String?
){
    val context = LocalContext.current
    ExitApp(navController = navController, context = context)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hi $userType")
        
    }

}