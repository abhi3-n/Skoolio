package com.project.skoolio.model.login

data class LoginRequest(
    val email:String,
    val password:String
)

data class LoginResponse(
    val status:String,
    val message:String
)
