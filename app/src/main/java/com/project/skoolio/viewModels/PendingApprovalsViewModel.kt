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
import com.project.skoolio.model.userType.Teacher
import com.project.skoolio.repositories.PendingApprovalsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class PendingApprovalsViewModel @Inject constructor(private val pendingApprovalsRepository: PendingApprovalsRepository): ViewModel() {
    private val _pendingStudentsList: MutableState<DataOrException<MutableList<Student>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Student>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val pendingStudentsList:State<DataOrException<MutableList<Student>, Boolean, Exception>> = _pendingStudentsList

    private val _pendingTeachersList: MutableState<DataOrException<MutableList<Teacher>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Teacher>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val pendingTeachersList:State<DataOrException<MutableList<Teacher>, Boolean, Exception>> = _pendingTeachersList


    fun getPendingStudents(schoolId: Int, context: Context): Unit {
        viewModelScope.launch {
            _pendingStudentsList.value = pendingApprovalsRepository.getPendingStudents(schoolId)
            if(_pendingStudentsList.value.exception!=null){
                Toast.makeText(context,"Some Error Occured while fetching the students list.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Pending students list fetched successfully.", Toast.LENGTH_SHORT).show()
            }
            getClassOptionsForEachStudent(schoolId, context)
        }
    }

     suspend fun getClassOptionsForEachStudent(schoolId: Int, context: Context) {
         for (student in pendingStudentsList.value.data){
             try {
                 student.studentSchoolDetails.admissionClassOptions =  pendingApprovalsRepository.getClassOptionsForStudent(student.studentSchoolDetails.admissionClass, schoolId)
             }
             catch (e:HttpException){
                 Toast.makeText(context,"Some error - ${e.message()}", Toast.LENGTH_SHORT).show()
             }
         }
    }

    fun updateStudentClassId(studentId: String, classId:String) {
        viewModelScope.launch {
            pendingApprovalsRepository.updateStudentClassId(studentId, classId)
        }
    }

    fun removeStudentFromPendingList(studentId: String) {
        viewModelScope.launch{
            _pendingStudentsList.value.data.removeIf {student:Student->
                student.studentId == studentId
            }
        }
    }


    fun getPendingTeachers(schoolId: Int, context: Context): Unit {
        viewModelScope.launch {
            _pendingTeachersList.value = pendingApprovalsRepository.getPendingTeachers(schoolId)
            if(_pendingTeachersList.value.exception!=null){
                Toast.makeText(context,"Some Error Occured while fetching the teachers list.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Pending teachers list fetched successfully.", Toast.LENGTH_SHORT).show()
            }
//            getClassOptionsForEachStudent(schoolId, context)
        }
    }

    fun updateTeacherStatus(teacherId: String) {
        viewModelScope.launch {
            pendingApprovalsRepository.updateTeacherStatus(teacherId)
        }
    }

    fun removeTeacherFromPendingList(teacherId: String) {
        viewModelScope.launch{
            _pendingTeachersList.value.data.removeIf {teacher:Teacher->
                teacher.teacherId == teacherId
            }
        }
    }
}