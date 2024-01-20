package com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.R
import com.project.skoolio.components.DOB
import com.project.skoolio.components.DropDownMenu
import com.project.skoolio.components.FormTitle
import com.project.skoolio.components.NameFields
import com.project.skoolio.components.ShowToast
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun RegistrationFormScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
    userType: String?
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        ) {
        if(userType == "Student"){
            Image(painter = painterResource(id = R.drawable.students),
                contentDescription = "Student")
            ShowStudentRegistrationForm()
        }
        else if(userType == "Teacher"){
            Image(painter = painterResource(id = R.drawable.teacher),
                contentDescription = "Teacher")
            ShowTeacherRegistrationForm()
        }
        else{
            Image(painter = painterResource(id = R.drawable.admin),
                contentDescription = "Admin")
            ShowAdminRegistrationForm()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowStudentRegistrationForm() {
    val studentFirstName = rememberSaveable {mutableStateOf("")}
    val studentMiddleName = rememberSaveable {mutableStateOf("")}
    val studentLastName = rememberSaveable {mutableStateOf("")}
    val dobState = rememberDatePickerState()

    FormTitle("Student Registration Form")
    NameFields(studentFirstName, studentMiddleName, studentLastName)
    DOB(dobState, LocalContext.current)
    NationalityRow()
}


@Composable
fun NationalityRow() {
    val nationList = listOf("Indian", "Other")
    val nationalitySelected = rememberSaveable { mutableStateOf("")}
    val expanded = rememberSaveable { mutableStateOf(false)}

    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Nationality:")
        DropDownMenu(modifier = Modifier.width(150.dp),expanded = expanded, selectedValue = nationalitySelected, dataList = nationList)
    }
}

@Composable
fun ShowTeacherRegistrationForm() {
    ShowToast(LocalContext.current,"Teacher Registration Form")
    FormTitle("Teacher Registration Form")

}

@Composable
fun ShowAdminRegistrationForm() {
    ShowToast(LocalContext.current,"Admin Registration Form")
    FormTitle("Admin Registration Form")

}
