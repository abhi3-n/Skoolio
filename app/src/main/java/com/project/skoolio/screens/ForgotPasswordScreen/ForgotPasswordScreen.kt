package com.project.skoolio.screens.ForgotPasswordScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.project.skoolio.components.CustomButton
import com.project.skoolio.components.CustomDropDownMenu
import com.project.skoolio.components.CustomTextField
import com.project.skoolio.utils.UserType
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun ForgotPasswordScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val email = rememberSaveable { mutableStateOf("") }
    val selectedType = rememberSaveable { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        CustomTextField(valueState = email, label = "Enter Email")
        CustomDropDownMenu(selectedValue = selectedType,
            dataList = UserType.types,
            registrationScreenViewModel = null)
        CustomButton(onClick = {

        }) {

        }
    }
}