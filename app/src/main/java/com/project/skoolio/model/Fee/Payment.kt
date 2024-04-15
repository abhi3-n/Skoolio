package com.project.skoolio.model.Fee

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