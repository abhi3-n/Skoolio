package com.project.skoolio.model.Fee

data class PaymentUpdateRequest(
    val studentId:String,
    val paymentId:String,
    val amount:Int,
    val paymentDate:Long
)
