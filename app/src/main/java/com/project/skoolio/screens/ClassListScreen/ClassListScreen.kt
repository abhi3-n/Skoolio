package com.project.skoolio.screens.ClassListScreen

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.model._Class
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.screens.TakeAttendanceScreen.ClassItem
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.viewModels.SchoolInformationViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun ClassListScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val context = LocalContext.current
    val schoolInformationViewModel = viewModelProvider.getSchoolInformationViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ClassListScreenContent(navController, schoolInformationViewModel, context)
    }
}

@Composable
fun ClassListScreenContent(
    navController: NavHostController,
    schoolInformationViewModel: SchoolInformationViewModel,
    context: Context
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState,"Admin", getUserDrawerItemsList("Admin", navController),
        scaffold = {
            CommonScaffold(
                title = "List Of Classes",
                icon = Icons.AutoMirrored.Filled.List,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    ClassListScreenMainContent(it, schoolInformationViewModel, context, navController)
                }
            )
        }
    )
}

@Composable
fun ClassListScreenMainContent(
    paddingValues: PaddingValues,
    schoolInformationViewModel: SchoolInformationViewModel,
    context: Context,
    navController: NavHostController
) {
    schoolInformationViewModel.getClassListForSchool(adminDetails.schoolId.value, context)
    Column(modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = 6.dp)
        .fillMaxSize(),
//        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        if (schoolInformationViewModel.classList.value.data.isNotEmpty()) {
            Text(text = "Class Count - ${schoolInformationViewModel.classList.value.data.size}")
            LazyColumn {
                items(schoolInformationViewModel.classList.value.data.sortedBy {_class:_Class->
                    _class.grade
                }) { _class: _Class ->
                    val onClick = {
                        val className = _class.grade + if(_class.section.isNotEmpty()) " "+ capitalize(_class.section) else ""
                        schoolInformationViewModel.setSelectedClass(className)
                        schoolInformationViewModel.getStudentsListForClass(_class.classId, context)
                        navController.navigate(AppScreens.ClassStudentsListScreen.name)
                    }
                    ListItem(itemInfo = { ClassItem(_class = _class, onClick = {onClick.invoke()}) },
                        onClick = {
                            onClick.invoke()
                        })
                }
            }
        }
    }
}
