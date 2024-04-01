package com.project.skoolio.screens.ClassStudentsListScreen

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
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.model.userType.Student
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.screens.TeacherListScreen.InfoTile
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.viewModels.SchoolInformationViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun ClassStudentsListScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider
) {
    val context = LocalContext.current
    val schoolInformationViewModel = viewModelProvider.getSchoolInformationViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StudentListScreenContent(navController, schoolInformationViewModel, context)
    }
}

@Composable
fun StudentListScreenContent(
    navController: NavHostController,
    schoolInformationViewModel: SchoolInformationViewModel,
    context: Context
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState,"Admin", getUserDrawerItemsList("Admin", navController),
        scaffold = {
            CommonScaffold(
                title = "List Of Students",
                icon = Icons.AutoMirrored.Filled.List,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    StudentListScreenMainContent(it, schoolInformationViewModel, context, navController)
                }
            )
        }
    )
}

@Composable
fun StudentListScreenMainContent(paddingValues: PaddingValues,
                                 schoolInformationViewModel: SchoolInformationViewModel,
                                 context: Context,
                                 navController: NavHostController) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = 6.dp)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Text(text = "Class - " +schoolInformationViewModel.getSelectedClass())
        if(schoolInformationViewModel.studentList.value.data.isNotEmpty()){
            Text(text = "Student Count - ${schoolInformationViewModel.studentList.value.data.size}")
            LazyColumn {
                items(schoolInformationViewModel.studentList.value.data){student: Student ->
                    val onClick = {
                        studentDetails.populateStudentDetails(student)
                        navController.navigate(AppScreens.FullInfoScreen.name + "/Student")
                    }
                    InfoTile(student.firstName, student.middleName, student.lastName, onClick)
                }
            }
        }
    }

}
