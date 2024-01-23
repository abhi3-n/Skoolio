package com.project.skoolio.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.skoolio.repositories.BackendRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginViewModel @Inject constructor(private val backendRepository: BackendRepository):ViewModel() {
    private val _message = mutableStateOf("")
    val message: State<String> = _message
    fun hitBackend(context:Context):Unit{
        Log.d("LoginViewModel", "hitbackend")
        viewModelScope.launch {
            _message.value =  backendRepository.hitBackend().message
            Toast.makeText(context, _message.value, Toast.LENGTH_SHORT).show()
        }
    }
}