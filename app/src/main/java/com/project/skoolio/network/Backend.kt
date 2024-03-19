package com.project.skoolio.network

import com.project.skoolio.model.EmailOtpRequest
import com.project.skoolio.model.EmailOtpResponse
import com.project.skoolio.model.RegisterResponse
import com.project.skoolio.model.SchoolInfo
import com.project.skoolio.model.login.LoginRequest
import com.project.skoolio.model.login.StudentLoginResponse
import com.project.skoolio.model.login.TeacherLoginResponse
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface Backend {

    @POST("email")
    suspend fun receiveOTP(
        @Body emailOtpRequest: EmailOtpRequest
    ):EmailOtpResponse

    @POST("student")
    suspend fun registerStudent(
        @Body student: Student
    ): RegisterResponse

    @POST("teacher")
    suspend fun registerTeacher(
        @Body teacher: Teacher
    ): RegisterResponse

    @GET("/schools/{city}")
    suspend fun getCitySchools(
        @Path("city") value: String
    ): List<SchoolInfo>


    @POST("student/login")
    suspend fun studentLogin(
        @Body loginRequest: LoginRequest
    ):StudentLoginResponse

//    @GET("student/detail")
//    suspend fun getStudentDetails(
//        @Body email:String
//    ):Student

    @GET("teacher/login")
    suspend fun teacherLogin(
        @Body loginRequest: LoginRequest
    ): TeacherLoginResponse

//    @GET("school/admin/login")
//    suspend fun adminLogin(
//        @Body loginRequest: LoginRequest
//    ):AdminLoginResponse

}