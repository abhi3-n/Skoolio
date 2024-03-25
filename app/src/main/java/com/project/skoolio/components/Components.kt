package com.project.skoolio.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.skoolio.model.EmailOtpRequest
import com.project.skoolio.model.userDetailSingleton.studentDetails
import com.project.skoolio.model.userDetailSingleton.teacherDetails
import com.project.skoolio.model.userDetailSingleton.userDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen.validDetails
import com.project.skoolio.utils.BackToLoginScreen
import com.project.skoolio.utils.SchoolList
import com.project.skoolio.utils.UserType
import com.project.skoolio.utils.convertEpochToDateString
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.utils.statesList
import com.project.skoolio.viewModels.LoginViewModel
import com.project.skoolio.viewModels.OtpValidationViewModel
import com.project.skoolio.viewModels.RegistrationScreenViewModel
import com.project.skoolio.viewModels.ViewModelProvider


@Composable
fun CustomTextField(modifier: Modifier = Modifier,
                   valueState: MutableState<String>,
                    label:String,
                    enabled: Boolean = true,
                    keyboardType: KeyboardType = KeyboardType.Text,
                   imeAction: ImeAction = ImeAction.Next,
                   keyboardActions: KeyboardActions = KeyboardActions.Default
                   ) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        enabled = enabled,
        singleLine = true,
        label = { Text(text = label) },
        textStyle = TextStyle(fontSize = 18.sp,color = Color.Black),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = keyboardActions
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    passwordState: MutableState<String>,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    onAction: KeyboardActions
) {
    val visualTransformation = if(passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = "Password") },
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = onAction,
        trailingIcon = {PasswordVisibility(passwordVisibility = passwordVisibility)}
    )
}
@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible }) {
        Icons.Default.Close
    }
}


@Composable
fun LoginButton(validInputs: Boolean,
                loading: MutableState<Boolean>,
                onClick:()->Unit = {}) {
    Button(onClick = { onClick.invoke() },
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),enabled = !loading.value && validInputs,
        shape = CircleShape
    ) {
        if(loading.value) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = "Login", Modifier.padding(5.dp))

    }
}
@Composable
fun ForgotPasswordText() {
    Text(
        text = "Forgot Password",
        color = Color(0xFF001F3F),
        modifier = Modifier.clickable {
            //TODO: Implement forgot password functionality
        },
        style = TextStyle(textDecoration = TextDecoration.Underline)
    )
}




@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    selectedValue: MutableState<String>,
    dataList: List<String>?,
    registrationScreenViewModel: RegistrationScreenViewModel?
) {
    val expanded = rememberSaveable { mutableStateOf(false)}
    val context = LocalContext.current
    ExposedDropdownMenuBox(modifier = modifier
        ,expanded = expanded.value, onExpandedChange = {
        expanded.value = it
    }) {
        //TODO:DropDownMenu should close when clicked on textfield
        OutlinedTextField(value = selectedValue.value,
            modifier = Modifier.menuAnchor(),
            onValueChange = {},
            readOnly = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) })
        ExposedDropdownMenu(expanded = expanded.value,
            onDismissRequest = { expanded.value = false }) {
            dataList!!.forEach { item ->
                DropdownMenuItem(text = {
                    Text(text = item, color = Color.Black)
                }, onClick = {
                    dataList.forEach{
                        if(item == it){
                            selectedValue.value = item
                            if(registrationScreenViewModel!=null) {
                                val schoolId = SchoolList.getSchoolIdForSchoolName(item)
                                //fetch all class names of this school
                                if (!SchoolList.IsClassListPopulatedForSchoolId(schoolId)) //registrationScreenViewModel != null is for school name selection only
                                {
                                    registrationScreenViewModel.getClassNameListForSchool(
                                        schoolId,
                                        context
                                    )
                                }
                            }
                        }
                    }
                    expanded.value = false
                })
            }
        }
    }
}

@Composable
fun ShowToast(context: Context, message: String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DOB(dobState: DatePickerState) {
    val openDialog = rememberSaveable {
        mutableStateOf(false)
    }
    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "DOB", modifier = Modifier.padding(top = 13.dp, start = 8.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            if(!dobState.selectedDateMillis.toString().isNullOrEmpty()){
                convertEpochToDateString(dobState.selectedDateMillis)?.let { Text(text = it, modifier = Modifier.padding(top = 13.dp)) }
            }
            if(dobState.selectedDateMillis == null){
                IconButton(onClick = {
                    openDialog.value = true
                }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date of Birth")
                }
            }
            else{
                IconButton(onClick = {
                    dobState.selectedDateMillis = null
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Clear Date of Birth")
                }
            }
        }
    }
    DatePickerCustom(dobState = dobState, openDialog = openDialog)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerCustom(
    dobState: DatePickerState,
    openDialog: MutableState<Boolean>) {
    if(openDialog.value){
        DatePickerDialog(onDismissRequest = { openDialog.value = false },
            confirmButton = { Button(onClick = {
                openDialog.value = false
            }) {
                Text(text = "Select")
            } }
//            dismissButton = { Button(onClick = {
//                openDialog.value = false
//                //TODO:when dismiss button is pressed, the date should be cleared.
//            }) {
//                Text(text = "Cancel")
//            }}
        ) {
            DatePicker(state = dobState)
        }
    }
}


@Composable
fun NameFields(
    firstName: MutableState<String>,
    middleName: MutableState<String>,
    lastName: MutableState<String>
) {
    CustomTextField(
        valueState = firstName,
        label = "First Name*"
    )
    CustomTextField(
        valueState = middleName,
        label = "Middle Name"
    )
    CustomTextField(
        valueState = lastName,
        label = "Last Name*"
    )
}

@Composable
fun FormTitle(formTitle: String,
              style: TextStyle = MaterialTheme.typography.titleLarge) {
    Text(text = formTitle, style = style)
}



@Composable
fun TextDropDownMenuRow(
    text: String,
    dataList: List<String>?,
    valueSelected: MutableState<String>,
    registrationScreenViewModel: RegistrationScreenViewModel?
) {
    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = text, modifier = Modifier.padding(top = 13.dp, start = 8.dp))
        CustomDropDownMenu(modifier = Modifier.width(180.dp), selectedValue = valueSelected, dataList = dataList, registrationScreenViewModel)
    }
}



@Composable
fun AddressComposable(
    resAddress: MutableState<String>,
    resCity: MutableState<String>,
    resState: MutableState<String>
) {
    CustomTextField(valueState = resAddress, label = "Residential Address*")
    CustomTextField(valueState = resCity, label = "City*")
    TextDropDownMenuRow(
        text = "State*",
        dataList = statesList.list,
        valueSelected = resState,
        null
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveButton(
    viewModelProvider: ViewModelProvider,
    userDetails: userDetails,
    navController: NavHostController,
    mailFieldEnabled: MutableState<Boolean>
) {

    val context = LocalContext.current
    val otpValidationViewModel:OtpValidationViewModel = viewModelProvider.getOtpValidationViewModel()

    val onOtpSuccess:(Context,NavHostController)->Unit = { context: Context, navHostController: NavHostController ->
        Toast.makeText(context, "OTP Sent on mail", Toast.LENGTH_SHORT).show()
        navController.navigate(AppScreens.OtpValidationScreen.name)
    }
    val onOtpFailure:(Context)->Unit = {context ->
        Toast.makeText(context, "Some error has occured.", Toast.LENGTH_SHORT).show()
    }
    val progressIndicatorLoading = rememberSaveable { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.Center){
        CustomButton(onClick = {
            if(validDetails(userDetails, context)) {
                progressIndicatorLoading.value = true
                otpValidationViewModel.receiveOTP(EmailOtpRequest(userDetails.email.value),
                    context,
                    navController,
                    onOtpSuccess,
                    onOtpFailure,
                    progressIndicatorLoading)
            }

        }) {
            if(progressIndicatorLoading.value){
                CircularProgressIndicatorCustom()
            }
            else{
                Text(text = "Save")
            }
        }
    }

    if(otpValidationViewModel.getIsOtpValidated()){
        otpValidationViewModel.resetOtpValidated()
        mailFieldEnabled.value = false
        if(userDetails is studentDetails) {
            navController.navigate(AppScreens.SetPasswordScreen.name + "/Student")
        }
        else if(userDetails is teacherDetails){
            navController.navigate(AppScreens.SetPasswordScreen.name + "/Teacher")
        }
    }
}

@Composable
fun CircularProgressIndicatorCustom() {
    CircularProgressIndicator(
        modifier = Modifier.size(20.dp),
        color = Color.White,
        strokeWidth = 2.dp
    )
}





@Composable
fun TextCustomTextField(
    text:String,
    valueState: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(start = 16.dp)
    )
    CustomTextField(
        valueState = valueState,
        label = "",
        keyboardType = keyboardType,
        imeAction = imeAction
    )
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick:()->Unit,
    enabled:Boolean = true,
    content:@Composable ()->Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        content.invoke()
    }
}

@Composable
fun RegisterButton(
    userDetails: userDetails,
    registrationScreenViewModel: RegistrationScreenViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current
    val showDialog = rememberSaveable { mutableStateOf(true)}
    val registrationDone = rememberSaveable {
        mutableStateOf(false)
    }
    val onRegisterFailure:(Context)->Unit = {context ->
        Toast.makeText(context,"Some error occured while registering. Please try again", Toast.LENGTH_SHORT).show()
    }
    Row(horizontalArrangement = Arrangement.Center) {
        CustomButton(onClick = {
            registrationScreenViewModel.register(
                onRegisterFailure,
                context,
                userDetails,
                registrationDone)
//            if(registerType is registerStudent) {
//                registrationScreenViewModel.registerStudent(onRegisterFailure, context)
//            }
//            else if(registerType is registerTeacher){
//                registrationScreenViewModel.registerTeacher(onRegisterFailure, context)
//            }
        }) {
            Text(text = "Register")
        }
    }

//    if(registrationScreenViewModel.registrationResponse.value.data.applicationId.isNotEmpty()
//        && showDialog.value == true)
    if(registrationDone.value)
    {
        AlertDialog(onDismissRequest = { },
            confirmButton = {
                Text(text = "Ok", Modifier.clickable {
                    userDetails.resetPassword()
                    registrationDone.value = false
//                    showDialog.value = false
                    BackToLoginScreen(navController)
                })
            },
            title = {
                Text(text = "Application Submitted.")
            },
            text = {
                Column(verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .height(200.dp)
                        .verticalScroll(rememberScrollState())) {
                    //TODO:Format the string properly using bold for the application number
                    Text(text = "The application has been submitted. Your application number is :- ${registrationScreenViewModel.registrationResponse.value.data.applicationId}.")
                    Text(text = "It has also been sent on your registered mail.")
                }
            }
        )
    }
}



@Composable
fun ContactDetails(
    primaryContact: MutableState<String>,
    primaryContactName: MutableState<String>,
    primaryContactRelation: MutableState<String>?,
    alternativeContact: MutableState<String>,
    alternativeContactName: MutableState<String>,
    alternativeContactRelation: MutableState<String>?,
    email: MutableState<String>,
    mailFieldEnabled: MutableState<Boolean>
) {
    val isChecked = rememberSaveable { mutableStateOf(false)}
    CustomTextField(valueState = primaryContact, label = "Contact Number*")
    CustomTextField(valueState = primaryContactName, label = "Contact Name*")
    if (primaryContactRelation != null) {
        TextDropDownMenuRow(
            text = "Relation*",
            dataList = listOf("Father", "Mother", "Other"),
            valueSelected = primaryContactRelation,
            null
        )
    }
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(4.dp)) {
        Checkbox(checked = isChecked.value, onCheckedChange ={
            isChecked.value = it
        } )
        Text(text = "Alternative contact same as primary contact.", Modifier.clickable {
            isChecked.value = !isChecked.value
        })
    }
    CustomTextField(valueState = alternativeContact, label = "Alternative Contact Number*")
    CustomTextField(valueState = alternativeContactName, label = "Alternative Contact Name*")
    if (alternativeContactRelation != null) {
        TextDropDownMenuRow(
            text = "Relation*",
            dataList = listOf("Father", "Mother", "Other"),
            valueSelected = alternativeContactRelation,
            null
        )
    }

    if(isChecked.value){
        alternativeContact.value = primaryContact.value
        alternativeContactName.value = primaryContactName.value
        if (alternativeContactRelation != null && primaryContactRelation != null) {
            alternativeContactRelation.value = primaryContactRelation.value
        }
    }else{
        alternativeContact.value = ""
        alternativeContactName.value = ""
        if (alternativeContactRelation != null) {
            alternativeContactRelation.value = ""
        }
    }
    CustomTextField(valueState = email, label = "Email*", enabled = mailFieldEnabled.value)
}


@Composable
fun OtherDetails(
    primaryContact: MutableState<String>,
    primaryContactName: MutableState<String>,
    primaryContactRelation: MutableState<String>?,
    alternativeContact: MutableState<String>,
    alternativeContactName: MutableState<String>,
    alternativeContactRelation: MutableState<String>?,
    email: MutableState<String>,
    resAddress: MutableState<String>,
    resCity: MutableState<String>,
    resState: MutableState<String>,
    MOT: MutableState<String>?,
    mailFieldEnabled: MutableState<Boolean>
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FormTitle("Other Details", style = MaterialTheme.typography.titleSmall)
            ContactDetails(primaryContact, primaryContactName, primaryContactRelation,
                alternativeContact, alternativeContactName, alternativeContactRelation,
                email, mailFieldEnabled)
            AddressComposable(resAddress, resCity, resState)
            if (MOT != null) {
                CustomTextField(valueState = MOT, label = "Mode Of Transportation")
            }
        }
    }
}

@Composable
fun SchoolDetails(
    userType: String,
    schoolName: MutableState<String>,
    admissionClass: MutableState<String>?,
    registrationScreenViewModel: RegistrationScreenViewModel,
) {
    val isClassListReady = registrationScreenViewModel.isClassIdAndNameListReady
    Surface(
        modifier = Modifier.padding(4.dp),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FormTitle("School Details", style = MaterialTheme.typography.titleSmall)
            TextDropDownMenuRow(
                text = "School:",
                dataList = SchoolList.getSchoolNames(),
                valueSelected = schoolName,
                registrationScreenViewModel = registrationScreenViewModel
            )
            if(userType == "Student" && isClassListReady.value){
                if (admissionClass != null) {
                    TextDropDownMenuRow(
                        text = "Admission Class:",
//                        dataList = listOf("Pre-Nursery", "Nursery"),
                        dataList =
//                        if(schoolName.value.isNotEmpty())
                            SchoolList.getClassNames(schoolName.value)
//                        else listOf()
                        ,
                        valueSelected = admissionClass,
                        null
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDetails(
    userType: String,
    firstName: MutableState<String>,
    middleName: MutableState<String>,
    lastName: MutableState<String>,
    gender: MutableState<String>,
    dobState: DatePickerState,
    nationalitySelected: MutableState<String>
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FormTitle("Basic Details", style = MaterialTheme.typography.titleSmall)
            NameFields(firstName, middleName, lastName)
            DOB(dobState)
            TextDropDownMenuRow(
                text = "Gender",
                dataList = listOf("Male", "Female"),
                valueSelected = gender,
                null
            )
            TextDropDownMenuRow(
                "Nationality:",
                dataList = listOf("Indian", "Other"),
                valueSelected = nationalitySelected,
                null
            )

        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RulesDialog(rulesList: List<String>, rulesAccepted: MutableState<Boolean>) {
    val showDialog = rememberSaveable { mutableStateOf(false)}
    val checked = rememberSaveable { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Row(horizontalArrangement = Arrangement.Start) {
            Checkbox(checked = checked.value, onCheckedChange = {
                checked.value = it
                rulesAccepted.value = it
            })
            Text(text = "I have read and accept the rules of the school.", Modifier.width(250.dp))
        }
        Icon(imageVector = Icons.TwoTone.Info,
            contentDescription = "School Rules Icon",
            Modifier
                .clickable { showDialog.value = true }
                .padding(top = 16.dp))
    }

    if(showDialog.value){
        AlertDialog(onDismissRequest = { showDialog.value = false },
            confirmButton = { Text(text = "Ok", Modifier.clickable { showDialog.value = false }) },
            title = {Text(text = "School Rules")},
            text = {
                Column(verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .height(300.dp)
                        .verticalScroll(rememberScrollState())) {
                    rulesList.forEachIndexed { index, rule ->
                        Text(text = "${index+1}.) $rule")
                    }
                }
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



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserLoginForm(
    loginViewModel: LoginViewModel,
    navController: NavHostController,
) {
//    val email = loginViewModel.email
//    val password = loginViewModel.password
    val email = rememberSaveable { mutableStateOf("anjumannarang17@gmail.com") }
    val password = rememberSaveable { mutableStateOf("abc") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val valid = rememberSaveable(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val loading = rememberSaveable { mutableStateOf(false) }
    val userTypeSelectedForLoginRequest = loginUserType
    val modifier = Modifier
        .height(350.dp)
        .verticalScroll(rememberScrollState())
    val context = LocalContext.current
    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Login", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        CustomTextField(valueState = email,
            enabled = !loading.value,
            keyboardType = KeyboardType.Email,
            label = "Email")
        PasswordTextField(passwordState = password,
            enabled = !loading.value,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                loginViewModel.loginRequest(
                    email.value.trim(),
                    password.value.trim(),
                    loading,
                    context,
                    navController
                )
                keyBoardController?.hide()
            })
        CustomDropDownMenu(
            modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
            userTypeSelectedForLoginRequest,
            UserType.types,
            null
        )
        LoginButton(
            validInputs = valid,
            loading = loading,
            onClick = {
                if(valid) {
                    loginViewModel.loginRequest(
                        email.value.trim(),
                        password.value.trim(),
                        loading,
                        context,
                        navController)
                    keyBoardController?.hide()
                }
            }
        )
    }
}


@Composable
fun NewAccountText(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
    signUpLoading: MutableState<Boolean>
) {
    val context = LocalContext.current
    val registrationScreenViewModel = viewModelProvider.getRegistrationScreenViewModel()
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "New User?")
        Text(
            text = "Sign up",
            Modifier
                .clickable {
                    registrationScreenViewModel.getCitiesList(navController, signUpLoading, context)
                }
                .padding(4.dp),
            color = Color.Cyan,
            fontWeight = FontWeight.Bold,
        )
    }
}

