package com.project.skoolio.repositories

import android.util.Log
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.RegisterResponse
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher
import com.project.skoolio.network.Backend
import javax.inject.Inject

class RegistrationScreenRepository @Inject constructor(private val backend: Backend){
    private val dataOrException: DataOrException<RegisterResponse, Boolean, Exception> =
        DataOrException<RegisterResponse, Boolean, Exception>(RegisterResponse())
    suspend fun registerStudent(student: Student)
    :DataOrException<RegisterResponse, Boolean, Exception> {
        val response = try {
            backend.registerStudent(student)
        }
        catch (e:Exception){
            dataOrException.exception = e
            Log.d("RepoRegisterStudent", "Some exception took place while registering student - "
                    +e.toString())
            return dataOrException
        }
        dataOrException.data = response
        Log.d("RepoRegisterStudent", "Student registered successfully in repository.")
        return dataOrException
    }

    suspend fun registerTeacher(teacher: Teacher): DataOrException<RegisterResponse, Boolean, Exception> {
        val response = try {
            backend.registerTeacher(teacher)
        }
        catch (e:Exception){
            dataOrException.exception = e
            Log.d("RepoRegisterTeacher", "Some exception took place while registering Teacher - "
                    +e.toString())
            return dataOrException
        }
        dataOrException.data = response
        Log.d("RepoRegisterTeacher", "Teacher registered successfully in repository.")
        return dataOrException

    }
}