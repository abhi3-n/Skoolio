package com.project.skoolio.screens.SetPasswordScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CustomButton
import com.project.skoolio.components.TextCustomTextField
import com.project.skoolio.model.registerSingleton.registerStudent
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun SetPasswordScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        val password = rememberSaveable { mutableStateOf("") }
        val confirmPassword = rememberSaveable { mutableStateOf("") }
        val context = LocalContext.current
        TextCustomTextField(text = "Password",
            valueState = password,
            imeAction = ImeAction.Next)
        Spacer(modifier = Modifier.height(4.dp))
        TextCustomTextField(text = "Confirm Password",
            valueState = confirmPassword,
            imeAction = ImeAction.Done)
        CustomButton(onClick = {
            if(validPassword(password, confirmPassword, context)){
                registerStudent.password.value = password.value
                navController.popBackStack()
            }
        }) {
            Text(text = "Save Password")
        }
    }
}


fun validPassword(
    password: MutableState<String>,
    confirmPassword: MutableState<String>,
    context: Context
): Boolean {
    if(password.value.isEmpty() || confirmPassword.value.isEmpty()){
        Toast.makeText(context,"Password field cannot be empty", Toast.LENGTH_SHORT).show()
        return false
    }
    if(password.value != confirmPassword.value){
        Toast.makeText(context,"Entries don't match.", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}