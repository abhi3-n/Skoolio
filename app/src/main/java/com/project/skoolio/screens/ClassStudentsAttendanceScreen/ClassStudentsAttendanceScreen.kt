package com.project.skoolio.screens.ClassStudentsAttendanceScreen

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.model.StudentInfo
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.classAttendance
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.presentDate
import com.project.skoolio.viewModels.AttendanceViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClassStudentsAttendanceScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
    userType: String?
) {
    val attendanceViewModel = viewModelProvider.getAttendanceViewModel()
    val context = LocalContext.current
    val activity =  context as? Activity
    androidx.activity.compose.BackHandler(enabled = true) {
        attendanceViewModel.resetClassStudentsList()
        classAttendance.resetStudentsAttendanceList()
        navController.popBackStack()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ClassStudentsAttendanceScreenContent(navController, userType, attendanceViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClassStudentsAttendanceScreenContent(
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
                    ClassStudentsAttendanceScreenMainContent(it,userType,navController, attendanceViewModel)
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClassStudentsAttendanceScreenMainContent(
    paddingValues: PaddingValues,
    userType: String?,
    navController: NavHostController,
    attendanceViewModel: AttendanceViewModel
) {
    attendanceViewModel.initializeStudentsAttendanceList()
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Grade: ${attendanceViewModel.selectedClass.value.grade}")
            if(attendanceViewModel.selectedClass.value.section.isNotEmpty()) {
                Text(text = "Section: "+ capitalize(attendanceViewModel.selectedClass.value.section))
            }
            Text(text = presentDate())
        }
        LazyColumn {
            items(attendanceViewModel.classStudentsList.value.data){studentInfo: StudentInfo ->
                ListItem(itemInfo = {
                    StudentRow(studentInfo, attendanceViewModel.selectedClass.value.classId)
                })
            }
        }
        SubmitAttendanceButton(attendanceViewModel, navController)
    }

}

@Composable
fun StudentRow(studentInfo: StudentInfo, classId: String) {
    val valueSelected = rememberSaveable { mutableStateOf("Not Selected")}
    val onSelectStatus = {
        classAttendance.addAttendance(studentInfo.studentId, valueSelected.value)
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)) {
        Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Student Image", modifier = Modifier.size(100.dp))
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center) {
            Text(text = "Name: " + capitalize(studentInfo.firstName) + " " + capitalize(studentInfo.lastName))
            if(valueSelected.value == "Not Selected"){
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        modifier = Modifier.padding(end = 4.dp),
                        onClick = {
                            valueSelected.value = "Present"
                            onSelectStatus.invoke()
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                        Text(text = "Present")
                    }
                    Button(
                        modifier = Modifier.padding(start =  4.dp),
                        onClick = {
                            valueSelected.value = "Absent"
                            onSelectStatus.invoke()
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                        Text(text = "Absent")
                    }
                }
            }
            else{
                IconButton(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        valueSelected.value = "Not Selected"
                        onSelectStatus.invoke()
                    }) {
                    Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Retake attendance for student")
                }
            }
            Text(modifier = Modifier.align(Alignment.End).padding(end = 20.dp)
                ,text = valueSelected.value, style = TextStyle(
                color = when(valueSelected.value){
                    "Present"-> Color.Green
                    "Absent"-> Color.Red
                    else->Color.Black
                }
            ))
        }
    }
}

@Composable
fun SubmitAttendanceButton(
    attendanceViewModel: AttendanceViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            attendanceViewModel.submitAttendance(context)
        }) {
            Text(text = "Submit")
        }
    }
}
