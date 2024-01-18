package com.project.skoolio.repositories

import android.util.Log
import com.project.skoolio.network.Backend
import javax.inject.Inject

class BackendRepository @Inject constructor(private val backend: Backend) {
    suspend fun hitBackend():String{
        Log.d("Repo", "hitbackend")
        return backend.hitBackend()
    }
}