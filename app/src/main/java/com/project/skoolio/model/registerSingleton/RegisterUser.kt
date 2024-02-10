package com.project.skoolio.model.registerSingleton

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object registerUser {
    var name:MutableState<String> = mutableStateOf("")
}