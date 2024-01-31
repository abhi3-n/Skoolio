package com.project.skoolio.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.EmailOtpRequest
import com.project.skoolio.model.EmailOtpResponse
import com.project.skoolio.network.Backend
import com.project.skoolio.repositories.OtpValidationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class OtpValidationViewModel @Inject constructor(private  val  otpValidationRepository: OtpValidationRepository):ViewModel() {
    private var _otpResponse: MutableState<DataOrException<EmailOtpResponse, Boolean, Exception>> =
        mutableStateOf<DataOrException<EmailOtpResponse, Boolean, Exception>>(
            DataOrException(EmailOtpResponse(""), false, null)
        )
    val otpResponse: State<DataOrException<EmailOtpResponse, Boolean, Exception>> = _otpResponse
    fun receiveOTP(emailOtpRequest: EmailOtpRequest):Unit{
        viewModelScope.launch {
            _otpResponse.value.loading = true
            Log.d("OtpResponse","Loading turned to true.")
            _otpResponse.value = otpValidationRepository.receiveOTP(emailOtpRequest)
            if(_otpResponse.value.data.otp.isNotEmpty() == true) {
                _otpResponse.value.loading = false
                Log.d("OtpResponse","Loading turned to false. Otp - "+ otpResponse.value.data.otp)
            }
        }

    }
}