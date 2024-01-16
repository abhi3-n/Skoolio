package com.project.skoolio.repositories

import com.project.skoolio.network.Backend
import javax.inject.Inject

class BackendRepository @Inject constructor(private val backend: Backend) {
    suspend fun hitBackend():String{
        return backend.hitBackend()
    }
}