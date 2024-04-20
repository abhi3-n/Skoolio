package com.project.skoolio.repositories

import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.ClassInfo
import com.project.skoolio.model.Fee.Payment
import com.project.skoolio.model.Fee.PaymentUpdateRequest
import com.project.skoolio.model.StudentInfo
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.network.Backend
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.removeExtraSpaces
import javax.inject.Inject

class FeePaymentRepository @Inject constructor(private val backend: Backend) {
    private val pendingFeeList: DataOrException<MutableList<Payment>, Boolean, Exception> =
        DataOrException<MutableList<Payment>, Boolean, Exception>(mutableListOf())
    private val monthlyFeeData: DataOrException<MutableList<Payment>, Boolean, Exception> =
        DataOrException<MutableList<Payment>, Boolean, Exception>(mutableListOf())
    private val paymentPageRelatedData: DataOrException<Map<String,String>, Boolean, Exception> =
        DataOrException<Map<String,String>, Boolean, Exception>(emptyMap())
    private val classInfoList: DataOrException<MutableList<ClassInfo>, Boolean, Exception> =
        DataOrException<MutableList<ClassInfo>, Boolean, Exception>(mutableListOf())
    private val classFee: DataOrException<Float, Boolean, Exception> =
        DataOrException<Float, Boolean, Exception>(0F)
    private val studentsList: DataOrException<MutableList<StudentInfo>, Boolean, Exception> =
        DataOrException<MutableList<StudentInfo>, Boolean, Exception>(mutableListOf())
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

    suspend fun fetchAllFeePaymentsForMonthAndClassId(
        monthEpoch: Long,
        classId: String
    ): DataOrException<MutableList<Payment>, Boolean, Exception> {
        val response =
            try {
                backend.fetchAllFeePaymentsForMonthAndClassId(monthEpoch.toString(),classId)
            }
            catch (e:Exception){
                monthlyFeeData.exception = e
                return monthlyFeeData
            }
        monthlyFeeData.data = response.toMutableList()
        return monthlyFeeData
    }

    suspend fun fetchNameOfStudent(studentId: String): String {
        val map = backend.fetchStudentNameForId(studentId)
        return capitalize(removeExtraSpaces(map["name"]!!))
    }

    suspend fun fetchFeeForClassID(classId: String): DataOrException<Float, Boolean, Exception> {
        val response =
            try {
                backend.fetchFeeForClassID(classId)
            }
            catch (e:Exception){
                classFee.exception = e
                return classFee
            }
        classFee.data = response["fee"]!!
        return classFee
    }

    suspend fun fetchAllStudentsForClassId(classId: String): DataOrException<MutableList<StudentInfo>, Boolean, Exception> {
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
}