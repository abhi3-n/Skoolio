package com.project.skoolio.screens.SettingsScreen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.CustomTextField
import com.project.skoolio.model.Settings.UpdateAddressDetails
import com.project.skoolio.model.Settings.UpdateContactDetails
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.model.singletonObject.teacherDetails
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.SettingsViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun EditDetailsScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
    editType: String?
) {
    val settingsViewModel = viewModelProvider.getSettingsViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EditDetailsScreenContent(navController, editType, settingsViewModel)
    }
}

@Composable
fun EditDetailsScreenContent(
    navController: NavHostController,
    editType: String?,
    settingsViewModel: SettingsViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState,
        loginUserType.value, getUserDrawerItemsList(loginUserType.value, navController),
        scaffold = {
            CommonScaffold(
                title = "Settings",
                icon = Icons.Default.Settings,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                isSettingsScreen = true,
                mainContent = {
                    if(editType == "Contact") {
                        EditContactDetailsScreenMainContent(it, navController, settingsViewModel)
                    }
                    else if(editType == "Address"){
                        EditAddressDetailsScreenMainContent(it, navController, settingsViewModel)
                    }
                }
            )
        }
    )
}

@Composable
fun EditAddressDetailsScreenMainContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current
    val addressLine = when (loginUserType.value) {
        "Admin" -> {
            rememberSaveable { mutableStateOf(adminDetails.addressLine.value) }
        }
        "Teacher" -> {
            rememberSaveable { mutableStateOf(teacherDetails.addressLine.value)}
        }
        else -> {
            rememberSaveable { mutableStateOf(studentDetails.addressLine.value)}
        }
    }
    val city = when (loginUserType.value) {
        "Admin" -> {
            rememberSaveable { mutableStateOf(adminDetails.city.value)}
        }
        "Teacher" -> {
            rememberSaveable { mutableStateOf(teacherDetails.city.value)}
        }
        else -> {
            rememberSaveable { mutableStateOf(studentDetails.city.value)}
        }
    }
    val state = when (loginUserType.value) {
        "Admin" -> {
            rememberSaveable { mutableStateOf(adminDetails.state.value)}
        }
        "Teacher" -> {
            rememberSaveable { mutableStateOf(teacherDetails.state.value)}
        }
        else -> {
            rememberSaveable { mutableStateOf(studentDetails.state.value)}
        }
    }
    val editAddressLine = rememberSaveable { mutableStateOf(false) }
    val editCity = rememberSaveable { mutableStateOf(false) }
    val editState = rememberSaveable { mutableStateOf(false) }
    val trailingIcon: @Composable (String)->Unit = {addressComponent->
        IconButton(onClick = {
                        if(addressComponent == "Address Line"){
                            editAddressLine.value = true
                        }else if(addressComponent == "City"){
                            editCity.value = true
                        } else{
                            editState.value = true
                        }
        }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Student Id")
        }
    }
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        CustomTextField(valueState = addressLine,
            label = "Address Line",
            maxLines = 3, singleLine = false,
            enabled = editAddressLine.value,
            trailingIcon = { trailingIcon("Address Line") })
        CustomTextField(valueState = city,
            label = "City",
            maxLines = 2, singleLine = false,
            enabled = editCity.value,
            trailingIcon = { trailingIcon("City") }
        )
        CustomTextField(valueState = state,
            label = "State",
            maxLines = 2, singleLine = false,
            enabled = editState.value,
            trailingIcon = { trailingIcon("State") }
            )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                updateAddressInfomation(addressLine.value, city.value, state.value, settingsViewModel, context, navController)
            }, enabled = editAddressLine.value || editCity.value || editState.value) {
                Text(text = "Update")
            }
            Button(onClick = {
                navController.popBackStack()
            }) {
                Text(text = "Back")
            }
        }
    }
}

@Composable
fun EditContactDetailsScreenMainContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current
    val primaryContact = when (loginUserType.value) {"Admin" -> {rememberSaveable { mutableStateOf(adminDetails.primaryContact.value) } }
        "Teacher" -> {rememberSaveable { mutableStateOf(teacherDetails.primaryContact.value)} }
        else -> {rememberSaveable { mutableStateOf(studentDetails.primaryContact.value)} } }
    val alternativeContact = when (loginUserType.value) {"Admin" -> {rememberSaveable { mutableStateOf(adminDetails.alternativeContact.value) } }
        "Teacher" -> {rememberSaveable { mutableStateOf(teacherDetails.alternativeContact.value)} }
        else -> {rememberSaveable { mutableStateOf(studentDetails.alternativeContact.value)} } }
    val primaryContactName = when (loginUserType.value) {"Admin" -> {rememberSaveable { mutableStateOf(adminDetails.primaryContactName.value) } }
        "Teacher" -> {rememberSaveable { mutableStateOf(teacherDetails.primaryContactName.value)} }
        else -> {rememberSaveable { mutableStateOf(studentDetails.primaryContactName.value)} } }
    val alternativeContactName = when (loginUserType.value) {"Admin" -> {rememberSaveable { mutableStateOf(adminDetails.alternativeContactName.value) } }
        "Teacher" -> {rememberSaveable { mutableStateOf(teacherDetails.alternativeContactName.value)} }
        else -> {rememberSaveable { mutableStateOf(studentDetails.alternativeContactName.value)} } }

    val primaryContactRelation = rememberSaveable { mutableStateOf(studentDetails.primaryContactRelation.value) }
    val alternativeContactRelation = rememberSaveable { mutableStateOf(studentDetails.alternativeContactRelation.value) }

    val editPrimaryContact = rememberSaveable { mutableStateOf(false) }
    val editAlternativeContact = rememberSaveable { mutableStateOf(false) }
    val editPrimaryContactName = rememberSaveable { mutableStateOf(false) }
    val editAlternativeContactName = rememberSaveable { mutableStateOf(false) }
    val editPrimaryContactRelation = rememberSaveable { mutableStateOf(false) }
    val editAlternativeContactRelation = rememberSaveable { mutableStateOf(false) }

    val trailingIcon: @Composable (String)->Unit = {addressComponent->
        IconButton(onClick = {
            if(addressComponent == "Primary Contact"){
                editPrimaryContact.value = true
            }else if(addressComponent == "Primary Contact Name"){
                editPrimaryContactName.value = true
            }else if(addressComponent == "Alternative Contact"){
                editAlternativeContact.value = true
            }else if(addressComponent == "Alternative Contact Name"){
                editAlternativeContactName.value = true
            }else if(addressComponent == "Primary Contact Relation"){
                editPrimaryContactRelation.value = true
            }else{
                editAlternativeContactRelation.value = true
            }
        }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Student Id")
        }
    }

    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        CustomTextField(valueState = primaryContact,
            label = "Primary Contact",
            enabled = editPrimaryContact.value,
            trailingIcon = { trailingIcon("Primary Contact") })
        CustomTextField(valueState = primaryContactName,
            label = "Primary Contact Name",
            enabled = editPrimaryContactName.value,
            trailingIcon = { trailingIcon("Primary Contact Name") })
        if(loginUserType.value == "Student"){
            CustomTextField(valueState = primaryContactRelation,
                label = "Primary Contact Relation",
                enabled = editPrimaryContactRelation.value,
                trailingIcon = { trailingIcon("Primary Contact Relation") })
        }
        CustomTextField(valueState = alternativeContact,
            label = "Alternative Contact",
            enabled = editAlternativeContact.value,
            trailingIcon = { trailingIcon("Alternative Contact") })
        CustomTextField(valueState = alternativeContactName,
            label = "Alternative Contact Name",
            enabled = editAlternativeContactName.value,
            trailingIcon = { trailingIcon("Alternative Contact Name") })
        if(loginUserType.value == "Student"){
            CustomTextField(valueState = alternativeContactRelation,
                label = "Alternative Contact Relation",
                enabled = editAlternativeContactRelation.value,
                trailingIcon = { trailingIcon("Alternative Contact Relation") })
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                updateContactInfomation(primaryContact.value, primaryContactName.value, primaryContactRelation.value,
                    alternativeContact.value, alternativeContactName.value, alternativeContactRelation.value,
                    settingsViewModel, context, navController)
            }, enabled = editPrimaryContact.value || editPrimaryContactName.value || editPrimaryContactRelation.value
                    || editAlternativeContact.value || editAlternativeContactName.value || editAlternativeContactRelation.value) {
                Text(text = "Update")
            }
            Button(onClick = {
                navController.popBackStack()
            }) {
                Text(text = "Back")
            }
        }
    }
}

fun updateContactInfomation(
    primaryContact: String,
    primaryContactName: String,
    primaryContactRelation: String,
    alternativeContact: String,
    alternativeContactName: String,
    alternativeContactRelation: String,
    settingsViewModel: SettingsViewModel,
    context: Context,
    navController: NavHostController
) {
    if(loginUserType.value == "Student" &&
        (studentDetails.primaryContact.value  != primaryContact || studentDetails.primaryContactName.value != primaryContactName || studentDetails.primaryContactRelation.value != primaryContactRelation
                || studentDetails.alternativeContact.value  != alternativeContact || studentDetails.alternativeContactName.value != alternativeContactName || studentDetails.alternativeContactRelation.value != alternativeContactRelation)
    ){
        settingsViewModel.updateContactDetails(UpdateContactDetails(primaryContact, primaryContactName, primaryContactRelation,
            alternativeContact, alternativeContactName, alternativeContactRelation ,studentDetails.studentId.value), context, navController)
    }else if(loginUserType.value == "Teacher" &&
        (teacherDetails.primaryContact.value  != primaryContact || teacherDetails.primaryContactName.value != primaryContactName
                || teacherDetails.alternativeContact.value  != alternativeContact || teacherDetails.alternativeContactName.value != alternativeContactName)
    ){
        settingsViewModel.updateContactDetails(UpdateContactDetails(primaryContact, primaryContactName, null,
            alternativeContact, alternativeContactName, null ,teacherDetails.teacherId.value), context, navController)
    }
    else if(loginUserType.value == "Admin" &&
        (adminDetails.primaryContact.value  != primaryContact || adminDetails.primaryContactName.value != primaryContactName
                || adminDetails.alternativeContact.value  != alternativeContact || adminDetails.alternativeContactName.value != alternativeContactName)
    ){
        settingsViewModel.updateContactDetails(UpdateContactDetails(primaryContact, primaryContactName, null,
            alternativeContact, alternativeContactName, null ,adminDetails.adminId.value), context, navController)
    }

}

fun updateAddressInfomation(
    addressLine: String,
    city: String,
    state: String,
    settingsViewModel: SettingsViewModel,
    context: Context,
    navController: NavHostController
) {
    if(loginUserType.value == "Student" &&
        (studentDetails.addressLine.value  != addressLine || studentDetails.city.value != city || studentDetails.state.value != state)
    ){
        settingsViewModel.updateAddressDetails(UpdateAddressDetails(addressLine,city,state, studentDetails.studentId.value), context, navController)
    }else if(loginUserType.value == "Teacher" &&
        (teacherDetails.addressLine.value  != addressLine || teacherDetails.city.value != city || teacherDetails.state.value != state)
    ){
        settingsViewModel.updateAddressDetails(UpdateAddressDetails(addressLine,city,state, teacherDetails.teacherId.value), context, navController)
    }
    else if(loginUserType.value == "Admin" &&
        (adminDetails.addressLine.value  != addressLine || adminDetails.city.value != city || adminDetails.state.value != state)
    ){
        settingsViewModel.updateAddressDetails(UpdateAddressDetails(addressLine,city,state, adminDetails.adminId.value), context, navController)
    }
}
