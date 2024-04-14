package com.project.skoolio.repositories

import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Fees.Payment
import com.project.skoolio.network.Backend
import javax.inject.Inject

class FeePaymentRepository @Inject constructor(private val backend: Backend) {
    private val pendingFeesList: DataOrException<MutableList<Payment>, Boolean, Exception> =
        DataOrException<MutableList<Payment>, Boolean, Exception>(mutableListOf())
    suspend fun fetchFeesListForStudent(studentId: String, status: String): DataOrException<MutableList<Payment>, Boolean, Exception> {
        val response =
            try {
                backend.getFeesListForStudent(studentId, status)
            }
            catch (e:Exception){
                pendingFeesList.exception = e
                return pendingFeesList
            }
        pendingFeesList.data = response.toMutableList()
        return pendingFeesList
    }
}