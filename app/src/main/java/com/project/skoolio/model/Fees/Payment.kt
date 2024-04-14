package com.project.skoolio.model.Fees

class Payment(
    val paymentId:String,
    val studentId:String,
    val classId:String,
    val feeMonthEpoch:Long,
    val feeAmount:Int,
    val status:String,
    val paidAmount:Int,
    val paymentDate:Long,
    val orderId:String
)