package com.project.skoolio.model.userDetails

data class StudentSchoolDetails(
    val school:String,
    val _class: String,
    val section:String?
)

data class TeacherSchoolDetails(
    val school:String
)
