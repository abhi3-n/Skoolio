package com.project.skoolio.screens.SchoolInformationScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import com.project.skoolio.model.userDetailSingleton.adminDetails
import com.project.skoolio.screens.PendingApprovalsScreen.PendingApprovalsScreenMainContent
import com.project.skoolio.utils.BackToHomeScreen
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun SchoolInformationScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
) {
    val context = LocalContext.current

    BackToHomeScreen(navController, "Admin",context)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SchoolInformationContent(navController)
    }
}

@Composable
fun SchoolInformationContent(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CommonModalNavigationDrawer(drawerState,"Admin", getUserDrawerItemsList("Admin", navController),
        scaffold = {
            CommonScaffold(
                title = "Profile",
                icon = Icons.Filled.AccountCircle,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    SchoolInformationScreenMainContent(it)
                }
            )
        }
    )
}

@Composable
fun SchoolInformationScreenMainContent(paddingValues: PaddingValues) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "Hi ${adminDetails.firstName.value}")
    }
}
