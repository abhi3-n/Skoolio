package com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen

import android.content.Context
import android.widget.Toast
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
import com.project.skoolio.model.registerSingleton.registerStudent
import com.project.skoolio.model.registerSingleton.registerType
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
        registerStudent.studentFirstName,
        registerStudent.studentMiddleName,
        registerStudent.studentLastName,
        registerStudent.gender,
        dobState,
        registerStudent.nationalitySelected,
        registerStudent.admissionSchool,
        registerStudent.admissionClass
    )
    FamilyDetails("Father",
        registerStudent.fatherName,
        registerStudent.fatherQualification,
        registerStudent.fatherOccupation)
    FamilyDetails("Mother",
        registerStudent.motherName,
        registerStudent.motherQualification,
        registerStudent.motherOccupation)
    OtherStudentDetails(registerStudent.primaryContact, registerStudent.primaryContactName,
        registerStudent.primaryContactRelation, registerStudent.alternativeContact,
        registerStudent.alternativeContactName, registerStudent.alternativeContactRelation,
        registerStudent.email, registerStudent.resAddress,
        registerStudent.resCity, registerStudent.resState,
        registerStudent.MOT)
    RulesDialog(StudentRules.rulesList, registerStudent.rulesAccepted)
    NextButton(viewModelProvider, registerStudent, navController, dobState)
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
                label = "Qualification*"
            )
            CustomTextField(
                valueState = occupation,
                label = "Occupation*"
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
    CustomTextField(valueState = email, label = "Email*")
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


@OptIn(ExperimentalMaterial3Api::class)
fun validDetails(registerType: registerType,
                 context: Context,
                 dobState: DatePickerState): Boolean {
    //Create all checks here
    if(registerType is registerStudent) {
        val student = registerType
        if(!validStudentDetails(student, context, dobState)){
            return false
        }
    }
    return true
    //TODO: this function needs to be modified for better checking
}

@OptIn(ExperimentalMaterial3Api::class)
fun validStudentDetails(
    student: registerStudent,
    context: Context,
    dobState: DatePickerState
): Boolean {
    if(!validGeneralDetails(context, student.studentFirstName, student.studentLastName,
            dobState.selectedDateMillis, student.nationalitySelected, student.gender,
            student.email, student.resAddress, student.resCity, student.resState)){
        return false
    }
    if(student.admissionSchool.value.isEmpty()) {
        Toast.makeText(context, "Select School", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.admissionClass.value.isEmpty()) {
        Toast.makeText(context, "Select Class", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.fatherName.value.isEmpty()) {
        Toast.makeText(context, "Enter Father Name", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.fatherQualification.value.isEmpty()) {
        Toast.makeText(context, "Enter Father Qualification", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.fatherOccupation.value.isEmpty()) {
        Toast.makeText(context, "Enter Father Occupation", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.motherName.value.isEmpty()) {
        Toast.makeText(context, "Enter Mother Name", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.motherQualification.value.isEmpty()) {
        Toast.makeText(context, "Enter Mother Qualification", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.motherOccupation.value.isEmpty()) {
        Toast.makeText(context, "Enter Mother Occupation", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.primaryContact.value.isEmpty()) {
        Toast.makeText(context, "Enter Contact", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.primaryContactName.value.isEmpty()) {
        Toast.makeText(context, "Enter Contact Name", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.primaryContactRelation.value.isEmpty()) {
        Toast.makeText(context, "Select Contact Relation", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.alternativeContact.value.isEmpty()) {
        Toast.makeText(context, "Enter Alternative Contact", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.alternativeContactName.value.isEmpty()) {
        Toast.makeText(context, "Enter Alternative Contact Name", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.alternativeContactRelation.value.isEmpty()) {
        Toast.makeText(context, "Select Alternative Contact Relation", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.rulesAccepted.value == false){
        Toast.makeText(context, "Please accept rules of the school", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}


fun validGeneralDetails(
    context: Context,
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    dob: Long?,
    nationalitySelected: MutableState<String>,
    gender: MutableState<String>,
    email: MutableState<String>,
    resAddress: MutableState<String>,
    resCity: MutableState<String>,
    resState: MutableState<String>
): Boolean {

    if(firstName.value.isEmpty()) {
        Toast.makeText(context, "Enter First Name", Toast.LENGTH_SHORT).show()
        return false
    }
    if(lastName.value.isEmpty()) {
        Toast.makeText(context, "Enter Last Name", Toast.LENGTH_SHORT).show()
        return false
    }
    if(gender.value.isEmpty()) {
        Toast.makeText(context, "Select Gender", Toast.LENGTH_SHORT).show()
        return false
    }
    if(nationalitySelected.value.isEmpty()) {
        Toast.makeText(context, "Select Nationality", Toast.LENGTH_SHORT).show()
        return false
    }
    if(email.value.isEmpty()) {
        Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show()
        return false
    }
    if(resAddress.value.isEmpty()) {
        Toast.makeText(context, "Enter address", Toast.LENGTH_SHORT).show()
        return false
    }
    if(resCity.value.isEmpty()) {
        Toast.makeText(context, "Enter city", Toast.LENGTH_SHORT).show()
        return false
    }
    if(resState.value.isEmpty()) {
        Toast.makeText(context, "Enter State", Toast.LENGTH_SHORT).show()
        return false
    }
//    if() {
//        Toast.makeText(context, "Select Date of Birth", Toast.LENGTH_SHORT).show()
//        return false
//    }


    return true

}
