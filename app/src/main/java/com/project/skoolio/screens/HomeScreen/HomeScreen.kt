package com.project.skoolio.screens.HomeScreen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.skoolio.R
import com.project.skoolio.components.FormTitle
import com.project.skoolio.components.SkoolioAppBar
import com.project.skoolio.model.userDetailSingleton.studentDetails
import com.project.skoolio.utils.ExitApp
import com.project.skoolio.viewModels.ViewModelProvider

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



@Composable
fun MainScaffold(
    navController: NavHostController = rememberNavController(),
    userType: String? = "Student",
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
@Preview
@Composable
fun prev()
{
    MainScaffold()
}
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
//        Text(text = "Hi $userType")
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .height(100.dp)
                .width(100.dp),
//            border = BorderStroke(width = 2.dp, color = Color.LightGray)
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

@Composable
fun StudentProfilePage() {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
            border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            FormTitle(formTitle = "Basic Details")
            Spacer(modifier = Modifier.height(4.dp))
            ProfileBasicDetails(studentDetails.studentFirstName.value,
                studentDetails.studentMiddleName.value,
                studentDetails.studentLastName.value)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            FormTitle(formTitle = "Basic Details")
            Spacer(modifier = Modifier.height(4.dp))
            ProfileBasicDetails(studentDetails.studentFirstName.value,
                studentDetails.studentMiddleName.value,
                studentDetails.studentLastName.value)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            FormTitle(formTitle = "Basic Details")
            Spacer(modifier = Modifier.height(4.dp))
            ProfileBasicDetails(studentDetails.studentFirstName.value,
                studentDetails.studentMiddleName.value,
                studentDetails.studentLastName.value)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            FormTitle(formTitle = "Basic Details")
            Spacer(modifier = Modifier.height(4.dp))
            ProfileBasicDetails(studentDetails.studentFirstName.value,
                studentDetails.studentMiddleName.value,
                studentDetails.studentLastName.value)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            FormTitle(formTitle = "Basic Details")
            Spacer(modifier = Modifier.height(4.dp))
            ProfileBasicDetails(studentDetails.studentFirstName.value,
                studentDetails.studentMiddleName.value,
                studentDetails.studentLastName.value)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            FormTitle(formTitle = "Basic Details")
            Spacer(modifier = Modifier.height(4.dp))
            ProfileBasicDetails(studentDetails.studentFirstName.value,
                studentDetails.studentMiddleName.value,
                studentDetails.studentLastName.value)
        }
    }
}

@Composable
fun ProfileBasicDetails(
    firstName: String,
    middleName: String,
    lastName: String
) {
    DetailRow("First Name:", firstName)
    Spacer(modifier = Modifier.height(4.dp))
    if(middleName.isNotEmpty()) {
        DetailRow("Middle Name:", middleName)
        Spacer(modifier = Modifier.height(4.dp))
    }
    DetailRow("Last Name:", lastName)
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun DetailRow(field: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),horizontalArrangement = Arrangement.SpaceBetween) {
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


