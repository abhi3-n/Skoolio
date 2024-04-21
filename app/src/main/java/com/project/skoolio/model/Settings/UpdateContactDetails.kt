package com.project.skoolio.model.Settings

import com.project.skoolio.model.userDetails.ContactDetails

class UpdateContactDetails(
    primaryContact: String,
    primaryContactName: String,
    primaryContactRelation: String?,
    alternativeContact: String,
    alternativeContactName: String,
    alternativeContactRelation: String?,
    val id:String
) : ContactDetails(primaryContact, primaryContactName, primaryContactRelation, alternativeContact, alternativeContactName, alternativeContactRelation) {
}