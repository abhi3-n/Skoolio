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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.skoolio.model.EmailOtpRequest
import com.project.skoolio.model.registerSingleton.registerStudent
import com.project.skoolio.model.registerSingleton.registerTeacher
import com.project.skoolio.model.registerSingleton.registerType
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen.validDetails
import com.project.skoolio.utils.BackToLoginScreen
import com.project.skoolio.utils.SchoolList
import com.project.skoolio.utils.convertEpochToDateString
import com.project.skoolio.utils.statesList
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
fun SubmitButton(validInputs: Boolean,
                 loading: Boolean,
                 onClick:()->Unit = {}) {
    Button(onClick = { onClick.invoke() },
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if(loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
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
    dataList: List<String>
) {
    val expanded = rememberSaveable { mutableStateOf(false)}

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
            dataList.forEach { item ->
                DropdownMenuItem(text = {
                    Text(text = item, color = Color.Black)
                }, onClick = {
                    dataList.forEach{
                        if(item == it){
                            selectedValue.value = item
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
                    dobState.setSelection(null)
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
    dataList: List<String>,
    valueSelected: MutableState<String>
) {
    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = text, modifier = Modifier.padding(top = 13.dp, start = 8.dp))
        CustomDropDownMenu(modifier = Modifier.width(180.dp), selectedValue = valueSelected, dataList = dataList)
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
    TextDropDownMenuRow(text = "State*", dataList = statesList.list, valueSelected = resState)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveButton(
    viewModelProvider: ViewModelProvider,
    registerType: registerType,
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
            if(validDetails(registerType, context)) {
                progressIndicatorLoading.value = true
                otpValidationViewModel.receiveOTP(EmailOtpRequest(registerType.email.value),
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
        if(registerType is registerStudent) {
            navController.navigate(AppScreens.SetPasswordScreen.name + "/Student")
        }
        else if(registerType is registerTeacher){
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
    registerType: registerType,
    registrationScreenViewModel: RegistrationScreenViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val showDialog = rememberSaveable { mutableStateOf(true)}
    val onRegisterFailure:(Context)->Unit = {context ->
        Toast.makeText(context,"Some error occured while registering. Please try again", Toast.LENGTH_SHORT).show()
    }
    Row(horizontalArrangement = Arrangement.Center) {
        CustomButton(onClick = {
            if(registerType is registerStudent) {
                registrationScreenViewModel.registerStudent(onRegisterFailure, context)
            }
            else if(registerType is registerTeacher){
                registrationScreenViewModel.registerTeacher(onRegisterFailure, context)
            }
        }) {
            Text(text = "Register")
        }
    }

    if(registrationScreenViewModel.registrationResponse.value.data.applicationId.isNotEmpty()
        && showDialog.value == true){
        AlertDialog(onDismissRequest = { },
            confirmButton = {
                Text(text = "Ok", Modifier.clickable {
                    showDialog.value = false
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
        TextDropDownMenuRow(text = "Relation*", dataList = listOf("Father", "Mother", "Other"), valueSelected = primaryContactRelation)
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
        TextDropDownMenuRow(text = "Relation*", dataList = listOf("Father", "Mother", "Other"), valueSelected = alternativeContactRelation)
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
fun SchoolDetails(userType: String,
                  school: MutableState<String>,
                  admissionClass: MutableState<String>?,
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
            FormTitle("School Details", style = MaterialTheme.typography.titleSmall)
            TextDropDownMenuRow(text = "School:",
                dataList = SchoolList.getSchoolNames(),
                valueSelected = school)
            school.value = "Innocent Heart Playway School" //Currently assuming for single school only
            if(userType == "Student"){
                if (admissionClass != null) {
                    TextDropDownMenuRow(
                        text = "Admission Class:",
                        dataList = listOf("Pre-Nursery", "Nursery"),
                        valueSelected = admissionClass
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
            TextDropDownMenuRow(text = "Gender",
                dataList = listOf("Male", "Female"),
                valueSelected = gender)
            TextDropDownMenuRow(
                "Nationality:",
                dataList = listOf("Indian", "Other"),
                valueSelected = nationalitySelected
            )

        }
    }
}