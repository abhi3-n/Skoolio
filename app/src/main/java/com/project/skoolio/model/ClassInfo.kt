package com.project.skoolio.model

data class ClassInfo(
    val classId:String,
    val grade:String,
    val section:String
)

data class ClassInfoRequest(
    private val schoolId:String,
    private val admissionClass:String
)