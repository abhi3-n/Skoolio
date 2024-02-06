package com.project.skoolio.model

data class EmailOtpRequest(
    val email: String
)


data class EmailOtpResponse(
    var otp: String
)
