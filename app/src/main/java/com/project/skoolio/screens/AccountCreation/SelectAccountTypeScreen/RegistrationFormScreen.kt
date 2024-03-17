package com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.project.skoolio.components.BasicDetails
import com.project.skoolio.components.CustomTextField
import com.project.skoolio.components.FormTitle
import com.project.skoolio.components.OtherDetails
import com.project.skoolio.components.RegisterButton
import com.project.skoolio.components.RulesDialog
import com.project.skoolio.components.SaveButton
import com.project.skoolio.components.SchoolDetails
import com.project.skoolio.components.ShowToast
import com.project.skoolio.model.registerSingleton.registerStudent
import com.project.skoolio.model.registerSingleton.registerTeacher
import com.project.skoolio.model.registerSingleton.registerType
import com.project.skoolio.utils.StudentRules
import com.project.skoolio.utils.TeacherRules
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
    Log.d("Next Button Clicked","6")
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
            ShowTeacherRegistrationForm(viewModelProvider, navController)
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
    Log.d("Next Button Clicked","7")

    val context = LocalContext.current
    val activity =  context as? Activity
    androidx.activity.compose.BackHandler(enabled = true) {
        registerStudent.resetPassword()
        navController.popBackStack()
    }

    val registrationScreenViewModel = viewModelProvider.getRegistrationScreenViewModel()

    registerStudent.dobState = rememberDatePickerState()
    val mailFieldEnabled = rememberSaveable { mutableStateOf(true) }
    Log.d("Next Button Clicked","8")

    //Form Starts here
    FormTitle("Student Registration Form")
    BasicDetails("Student",
        registerStudent.studentFirstName,
        registerStudent.studentMiddleName,
        registerStudent.studentLastName,
        registerStudent.gender,
        registerStudent.dobState!!,
        registerStudent.nationalitySelected
    )
    SchoolDetails("Student",
        registerStudent.admissionSchoolName,
        registerStudent.admissionClassName);
    FamilyDetails("Father",
        registerStudent.fatherName,
        registerStudent.fatherQualification,
        registerStudent.fatherOccupation)
    FamilyDetails("Mother",
        registerStudent.motherName,
        registerStudent.motherQualification,
        registerStudent.motherOccupation)
    OtherDetails(registerStudent.primaryContact, registerStudent.primaryContactName,
        registerStudent.primaryContactRelation, registerStudent.alternativeContact,
        registerStudent.alternativeContactName, registerStudent.alternativeContactRelation,
        registerStudent.email, registerStudent.addressLine,
        registerStudent.city, registerStudent.state,
        registerStudent.MOT, mailFieldEnabled)
    RulesDialog(StudentRules.rulesList, registerStudent.rulesAccepted)
    if(registerStudent.password.value.isEmpty()) {
        SaveButton(viewModelProvider, registerStudent, navController, mailFieldEnabled)
    }
    else{
        RegisterButton(registerStudent,registrationScreenViewModel, navController)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTeacherRegistrationForm(
    viewModelProvider: ViewModelProvider,
    navController: NavHostController
) {
    registerTeacher.dobState = rememberDatePickerState()
    val mailFieldEnabled = rememberSaveable { mutableStateOf(true) }
    val registrationScreenViewModel = viewModelProvider.getRegistrationScreenViewModel()

    val context = LocalContext.current
    val activity =  context as? Activity
    androidx.activity.compose.BackHandler(enabled = true) {
        registerTeacher.password.value = ""
        navController.popBackStack()
    }

    FormTitle("Teacher Registration Form")
    BasicDetails(
        "Teacher",
        registerTeacher.teachertFirstName,
        registerTeacher.teacherMiddleName,
        registerTeacher.teacherLastName,
        registerTeacher.gender,
        registerTeacher.dobState!!,
        registerTeacher.nationalitySelected,
    )
    SchoolDetails(userType = "Teacher",
        schoolName = registerTeacher.employingSchoolName,
        admissionClass = null)
    PreviousEmploymentDetails(
        registerTeacher.previousEmployerName,
        registerTeacher.previousEmploymentDuration,
        registerTeacher.jobTitle
    )
    OtherDetails(registerTeacher.primaryContact, registerTeacher.primaryContactName,
        null, registerTeacher.alternativeContact,
        registerTeacher.alternativeContactName, null,
        registerTeacher.email, registerTeacher.resAddress,
        registerTeacher.resCity, registerTeacher.resState,
        null, mailFieldEnabled)
    RulesDialog(TeacherRules.rulesList, registerTeacher.rulesAccepted)
    if(registerTeacher.password.value.isEmpty()) {
        SaveButton(viewModelProvider, registerTeacher, navController, mailFieldEnabled)
    }
    else{
        RegisterButton(registerTeacher, registrationScreenViewModel, navController)
    }

}

@Composable
fun PreviousEmploymentDetails(
    previousEmployerName: MutableState<String>,
    previousEmploymentDuration: MutableState<String>,
    jobTitle: MutableState<String>
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
            FormTitle("Previous Employment Details", style = MaterialTheme.typography.titleSmall)
            CustomTextField(
                valueState = previousEmployerName,
                label = "Employer Name"
            )
            CustomTextField(
                valueState = previousEmploymentDuration,
                label = "Employment Duration(yrs)"
            )
            CustomTextField(
                valueState = jobTitle,
                label = "Job Title"
            )
        }
    }
}


@Composable
fun FamilyDetails(
    member: String,
    name: MutableState<String>,
    qualification: MutableState<String>,
    occupation: MutableState<String>,
) {
    Log.d("Next Button Clicked","12 $member")
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
fun ShowAdminRegistrationForm() {
    ShowToast(LocalContext.current,"Admin Registration Form")
    FormTitle("Admin Registration Form")
}


@OptIn(ExperimentalMaterial3Api::class)
fun validDetails(registerType: registerType,
                 context: Context,
//                 dobState: DatePickerState
): Boolean {
    //Create all checks here
    if(registerType is registerStudent) {
        val student = registerType
        if(!validStudentDetails(student, context)){
            return false
        }
    }
    else if (registerType is registerTeacher){
        val teacher = registerType
        if(!validTeacherDetails(teacher, context)){
            return false
        }
    }
    return true
    //TODO: this function needs to be modified for better checking
}

@OptIn(ExperimentalMaterial3Api::class)
fun validTeacherDetails(teacher: registerTeacher, context: Context): Boolean {
    if(!validGeneralDetails(context,teacher.teachertFirstName,teacher.teacherLastName,
            teacher.dobState?.selectedDateMillis,teacher.nationalitySelected,teacher.gender,
            teacher.email,
            teacher.resAddress, teacher.resCity, teacher.resState,
            teacher.primaryContact, teacher.primaryContactName, null,
            teacher.alternativeContact, teacher.alternativeContactName, null,
            teacher.rulesAccepted
        )){
        return false
    }
    return true
}

@OptIn(ExperimentalMaterial3Api::class)
fun validStudentDetails(
    student: registerStudent,
    context: Context,
//    dobState: DatePickerState
): Boolean {
    if(!validGeneralDetails(context, student.studentFirstName, student.studentLastName,
            student.dobState?.selectedDateMillis, student.nationalitySelected, student.gender,
            student.email, student.addressLine, student.city, student.state,
            student.primaryContact, student.primaryContactName, student.primaryContactRelation,
            student.alternativeContact, student.alternativeContactName, student.alternativeContactRelation,
            student.rulesAccepted)){
        return false
    }
    if(student.admissionSchoolName.value.isEmpty()) {
        Toast.makeText(context, "Select School", Toast.LENGTH_SHORT).show()
        return false
    }
    if(student.admissionClassId.value.isEmpty()) {
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
    resState: MutableState<String>,
    primaryContact: MutableState<String>,
    primaryContactName: MutableState<String>,
    primaryContactRelation: MutableState<String>?,
    alternativeContact: MutableState<String>,
    alternativeContactName: MutableState<String>,
    alternativeContactRelation: MutableState<String>?,
    rulesAccepted: MutableState<Boolean>
): Boolean {

    if(firstName.value.isEmpty()) {
        Toast.makeText(context, "Enter First Name", Toast.LENGTH_SHORT).show()
        return false
    }
    if(lastName.value.isEmpty()) {
        Toast.makeText(context, "Enter Last Name", Toast.LENGTH_SHORT).show()
        return false
    }
    if(dob == null) {
        Toast.makeText(context, "Select Date of Birth", Toast.LENGTH_SHORT).show()
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
    if(primaryContact.value.isEmpty()) {
        Toast.makeText(context, "Enter Contact", Toast.LENGTH_SHORT).show()
        return false
    }
    if(primaryContactName.value.isEmpty()) {
        Toast.makeText(context, "Enter Contact Name", Toast.LENGTH_SHORT).show()
        return false
    }
    if(primaryContactRelation?.value?.isEmpty() == true) {
        Toast.makeText(context, "Select Contact Relation", Toast.LENGTH_SHORT).show()
        return false
    }
    if(alternativeContact.value.isEmpty()) {
        Toast.makeText(context, "Enter Alternative Contact", Toast.LENGTH_SHORT).show()
        return false
    }
    if(alternativeContactName.value.isEmpty()) {
        Toast.makeText(context, "Enter Alternative Contact Name", Toast.LENGTH_SHORT).show()
        return false
    }
    if(alternativeContactRelation?.value?.isEmpty() == true) {
        Toast.makeText(context, "Select Alternative Contact Relation", Toast.LENGTH_SHORT).show()
        return false
    }
    if(rulesAccepted.value == false){
        Toast.makeText(context, "Please accept rules of the school", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}
