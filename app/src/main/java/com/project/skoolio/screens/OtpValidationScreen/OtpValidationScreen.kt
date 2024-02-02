package com.project.skoolio.screens.OtpValidationScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.project.skoolio.components.CustomButton
import com.project.skoolio.components.TextCustomTextField
import com.project.skoolio.viewModels.OtpValidationViewModel
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
        TextCustomTextField(
            text = "Enter OTP",
            valueState = userOtp,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done)
        CustomButton(onClick = {
            if(!otpValidationViewModel.validateOtp(userOtp.value)){
                Toast.makeText(context, "Invalid OTP.",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Otp Validation Successful.",Toast.LENGTH_SHORT).show()
                otpValidationViewModel.clearStoredOtp()
                //TODO: navigate to set password screen
                navController.popBackStack()
            }
        }) {
            Text(text = "Validate OTP")
        }
    }
}

