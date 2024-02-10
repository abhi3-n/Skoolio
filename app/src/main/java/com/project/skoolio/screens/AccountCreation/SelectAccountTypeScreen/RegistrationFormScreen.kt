package com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.project.skoolio.components.AddressComposable
import com.project.skoolio.components.CustomTextField
import com.project.skoolio.components.DOB
import com.project.skoolio.components.FormTitle
import com.project.skoolio.components.NameFields
import com.project.skoolio.components.NextButton
import com.project.skoolio.components.ShowToast
import com.project.skoolio.components.TextDropDownMenuRow
import com.project.skoolio.utils.StudentRules
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
            ShowStudentRegistrationForm(viewModelProvider, navController)
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
fun ShowStudentRegistrationForm(
    viewModelProvider: ViewModelProvider,
    navController: NavHostController
) {
    //Some states to be used. TODO: Use some sort of class here
    //Basic Details
    val studentFirstName = rememberSaveable {mutableStateOf("")}
    val studentMiddleName = rememberSaveable {mutableStateOf("")}
    val studentLastName = rememberSaveable {mutableStateOf("")}
    val dobState = rememberDatePickerState()
    val gender = rememberSaveable { mutableStateOf("") }
    val nationalitySelected = rememberSaveable { mutableStateOf("")}
    val admissionSchool = rememberSaveable { mutableStateOf("")}
    val admissionClass = rememberSaveable { mutableStateOf("")}
    val admissionSection = rememberSaveable { mutableStateOf("")}


    //Father Details
    val fatherName = rememberSaveable {mutableStateOf("")}
    val fatherQualification = rememberSaveable {mutableStateOf("")}
    val fatherOccupation = rememberSaveable {mutableStateOf("")}

    //Mother Details
    val motherName = rememberSaveable {mutableStateOf("")}
    val motherQualification = rememberSaveable {mutableStateOf("")}
    val motherOccupation = rememberSaveable {mutableStateOf("")}

    //Other Details
    val primaryContact = rememberSaveable {mutableStateOf("")}
    val primaryContactName = rememberSaveable {mutableStateOf("")}
    val primaryContactRelation = rememberSaveable {mutableStateOf("")}
    val alternativeContact = rememberSaveable {mutableStateOf("")}
    val alternativeContactName = rememberSaveable {mutableStateOf("")}
    val alternativeContactRelation = rememberSaveable {mutableStateOf("")}
    val email = rememberSaveable {mutableStateOf("")}
    val resAddress = rememberSaveable {mutableStateOf("")}
    val resCity = rememberSaveable {mutableStateOf("")}
    val resState = rememberSaveable {mutableStateOf("")}
    val MOT = rememberSaveable {mutableStateOf("")}
    
    //accept Rules
    val rulesAccepted = rememberSaveable { mutableStateOf(false)}

    //Form Starts here
    FormTitle("Student Registration Form")
    BasicDetails("Student",
        studentFirstName,
        studentMiddleName,
        studentLastName,
        gender,
        dobState,
        nationalitySelected,
        admissionSchool,
        admissionClass,
        admissionSection
    )
    FamilyDetails("Father", fatherName, fatherQualification, fatherOccupation)
    FamilyDetails("Mother", motherName, motherQualification, motherOccupation)
    OtherStudentDetails(primaryContact, primaryContactName, primaryContactRelation,
        alternativeContact, alternativeContactName, alternativeContactRelation, email,
        resAddress, resCity, resState,
        MOT)
    RulesDialog(StudentRules.rulesList, rulesAccepted)
    NextButton(viewModelProvider, email, navController)
}

@Composable
fun FamilyDetails(
    member: String,
    name: MutableState<String>,
    qualification: MutableState<String>,
    occupation: MutableState<String>,
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
        }
    }
}

@Composable
fun OtherStudentDetails(
    primaryContact: MutableState<String>,
    primaryContactName: MutableState<String>,
    primaryContactRelation: MutableState<String>,
    alternativeContact: MutableState<String>,
    alternativeContactName: MutableState<String>,
    alternativeContactRelation: MutableState<String>,
    email: MutableState<String>,
    resAddress: MutableState<String>,
    resCity: MutableState<String>,
    resState: MutableState<String>,
    MOT: MutableState<String>
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
            FormTitle("Other Details", style = MaterialTheme.typography.titleSmall)
            ContactDetails(primaryContact, primaryContactName, primaryContactRelation,
                alternativeContact, alternativeContactName, alternativeContactRelation,
                email)
            AddressComposable(resAddress, resCity, resState)
            CustomTextField(valueState = MOT, label = "Mode Of Transportation")
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RulesDialog(rulesList: List<String>, rulesAccepted: MutableState<Boolean>) {
    val showDialog = rememberSaveable { mutableStateOf(false)}
    val checked = rememberSaveable { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Row(horizontalArrangement = Arrangement.Start) {
            Checkbox(checked = checked.value, onCheckedChange = {
                checked.value = it
                rulesAccepted.value = it
            })
            Text(text = "I have read and accept the rules of the school.", Modifier.width(250.dp))
        }
        Icon(imageVector = Icons.TwoTone.Info,
            contentDescription = "School Rules Icon",
            Modifier
                .clickable { showDialog.value = true }
                .padding(top = 16.dp))
    }

    if(showDialog.value){
        AlertDialog(onDismissRequest = { showDialog.value = false },
            confirmButton = { Text(text = "Ok", Modifier.clickable { showDialog.value = false }) },
            title = {Text(text = "School Rules")},
            text = {
                Column(verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .height(300.dp)
                        .verticalScroll(rememberScrollState())) {
                    rulesList.forEachIndexed { index, rule ->
                        Text(text = "${index+1}.) $rule")
                    }
                }
            }
        )
    }
}


@Composable
fun ContactDetails(
    primaryContact: MutableState<String>,
    primaryContactName: MutableState<String>,
    primaryContactRelation: MutableState<String>,
    alternativeContact: MutableState<String>,
    alternativeContactName: MutableState<String>,
    alternativeContactRelation: MutableState<String>,
    email: MutableState<String>
) {
    val isChecked = rememberSaveable { mutableStateOf(false)}
    CustomTextField(valueState = primaryContact, label = "Contact Number*")
    CustomTextField(valueState = primaryContactName, label = "Contact Name*")
    TextDropDownMenuRow(text = "Relation*", dataList = listOf("Father", "Mother", "Other"), valueSelected = primaryContactRelation)
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(4.dp)) {
        Checkbox(checked = isChecked.value, onCheckedChange ={
            isChecked.value = it
        } )
        Text(text = "Alternative contact same as primary contact.", Modifier.clickable {
            isChecked.value = !isChecked.value
        })
    }
    CustomTextField(valueState = alternativeContact, label = "Alternative Contact Number*")
    CustomTextField(valueState = alternativeContactName, label = "Alternative Contact Name*")
    TextDropDownMenuRow(text = "Relation*", dataList = listOf("Father", "Mother", "Other"), valueSelected = alternativeContactRelation)

    if(isChecked.value){
        alternativeContact.value = primaryContact.value
        alternativeContactName.value = primaryContactName.value
        alternativeContactRelation.value = primaryContactRelation.value
    }else{
        alternativeContact.value = ""
        alternativeContactName.value = ""
        alternativeContactRelation.value = ""
    }
    CustomTextField(valueState = email, label = "Email")
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

//    val fatherName = rememberSaveable {mutableStateOf("")}
//    val fatherQualification = rememberSaveable {mutableStateOf("")}
//    val fatherOccupation = rememberSaveable {mutableStateOf("")}
//    val fatherContact = rememberSaveable {mutableStateOf("")}
//
//    val motherName = rememberSaveable {mutableStateOf("")}
//    val motherQualification = rememberSaveable {mutableStateOf("")}
//    val motherOccupation = rememberSaveable {mutableStateOf("")}
//    val motherContact = rememberSaveable {mutableStateOf("")}


    FormTitle("Teacher Registration Form")
//    BasicDetails(
//        "Teacher",
//        teacherFirstName,
//        teacherMiddleName,
//        teacherLastName,
//        gender,
//        dobState,
//        nationalitySelected,
//        admissionClass1 = admissionClass
//    )
//    FamilyDetails("Father",fatherName, fatherQualification, fatherOccupation, fatherContact)
//    FamilyDetails("Mother", motherName, motherQualification, motherOccupation, motherContact)
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
    admissionSchool: MutableState<String>,
    admissionClass: MutableState<String>,
    admissionSection: MutableState<String>
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
//                TextDropDownMenuRow(text = "School:",
//                    dataList = SchoolList.listOfSchools,
//                    valueSelected = admissionSchool)
                admissionSchool.value = "Innocent Heart Playway School" //Currently assuming for single school only
                admissionSection.value = "-"
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
