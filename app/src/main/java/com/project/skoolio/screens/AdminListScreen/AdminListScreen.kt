package com.project.skoolio.screens.AdminListScreen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.model.userType.SchoolAdministrator
import com.project.skoolio.screens.SchoolInformationScreen.SchoolInformationScreenMainContent
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.viewModels.SchoolInformationViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun AdminListScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val context = LocalContext.current
    val schoolInformationViewModel = viewModelProvider.getSchoolInformationViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AdminListScreenContent(navController, schoolInformationViewModel, context)
    }
}

@Composable
fun AdminListScreenContent(
    navController: NavHostController,
    schoolInformationViewModel: SchoolInformationViewModel,
    context: Context
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CommonModalNavigationDrawer(drawerState,"Admin", getUserDrawerItemsList("Admin", navController),
        scaffold = {
            CommonScaffold(
                title = "List Of Admins",
                icon = Icons.AutoMirrored.Filled.List,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    AdminListScreenMainContent(it, schoolInformationViewModel, context, navController)
                }
            )
        }
    )

}

@Composable
fun AdminListScreenMainContent(
    paddingValues: PaddingValues,
    schoolInformationViewModel: SchoolInformationViewModel,
    context: Context,
    navController: NavHostController
) {
    schoolInformationViewModel.getAdminListForSchool(adminDetails.schoolId.value, context)
    Column(modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = 6.dp)
        .fillMaxSize(),
//        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        if(schoolInformationViewModel.adminList.value.data.isNotEmpty()){
            LazyColumn {
                items(schoolInformationViewModel.adminList.value.data){schoolAdministrator: SchoolAdministrator ->
                    ListItem(
                        itemInfo = {
                            Column {
                                Text(text = "First Name - ${schoolAdministrator.firstName}")
                                Text(text = "Last Name - ${schoolAdministrator.lastName}")
                            }
                        },
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}

