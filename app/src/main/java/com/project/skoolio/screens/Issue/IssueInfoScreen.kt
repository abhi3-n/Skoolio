package com.project.skoolio.screens.Issue

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.IssueViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun IssueInfoScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
) {
    val context = LocalContext.current
    val issueViewModel = viewModelProvider.getIssueViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IssueInfoScreenContent(navController, context, issueViewModel)
    }
}

@Composable
fun IssueInfoScreenContent(
    navController: NavHostController,
    context: Context,
    issueViewModel: IssueViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState, loginUserType.value, getUserDrawerItemsList(
        loginUserType.value, navController),
        scaffold = {
            CommonScaffold(
                title = "Info",
                icon = Icons.AutoMirrored.Filled.List,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    IssueInfoScreenMainContent(it, context, navController, issueViewModel)
                },
            )
        }
    )
}

@Composable
fun IssueInfoScreenMainContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    issueViewModel: IssueViewModel
) {


}
