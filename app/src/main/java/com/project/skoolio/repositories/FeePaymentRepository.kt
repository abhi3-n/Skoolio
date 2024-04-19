package com.project.skoolio.repositories

import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.ClassInfo
import com.project.skoolio.model.Fee.Payment
import com.project.skoolio.model.Fee.PaymentUpdateRequest
import com.project.skoolio.network.Backend
import javax.inject.Inject

class FeePaymentRepository @Inject constructor(private val backend: Backend) {
    private val pendingFeeList: DataOrException<MutableList<Payment>, Boolean, Exception> =
        DataOrException<MutableList<Payment>, Boolean, Exception>(mutableListOf())
    private val paymentPageRelatedData: DataOrException<Map<String,String>, Boolean, Exception> =
        DataOrException<Map<String,String>, Boolean, Exception>(emptyMap())
    private val classInfoList: DataOrException<MutableList<ClassInfo>, Boolean, Exception> =
        DataOrException<MutableList<ClassInfo>, Boolean, Exception>(mutableListOf())
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

    suspend fun fetchPaymentPageRelatedData(): DataOrException<Map<String,String>, Boolean, Exception>  {
        val response =
            try {
                backend.fetchPaymentPageRelatedData()
            }
            catch (e:Exception){
                paymentPageRelatedData.exception = e
                return paymentPageRelatedData
            }
        paymentPageRelatedData.data = response
        return paymentPageRelatedData
    }

    suspend fun updateFeePaymentStatus(
        studentId: String,
        paymentId: String,
        feeAmount: Int,
        currentEpochSeconds: Long
    ){
        backend.updateFeePaymentStatus(PaymentUpdateRequest(studentId, paymentId, feeAmount, currentEpochSeconds))
    }

    //following method to be used admin login to fetch student payment details
    suspend fun fetchFeeListForStudent(studentId: String, status: String, schoolId:Int): DataOrException<MutableList<Payment>, Boolean, Exception> {
        val response =
            try {
                backend.getFeeListForStudent(schoolId.toString(), studentId, status)
            }
            catch (e:Exception){
                pendingFeeList.exception = e
                return pendingFeeList
            }
        pendingFeeList.data = response.toMutableList()
        return pendingFeeList
    }

    suspend fun fetchClassInfoList(schoolId: Int): DataOrException<MutableList<ClassInfo>, Boolean, Exception> {
        val response =
            try {
                backend.fetchClassInfoList(schoolId)
            }
            catch (e:Exception){
                classInfoList.exception = e
                return classInfoList
            }
        classInfoList.data = response.toMutableList()
        return classInfoList
    }
}