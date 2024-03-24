package com.project.skoolio.model

data class Attendance(
    val date:Long,
    val studentId:String,
    val classId:String,
    var isPresent:Char,
    val takenBy:String,
    val takerType:Char
)
