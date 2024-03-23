package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.userType.Student
import com.project.skoolio.repositories.PendingApprovalsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PendingApprovalsViewModel @Inject constructor(private val pendingApprovalsRepository: PendingApprovalsRepository): ViewModel() {
    private val _pendingStudentsList: MutableState<DataOrException<MutableList<Student>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Student>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val pendingStudentsList:State<DataOrException<MutableList<Student>, Boolean, Exception>> = _pendingStudentsList
    fun getPendingStudents(schoolId: Int, context: Context): Unit {
        viewModelScope.launch {
            _pendingStudentsList.value = pendingApprovalsRepository.getPendingStudents(schoolId)
            if(_pendingStudentsList.value.exception!=null){
                Toast.makeText(context,"Some Error Occured while fetching the students list.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Pending students list fetched successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}