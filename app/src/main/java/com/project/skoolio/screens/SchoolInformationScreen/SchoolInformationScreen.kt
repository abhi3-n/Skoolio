package com.project.skoolio.screens.SchoolInformationScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CircularProgressIndicatorCustom
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.DetailSection
import com.project.skoolio.components.FormTitle
import com.project.skoolio.components.ImageSurface
import com.project.skoolio.components.ListItem
import com.project.skoolio.components.SchoolBasicDetails
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.model.singletonObject.schoolDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.BackToHomeScreen
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.viewModels.SchoolInformationViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun SchoolInformationScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
) {
    val context = LocalContext.current
    val schoolInformationViewModel = viewModelProvider.getSchoolInformationViewModel()
    BackToHomeScreen(navController, "Admin",context)
    DisposableEffect(key1 = Unit) {
        onDispose {
//            Toast.makeText(context,"School Info page recomposed", Toast.LENGTH_SHORT).show()
            schoolDetails.resetSchoolDetails()
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SchoolInformationContent(navController, schoolInformationViewModel, context)
    }
}

@Composable
fun SchoolInformationContent(
    navController: NavHostController,
    schoolInformationViewModel: SchoolInformationViewModel,
    context: Context
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CommonModalNavigationDrawer(drawerState,"Admin", getUserDrawerItemsList("Admin", navController),
        scaffold = {
            CommonScaffold(
                title = "School Info",
                icon = Icons.Filled.Info,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    SchoolInformationScreenMainContent(it, schoolInformationViewModel, context, navController)
                }
            )
        }
    )
}

@Composable
fun SchoolInformationScreenMainContent(
    paddingValues: PaddingValues,
    schoolInformationViewModel: SchoolInformationViewModel,
    context: Context,
    navController: NavHostController
) {
    if(schoolDetails.schoolName.value.isEmpty()
        && schoolInformationViewModel.schoolInfo.value.loading == false){
        refreshInformation(schoolInformationViewModel, context)
    }
    Column(modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = 6.dp)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        ImageSurface()
        Spacer(modifier = Modifier.height(8.dp))
        DetailSection(endSpacing = 1.dp, details =  @Composable{
            FormTitle(formTitle = "School Details")
            Spacer(modifier = Modifier.height(4.dp))
            if(schoolDetails.schoolName.value.isNotEmpty()){
                SchoolBasicDetails()
            }
            else{
                if(schoolInformationViewModel.schoolInfo.value.exception == null){
                    Toast.makeText(context,"Loading", Toast.LENGTH_SHORT).show()
                    CircularProgressIndicatorCustom()
                }
            }
        })
        TextButton(modifier = Modifier
            .align(Alignment.End)
            .padding(top = 1.dp), onClick = { refreshInformation(schoolInformationViewModel, context) }) {
            Text(text = "Refresh")
        }
        Spacer(modifier = Modifier.height(4.dp))
        ListItem(itemInfo = {
            SchoolInfoRowItem(navController, AppScreens.AdminListScreen.name, "List Of Admins")
        },
            onClick = {
                navController.navigate(AppScreens.AdminListScreen.name)
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        ListItem(itemInfo = {
            SchoolInfoRowItem(navController, AppScreens.TeacherListScreen.name, "List Of Teachers")
        },
            onClick = {
                navController.navigate(AppScreens.TeacherListScreen.name)
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        ListItem(itemInfo = {
            SchoolInfoRowItem(navController, AppScreens.ClassListScreen.name, "List Of Classes")
        },
            onClick = {
                navController.navigate(AppScreens.ClassListScreen.name)
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun SchoolInfoRowItem(navController: NavHostController, screen: String, field: String) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = field,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable {
                    navController.navigate(screen)
                },
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Go to",
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clickable {
                    navController.navigate(screen)
                })
    }
}



fun refreshInformation(schoolInformationViewModel: SchoolInformationViewModel, context: Context) {
    schoolDetails.resetSchoolDetails()
    schoolInformationViewModel.getSchoolInformation(adminDetails.schoolId.value, context)
}
