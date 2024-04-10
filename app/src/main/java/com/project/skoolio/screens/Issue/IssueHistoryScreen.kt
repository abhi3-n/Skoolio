package com.project.skoolio.screens.Issue

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.IssueViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun IssueHistoryScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val context = LocalContext.current
    val issueViewModel = viewModelProvider.getIssueViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IssueHistoryScreenContent(navController, context, issueViewModel)
    }
}

@Composable
fun IssueHistoryScreenContent(
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
                title = "History",
                icon = Icons.AutoMirrored.Filled.List,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    IssueHistoryScreenMainContent(it, context, navController, issueViewModel)
                },
            )
        }
    )
}

@Composable
fun IssueHistoryScreenMainContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    issueViewModel: IssueViewModel
) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

    }

}
