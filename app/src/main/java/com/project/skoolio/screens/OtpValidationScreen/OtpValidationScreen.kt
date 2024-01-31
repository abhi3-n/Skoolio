package com.project.skoolio.screens.OtpValidationScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.project.skoolio.components.CustomTextField
import com.project.skoolio.viewModels.OtpValidationViewModel
import com.project.skoolio.viewModels.RegistrationScreenViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun OtpValidationScreen(navController: NavHostController,
                        viewModelProvider: ViewModelProvider) {
    val otpValidationViewModel:OtpValidationViewModel = viewModelProvider.getOtpValidationViewModel()
    val userOtp = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Text(text = "Enter Otp", style = MaterialTheme.typography.titleMedium)
        CustomTextField(valueState = userOtp,
            label = "",
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done)
        TextButton(onClick = {
            if(userOtp.value != otpValidationViewModel.otpResponse.value.data.otp){
                Toast.makeText(context, "Otp does not match.",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Otp matches.",Toast.LENGTH_SHORT).show()
                //TODO: navigate to set password screen
            }
        }) {
            Text(text = "Validate OTP")
        }
    }
}