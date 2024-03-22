package com.project.skoolio.model.userDetails

data class StudentSchoolDetails(
    val schoolId:Int,
    val classId: String,
    val admissionClass:String
//    val section:String?
)

data class TeacherSchoolDetails(
    val schoolId:Int
)
