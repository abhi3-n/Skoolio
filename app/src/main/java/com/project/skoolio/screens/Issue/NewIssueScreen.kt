package com.project.skoolio.screens.Issue

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.CustomTextField
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.IssueViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun NewIssueScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val context = LocalContext.current
    val issueViewModel = viewModelProvider.getIssueViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NewIssueScreenContent(navController, context, issueViewModel)
    }
}

@Composable
fun NewIssueScreenContent(
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
                title = "New Issue",
                icon = Icons.Default.Warning,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    NewIssueScreenMainContent(it, context, navController, issueViewModel)
                }
            )
        }
    )
}

@Composable
fun NewIssueScreenMainContent(paddingValues: PaddingValues, context: Context, navController: NavHostController, issueViewModel: IssueViewModel) {
    val title = issueViewModel.issueTitle
    val description = issueViewModel.issueDescription
    val valid = rememberSaveable(title.value, description.value) {
        title.value.trim().isNotEmpty() && description.value.trim().isNotEmpty()
    }

    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        Text(text = "Title", modifier = Modifier.padding(start = 12.dp), style = MaterialTheme.typography.titleMedium)
        CustomTextField(valueState = title, label = "Issue Title")
        Text(text = "Description", modifier = Modifier.padding(start = 12.dp), style = MaterialTheme.typography.titleMedium)
        CustomTextField(valueState = description,
            label = "",
            singleLine = false,
            maxLines = 50,
            modifier = Modifier.height(160.dp))

        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                if(valid) {
                    issueViewModel.createIssue(navController, context)
                }
            },
            enabled = valid) {
            Text(text = "Create")
        }
    }

}
