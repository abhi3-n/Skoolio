package com.project.skoolio.screens.TakeAttendanceScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import com.project.skoolio.model.singletonObject.teacherDetails
import com.project.skoolio.utils.BackToHomeScreen
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.AttendanceViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun TakeAttendanceScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
    userType: String?
) {
    val attendanceViewModel = viewModelProvider.getAttendanceViewModel()
    val context = LocalContext.current
    BackToHomeScreen(navController,userType, context)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TakeAttendanceScreenContent(navController, userType, attendanceViewModel)
    }
}

@Composable
fun TakeAttendanceScreenContent(
    navController: NavHostController,
    userType: String?,
    attendanceViewModel: AttendanceViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CommonModalNavigationDrawer(drawerState,userType, getUserDrawerItemsList(userType, navController),
        scaffold = {
            CommonScaffold(
                title = "Attendance",
                icon = Icons.Filled.DateRange,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    TakeAttendanceScreenMainContent(it,userType, attendanceViewModel, navController)
                }
            )
        }
    )
}

@Composable
fun TakeAttendanceScreenMainContent(
    paddingValues: PaddingValues,
    userType: String?,
    attendanceViewModel: AttendanceViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    //Firstly get all classes for schoolId of the user (Admin or Teacher)
    if(loginUserType.value == "Admin") {
        attendanceViewModel.getClassListForSchoolForAdmin(adminDetails.schoolId.value, context)
    }
    else if(loginUserType.value == "Teacher"){
        attendanceViewModel.getClassListForSchoolForTeacher(teacherDetails.teacherId, context)
    }
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
    ) {
        if(attendanceViewModel.classList.value.data.isNotEmpty()){
            LazyColumn {
                items(attendanceViewModel.classList.value.data.sortedBy {_class:_Class->
                    _class.grade
                }){ _class:_Class->
                    val onClick:()->Unit = {
                        attendanceViewModel.getClassStudents(_class, context, navController)
                    }
                    ListItem(itemInfo = { ClassItem(_class, onClick) },
                        onClick = onClick)
                }
            }
        }
    }
}

@Composable
fun ClassItem(
    _class: _Class,
    onClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()) {
        Text(text = capitalize(_class.grade) + " "+ capitalize(_class.section),
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp).clickable {
                onClick.invoke()
//                attendanceViewModel.getClassStudents(_class, context)
            })
    }
}
