package com.project.skoolio.screens.AdminListScreen

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
import com.project.skoolio.model.userType.SchoolAdministrator
import com.project.skoolio.utils.formatName
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
        verticalArrangement = Arrangement.Top) {
        if(schoolInformationViewModel.adminList.value.data.isNotEmpty()){
            LazyColumn {
                items(schoolInformationViewModel.adminList.value.data){schoolAdministrator: SchoolAdministrator ->
                    val showMoreInfo = rememberSaveable { mutableStateOf(false) }
                    ListItem(shape = RectangleShape,
                        itemInfo = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {
                                Image(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = "${schoolAdministrator.firstName}'s Image",
                                    modifier = Modifier
                                        .size(90.dp)
                                        .padding(4.dp)
                                )
                                Text(text =  formatName(schoolAdministrator.firstName, schoolAdministrator.middleName, schoolAdministrator.lastName))
                                if(showMoreInfo.value == true){
                                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                                        Icon(imageVector = Icons.Default.Email, contentDescription = "${schoolAdministrator.firstName} Email Id")
                                        Text(text = schoolAdministrator.email)
                                    }
                                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                                        Icon(imageVector = Icons.Default.Call, contentDescription = "${schoolAdministrator.firstName} Contact")
                                        Text(text = schoolAdministrator.contactDetails.primaryContact)
                                    }
                                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                                        Icon(imageVector = Icons.Default.Call, contentDescription = "${schoolAdministrator.firstName} Contact")
                                        Text(text = schoolAdministrator.contactDetails.alternativeContact)
                                    }
                                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                                        Icon(imageVector = Icons.Default.Home, contentDescription = "${schoolAdministrator.firstName} Contact")
                                        Text(text = schoolAdministrator.addressDetails.getAddress(), style = TextStyle(textAlign = TextAlign.Center))
                                    }
                                }
                                IconButton(modifier = Modifier.align(Alignment.End),onClick = {
                                    showMoreInfo.value = !showMoreInfo.value
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = "${schoolAdministrator.firstName}'s Image",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        },
                        onClick = {
                            showMoreInfo.value = !showMoreInfo.value
                        }
                    )
                }
            }
        }
    }
}




