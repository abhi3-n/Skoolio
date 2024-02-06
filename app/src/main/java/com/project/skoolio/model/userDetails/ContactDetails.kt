package com.project.skoolio.model.userDetails

data class ContactDetails(
    val primaryContact:String,
    val primaryContactName: String,
    val primaryContactRelation: String,
    val alternativeContact:String,
    val alternativeContactName:String,
    val alternativeContactRelation:String,
    val email:String
)
