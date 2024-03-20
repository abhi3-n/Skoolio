package com.project.skoolio.model.userDetailSingleton

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.capitalize
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
import com.project.skoolio.utils.capitalize
import java.util.Locale

interface userDetails{
    fun resetPassword()

    val email: MutableState<String>
        get() = mutableStateOf("")

}

object studentDetails:userDetails {
    val studentFirstName:MutableState<String> = mutableStateOf("")
    val studentMiddleName:MutableState<String> = mutableStateOf("")
    val studentLastName:MutableState<String> = mutableStateOf("")
    //date of birth
    val gender:MutableState<String> = mutableStateOf("")
    val nationality:MutableState<String> = mutableStateOf("")
    val dobValue:MutableState<Long> = mutableStateOf(0)

    val admissionSchoolId:MutableState<Int> = mutableStateOf(0)
    val schoolName:MutableState<String> = mutableStateOf("")
    val className:MutableState<String> = mutableStateOf("")
    val classId:MutableState<String> = mutableStateOf("")

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
        return Student(id = "",studentFirstName.value, studentMiddleName.value, studentLastName.value, dobState?.selectedDateMillis!!,
            gender = if(gender.value == "Male") 'm' else 'f', nationality.value, email.value, password.value,
            addressDetails = AddressDetails(addressLine.value, city.value, state.value),
            studentSchoolDetails = StudentSchoolDetails(SchoolList.getSchoolIdForSchoolName(schoolName.value), className.value),  //TODO:className to be replaced with classId later.
            contactDetails = ContactDetails(primaryContact.value, primaryContactName.value, primaryContactRelation.value, alternativeContact.value, alternativeContactName.value, alternativeContactRelation.value),
            father = FatherDetails(fatherName  = fatherName.value, fatherQualification = fatherQualification.value, fatherOccupation = fatherOccupation.value),
            mother = MotherDetails(motherName  = motherName.value, motherQualification = motherQualification.value, motherOccupation = motherOccupation.value),
            mot = MOT.value);
    }
    @OptIn(ExperimentalMaterial3Api::class)
    fun populateStudentDetails(student: Student) {
        studentFirstName.value = capitalize(student.firstName)
        studentMiddleName.value = capitalize(student.middleName)
        studentLastName.value = capitalize(student.lastName)
        gender.value = if(student.gender == 'm') "Male" else "Female"
        nationality.value = capitalize(student.nationality)
        dobValue.value = student.dob
        className.value = capitalize(student.studentSchoolDetails.classId) //TODO:need to have class Name
        schoolName.value = capitalize(student.studentSchoolDetails.schoolId.toString()) //TODO:need to have school Name
        fatherName.value = capitalize(student.father.fatherName)
        fatherQualification.value = capitalize(student.father.fatherQualification)
        fatherOccupation.value = capitalize(student.father.fatherOccupation)
        motherName.value = capitalize(student.mother.motherName)
        motherQualification.value = capitalize(student.mother.motherQualification)
        motherOccupation.value = capitalize(student.mother.motherOccupation)
        primaryContact.value = capitalize(student.contactDetails.primaryContact)
        primaryContactName.value = capitalize(student.contactDetails.primaryContactName)
        primaryContactRelation.value = capitalize(student.contactDetails.primaryContactRelation.toString())
        alternativeContact.value = capitalize(student.contactDetails.alternativeContact)
        alternativeContactName.value = capitalize(student.contactDetails.alternativeContactName)
        alternativeContactRelation.value = capitalize(student.contactDetails.alternativeContactRelation.toString())
        email.value = capitalize(student.email)
        addressLine.value = capitalize(student.addressDetails.addressLine)
        city.value = capitalize(student.addressDetails.city)
        state.value = capitalize(student.addressDetails.state)
        MOT.value = capitalize(student.mot)
    }

    suspend fun resetStudentDetails(){
        studentFirstName.value = ""
        studentMiddleName.value = ""
        studentLastName.value = ""
        gender.value = ""
        nationality.value = ""
        dobValue.value = 0
        className.value = ""
        schoolName.value = ""
        fatherName.value = ""
        fatherQualification.value = ""
        fatherOccupation.value = ""
        motherName.value = ""
        motherQualification.value = ""
        motherOccupation.value = ""
        primaryContact.value = ""
        primaryContactName.value = ""
        primaryContactRelation.value = ""
        alternativeContact.value = ""
        alternativeContactName.value = ""
        alternativeContactRelation.value = ""
        email.value = ""
        addressLine.value = ""
        city.value = ""
        state.value = ""
        MOT.value = ""
    }

    init {
        studentFirstName.value = "Abhinandan"
        studentLastName.value = "Narang"
        gender.value = "Male"
        nationality.value = "Indian"
        className.value = "Pre-Nursery"
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

object teacherDetails:userDetails {
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
    val alternativeContact:MutableState<String> = mutableStateOf("")
    val alternativeContactName:MutableState<String> = mutableStateOf("")
    override fun resetPassword() {
        password.value = ""
    }

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
        return Teacher(id = "",
            teachertFirstName.value, teacherMiddleName.value, teacherLastName.value,
            dobState?.selectedDateMillis!!,
            gender = if(studentDetails.gender.value == "Male") 'm' else 'f', nationalitySelected.value, email.value, password.value,
            addressDetails = AddressDetails(resAddress.value, resCity.value, resState.value),
            teacherSchoolDetails = TeacherSchoolDetails(employingSchoolId.value),
            previousEmploymentDetails = PreviousEmploymentDetails(previousEmployerName.value, previousEmploymentDuration.value, jobTitle.value),
            contactDetails = ContactDetails(primaryContact.value, primaryContactName.value, null, alternativeContact.value, alternativeContactName.value, null));
    }

    fun populateTeacherDetails(teacher: Teacher) {

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

