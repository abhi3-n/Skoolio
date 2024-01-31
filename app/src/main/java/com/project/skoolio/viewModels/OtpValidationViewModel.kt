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
import com.project.skoolio.repositories.OtpValidationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class OtpValidationViewModel @Inject constructor(private  val  otpValidationRepository: OtpValidationRepository):ViewModel() {
    private var _otpResponse: MutableState<DataOrException<EmailOtpResponse, Boolean, Exception>> =
        mutableStateOf<DataOrException<EmailOtpResponse, Boolean, Exception>>(
            DataOrException(EmailOtpResponse(""), false, null)
        )
    val otpResponse: State<DataOrException<EmailOtpResponse, Boolean, Exception>> = _otpResponse
    private val _isOtpValidated = mutableStateOf(false)
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

    fun validateOtp(otp:String):Boolean{
         if(otp == _otpResponse.value.data.otp){
             _isOtpValidated.value = true
         }
        else{
            _isOtpValidated.value = false
         }
        return _isOtpValidated.value
    }

    fun clearStoredOtp() {
        _otpResponse.value.data.otp = ""
    }

    fun getIsOtpValidated(): Boolean {
        return _isOtpValidated.value
    }
    fun resetOtpValidated():Unit{
        _isOtpValidated.value = false
    }
}