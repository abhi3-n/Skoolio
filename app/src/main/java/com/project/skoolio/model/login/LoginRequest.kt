package com.project.skoolio.model.login

import com.project.skoolio.model.userDetails.AddressDetails
import com.project.skoolio.model.userDetails.ContactDetails
import com.project.skoolio.model.userDetails.FatherDetails
import com.project.skoolio.model.userDetails.MotherDetails
import com.project.skoolio.model.userDetails.StudentSchoolDetails
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher
import com.project.skoolio.utils.getDefaultStudent

data class LoginRequest(
    val email:String,
    val password:String
)

open class LoginResponse(
    val status:String = "",
    val message:String = ""
)
class StudentLoginResponse(
    status: String,
    message: String,
    val student: Student
)
    :LoginResponse(status, message){
    }


class TeacherLoginResponse(
    status: String,
    message: String,
    val teacher: Teacher)
    :LoginResponse(status, message) {
}
//data class AdminLoginResponse(
//    val status:String,
//    val message:String,
//    val user: Admin
//)