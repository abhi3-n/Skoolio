package com.project.skoolio.network

import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface Backend {
    @GET("users")
    suspend fun hitBackend():String
}