package com.project.skoolio.repositories

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Attendance
import com.project.skoolio.model.StudentInfo
import com.project.skoolio.model._Class
import com.project.skoolio.network.Backend
import javax.inject.Inject

class AttendanceRepository @Inject constructor(private val backend: Backend){
    private val classList: DataOrException<MutableList<_Class>, Boolean, Exception> =
        DataOrException<MutableList<_Class>, Boolean, Exception>(mutableListOf())
    private val studentsList: DataOrException<MutableList<StudentInfo>, Boolean, Exception> =
        DataOrException<MutableList<StudentInfo>, Boolean, Exception>(mutableListOf())
    suspend fun getClassListForSchoolAdmin(schoolId: Int): DataOrException<MutableList<_Class>, Boolean, Exception> {
        val response =
            try {
                backend.getClassListForSchoolAdmin(schoolId.toString())
            }
            catch (e:Exception){
                classList.exception = e
                return classList
            }
        classList.data = response.toMutableList()
        return classList
    }

    suspend fun getClassListForSchoolForTeacher(teacherId: MutableState<String>): DataOrException<MutableList<_Class>, Boolean, Exception> {
        val response =
            try {
                backend.getClassListForTeacher(teacherId.value)
            }
            catch (e:Exception){
                classList.exception = e
                Log.d("Teacher Attendance","Repo - ${e.message}")
                return classList
            }
        classList.data = response.toMutableList()
        return classList
    }

    suspend fun getClassStudents(classId: String): DataOrException<MutableList<StudentInfo>, Boolean, Exception> {
        val response =
            try {
                backend.getClassStudents(classId)
            }
            catch (e:Exception){
                studentsList.exception = e
                return studentsList
            }
        studentsList.data = response.toMutableList()
        return studentsList
    }

    suspend fun submitAttendance(studentsAttendanceList: MutableList<Attendance>) {
        backend.submitAttendance(studentsAttendanceList.toList())
    }
}