package com.project.skoolio.screens.LoginScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.skoolio.components.EmailTextField
import com.project.skoolio.components.ForgotPasswordText
import com.project.skoolio.components.PasswordTextField
import com.project.skoolio.components.SubmitButton
import com.project.skoolio.utils.ExitApp
import com.project.skoolio.viewModels.LoginViewModel
import com.project.skoolio.viewModels.ViewModelProvider

//@Preview
//@Composable
//fun Login(){
//    LoginScreen()
//}


@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    viewModelProvider: ViewModelProvider
) {
    val loginViewModel = viewModelProvider.getLoginViewModel()

    ExitApp(navController = navController, context = LocalContext.current)

    Surface(Modifier.fillMaxSize(), color = Color.White) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppLogoText()
            Spacer(modifier = Modifier.height(15.dp))
            UserLoginForm(false, loginViewModel){email,pwd->
            }
            ForgotPasswordText()
            NewAccountText()
        }
    }

}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserLoginForm(loading: Boolean,
                  loginViewModel: LoginViewModel,
                  onDone: (String, String) -> Unit
) {
    val context = LocalContext.current
    val showState = remember{ mutableStateOf(true)} //TODO:Remove later
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val valid = rememberSaveable(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier = Modifier
        .height(250.dp)
        .verticalScroll(rememberScrollState())

    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Login", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        EmailTextField(emailState = email,
            enabled = !loading,
            )
        PasswordTextField(passwordState = password,
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
                keyBoardController?.hide()
            })
        SubmitButton(
            validInputs = valid,
            loading = loading,
            onClick = {
                onDone(email.value.trim(), password.value.trim())
                keyBoardController?.hide()
                loginViewModel.hitBackend()
//                if(showState.value && loginViewModel.message.value.isNotEmpty()){
//                    Toast.makeText(context, loginViewModel.message.value, Toast.LENGTH_SHORT).show()
//                    showState.value = false
//                }
            }
        )
    }
}

@Composable
fun AppLogoText() {
    Text(
        text = "Innocent Heart Playway School",
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(40.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        fontFamily = FontFamily.Cursive,
        color = Color(0xFF008080),
        fontWeight = FontWeight.ExtraBold
    )
}
@Composable
fun NewAccountText() {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "New User?")
        Text(
            text = "Sign up",
            Modifier
                .clickable {
                    //TODO:Takes to Account Creation Setup
                }
                .padding(4.dp),
            color = Color.Cyan,
            fontWeight = FontWeight.Bold,
        )
    }
}

