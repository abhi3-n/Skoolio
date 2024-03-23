package com.project.skoolio.screens.TakeAttendanceScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.screens.HomeScreen.HomeScreenMainContent
import com.project.skoolio.utils.BackToHomeScreen
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun TakeAttendanceScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
    userType: String?
) {
    val context = LocalContext.current
    BackToHomeScreen(navController,userType, context)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TakeAttendanceScreenContent(navController, userType)
    }
}

@Composable
fun TakeAttendanceScreenContent(navController: NavHostController, userType: String?) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CommonModalNavigationDrawer(drawerState,userType, getUserDrawerItemsList(userType, navController),
        scaffold = {
            CommonScaffold(
                title = "Attendance",
                icon = Icons.Filled.DateRange,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    TakeAttendanceScreenMainContent(it,userType)
                }
            )
        }
    )
}

@Composable
fun TakeAttendanceScreenMainContent(paddingValues: PaddingValues, userType: String?) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "Attendance page for $userType")
    }
}
