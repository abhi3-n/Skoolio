package com.project.skoolio.repositories

import android.util.Log
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.ClassInfo
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher
import com.project.skoolio.network.Backend
import retrofit2.HttpException
import javax.inject.Inject

class PendingApprovalsRepository @Inject constructor(private val backend: Backend) {
    private val studentDataOrException: DataOrException<MutableList<Student>, Boolean, Exception> =
        DataOrException<MutableList<Student>, Boolean, Exception>(mutableListOf())

    private val teacherDataOrException: DataOrException<MutableList<Teacher>, Boolean, Exception> =
        DataOrException<MutableList<Teacher>, Boolean, Exception>(mutableListOf())

    suspend fun getPendingStudents(schoolId: Int): DataOrException<MutableList<Student>, Boolean, Exception> {
        val response =
            try {
                backend.getPendingStudents(schoolId.toString())
            }
            catch (e:Exception){
                studentDataOrException.exception = e
                return studentDataOrException
            }
        studentDataOrException.data = response.toMutableList()
        return studentDataOrException
    }

    suspend fun getPendingTeachers(schoolId: Int): DataOrException<MutableList<Teacher>, Boolean, Exception> {
        val response =
            try {
                backend.getPendingTeachers(schoolId.toString())
            }
            catch (e:Exception){
                Log.d("Teacher pending", "${e.message}")
                teacherDataOrException.exception = e
                return teacherDataOrException
            }
        teacherDataOrException.data = response.toMutableList()
        return teacherDataOrException
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