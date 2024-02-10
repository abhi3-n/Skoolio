package com.project.skoolio.model.registerSingleton

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object registerStudent {
    val studentFirstName:MutableState<String> = mutableStateOf("")
    val studentMiddleName:MutableState<String> = mutableStateOf("")
    val studentLastName:MutableState<String> = mutableStateOf("")
    //date of birth
    val gender:MutableState<String> = mutableStateOf("")
    val nationalitySelected:MutableState<String> = mutableStateOf("")

    val admissionSchool:MutableState<String> = mutableStateOf("")
    val admissionClass:MutableState<String> = mutableStateOf("")

    val fatherName:MutableState<String> = mutableStateOf("")
    val fatherQualification:MutableState<String> = mutableStateOf("")
    val fatherOccupation:MutableState<String> = mutableStateOf("")

    val motherName:MutableState<String> = mutableStateOf("")
    val motherQualification:MutableState<String> = mutableStateOf("")
    val motherOccupation:MutableState<String> = mutableStateOf("")

    val primaryContact:MutableState<String> = mutableStateOf("")
    val primaryContactName:MutableState<String> = mutableStateOf("")
    val primaryContactRelation:MutableState<String> = mutableStateOf("")
    val alternativeContact:MutableState<String> = mutableStateOf("")
    val alternativeContactName:MutableState<String> = mutableStateOf("")
    val alternativeContactRelation:MutableState<String> = mutableStateOf("")
    val email:MutableState<String> = mutableStateOf("")

    val resAddress:MutableState<String> = mutableStateOf("")
    val resCity:MutableState<String> = mutableStateOf("")
    val resState:MutableState<String> = mutableStateOf("")

    val MOT:MutableState<String> = mutableStateOf("")
}