package com.project.skoolio.screens.PendingApprovalsScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CircularProgressIndicatorCustom
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.model.userDetailSingleton.adminDetails
import com.project.skoolio.model.userDetailSingleton.studentDetails
import com.project.skoolio.model.userType.Student
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.BackToHomeScreen
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.viewModels.PendingApprovalsViewModel
import com.project.skoolio.viewModels.ViewModelProvider


@Composable
fun PendingApprovalsScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
) {
    val pendingApprovalsViewModel = viewModelProvider.getPendingApprovalsViewModel()
    val context = LocalContext.current
    BackToHomeScreen(navController, "Admin",context)
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//    }
    PendingApprovalsContent(navController,pendingApprovalsViewModel)

}

@SuppressLint("NewApi")
@Composable
fun PendingApprovalsContent(
    navController: NavHostController,
    pendingApprovalsViewModel: PendingApprovalsViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CommonModalNavigationDrawer(drawerState,"Admin", getUserDrawerItemsList("Admin", navController),
        scaffold = {
            CommonScaffold(
                title = "Pending Approvals",
                icon = Icons.Default.AccountCircle,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    PendingApprovalsScreenMainContent(it, navController, pendingApprovalsViewModel)
                }
            )
        }
    )
}

@Composable
fun PendingApprovalsScreenMainContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    pendingApprovalsViewModel: PendingApprovalsViewModel
) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
    ) {
        val showStudentSection = rememberSaveable {
            mutableStateOf(false)
        }
        val showTeacherSection = rememberSaveable {
            mutableStateOf(false)
        }
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceEvenly) {
            TextButton(onClick = {
                showTeacherSection.value = false
                showStudentSection.value = true
            }) {
                Text(text = "Students", style = TextStyle(fontSize = 24.sp))
            }
            VerticalDivider(modifier = Modifier.height(48.dp), thickness = 2.dp, color = Color.DarkGray)
            TextButton(onClick = {
                showStudentSection.value = false
                showTeacherSection.value = true
            }) {
                Text(text = "Teachers", style = TextStyle(fontSize = 24.sp))
            }
        }
        HorizontalDivider()
        if(showStudentSection.value){
            PendingStudentsList(navController, pendingApprovalsViewModel)
        }
        else if(showTeacherSection.value){
            PendingTeachersList(navController, pendingApprovalsViewModel)
        }
    }
}

@Composable
fun PendingTeachersList(
    navController: NavHostController,
    pendingApprovalsViewModel: PendingApprovalsViewModel
) {
    Text(text = "Teacher List")
}

@Composable
fun PendingStudentsList(
    navController: NavHostController,
    pendingApprovalsViewModel: PendingApprovalsViewModel
) {
    val context = LocalContext.current
    pendingApprovalsViewModel.getPendingStudents(adminDetails.schoolId.value, context)
    if(pendingApprovalsViewModel.pendingStudentsList.value.data.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn {
                items(pendingApprovalsViewModel.pendingStudentsList.value.data) { student: Student ->
                    ListItem(navController, student){
                        studentDetails.populateStudentDetails(student)
                        navController.navigate(AppScreens.HomeScreen.name+"/Student")
                    }
                }
            }
        }
    }
    else{
        if(pendingApprovalsViewModel.pendingStudentsList.value.exception != null){
            CircularProgressIndicatorCustom()
        }
    }
}

@Composable
fun ListItem(navController: NavHostController, student: Student, onClick:()->Unit = {}) {
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            },
        shape = CircleShape,
        color = Color.White
    ) {
        Row(Modifier.padding(start = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center) {
                Text(text = "Name: ${student.firstName} ${student.lastName}")
                Text(text = "Registration Id: ${student.registrationId}")
            }
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Student Image", modifier = Modifier.size(70.dp))
        }
    }
}


