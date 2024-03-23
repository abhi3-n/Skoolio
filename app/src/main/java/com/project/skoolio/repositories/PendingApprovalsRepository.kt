package com.project.skoolio.repositories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.userType.Student
import com.project.skoolio.network.Backend
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
}