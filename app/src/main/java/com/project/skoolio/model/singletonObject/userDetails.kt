package com.project.skoolio.model.singletonObject

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.project.skoolio.model.userDetails.AddressDetails
import com.project.skoolio.model.userDetails.AdminSchoolDetails
import com.project.skoolio.model.userDetails.StudentSchoolDetails
import com.project.skoolio.model.userDetails.ContactDetails
import com.project.skoolio.model.userDetails.FatherDetails
import com.project.skoolio.model.userDetails.MotherDetails
import com.project.skoolio.model.userDetails.PreviousEmploymentDetails
import com.project.skoolio.model.userDetails.TeacherSchoolDetails
import com.project.skoolio.model.userType.SchoolAdministrator
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher
import com.project.skoolio.utils.SchoolList
import com.project.skoolio.utils.capitalize

interface userDetails{
    fun resetPassword()

    val email: MutableState<String>
        get() = mutableStateOf("")

}

object studentDetails:userDetails {
    val studentId:MutableState<String> = mutableStateOf("")
    val firstName:MutableState<String> = mutableStateOf("")
    val middleName:MutableState<String> = mutableStateOf("")
    val lastName:MutableState<String> = mutableStateOf("")
    //date of birth
    val gender:MutableState<String> = mutableStateOf("")
    val nationality:MutableState<String> = mutableStateOf("")
    val dobValue:MutableState<Long> = mutableStateOf(0)

    val schoolId:MutableState<Int> = mutableStateOf(0)
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

    override val email:MutableState<String> = mutableStateOf("")
    val password:MutableState<String> = mutableStateOf("")

    val addressLine:MutableState<String> = mutableStateOf("")
    val city:MutableState<String> = mutableStateOf("")
    val state:MutableState<String> = mutableStateOf("")

    val MOT:MutableState<String> = mutableStateOf("")
    val rulesAccepted:MutableState<Boolean> = mutableStateOf(false)
    val registrationId:MutableState<String> = mutableStateOf("")
    override fun resetPassword() {
        password.value = ""
    }

    @OptIn(ExperimentalMaterial3Api::class)
    var dobState:DatePickerState? = null

    @OptIn(ExperimentalMaterial3Api::class)
    fun getStudent(): Student {
        return Student(
            studentId = "",firstName.value, middleName.value, lastName.value, (dobState?.selectedDateMillis!!)/1000,
            gender = if(gender.value == "Male") 'm' else 'f', nationality.value, email.value, password.value,
            addressDetails = AddressDetails(addressLine.value, city.value, state.value),
            studentSchoolDetails = StudentSchoolDetails(SchoolList.getSchoolIdForSchoolName(schoolName.value),classId = classId.value , admissionClass = className.value, null),  //TODO:className to be replaced with classId later.
            contactDetails = ContactDetails(primaryContact.value, primaryContactName.value, primaryContactRelation.value, alternativeContact.value, alternativeContactName.value, alternativeContactRelation.value),
            father = FatherDetails(fatherName  = fatherName.value, fatherQualification = fatherQualification.value, fatherOccupation = fatherOccupation.value),
            mother = MotherDetails(motherName  = motherName.value, motherQualification = motherQualification.value, motherOccupation = motherOccupation.value),
            mot = MOT.value, registrationId = "");
    }
    fun populateStudentDetails(student: Student) {
        studentId.value = student.studentId
        firstName.value = capitalize(student.firstName)
        middleName.value = capitalize(student.middleName)
        lastName.value = capitalize(student.lastName)
        gender.value = if(student.gender == 'm') "Male" else "Female"
        nationality.value = capitalize(student.nationality)
        dobValue.value = student.dob
        className.value = student.studentSchoolDetails.classId //TODO:need to have class Name
        classId.value = student.studentSchoolDetails.classId
        schoolId.value = student.studentSchoolDetails.schoolId //TODO:need to have school Name
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
        email.value = student.email
        addressLine.value = capitalize(student.addressDetails.addressLine)
        city.value = capitalize(student.addressDetails.city)
        state.value = capitalize(student.addressDetails.state)
        MOT.value = capitalize(student.mot)
        registrationId.value = student.registrationId
    }

    suspend fun resetStudentDetails(){
        studentId.value = ""
        firstName.value = ""
        middleName.value = ""
        lastName.value = ""
        gender.value = ""
        nationality.value = ""
        dobValue.value = 0
        className.value = ""
        classId.value = ""
        schoolId.value = 0
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
        registrationId.value = ""
    }

    init {
        firstName.value = "Abhinandan"
        lastName.value = "Narang"
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
    val teacherId:MutableState<String> = mutableStateOf("")
    val firstName:MutableState<String> = mutableStateOf("")
    val middleName:MutableState<String> = mutableStateOf("")
    val lastName:MutableState<String> = mutableStateOf("")
    val gender:MutableState<String> = mutableStateOf("")
    val nationality:MutableState<String> = mutableStateOf("")
    val dobValue:MutableState<Long> = mutableStateOf(0)

    val schoolId:MutableState<Int> = mutableStateOf(0)
    val employingSchoolName:MutableState<String> = mutableStateOf("")

    val previousEmployerName:MutableState<String> = mutableStateOf("")
    val previousEmploymentDuration:MutableState<String> = mutableStateOf("")
    val previousJobTitle:MutableState<String> = mutableStateOf("")

    val primaryContact:MutableState<String> = mutableStateOf("")
    val primaryContactName:MutableState<String> = mutableStateOf("")
    val alternativeContact:MutableState<String> = mutableStateOf("")
    val alternativeContactName:MutableState<String> = mutableStateOf("")

    override val email:MutableState<String> = mutableStateOf("")
    val password:MutableState<String> = mutableStateOf("")

    val addressLine:MutableState<String> = mutableStateOf("")
    val city:MutableState<String> = mutableStateOf("")
    val state:MutableState<String> = mutableStateOf("")

    val rulesAccepted:MutableState<Boolean> = mutableStateOf(false)
    val registrationId:MutableState<String> = mutableStateOf("")


    override fun resetPassword() {
        password.value = ""
    }
    @OptIn(ExperimentalMaterial3Api::class)
    var dobState:DatePickerState? = null

    @OptIn(ExperimentalMaterial3Api::class)
    fun getTeacher(): Teacher {
        return Teacher(teacherId = "",
            firstName.value, middleName.value, lastName.value,
            dobState?.selectedDateMillis!!,
            gender = if(studentDetails.gender.value == "Male") 'm' else 'f', nationality.value, email.value, password.value,
            addressDetails = AddressDetails(addressLine.value, city.value, state.value),
            teacherSchoolDetails = TeacherSchoolDetails(SchoolList.getSchoolIdForSchoolName(
                employingSchoolName.value)),
            previousEmploymentDetails = PreviousEmploymentDetails(previousEmployerName.value, previousEmploymentDuration.value, previousJobTitle.value),
            contactDetails = ContactDetails(primaryContact.value, primaryContactName.value, null, alternativeContact.value, alternativeContactName.value, null),
            registrationId = "")
    }

    fun populateTeacherDetails(teacher: Teacher) {
        teacherId.value = teacher.teacherId
        firstName.value = capitalize(teacher.firstName)
        middleName.value = capitalize(teacher.middleName)
        lastName.value = capitalize(teacher.lastName)
        gender.value = if(teacher.gender == 'm') "Male" else "Female"
        nationality.value = capitalize(teacher.nationality)
        dobValue.value = teacher.dob
        schoolId.value = teacher.teacherSchoolDetails.schoolId
        primaryContact.value = capitalize(teacher.contactDetails.primaryContact)
        primaryContactName.value = capitalize(teacher.contactDetails.primaryContactName)
        alternativeContact.value = capitalize(teacher.contactDetails.alternativeContact)
        alternativeContactName.value = capitalize(teacher.contactDetails.alternativeContactName)
        email.value = teacher.email
        addressLine.value = capitalize(teacher.addressDetails.addressLine)
        city.value = capitalize(teacher.addressDetails.city)
        state.value = capitalize(teacher.addressDetails.state)
        previousEmployerName.value = capitalize(teacher.previousEmploymentDetails.employerName)
        previousEmploymentDuration.value = capitalize(teacher.previousEmploymentDetails.employmentDuration)
        previousJobTitle.value = capitalize(teacher.previousEmploymentDetails.jobTitle)
        registrationId.value = teacher.registrationId
    }

    init {
        firstName.value = "Abhinandan"
        lastName.value = "Narang"
        gender.value = "Male"
        nationality.value = "Indian"
        schoolId.value = 4
        primaryContact.value = "9646388606"
        primaryContactName.value = "Abhinandan"
        email.value = "abhinandannarang016@gmail.com"
        addressLine.value = "Anant Nagar"
        city.value = "Khanna"
        state.value = "Punjab"
    }
    suspend fun resetTeacherDetails(){
        firstName.value = ""
        middleName.value = ""
        lastName.value = ""
        gender.value = ""
        nationality.value = ""
        dobValue.value = 0
        primaryContact.value = ""
        primaryContactName.value = ""
        alternativeContact.value = ""
        alternativeContactName.value = ""
        email.value = ""
        addressLine.value = ""
        city.value = ""
        state.value = ""
        previousEmployerName.value = ""
        previousEmploymentDuration.value = ""
        previousJobTitle.value = ""
        employingSchoolName.value = ""
        schoolId.value = 0
        registrationId.value = ""
    }
}

object adminDetails:userDetails {
    //TODO:A person who is admin can be admin of multiple schools. Currently, assuming only single school. Later Extend it to encapsulate multiple schools.
    val adminId:MutableState<String> = mutableStateOf("")
    val firstName:MutableState<String> = mutableStateOf("")
    val middleName:MutableState<String> = mutableStateOf("")
    val lastName:MutableState<String> = mutableStateOf("")

    val dobValue:MutableState<Long> = mutableStateOf(0)
    val gender:MutableState<String> = mutableStateOf("")
    val nationality:MutableState<String> = mutableStateOf("")

    val schoolName:MutableState<String> = mutableStateOf("")

    val primaryContact:MutableState<String> = mutableStateOf("")
    val primaryContactName:MutableState<String> = mutableStateOf("")
    val alternativeContact:MutableState<String> = mutableStateOf("")
    val alternativeContactName:MutableState<String> = mutableStateOf("")

    val schoolId:MutableState<Int> = mutableStateOf(0)

    override val email:MutableState<String> = mutableStateOf("")
    val password:MutableState<String> = mutableStateOf("")

    val addressLine:MutableState<String> = mutableStateOf("")
    val city:MutableState<String> = mutableStateOf("")
    val state:MutableState<String> = mutableStateOf("")

    override fun resetPassword() {
        password.value = ""
    }

    @OptIn(ExperimentalMaterial3Api::class)
    var dobState:DatePickerState? = null

    @OptIn(ExperimentalMaterial3Api::class)
    fun getAdmin(): SchoolAdministrator {
        return SchoolAdministrator(adminId = "",
            firstName.value, middleName.value, lastName.value,
            dob = dobState?.selectedDateMillis!!,
            gender = if(adminDetails.gender.value == "Male") 'm' else 'f', nationality = nationality.value,
            email = email.value, password = password.value,
            addressDetails = AddressDetails(addressLine.value, city.value, state.value),
            contactDetails = ContactDetails(primaryContact.value, primaryContactName.value, null, alternativeContact.value, alternativeContactName.value, null),
            schoolId = AdminSchoolDetails(schoolId.value)
        );
    }

    fun populateAdminDetails(admin: SchoolAdministrator) {
        adminId.value = admin.adminId
        firstName.value = capitalize(admin.firstName)
        middleName.value = capitalize(admin.middleName)
        lastName.value = capitalize(admin.lastName)
        gender.value = if(admin.gender == 'm') "Male" else "Female"
        nationality.value = capitalize(admin.nationality)
        dobValue.value = admin.dob
        schoolId.value = admin.schoolId.schoolId
        primaryContact.value = capitalize(admin.contactDetails.primaryContact)
        primaryContactName.value = capitalize(admin.contactDetails.primaryContactName)
        alternativeContact.value = capitalize(admin.contactDetails.alternativeContact)
        alternativeContactName.value = capitalize(admin.contactDetails.alternativeContactName)
        email.value = admin.email
        addressLine.value = capitalize(admin.addressDetails.addressLine)
        city.value = capitalize(admin.addressDetails.city)
        state.value = capitalize(admin.addressDetails.state)
    }

    init {
        firstName.value = "Anjuman"
        lastName.value = "-"
        gender.value = "Female"
        nationality.value = "Indian"
        schoolId.value = 4
        primaryContact.value = "9888802706"
        primaryContactName.value = "Anjuman"
        email.value = "anjumannarang17@gmail.com"
        addressLine.value = "Anant Nagar"
        city.value = "Khanna"
        state.value = "Punjab"
    }
    suspend fun resetAdminDetails(){
        firstName.value = ""
        middleName.value = ""
        lastName.value = ""
        gender.value = ""
        nationality.value = ""
        dobValue.value = 0
        primaryContact.value = ""
        primaryContactName.value = ""
        alternativeContact.value = ""
        alternativeContactName.value = ""
        email.value = ""
        addressLine.value = ""
        city.value = ""
        state.value = ""
        schoolId.value = 0
        schoolName.value = ""
    }
}


