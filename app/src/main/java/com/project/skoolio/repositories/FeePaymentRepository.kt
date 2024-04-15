package com.project.skoolio.repositories

import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Fee.Payment
import com.project.skoolio.network.Backend
import javax.inject.Inject

class FeePaymentRepository @Inject constructor(private val backend: Backend) {
    private val pendingFeeList: DataOrException<MutableList<Payment>, Boolean, Exception> =
        DataOrException<MutableList<Payment>, Boolean, Exception>(mutableListOf())
    suspend fun fetchFeeListForStudent(studentId: String, status: String): DataOrException<MutableList<Payment>, Boolean, Exception> {
        val response =
            try {
                backend.getFeeListForStudent(studentId, status)
            }
            catch (e:Exception){
                pendingFeeList.exception = e
                return pendingFeeList
            }
        pendingFeeList.data = response.toMutableList()
        return pendingFeeList
    }
}