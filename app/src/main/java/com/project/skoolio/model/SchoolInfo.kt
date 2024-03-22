package com.project.skoolio.model

data class SchoolInfo(
    val schoolId: Int,
    val schoolName:String,
    var listOfClasses:List<String> = listOf()
)