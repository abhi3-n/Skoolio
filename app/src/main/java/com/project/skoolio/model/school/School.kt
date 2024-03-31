package com.project.skoolio.model.school

import com.project.skoolio.model.userDetails.AddressDetails

data class School(
    var schoolId:Int = 0,
    var schoolName:String = "",
    val addressDetails: AddressDetails = AddressDetails("","",""),
    val schoolContactDetails: SchoolContactDetails = SchoolContactDetails(),
    var registrationDate:Long = 0
) {
    fun resetStudent() {
        schoolName = ""
    }
}

data class SchoolContactDetails(
    val email:String = "",
    val primaryContact:String = "",
    val secondaryContact:String = ""
)