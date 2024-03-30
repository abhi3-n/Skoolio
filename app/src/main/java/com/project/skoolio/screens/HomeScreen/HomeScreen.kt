package com.project.skoolio.screens.HomeScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.DetailSection
import com.project.skoolio.components.FormTitle
import com.project.skoolio.components.GuardianDetails
import com.project.skoolio.components.ProfileAddressDetails
import com.project.skoolio.components.ProfileBasicDetails
import com.project.skoolio.components.ProfileContactDetails
import com.project.skoolio.components.ProfileImageSurface
import com.project.skoolio.components.ProfileSchoolDetails
import com.project.skoolio.components.SideDrawerTitle
import com.project.skoolio.components.SkoolioAppBar
import com.project.skoolio.model.userDetailSingleton.adminDetails
import com.project.skoolio.model.userDetailSingleton.studentDetails
import com.project.skoolio.model.userDetailSingleton.teacherDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.ExitApp
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.ViewModelProvider
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModelProvider: ViewModelProvider,
    userType: String?
){
    val context = LocalContext.current
    if(loginUserType.value == userType){
        ExitApp(navController = navController, context = context)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HomeScreenContent(navController, userType)
    }
}

@SuppressLint("NewApi")
@Composable
fun HomeScreenContent(navController: NavHostController, userType: String?) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CommonModalNavigationDrawer(drawerState,userType, getUserDrawerItemsList(userType, navController),
        scaffold = {
            CommonScaffold(
                title = "Profile",
                icon = Icons.Filled.AccountCircle,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    HomeScreenMainContent(it,userType)
                }
            )
        }
    )
}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun MainScaffold(
//    navController: NavHostController,
//    userType: String? ,
////    viewModelProvider: ViewModelProvider
//) {
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    ModalNavigationDrawer(
//        gesturesEnabled = true,
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet(
//                modifier = Modifier
//                    .width(300.dp)    // or your desired width
//                    .fillMaxHeight(),
//            ) {
//                ProfileImageSurface(
//                    Modifier
//                        .padding(4.dp)
//                        .height(50.dp)
//                        .width(50.dp))
//                SideDrawerTitle(userType)
//                HorizontalDivider()
//                Spacer(modifier = Modifier.height(2.dp))
//                NavigationDrawerItem(
//                    label = { Text(text = "Profile Page") },
//                    selected = true,
//                    onClick = {
//                        navController.navigate(AppScreens.HomeScreen.name+"/$userType")
//                    }
//                )
//            }
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                SkoolioAppBar("Profile",
//                    navController= navController,
//                    elevation = 5.dp,
//                    icon = Icons.Filled.AccountCircle,
//                    sideDrawerToggle = {
//                        scope.launch {
//                            drawerState.apply {
//                                if (isClosed) open() else close()
//                            }
//                        }
//                    }
//                )
//            },
//        ){
//            HomeScreenMainContent(it, userType,
////            viewModelProvider
//            )
//        }
//    }
//
//}
//


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenMainContent(
    paddingValues: PaddingValues ,
    userType: String?
//    viewModelProvider: ViewModelProvider
) {

    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        ProfileImageSurface()
        if(userType!=null) {
            Text(text = capitalize(userType))
        }
        Spacer(modifier = Modifier.height(8.dp))
            if(userType == "Student"){
                StudentProfilePage()
            }
            else if(userType == "Teacher"){
                TeacherProfilePage()
            }
            else{
                AdminProfilePage()
            }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudentProfilePage() {
    DetailSection(@Composable {
        FormTitle(formTitle = "Basic Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileBasicDetails(
            studentDetails.firstName.value,
            studentDetails.middleName.value,
            studentDetails.lastName.value,
            studentDetails.nationality.value,
            studentDetails.dobValue.value,
            studentDetails.gender.value
        )
    }
    )
    DetailSection {
        FormTitle(formTitle = "School Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileSchoolDetails(studentDetails.studentId.value,studentDetails.schoolName.value, studentDetails.className.value)
    }
    
    DetailSection {
        FormTitle(formTitle = "Father Details")
        Spacer(modifier = Modifier.height(4.dp))
        GuardianDetails("Father", studentDetails.fatherName.value, studentDetails.fatherQualification.value, studentDetails.fatherOccupation.value)
    }
    DetailSection {
        FormTitle(formTitle = "Mother Details")
        Spacer(modifier = Modifier.height(4.dp))
        GuardianDetails("Mother", studentDetails.motherName.value, studentDetails.motherQualification.value, studentDetails.motherOccupation.value)
    }
    DetailSection {
        FormTitle(formTitle = "Address Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileAddressDetails(studentDetails.addressLine.value,
            studentDetails.city.value,
            studentDetails.state.value)
    }
    DetailSection {
        FormTitle(formTitle = "Contact Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileContactDetails(studentDetails.email.value,studentDetails.primaryContact.value, studentDetails.primaryContactName.value, studentDetails.primaryContactRelation.value,
            studentDetails.alternativeContact.value,studentDetails.alternativeContactName.value, studentDetails.alternativeContactRelation.value
            )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TeacherProfilePage() {
    DetailSection(@Composable {
        FormTitle(formTitle = "Basic Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileBasicDetails(
            teacherDetails.firstName.value,
            teacherDetails.middleName.value,
            teacherDetails.lastName.value,
            teacherDetails.nationality.value,
            teacherDetails.dobValue.value,
            teacherDetails.gender.value
        )
    }
    )
    DetailSection {
        FormTitle(formTitle = "School Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileSchoolDetails(teacherDetails.teacherId.value,
            teacherDetails.employingSchoolName.value,
            null)
    }
    DetailSection {
        FormTitle(formTitle = "Address Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileAddressDetails(teacherDetails.addressLine.value,
            teacherDetails.city.value,
            teacherDetails.state.value)
    }
    DetailSection {
        FormTitle(formTitle = "Contact Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileContactDetails(teacherDetails.email.value,teacherDetails.primaryContact.value, teacherDetails.primaryContactName.value, null,
            teacherDetails.alternativeContact.value,teacherDetails.alternativeContactName.value, null
        )
    }
}

@SuppressLint("NewApi")
@Composable
fun AdminProfilePage() {
    DetailSection(@Composable {
        FormTitle(formTitle = "Basic Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileBasicDetails(
            adminDetails.firstName.value,
            adminDetails.middleName.value,
            adminDetails.lastName.value,
            adminDetails.nationality.value,
            adminDetails.dobValue.value,
            adminDetails.gender.value
        )
    }
    )
    DetailSection {
        FormTitle(formTitle = "School Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileSchoolDetails(adminDetails.adminId.value,
            adminDetails.schoolName.value,
            null)
    }

    DetailSection {
        FormTitle(formTitle = "Address Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileAddressDetails(adminDetails.addressLine.value,
            adminDetails.city.value,
            adminDetails.state.value)
    }
    DetailSection {
        FormTitle(formTitle = "Contact Details")
        Spacer(modifier = Modifier.height(4.dp))
        ProfileContactDetails(adminDetails.email.value,adminDetails.primaryContact.value, adminDetails.primaryContactName.value, null,
            adminDetails.alternativeContact.value,adminDetails.alternativeContactName.value, null
        )
    }
}


