package com.project.skoolio.network

import com.project.skoolio.model.Attendance
import com.project.skoolio.model.ClassInfo
import com.project.skoolio.model.EmailOtpRequest
import com.project.skoolio.model.EmailOtpResponse
import com.project.skoolio.model.Fee.Payment
import com.project.skoolio.model.Fee.PaymentUpdateRequest
import com.project.skoolio.model.Issue.Issue
import com.project.skoolio.model.Issue.IssueCloseRequest
import com.project.skoolio.model.Issue.IssueMessageRequest
import com.project.skoolio.model.RegisterResponse
import com.project.skoolio.model.SchoolInfo
import com.project.skoolio.model.StudentInfo
import com.project.skoolio.model._Class
import com.project.skoolio.model.login.AdminLoginResponse
import com.project.skoolio.model.login.LoginRequest
import com.project.skoolio.model.login.SchoolName
import com.project.skoolio.model.login.StudentLoginResponse
import com.project.skoolio.model.login.TeacherLoginResponse
import com.project.skoolio.model.school.School
import com.project.skoolio.model.userType.SchoolAdministrator
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher
import retrofit2.http.Body
import retrofit2.http.GET
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

    //Used for taking attendance, requires minimal info of each student
    @GET("students/info/{classId}")
    suspend fun getClassStudents(
        @Path("classId") classId: String
    ): List<StudentInfo>

    @GET("student/name/{studentId}")
    suspend fun fetchStudentNameForId(
        @Path("studentId") studentId: String
    ): Map<String,String>

    //Used for School Information
    @GET("students/{classId}")
    suspend fun getStudentsListForClass(
        @Path("classId") classId: String
    ): List<Student>

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


    @GET("teachers/{schoolId}")
    suspend fun getTeacherListForSchool(
        @Path("schoolId") schoolId: String
    ): List<Teacher>

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


    @GET("school/{schoolId}")
    suspend fun getSchoolInformation(
        @Path("schoolId") schoolId: String
    ): School


//    ):Student

    //admin related api endpoints
    @POST("admin/login")
    suspend fun adminLogin(
        @Body loginRequest: LoginRequest
    ): AdminLoginResponse


    @GET("admins/{schoolId}")
    suspend fun getAdminListForSchool(
        @Path("schoolId") schoolId: String
    ): List<SchoolAdministrator>

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
    suspend fun getClassListForSchoolAdmin(
        @Path("schoolId") schoolId: String
    ): List<_Class>

    @GET("classes/classTeacherId/{classTeacherId}")
    suspend fun getClassListForTeacher(
        @Path("classTeacherId") classTeacherId: String
    ): List<_Class>

    @GET("classes/classTeacherId/classInfo/{classTeacherId}")
    suspend fun getClassInfoList(
        @Path("classTeacherId") classTeacherId: String
    ): List<ClassInfo>

    @GET("classes/classInfo/{schoolId}")
    suspend fun fetchClassInfoList(@Path("schoolId") schoolId: Int): List<ClassInfo>

    //attendance endpoints
    @POST("attendance")
    suspend fun submitAttendance(
        @Body list: List<Attendance>
    ):Unit

    @GET("attendance/range/{start}/{end}/{studentId}")
    suspend fun getAttendanceListForRange(
        @Path("start") start: String,
        @Path("end") end: String,
        @Path("studentId") studentId: String
    ): List<Attendance>

    //issue endpoint
    @POST("issue")
    suspend fun createIssue(
        @Body issue: Issue
    ):Map<String, String>

    @GET("issues/student/{studentId}/{status}")
    suspend fun getIssuesListForStudent(
        @Path("studentId") studentId: String,
        @Path("status") status: String
    ): List<Issue>

    @GET("issues/admin/{schoolId}/{status}")
    suspend fun getIssuesListForAdmin(
        @Path("schoolId") schoolId: String,
        @Path("status") status: String
    ): List<Issue>

    @GET("issues/teacher/{classId}/{status}")
    suspend fun getIssuesListForTeacher(
        @Path("classId") classId: String,
        @Path("status") status: String
    ): List<Issue>

    @PATCH("issue/addMessage")
    suspend fun addIssueMessageToList(
        @Body issueMessageRequest: IssueMessageRequest
    ):Map<String, String>

    @PATCH("issue/closeIssue")
    suspend fun closeIssue(
        @Body issueCloseRequest: IssueCloseRequest
    ):Map<String, String>

    //payment endpoints
    @GET("payments/{studentId}/{status}")
    suspend fun getFeeListForStudent(
        @Path("studentId") studentId: String,
        @Path("status") status: String
    ): List<Payment>

    @GET("payments/{schoolId}/{studentId}/{status}")
    suspend fun getFeeListForStudent(
        @Path("schoolId") schoolId: String,
        @Path("studentId") studentId: String,
        @Path("status") status: String
    ): List<Payment>

    @GET("payment/info")
    suspend fun fetchPaymentPageRelatedData(): Map<String,String>

    @PATCH("payment")
    suspend fun updateFeePaymentStatus(
        @Body paymentUpdateRequest: PaymentUpdateRequest
    ):Unit

    @GET("payments/monthlyData/{monthEpoch}/{classId}")
    suspend fun fetchAllFeePaymentsForMonthAndClassId(
        @Path("monthEpoch") monthEpoch: String,
        @Path("classId") classId: String
    ): List<Payment>

    @GET("class/fee/{classId}")
     suspend fun fetchFeeForClassID(
         @Path("classId") classId: String
     ): Map<String,Float>

}