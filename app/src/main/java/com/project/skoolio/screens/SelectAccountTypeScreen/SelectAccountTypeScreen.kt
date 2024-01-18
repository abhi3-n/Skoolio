package com.project.skoolio.screens.SelectAccountTypeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.viewModels.ViewModelProvider

//@Preview
//@Composable
//fun select(){
//    SelectAccountTypeScreen()
//}

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
        "Admin"
        )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "Select Account Type", Modifier.clickable { expanded.value = !expanded.value })
        Spacer(modifier = Modifier.height(20.dp))
        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            accountTypeList.forEach {accountType->
                DropdownMenuItem(text = { Text(text = accountType) },
                    onClick = {
                        expanded.value = false
                        selectedAccountType.value =  when(accountType){
                            "Student"-> "Student"
                            "Teacher" ->"Teacher"
                            else-> "Admin"
                        }
                    })
            }
        }
    }
}