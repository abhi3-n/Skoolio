package com.project.skoolio.network

import Response
import com.project.skoolio.model.EmailOtpRequest
import com.project.skoolio.model.EmailOtpResponse
import com.project.skoolio.model.RegisterResponse
import com.project.skoolio.model.userType.Student
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface Backend {

    //"users" endpoint is just for trial
    @GET("users")
    suspend fun hitBackend():Response

    @POST("email")
    suspend fun receiveOTP(
        @Body emailOtpRequest: EmailOtpRequest
    ):EmailOtpResponse

    @POST("students")
    suspend fun registerStudent(
        @Body student: Student
    ): RegisterResponse
}