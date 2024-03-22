package com.project.skoolio.screens.HomeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.skoolio.R
import com.project.skoolio.components.FormTitle
import com.project.skoolio.components.SkoolioAppBar
import com.project.skoolio.model.userDetailSingleton.studentDetails
import com.project.skoolio.utils.ExitApp
import com.project.skoolio.utils.calculateAge
import com.project.skoolio.viewModels.ViewModelProvider

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModelProvider: ViewModelProvider,
    userType: String?
){
    val context = LocalContext.current
    ExitApp(navController = navController, context = context)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MainScaffold(navController = navController, userType,
//            viewModelProvider
        )
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScaffold(
    navController: NavHostController,
    userType: String? ,
//    viewModelProvider: ViewModelProvider
) {
    Scaffold(
        topBar = {
            SkoolioAppBar("Profile",
                navController= navController,
                elevation = 5.dp,
            )
        },
    ){
        MainContent(it, userType,
//            viewModelProvider
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainContent(
    paddingValues: PaddingValues = PaddingValues(),
    userType: String? = "Student"
//    viewModelProvider: ViewModelProvider
) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .height(100.dp)
                .width(100.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = "User Image"
            )
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

@Composable
fun ProfileSchoolDetails(id: String, schoolName: String, className: String?) {
    DetailRow( "Skoolio Id:", id)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow( "School Name:", schoolName)
    Spacer(modifier = Modifier.height(4.dp))
    if(className!=null){
        DetailRow( "Class Name:", className)
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun ProfileContactDetails(email:String,primaryContact: String, primaryContactName: String, primaryContactRelation: String?,
                          alternativeContact: String, alternativeContactName: String, alternativeContactRelation: String?) {
    DetailRow(field = "Email", value = email)
    FormTitle(formTitle = "Primary Contact", style = MaterialTheme.typography.titleMedium)
    GeneralContactDetails(primaryContact, primaryContactName, primaryContactRelation)
    FormTitle(formTitle = "Alternate Contact", style = MaterialTheme.typography.titleMedium)
    GeneralContactDetails(alternativeContact, alternativeContactName, alternativeContactRelation)

}

@Composable
fun GeneralContactDetails(
    contact: String,
    contactName: String,
    contactRelation: String?
) {
    DetailRow( "No.:", contact)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Name:", contactName)
    Spacer(modifier = Modifier.height(4.dp))
    if(contactRelation!=null){
        DetailRow("Relation:", contactRelation)
        Spacer(modifier = Modifier.height(4.dp))
    }
}
@Composable
fun ProfileAddressDetails(addressLine: String, city: String, state: String) {
    DetailRow( "Address Line:", addressLine)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("City:", city)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("State:", state)
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun GuardianDetails(
    guardian: String,
    name: String,
    qualification: String,
    occupation: String) {
    DetailRow(guardian + "'s Name:", name)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow(guardian + "'s Qualification:", qualification)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow(guardian + "'s Occupation:", occupation)
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun DetailSection(details: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            details.invoke()
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileBasicDetails(
    firstName: String,
    middleName: String,
    lastName: String,
    nationality: String,
    dob: Long,
    gender: String
) {
    DetailRow("First Name:", firstName)
    Spacer(modifier = Modifier.height(4.dp))
    if(middleName.isNotEmpty()) {
        DetailRow("Middle Name:", middleName)
        Spacer(modifier = Modifier.height(4.dp))
    }
    DetailRow("Last Name:", lastName)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Age:", calculateAge(881289600000))
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Gender:", gender)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Nationality:", nationality)
    Spacer(modifier = Modifier.height(4.dp))

}

@Composable
fun DetailRow(field: String, value: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp, end = 8.dp),horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = field, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
        Text(text = value, style = TextStyle(fontSize = 20.sp))
    }
}

@Composable
fun AdminProfilePage() {

}

@Composable
fun TeacherProfilePage() {

}


