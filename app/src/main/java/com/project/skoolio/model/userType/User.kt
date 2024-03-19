package com.project.skoolio.model.userType

import com.project.skoolio.model.userDetails.AddressDetails
import com.project.skoolio.model.userDetails.StudentSchoolDetails
import com.project.skoolio.model.userDetails.ContactDetails
import com.project.skoolio.model.userDetails.FatherDetails
import com.project.skoolio.model.userDetails.MotherDetails
import com.project.skoolio.model.userDetails.PreviousEmploymentDetails
import com.project.skoolio.model.userDetails.TeacherSchoolDetails

open class User(
    val id:String,
    val firstName:String,
    val middleName:String,
    val lastName:String,
    val dob: Long,
    val gender:Char,
    val nationality:String,
    val email:String,
    val password:String,
    val addressDetails: AddressDetails,
    val contactDetails: ContactDetails,
    ) {

}

class Student(
    id:String,
    firstName: String,
    middleName: String,
    lastName: String,
    dob: Long,
    gender: Char,
    nationality: String,
    email:String,
    password: String,
    addressDetails: AddressDetails,
    contactDetails: ContactDetails,
    val studentSchoolDetails: StudentSchoolDetails,
    val father: FatherDetails,
    val mother: MotherDetails,
    val mot:String
    ) : User(id, firstName, middleName, lastName, dob, gender, nationality, email, password, addressDetails, contactDetails) {
}

class Teacher(
    id:String,
    firstName: String,
    middleName: String,
    lastName: String,
    dob: Long,
    gender: Char,
    nationality: String,
    email: String,
    password: String,
    addressDetails: AddressDetails,
    contactDetails: ContactDetails,
    val teacherSchoolDetails: TeacherSchoolDetails,
    val previousEmploymentDetails: PreviousEmploymentDetails,
) : User(id, firstName, middleName, lastName, dob, gender, nationality, email, password, addressDetails, contactDetails){

}