package com.project.skoolio.screens.TeacherListScreen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.model.singletonObject.teacherDetails
import com.project.skoolio.model.userType.Teacher
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.formatName
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.viewModels.SchoolInformationViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun TeacherListScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val context = LocalContext.current
    val schoolInformationViewModel = viewModelProvider.getSchoolInformationViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TeacherListScreenContent(navController, schoolInformationViewModel, context)
    }
}

@Composable
fun TeacherListScreenContent(
    navController: NavHostController,
    schoolInformationViewModel: SchoolInformationViewModel,
    context: Context
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState,"Admin", getUserDrawerItemsList("Admin", navController),
        scaffold = {
            CommonScaffold(
                title = "List Of Teachers",
                icon = Icons.AutoMirrored.Filled.List,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    TeacherListScreenMainContent(it, schoolInformationViewModel, context, navController)
                }
            )
        }
    )
}

@Composable
fun TeacherListScreenMainContent(
    paddingValues: PaddingValues,
    schoolInformationViewModel: SchoolInformationViewModel,
    context: Context,
    navController: NavHostController
) {
    schoolInformationViewModel.getTeacherListForSchool(adminDetails.schoolId.value, context)
    Column(modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = 6.dp)
        .fillMaxSize(),
//        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        if(schoolInformationViewModel.teacherList.value.data.isNotEmpty()){
            LazyColumn {
                items(schoolInformationViewModel.teacherList.value.data){teacher: Teacher ->
                    val onClick = {
                        teacherDetails.populateTeacherDetails(teacher)
                        navController.navigate(AppScreens.FullInfoScreen.name + "/Teacher")
                    }
                    ListItem(shape = RectangleShape,
                        itemInfo = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {
                                Image(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = "${teacher.firstName}'s Image",
                                    modifier = Modifier
                                        .size(90.dp)
                                        .padding(4.dp)
                                )
                                Text(text =  formatName(teacher.firstName, teacher.middleName, teacher.lastName))
                                IconButton(modifier = Modifier.align(Alignment.End),
                                    onClick = {
                                        onClick.invoke()
                                    }) {
                                    Icon(
                                        imageVector = Icons.Filled.Info,
                                        contentDescription = "Info Page",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        },
                        onClick = {
                            onClick.invoke()
                        }
                    )
                }
            }
        }
    }

}
