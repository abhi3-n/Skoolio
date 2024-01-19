package com.project.skoolio.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun textField(modifier: Modifier = Modifier,
                   valueState: MutableState<String>,
                   enabled: Boolean = true,
                   keyboardType: KeyboardType = KeyboardType.Text,
                   label:String,
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
fun DropDownMenu(
    expanded: MutableState<Boolean>,
    selectedValue: MutableState<String>,
    dataList: List<String>
) {
    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = {
        expanded.value = it
    }) {
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


