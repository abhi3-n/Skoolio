package com.project.skoolio.model.userType

import com.project.skoolio.model.userDetails.AddressDetails
import com.project.skoolio.model.userDetails.StudentSchoolDetails
import com.project.skoolio.model.userDetails.ContactDetails
import com.project.skoolio.model.userDetails.GuardianDetails
import com.project.skoolio.model.userDetails.PreviousEmploymentDetails
import com.project.skoolio.model.userDetails.TeacherSchoolDetails

open class User(
    protected val firstName:String,
    protected val middleName:String,
    protected val lastName:String,
    protected val dob: Long,
    protected val gender:String,
    protected val nationality:String,
    protected val email:String,
    protected val password:String,
    protected val addressDetails: AddressDetails,
    protected val contactDetails: ContactDetails,
    ) {

}

class Student(
    firstName: String,
    middleName: String,
    lastName: String,
    dob: Long,
    gender: String,
    nationality: String,
    email:String,
    password: String,
    addressDetails: AddressDetails,
    contactDetails: ContactDetails,
    protected val studentSchoolDetails: StudentSchoolDetails,
    protected val father: GuardianDetails,
    protected val mother: GuardianDetails,
    protected val mot:String
    ) : User(firstName, middleName, lastName, dob, gender, nationality, email, password, addressDetails, contactDetails) {
}

class Teacher(
    firstName: String,
    middleName: String,
    lastName: String,
    dob: Long,
    gender: String,
    nationality: String,
    email: String,
    password: String,
    addressDetails: AddressDetails,
    contactDetails: ContactDetails,
    protected val teacherSchoolDetails: TeacherSchoolDetails,
    protected val previousEmploymentDetails: PreviousEmploymentDetails,
) : User(firstName, middleName, lastName, dob, gender, nationality, email, password, addressDetails, contactDetails){

}