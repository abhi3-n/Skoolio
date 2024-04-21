package com.project.skoolio.model.Settings

data class PasswordChangeRequest(
    val email:String,
    val newPassword:String
)