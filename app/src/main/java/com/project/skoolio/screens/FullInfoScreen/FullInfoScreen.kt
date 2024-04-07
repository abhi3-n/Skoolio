package com.project.skoolio.screens.FullInfoScreen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.screens.HomeScreen.HomeScreenMainContent
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.viewModels.ViewModelProvider

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FullInfoScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
    userType: String?
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FullInfoScreenContent(navController, viewModelProvider, context, userType)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FullInfoScreenContent(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
    context: Context,
    userType: String?
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState,"Admin", getUserDrawerItemsList("Admin", navController),
        scaffold = {
            CommonScaffold(
                title = "Full Info",
                icon = Icons.Default.Info,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    FullInfoScreenMainContent(it, viewModelProvider, context, navController, userType)
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FullInfoScreenMainContent(
    paddingValues: PaddingValues,
    viewModelProvider: ViewModelProvider,
    context: Context,
    navController: NavHostController,
    userType: String?
) {
    HomeScreenMainContent(paddingValues = paddingValues, userType = userType, viewModelProvider)
    Text(text = "Hi")
}
