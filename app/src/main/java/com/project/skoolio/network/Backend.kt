package com.project.skoolio.network

import com.project.skoolio.model.Attendance
import com.project.skoolio.model.ClassInfo
import com.project.skoolio.model.EmailOtpRequest
import com.project.skoolio.model.EmailOtpResponse
import com.project.skoolio.model.RegisterResponse
import com.project.skoolio.model.SchoolInfo
import com.project.skoolio.model.StudentInfo
import com.project.skoolio.model._Class
import com.project.skoolio.model.login.AdminLoginResponse
import com.project.skoolio.model.login.LoginRequest
import com.project.skoolio.model.login.SchoolName
import com.project.skoolio.model.login.StudentLoginResponse
import com.project.skoolio.model.login.TeacherLoginResponse
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface Backend {

    @POST("email")
    suspend fun receiveOTP(
        @Body emailOtpRequest: EmailOtpRequest
    ):EmailOtpResponse

    //student service api endpoints
    @POST("student")
    suspend fun registerStudent(
        @Body student: Student
    ): RegisterResponse

    @POST("student/login")
    suspend fun studentLogin(
        @Body loginRequest: LoginRequest
    ):StudentLoginResponse

    @GET("students/approvalPending/{schoolId}")
    suspend fun getPendingStudents(
        @Path("schoolId") schoolId: String
    ): List<Student>

    @PATCH("student/approve/{studentId}/{classId}")
    suspend fun updateStudentClassId(
        @Path("studentId") studentId: String,
        @Path("classId") classId: String,
    ):Unit

    @GET("students/{classId}")
    suspend fun getClassStudents(
        @Path("classId") classId: String
    ): List<StudentInfo>


    //teacher service api endpoints
    @POST("teacher")
    suspend fun registerTeacher(
        @Body teacher: Teacher
    ): RegisterResponse

    @POST("teacher/login")
    suspend fun teacherLogin(
        @Body loginRequest: LoginRequest
    ): TeacherLoginResponse


    @GET("teachers/approvalPending/{schoolId}")
    suspend fun getPendingTeachers(
        @Path("schoolId") schoolId: String
    ): List<Teacher>

    @PATCH("teacher/approve/{teacherId}")
    suspend fun updateTeacherStatus(
        @Path("teacherId") teacherId: String,
    ):Unit

    //school service api endpoints
    @GET("/schools/{city}")
    suspend fun getCitySchools(
        @Path("city") value: String
    ): List<SchoolInfo>

    @GET("schools/cities")
    suspend fun getCitiesList(): List<String>

    @GET("school/name/{schoolId}")
    suspend fun getSchoolNameForSchoolId(
        @Path("schoolId") schoolId: String
    ): SchoolName



//    @GET("student/detail")
//    suspend fun getStudentDetails(
//        @Body email:String
//    ):Student


    //admin related api endpoints
    @POST("admin/login")
    suspend fun adminLogin(
        @Body loginRequest: LoginRequest
    ): AdminLoginResponse



    //class service endpoints
    @GET("class/classListForSchool/{schoolId}")
    suspend fun getClassNameListForSchool(
        @Path("schoolId") schoolId: String
    ):List<String>

    @GET("classes/classInfoList/{schoolId}/{admissionClass}")
    suspend fun getClassOptionsForStudent(
        @Path ("schoolId") schoolId: String,
        @Path ("admissionClass") admissionClass:String
    ): List<ClassInfo>?

    @GET("classes/{schoolId}")
    suspend fun getClassListAdmin(
        @Path("schoolId") schoolId: String
    ): List<_Class>

    @GET("classes/{teacherId}")
    fun getClassListForTeacher(
        @Path("teacherId") teacherId: String
    ): List<_Class>

    //attendance endpoints
    @POST("attendance")
    suspend fun submitAttendance(
        @Body list: List<Attendance>
    ):Unit
}