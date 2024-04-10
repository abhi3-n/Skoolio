package com.project.skoolio.screens.Issue

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Issue.Issue
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.IssueViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun IssuesListScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
    screenType: String?
) {
    val context = LocalContext.current
    val issueViewModel = viewModelProvider.getIssueViewModel()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IssuesListScreenContent(navController, context, issueViewModel, screenType)
    }
}

@Composable
fun IssuesListScreenContent(
    navController: NavHostController,
    context: Context,
    issueViewModel: IssueViewModel,
    screenType: String?
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState, loginUserType.value, getUserDrawerItemsList(
        loginUserType.value, navController),
        scaffold = {
            CommonScaffold(
                title = if(screenType == "Open") "Open Issues" else "History",
                icon = Icons.AutoMirrored.Filled.List,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    IssuesListScreenMainContent(it, navController, issueViewModel, screenType)
                },
            )
        }
    )
}

@Composable
fun IssuesListScreenMainContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    issueViewModel: IssueViewModel,
    screenType: String?
) {
    if(screenType == "Open"){
        IssueListView(
            issueViewModel,
            issueViewModel.openIssuesList,
            paddingValues,
            navController,
            true
        )
    }
    else if(screenType == "History") {
        IssueListView(
            issueViewModel,
            issueViewModel.closedIssuesList,
            paddingValues,
            navController,
            false)
    }
}

@Composable
fun IssueListView(
    issueViewModel: IssueViewModel,
    issuesList: State<DataOrException<MutableList<Issue>, Boolean, Exception>>,
    paddingValues: PaddingValues,
    navController: NavHostController,
    isOpenIssueSelected: Boolean
) {
//    val context = LocalContext.current
//    if(issueViewModel.openIssuesList.value.loading == false
//        && issueViewModel.openIssuesList.value.data.isEmpty()){
//        issueViewModel.fetchOpenIssuesList(context, "o")
//    }
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        if(issueViewModel.listReady.value == true){
            LazyColumn {
                items(issuesList.value.data){issue:Issue->
                    ListItem(
                        itemInfo = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {
                                Text(text = issue.issueId)
                                Text(text = issue.title)
                            }
                        },
                        onClick = {
                            issueViewModel.currentIssue.value = issue
                            if(isOpenIssueSelected) issueViewModel.isOpenIssueSelected.value = true
                            navController.navigate(AppScreens.IssueInfoScreen.name)
                        },
                        shape = RectangleShape
                    )
                }
            }
        }
    }
}
