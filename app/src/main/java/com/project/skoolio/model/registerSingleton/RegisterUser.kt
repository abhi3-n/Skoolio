package com.project.skoolio.model.registerSingleton

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.project.skoolio.model.userDetails.AddressDetails
import com.project.skoolio.model.userDetails.StudentSchoolDetails
import com.project.skoolio.model.userDetails.ContactDetails
import com.project.skoolio.model.userDetails.FatherDetails
import com.project.skoolio.model.userDetails.MotherDetails
import com.project.skoolio.model.userDetails.PreviousEmploymentDetails
import com.project.skoolio.model.userDetails.TeacherSchoolDetails
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher
import com.project.skoolio.utils.SchoolList

interface registerType{
    fun resetPassword()

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

    val admissionSchoolId:MutableState<Int> = mutableStateOf(0)
    val admissionSchoolName:MutableState<String> = mutableStateOf("")
    val admissionClassName:MutableState<String> = mutableStateOf("")
    val admissionClassId:MutableState<String> = mutableStateOf("")

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
    override fun resetPassword() {
        password.value = ""
    }

    override val email:MutableState<String> = mutableStateOf("")
    val password:MutableState<String> = mutableStateOf("")

    val addressLine:MutableState<String> = mutableStateOf("")
    val city:MutableState<String> = mutableStateOf("")
    val state:MutableState<String> = mutableStateOf("")

    val MOT:MutableState<String> = mutableStateOf("")
    val rulesAccepted:MutableState<Boolean> = mutableStateOf(false)

    @OptIn(ExperimentalMaterial3Api::class)
    var dobState:DatePickerState? = null

    @OptIn(ExperimentalMaterial3Api::class)
    fun getStudent(): Student {
        return Student(studentFirstName.value, studentMiddleName.value, studentLastName.value, dobState?.selectedDateMillis!!,
            gender = if(gender.value == "Male") 'm' else 'f', nationalitySelected.value, email.value, password.value,
            addressDetails = AddressDetails(addressLine.value, city.value, state.value),
            studentSchoolDetails = StudentSchoolDetails(SchoolList.getSchoolIdForSchoolName(admissionSchoolName.value), admissionClassId.value),
            contactDetails = ContactDetails(primaryContact.value, primaryContactName.value, primaryContactRelation.value, alternativeContact.value, alternativeContactName.value, alternativeContactRelation.value),
            father = FatherDetails(fatherName  = fatherName.value, fatherQualification = fatherQualification.value, fatherOccupation = fatherOccupation.value),
            mother = MotherDetails(motherName  = motherName.value, motherQualification = motherQualification.value, motherOccupation = motherOccupation.value),
            mot = MOT.value);
    }

    init {
        studentFirstName.value = "Abhinandan"
        studentLastName.value = "Narang"
        gender.value = "Male"
        nationalitySelected.value = "Indian"
        admissionClassId.value = "Pre-Nursery"
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
        addressLine.value = "Anant Nagar"
        city.value = "Khanna"
        state.value = "Punjab"
        MOT.value = "Activa"
    }
}

object registerTeacher:registerType {
    val teachertFirstName:MutableState<String> = mutableStateOf("")
    val teacherMiddleName:MutableState<String> = mutableStateOf("")
    val teacherLastName:MutableState<String> = mutableStateOf("")
    //date of birth
    val gender:MutableState<String> = mutableStateOf("")
    val nationalitySelected:MutableState<String> = mutableStateOf("")

    val employingSchoolId:MutableState<Int> = mutableStateOf(0)
    val employingSchoolName:MutableState<String> = mutableStateOf("")

    val previousEmployerName:MutableState<String> = mutableStateOf("")
    val previousEmploymentDuration:MutableState<String> = mutableStateOf("")
    val jobTitle:MutableState<String> = mutableStateOf("")

    val primaryContact:MutableState<String> = mutableStateOf("")
    val primaryContactName:MutableState<String> = mutableStateOf("")
//    val primaryContactRelation:MutableState<String> = mutableStateOf("")
    val alternativeContact:MutableState<String> = mutableStateOf("")
    val alternativeContactName:MutableState<String> = mutableStateOf("")
    override fun resetPassword() {
        password.value = ""
    }

    //    val alternativeContactRelation:MutableState<String> = mutableStateOf("")
    override val email:MutableState<String> = mutableStateOf("")
    val password:MutableState<String> = mutableStateOf("")

    val resAddress:MutableState<String> = mutableStateOf("")
    val resCity:MutableState<String> = mutableStateOf("")
    val resState:MutableState<String> = mutableStateOf("")

    val rulesAccepted:MutableState<Boolean> = mutableStateOf(false)

    @OptIn(ExperimentalMaterial3Api::class)
    var dobState:DatePickerState? = null

    @OptIn(ExperimentalMaterial3Api::class)
    fun getTeacher(): Teacher {
        return Teacher(
            teachertFirstName.value, teacherMiddleName.value, teacherLastName.value,
            dobState?.selectedDateMillis!!,
            gender = if(registerStudent.gender.value == "Male") 'm' else 'f', nationalitySelected.value, email.value, password.value,
            addressDetails = AddressDetails(resAddress.value, resCity.value, resState.value),
            teacherSchoolDetails = TeacherSchoolDetails(employingSchoolId.value),
            previousEmploymentDetails = PreviousEmploymentDetails(previousEmployerName.value, previousEmploymentDuration.value, jobTitle.value),
            contactDetails = ContactDetails(primaryContact.value, primaryContactName.value, null, alternativeContact.value, alternativeContactName.value, null));
    }
//
    init {
        teachertFirstName.value = "Abhinandan"
        teacherLastName.value = "Narang"
        gender.value = "Male"
        nationalitySelected.value = "Indian"
        employingSchoolId.value = 4
        primaryContact.value = "9646388606"
        primaryContactName.value = "Abhinandan"
        email.value = "abhinandannarang016@gmail.com"
        resAddress.value = "Anant Nagar"
        resCity.value = "Khanna"
        resState.value = "Punjab"
    }
}

