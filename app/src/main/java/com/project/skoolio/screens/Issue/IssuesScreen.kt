package com.project.skoolio.screens.Issue

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.BackToHomeScreen
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.IssueViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun IssuesScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider
) {
    BackToHomeScreen(navController, loginUserType.value)
    val context = LocalContext.current
    val issueViewModel = viewModelProvider.getIssueViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IssuesScreenContent(navController, context, issueViewModel)
    }
}

@Composable
fun IssuesScreenContent(
    navController: NavHostController,
    context: Context,
    issueViewModel: IssueViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState, loginUserType.value, getUserDrawerItemsList(loginUserType.value, navController),
        scaffold = {
            CommonScaffold(
                title = "Issues",
                icon = Icons.Default.Warning,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    IssuesScreenMainContent(it, context, navController, issueViewModel)
                },
                floatingActionButton = {
                    if (loginUserType.value == "Student") {
                        FloatingActionButton(
                            modifier = Modifier.padding(end = 16.dp, bottom = 16.dp), onClick = {
                                navController.navigate(AppScreens.NewIssueScreen.name)
                            },
                            shape = CircleShape,
                            containerColor = Color.Cyan
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Create New Issue",
                                tint = Color.Black
                            )
                        }
                    }
                }
            )
        }
    )
}

@Composable
fun IssuesScreenMainContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    issueViewModel: IssueViewModel
) {
    val openIssuesClick = {
        issueViewModel.fetchIssuesList(context,"o")
        navController.navigate(AppScreens.IssuesListScreen.name + "/Open")
    }
    val issueHistoryClick = {
        issueViewModel.fetchIssuesList(context,"c")
        navController.navigate(AppScreens.IssuesListScreen.name + "/History")
    }
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        ListItem(shape = CircleShape,
            itemInfo = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "Open Issues",
                        style = TextStyle(fontSize = 32.sp),
                        modifier = Modifier.padding(4.dp).clickable {
                            openIssuesClick.invoke()
                        })
                }
            },
            onClick = {
                openIssuesClick.invoke()
            })
        Spacer(modifier = Modifier.height(2.dp))
        ListItem(shape = CircleShape,
            itemInfo = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "History",
                        style = TextStyle(fontSize = 32.sp),
                        modifier = Modifier.padding(4.dp).clickable {
                            issueHistoryClick.invoke()
                        })
                }
            },
            onClick = {
                issueHistoryClick.invoke()
            })
    }
}
