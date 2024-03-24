package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.StudentInfo
import com.project.skoolio.model._Class
import com.project.skoolio.repositories.AttendanceRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AttendanceViewModel @Inject constructor(private val attendanceRepository: AttendanceRepository): ViewModel() {
    private val _classList: MutableState<DataOrException<MutableList<_Class>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<_Class>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null))
    val classList: State<DataOrException<MutableList<_Class>, Boolean, Exception>> = _classList

    private val _classStudentsList: MutableState<DataOrException<MutableList<StudentInfo>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<StudentInfo>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null))
    val classStudentsList: State<DataOrException<MutableList<StudentInfo>, Boolean, Exception>> = _classStudentsList

    val selectedClass:MutableState<_Class> = mutableStateOf(_Class("","","","", 0,))
    fun getClassListForSchoolForAdmin(schoolId: Int, context: Context) {
        viewModelScope.launch {
            _classList.value = attendanceRepository.getClassListForSchoolForAdmin(schoolId)
            if(_classList.value.exception != null){
                Toast.makeText(context,"Some Error Occured while fetching the class list.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Class list fetched successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getClassListForSchoolForTeacher(teacherId: MutableState<String>, context: Context) {
        viewModelScope.launch {
            _classList.value = attendanceRepository.getClassListForSchoolForTeacher(teacherId)
            if(_classList.value.exception != null){
                Toast.makeText(context,"Some Error Occured while fetching the class list.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Class list fetched successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getClassStudents(_class: _Class, context: Context) {
        viewModelScope.launch{
            selectedClass.value = _class
            _classStudentsList.value = attendanceRepository.getClassStudents(_class.classId)
            if(_classStudentsList.value.exception!= null){
                Toast.makeText(context,"Some Error Occured while fetching the class list.", Toast.LENGTH_SHORT).show()
            }
            else{
                if(_classStudentsList.value.data.isEmpty()){
                    Toast.makeText(context,"Class list fetched for ${_class.grade} ${_class.section} is empty.", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context,"Class list fetched for ${_class.grade} ${_class.section} successfully.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}