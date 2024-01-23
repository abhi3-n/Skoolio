package com.project.skoolio.network

import Response
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton
@Singleton
interface Backend {
    @GET("users")
    suspend fun hitBackend():Response

    @POST("email")
    suspend fun sendOTP():Unit
}