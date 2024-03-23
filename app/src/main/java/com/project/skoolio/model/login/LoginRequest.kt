package com.project.skoolio.model.login

import com.project.skoolio.model.userType.SchoolAdministrator
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher

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
    val student: Student) :LoginResponse(status, message){}


class TeacherLoginResponse(
    status: String,
    message: String,
    val teacher: Teacher)
    :LoginResponse(status, message) {
}
class AdminLoginResponse(
    status:String,
    message:String,
    val schoolAdministrator: SchoolAdministrator
):LoginResponse(status, message){

}