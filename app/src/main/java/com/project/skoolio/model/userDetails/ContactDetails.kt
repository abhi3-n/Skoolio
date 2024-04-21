package com.project.skoolio.model.userDetails

open class ContactDetails(
    val primaryContact:String,
    val primaryContactName: String,
    val primaryContactRelation: String?, //This is nullable as we don't want teacher's contact relation detail
    val alternativeContact:String,
    val alternativeContactName:String,
    val alternativeContactRelation:String?,
)
