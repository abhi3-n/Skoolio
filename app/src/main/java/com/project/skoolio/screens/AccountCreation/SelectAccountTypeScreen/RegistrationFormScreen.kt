package com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.R
import com.project.skoolio.components.CustomTextField
import com.project.skoolio.components.DOB
import com.project.skoolio.components.FormTitle
import com.project.skoolio.components.NameFields
import com.project.skoolio.components.ShowToast
import com.project.skoolio.components.TextDropDownMenuRow
import com.project.skoolio.viewModels.ViewModelProvider
//
//@Preview
//@Composable
//fun register(){
//    RegistrationFormScreen()
//}

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
    val gender = rememberSaveable { mutableStateOf("") }
    val nationalitySelected = rememberSaveable { mutableStateOf("")}
    val admissionClass = rememberSaveable { mutableStateOf("")}

    val fatherName = rememberSaveable {mutableStateOf("")}
    val fatherQualification = rememberSaveable {mutableStateOf("")}
    val fatherOccupation = rememberSaveable {mutableStateOf("")}
    val fatherContact = rememberSaveable {mutableStateOf("")}

    val motherName = rememberSaveable {mutableStateOf("")}
    val motherQualification = rememberSaveable {mutableStateOf("")}
    val motherOccupation = rememberSaveable {mutableStateOf("")}
    val motherContact = rememberSaveable {mutableStateOf("")}


    FormTitle("Student Registration Form")
    BasicDetails("Student",
        studentFirstName,
        studentMiddleName,
        studentLastName,
        gender,
        dobState,
        nationalitySelected,
        admissionClass
    )
    FamilyDetails("Father", fatherName, fatherQualification, fatherOccupation, fatherContact)
    FamilyDetails("Mother", motherName, motherQualification, motherOccupation, motherContact)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTeacherRegistrationForm() {
    val teacherFirstName = rememberSaveable {mutableStateOf("")}
    val teacherMiddleName = rememberSaveable {mutableStateOf("")}
    val teacherLastName = rememberSaveable {mutableStateOf("")}
    val dobState = rememberDatePickerState()
    val gender = rememberSaveable { mutableStateOf("") }
    val nationalitySelected = rememberSaveable { mutableStateOf("")}

    val fatherName = rememberSaveable {mutableStateOf("")}
    val fatherQualification = rememberSaveable {mutableStateOf("")}
    val fatherOccupation = rememberSaveable {mutableStateOf("")}
    val fatherContact = rememberSaveable {mutableStateOf("")}

    val motherName = rememberSaveable {mutableStateOf("")}
    val motherQualification = rememberSaveable {mutableStateOf("")}
    val motherOccupation = rememberSaveable {mutableStateOf("")}
    val motherContact = rememberSaveable {mutableStateOf("")}


    FormTitle("Teacher Registration Form")
    BasicDetails(
        "Teacher",
        teacherFirstName,
        teacherMiddleName,
        teacherLastName,
        gender,
        dobState,
        nationalitySelected
    )
    FamilyDetails("Father",fatherName, fatherQualification, fatherOccupation, fatherContact)
    FamilyDetails("Mother", motherName, motherQualification, motherOccupation, motherContact)
}


@Composable
fun FamilyDetails(
    member: String,
    name: MutableState<String>,
    qualification: MutableState<String>,
    occupation: MutableState<String>,
    contact: MutableState<String>
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FormTitle("$member Details", style = MaterialTheme.typography.titleSmall)
            CustomTextField(
                valueState = name,
                label = "Name*"
            )
            CustomTextField(
                valueState = qualification,
                label = "Qualification"
            )
            CustomTextField(
                valueState = occupation,
                label = "Occupation"
            )
            CustomTextField(
                valueState = contact,
                label = "Contact Number"
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDetails(
    userType: String,
    firstName: MutableState<String>,
    middleName: MutableState<String>,
    lastName: MutableState<String>,
    gender: MutableState<String>,
    dobState: DatePickerState,
    nationalitySelected: MutableState<String>,
    admissionClass: MutableState<String> = mutableStateOf("")
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FormTitle("Basic Details", style = MaterialTheme.typography.titleSmall)
            NameFields(firstName, middleName, lastName)
            DOB(dobState)
            TextDropDownMenuRow(text = "Gender",
                dataList = listOf("Male", "Female"),
                valueSelected = gender)
            TextDropDownMenuRow(
                "Nationality:",
                dataList = listOf("Indian", "Other"),
                valueSelected = nationalitySelected
            )
            if(userType == "Student"){
                TextDropDownMenuRow(
                    text = "Admission Class:",
                    dataList = listOf("Pre-Nursery", "Nursery"),
                    valueSelected = admissionClass
                )
            }
        }
    }
}

@Composable
fun ShowAdminRegistrationForm() {
    ShowToast(LocalContext.current,"Admin Registration Form")
    FormTitle("Admin Registration Form")

}
