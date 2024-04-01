package com.project.skoolio.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.skoolio.R
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.model.singletonObject.teacherDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.calculateAge
import com.project.skoolio.utils.convertEpochToDateString


//@Preview
//@Composable
//fun prev()
//{
//    SkoolioAppBar()
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkoolioAppBar(
    pageTitle: String,
    icon: ImageVector,
    elevation: Dp = 0.dp,
    navController: NavHostController,
    sideDrawerToggle: () -> Unit
){
    val showDialog = remember {        // show dialog for MoreVert button
        mutableStateOf(false)
    }
    if(showDialog.value == true){
        ShowDropDownMenu(showDialog, navController)
    }

    val context = LocalContext.current

    TopAppBar(
        title = {
            Row {
                Column {
                    Spacer(modifier = Modifier.height(3.dp))
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                    )
                }

                Text(
                    text = pageTitle,
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.LightGray
        ),
        actions = {
            IconButton(onClick = { showDialog.value = !showDialog.value }) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null)
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                sideDrawerToggle.invoke()
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
            }           
        }
    )
}

@Composable
fun ShowDropDownMenu(showDialog: MutableState<Boolean> ,
                     navController: NavHostController
) {
    val doSignOut:()->Unit = {
            //Need to update a state that tells if user is logged in or not.
            //Also other data to be removed.
        navController.navigate(AppScreens.LoginScreen.name)
    }
    val optionsTask:(String)->Unit = {option->
        when (option) {
            "Sign Out" -> {
                doSignOut.invoke()
            }

            else -> navController.navigate(AppScreens.LoginScreen.name)
        }
    }

    var expanded by remember {
        mutableStateOf(true)
    }
    val listOfOptions = listOf("Sign Out")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.BottomEnd)
            .absolutePadding(top = 45.dp, right = 20.dp),
    ) {
        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
            showDialog.value = false
        }, modifier = Modifier
            .width(140.dp)
            .background(Color.White)) {
            listOfOptions.forEachIndexed { index, option ->
                DropdownMenuItem(text = {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()) {
                        Icon(imageVector = when(option){
                            "Sign Out"-> Icons.AutoMirrored.Filled.ExitToApp
                            else -> Icons.Default.Settings
                        }
                            , contentDescription = null)
                        Text(text = option,
                            Modifier
                                .fillMaxHeight()
                                .clickable {
                                    showDialog.value = false
                                    expanded = false
                                    optionsTask.invoke(option)
                                }, fontWeight = FontWeight.W300)
                    }
                }, onClick = {
                    showDialog.value = false
                    expanded = false
                })
            }
        }
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
    DetailRow(field = "Email", value = email, valueStyle = TextStyle(fontSize = 15.sp), modifier = Modifier.padding(top = 4.dp))
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
fun DetailSection(endSpacing:Dp = 8.dp,details: @Composable () -> Unit) {
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
    Spacer(modifier = Modifier.height(endSpacing))
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
    if(lastName != "-") {
        DetailRow("Last Name:", lastName)
        Spacer(modifier = Modifier.height(4.dp))
    }
    convertEpochToDateString(dob*1000)?.let { DetailRow("Date Of Birth:", it) }
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Age:", calculateAge(dob*1000))
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Gender:", gender)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Nationality:", nationality)
    Spacer(modifier = Modifier.height(4.dp))

}

@Composable
fun DetailRow(field: String,
              value: String,
              valueStyle: TextStyle= TextStyle(fontSize = 20.sp),
              modifier: Modifier = Modifier) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp, end = 8.dp),horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = field, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
        Text(text = value, style = valueStyle, modifier = modifier)
    }
}


@Composable
fun ImageSurface(
    modifier:Modifier = Modifier
        .padding(4.dp)
        .height(100.dp)
        .width(100.dp)
) {
    Surface(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_account_circle_24),
            contentDescription = "User Image"
        )
    }
}

@Composable
fun SideDrawerTitle(userType: String?) {
    if(userType == "Student"){
        Text("Welcome, ${studentDetails.firstName.value}", modifier = Modifier.padding(16.dp))
    }
    else if(userType == "Teacher"){
        Text("Welcome, ${teacherDetails.firstName.value}", modifier = Modifier.padding(16.dp))
    }
    else if(userType == "Admin"){
        Text("Welcome, ${adminDetails.firstName.value}", modifier = Modifier.padding(16.dp))
    }
}

