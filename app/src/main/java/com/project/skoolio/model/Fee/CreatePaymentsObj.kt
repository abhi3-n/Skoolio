package com.project.skoolio.model.Fee

class CreatePaymentsObj(
    val feeMonthEpoch:Long,
    val classId:String,
    val feeAmount:Int,
    var listOfStudentId:List<String>
) {

}