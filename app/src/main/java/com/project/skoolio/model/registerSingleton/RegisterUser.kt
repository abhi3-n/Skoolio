package com.project.skoolio.model.registerSingleton

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.project.skoolio.model.userDetails.AddressDetails
import com.project.skoolio.model.userDetails.StudentSchoolDetails
import com.project.skoolio.model.userDetails.ContactDetails
import com.project.skoolio.model.userDetails.GuardianDetails
import com.project.skoolio.model.userType.Student

interface registerType{
    val email: MutableState<String>
        get() = mutableStateOf("")

//    val rulesAccepted:MutableState<Boolean>
//        get() = mutableStateOf(false)

}

object registerStudent:registerType {
    val studentFirstName:MutableState<String> = mutableStateOf("")
    val studentMiddleName:MutableState<String> = mutableStateOf("")
    val studentLastName:MutableState<String> = mutableStateOf("")
    //date of birth
    val gender:MutableState<String> = mutableStateOf("")
    val nationalitySelected:MutableState<String> = mutableStateOf("")

    val admissionSchool:MutableState<String> = mutableStateOf("")
    val admissionClass:MutableState<String> = mutableStateOf("")

    val fatherName:MutableState<String> = mutableStateOf("")
    val fatherQualification:MutableState<String> = mutableStateOf("")
    val fatherOccupation:MutableState<String> = mutableStateOf("")

    val motherName:MutableState<String> = mutableStateOf("")
    val motherQualification:MutableState<String> = mutableStateOf("")
    val motherOccupation:MutableState<String> = mutableStateOf("")

    val primaryContact:MutableState<String> = mutableStateOf("")
    val primaryContactName:MutableState<String> = mutableStateOf("")
    val primaryContactRelation:MutableState<String> = mutableStateOf("")
    val alternativeContact:MutableState<String> = mutableStateOf("")
    val alternativeContactName:MutableState<String> = mutableStateOf("")
    val alternativeContactRelation:MutableState<String> = mutableStateOf("")
    override val email:MutableState<String> = mutableStateOf("")
    val password:MutableState<String> = mutableStateOf("")

    val resAddress:MutableState<String> = mutableStateOf("")
    val resCity:MutableState<String> = mutableStateOf("")
    val resState:MutableState<String> = mutableStateOf("")

    val MOT:MutableState<String> = mutableStateOf("")
    val rulesAccepted:MutableState<Boolean> = mutableStateOf(false)

    @OptIn(ExperimentalMaterial3Api::class)
    var dobState:DatePickerState? = null

    @OptIn(ExperimentalMaterial3Api::class)
    fun getStudent(): Student {
        return Student(studentFirstName.value, studentMiddleName.value, studentLastName.value, dobState?.selectedDateMillis!!,
            gender.value, nationalitySelected.value, email.value, password.value,
            addressDetails = AddressDetails(resAddress.value, resCity.value, resState.value),
            studentSchoolDetails = StudentSchoolDetails(admissionSchool.value, admissionClass.value, null),
            contactDetails = ContactDetails(primaryContact.value, primaryContactName.value, primaryContactRelation.value, alternativeContact.value, alternativeContactName.value, alternativeContactRelation.value),
            father = GuardianDetails(relationType = "father", name  = fatherName.value, occupation = fatherOccupation.value, qualification = fatherOccupation.value),
            mother = GuardianDetails(relationType = "father", name  = motherName.value, occupation = motherOccupation.value, qualification = motherQualification.value),
            mot = MOT.value);
    }

    init {
        studentFirstName.value = "Abhinandan"
        studentLastName.value = "Narang"
        gender.value = "Male"
        nationalitySelected.value = "Indian"
        admissionClass.value = "Pre-Nursery"
        fatherName.value = "Rajeev"
        fatherQualification.value = "B.Com."
        fatherOccupation.value = "Business"
        motherName.value = "Anjuman"
        motherQualification.value = "M.Com."
        motherOccupation.value = "School Head"
        primaryContact.value = "9646388606"
        primaryContactName.value = "Abhinandan"
        primaryContactRelation.value = "Other"
        email.value = "abhinandannarang016@gmail.com"
        resAddress.value = "Anant Nagar"
        resCity.value = "Khanna"
        resState.value = "Punjab"
        MOT.value = "Activa"
    }
}