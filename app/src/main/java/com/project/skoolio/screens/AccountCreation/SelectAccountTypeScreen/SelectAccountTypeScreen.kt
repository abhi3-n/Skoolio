package com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.R
import com.project.skoolio.components.DropDownMenu
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.viewModels.ViewModelProvider

//@Preview
//@Composable
//fun select(){
//    SelectAccountTypeScreen()
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAccountTypeScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider
){
    val expanded = rememberSaveable {
        mutableStateOf(false)
    }
    val selectedAccountType = rememberSaveable {
        mutableStateOf("")
    }
    val accountTypeList = listOf("Student",
                                "Teacher",
                                "Admin")
    val img = rememberSaveable {
        mutableStateOf(0)
    }
    img.value = when(selectedAccountType.value){
        "Student"->R.drawable.students
        "Teacher"->R.drawable.teacher
        "Admin"->R.drawable.admin
        else-> 0
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        if(img.value != 0){
            Image(painter = painterResource(id = img.value ),
                contentDescription = null /*TODO:implement Image description*/)
        }
        Text(text = "Select Account Type")
        Spacer(modifier = Modifier.height(20.dp))
        DropDownMenu(expanded = expanded, selectedValue =  selectedAccountType, dataList =  accountTypeList)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            navController.navigate(AppScreens.RegistrationFormScreen.name+"/${selectedAccountType.value}")
        },
            enabled = if(selectedAccountType.value.isNotEmpty()) true else false
        ) {
            Text(text = "Next")
        }
    }
}
