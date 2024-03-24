package com.project.skoolio.model.userDetails

import com.project.skoolio.model.ClassInfo

data class StudentSchoolDetails(
    val schoolId:Int,
    val classId: String,
    val admissionClass:String,
    var admissionClassOptions:MutableList<ClassInfo>? = mutableListOf()
)

data class TeacherSchoolDetails(
    val schoolId:Int
)

data class AdminSchoolDetails(
    val schoolId:Int
)
