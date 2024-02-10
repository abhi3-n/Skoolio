package com.project.skoolio.repositories

import Request
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.EmailOtpRequest
import com.project.skoolio.model.EmailOtpResponse
import com.project.skoolio.network.Backend
import javax.inject.Inject


class OtpValidationRepository @Inject constructor(private val backend: Backend) {
    private val dataOrException: DataOrException<EmailOtpResponse, Boolean, Exception> =
        DataOrException<EmailOtpResponse, Boolean, Exception>(EmailOtpResponse(""))
    suspend fun receiveOTP(emailOtpRequest: EmailOtpRequest): DataOrException<EmailOtpResponse, Boolean, Exception> {
        val response = try {
            backend.receiveOTP(emailOtpRequest)
        }
        catch (e:Exception){
            dataOrException.exception = e
            Log.d("OtpResponse", "Some exception took place while recieving Otp response - "+e.toString())
            return dataOrException
        }
        dataOrException.data = response
        Log.d("", "Otp response received successfully in repository.")
        return dataOrException
    }

    fun resetException() {
        dataOrException.exception = null
    }
}