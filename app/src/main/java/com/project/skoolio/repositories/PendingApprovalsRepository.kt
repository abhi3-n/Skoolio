package com.project.skoolio.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.ClassInfo
import com.project.skoolio.model.ClassInfoRequest
import com.project.skoolio.model.userType.Student
import com.project.skoolio.network.Backend
import retrofit2.HttpException
import javax.inject.Inject

class PendingApprovalsRepository @Inject constructor(private val backend: Backend) {
    private val dataOrException: DataOrException<MutableList<Student>, Boolean, Exception> =
        DataOrException<MutableList<Student>, Boolean, Exception>(mutableListOf())

    suspend fun getPendingStudents(schoolId: Int): DataOrException<MutableList<Student>, Boolean, Exception> {
        val response =
            try {
                backend.getPendingStudents(schoolId.toString())
            }
            catch (e:Exception){
                dataOrException.exception = e
                return dataOrException
            }
        dataOrException.data = response.toMutableList()
        return dataOrException
    }

    suspend fun getClassOptionsForStudent(admissionClass: String, schoolId: Int): MutableList<ClassInfo>? {
        return backend.getClassOptionsForStudent(schoolId.toString(),admissionClass)?.toMutableList()
    }

    suspend fun updateStudentClassId(studentId: String, classId:String) {
        try {
            backend.updateStudentClassId(studentId, classId)
        }
        catch (e:HttpException){
            Log.d("Update class","Some error ${e.message()}")
        }
    }
}