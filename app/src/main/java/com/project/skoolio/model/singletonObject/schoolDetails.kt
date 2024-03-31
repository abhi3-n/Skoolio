package com.project.skoolio.model.singletonObject

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.project.skoolio.model.school.School

object schoolDetails {
    val schoolId:MutableState<Int> = mutableStateOf(0)
    val schoolName:MutableState<String> = mutableStateOf("")
    val schoolAddressLine:MutableState<String> = mutableStateOf("")
    val schoolCity:MutableState<String> = mutableStateOf("")
    val schoolState:MutableState<String> = mutableStateOf("")
    val schoolEmail:MutableState<String> = mutableStateOf("")
    val schoolPrimaryContact:MutableState<String> = mutableStateOf("")
    val schoolSecondaryContact:MutableState<String> = mutableStateOf("")
    val schoolRegisteredDate:MutableState<Long> = mutableStateOf(0)


    fun populateSchoolDetails(school: School){
        schoolId.value = school.schoolId
        schoolName.value = school.schoolName
        schoolAddressLine.value = school.addressDetails.addressLine
        schoolCity.value = school.addressDetails.city
        schoolState.value = school.addressDetails.state
        schoolEmail.value = school.schoolContactDetails.email
        schoolPrimaryContact.value = school.schoolContactDetails.primaryContact
        schoolSecondaryContact.value = school.schoolContactDetails.secondaryContact
        schoolRegisteredDate.value = school.registrationDate
    }

    fun resetSchoolDetails(){
        schoolId.value = 0
        schoolName.value = ""
        schoolAddressLine.value = ""
        schoolCity.value = ""
        schoolState.value = ""
        schoolEmail.value = ""
        schoolPrimaryContact.value = ""
        schoolSecondaryContact.value = ""
        schoolRegisteredDate.value = 0
    }
}