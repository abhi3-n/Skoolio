package com.project.skoolio.screens.SettingsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ImageSurface
import com.project.skoolio.utils.BackToHomeScreen
import com.project.skoolio.utils.doSignOut
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider
) {
    val context = LocalContext.current
    BackToHomeScreen(navController, loginUserType.value)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SettingsScreenContent(navController, loginUserType.value)
    }
}

@Composable
fun SettingsScreenContent(navController: NavHostController, userType: String?) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CommonModalNavigationDrawer(drawerState,userType, getUserDrawerItemsList(userType, navController),
        scaffold = {
            CommonScaffold(
                title = "Settings",
                icon = Icons.Default.Settings,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                isSettingsScreen = true,
                mainContent = {
                    SettingsScreenMainContent(it,userType, navController)
                }
            )
        }
    )
}

@Composable
fun SettingsScreenMainContent(
    paddingValues: PaddingValues,
    userType: String?,
    navController: NavHostController
) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        ImageSurface()
        Spacer(modifier = Modifier.height(40.dp))
        TextButton(onClick = {

        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Edit Contact Details", fontSize = 20.sp)
        }
//        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.8f))
        TextButton(onClick = {

        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Edit Address Details", fontSize = 20.sp)
        }
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.8f))
        TextButton(onClick = { doSignOut.invoke(navController) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Log Out", fontSize = 20.sp)
        }
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillMaxWidth(0.8f))
    }
}

