package com.project.skoolio.screens.HomeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
//        MainScaffold(navController = navController, userType, viewModelProvider)
        MainContent()
    }
}

@Composable
fun MainContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Surface(
            modifier = Modifier.padding(4.dp).height(100.dp).width(100.dp),
            border = BorderStroke(width = 2.dp, color = Color.LightGray)
        ) {


        }
    }
}

//@Composable
//fun MainScaffold(
//    navController: NavHostController,
//    userType: String?,
//    viewModelProvider: ViewModelProvider
//) {
//    Scaffold(
//        topBar = {
//            WeatherAppBar(data.city.name+", ${data.city.country}",
//                navController= navController,
//                elevation = 5.dp,
//                onAddActionClicked = {
//                    navController.navigate(WeatherScreens.SearchScreen.name)
//                }
//            )
//        },
//    ){
//        MainContent(data, it)
//    }
//}




