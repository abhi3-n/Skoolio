package com.project.skoolio.screens.ForgotPasswordScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.skoolio.components.CircularProgressIndicatorCustom
import com.project.skoolio.components.CustomButton
import com.project.skoolio.components.CustomDropDownMenu
import com.project.skoolio.components.CustomTextField
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.UserType
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun ForgotPasswordScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val changePasswordViewModel = viewModelProvider.getChangePasswordViewModel()
    val context = LocalContext.current
    val email =  changePasswordViewModel.email
    val selectedType = changePasswordViewModel.selectedType
    val otpValidationViewModel = viewModelProvider.getOtpValidationViewModel()
    val progressIndicatorLoading = rememberSaveable { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(text = "Verify Email", style = MaterialTheme.typography.titleLarge)
        CustomTextField(valueState = email, label = "Enter Email")
        CustomDropDownMenu(selectedValue = selectedType,
            dataList = UserType.types,
            registrationScreenViewModel = null)
        CustomButton(enabled = selectedType.value.isNotEmpty() && email.value.isNotEmpty() && progressIndicatorLoading.value == false,
            onClick = {
                changePasswordViewModel.verifyEmailAndReceiveOTP(otpValidationViewModel, navController,
                    context, progressIndicatorLoading)
        }) {
            Text(text = "Send OTP")
        }
        if(progressIndicatorLoading.value){
            CircularProgressIndicatorCustom()
        }
    }
    if(otpValidationViewModel.getIsOtpValidated()){
        otpValidationViewModel.resetOtpValidated()
//        mailFieldEnabled.value = false
        if(selectedType.value == "Student") {
            navController.navigate(AppScreens.SetPasswordScreen.name + "/Student/change")
        }
        else if(selectedType.value == "Admin"){
            navController.navigate(AppScreens.SetPasswordScreen.name + "/Teacher/change")
        }else{
            navController.navigate(AppScreens.SetPasswordScreen.name + "/Admin/change")
        }
    }
}