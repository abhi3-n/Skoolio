package com.project.skoolio.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.skoolio.model.singletonObject.schoolDetails
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.convertEpochToDateString

@Composable
fun SchoolBasicDetails() {

    DetailRow("Id:", schoolDetails.schoolId.value.toString())
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Name:", capitalize(schoolDetails.schoolName.value))
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Address:", capitalize(schoolDetails.schoolAddressLine.value))
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("City:", capitalize(schoolDetails.schoolCity.value))
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("State:", capitalize(schoolDetails.schoolState.value))
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Contact 1:", schoolDetails.schoolPrimaryContact.value)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Contact 2:", schoolDetails.schoolSecondaryContact.value)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Email:", schoolDetails.schoolEmail.value)
    Spacer(modifier = Modifier.height(4.dp))
    DetailRow("Registered On:", convertEpochToDateString(schoolDetails.schoolRegisteredDate.value * 1000).toString())
    Spacer(modifier = Modifier.height(4.dp))
}


@Composable
fun LazyRowListItem(value:String){
    Surface(color = Color.LightGray) {
        Column(modifier = Modifier.width(120.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Image", modifier = Modifier.size(70.dp))
            Text(text = value, style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center))
        }
    }
}