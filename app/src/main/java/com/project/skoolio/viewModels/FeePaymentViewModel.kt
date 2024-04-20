package com.project.skoolio.viewModels

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.ClassInfo
import com.project.skoolio.model.Fee.CreatePaymentsObj
import com.project.skoolio.model.Fee.Payment
import com.project.skoolio.model.StudentInfo
import com.project.skoolio.repositories.FeePaymentRepository
import com.project.skoolio.utils.currentPayment
import com.project.skoolio.utils.getClassIdForClassSelected
import com.project.skoolio.utils.getEpochOfFirstDayOfMonth
import com.project.skoolio.utils.paymentPageData
import kotlinx.coroutines.launch
import java.time.Year
import java.util.Locale
import javax.inject.Inject

class FeePaymentViewModel @Inject constructor(private val feePaymentRepository: FeePaymentRepository) : ViewModel() {
    private val _pendingFeeList: MutableState<DataOrException<MutableList<Payment>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Payment>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val pendingFeeList: State<DataOrException<MutableList<Payment>, Boolean, Exception>> = _pendingFeeList

    private val _feeHistoryList: MutableState<DataOrException<MutableList<Payment>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Payment>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val feeHistoryList: State<DataOrException<MutableList<Payment>, Boolean, Exception>> = _feeHistoryList

    private val _classInfoList: MutableState<DataOrException<MutableList<ClassInfo>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<ClassInfo>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val classInfoList: State<DataOrException<MutableList<ClassInfo>, Boolean, Exception>> = _classInfoList

    private val _monthlyDataList: MutableState<DataOrException<MutableList<Payment>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Payment>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val monthlyDataList: State<DataOrException<MutableList<Payment>, Boolean, Exception>> = _monthlyDataList

    private val _studentsList: MutableState<DataOrException<MutableList<StudentInfo>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<StudentInfo>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val studentsList: State<DataOrException<MutableList<StudentInfo>, Boolean, Exception>> = _studentsList

    val selectedClassFee: MutableState<DataOrException<Float, Boolean, Exception>> =
        mutableStateOf<DataOrException<Float, Boolean, Exception>>(
            DataOrException(0F, false, null)
        )


    val listReady:MutableState<Boolean> = mutableStateOf(false)
    val classInfoListReady:MutableState<Boolean> = mutableStateOf(false)
    val monthlyDataListReady:MutableState<Boolean> = mutableStateOf(false)
    val studentListReady:MutableState<Boolean> = mutableStateOf(false)

    lateinit var createPaymentsObj: CreatePaymentsObj

    private val _paymentPageRelatedData: MutableState<DataOrException<Map<String,String>, Boolean, Exception>> =
        mutableStateOf<DataOrException<Map<String,String>, Boolean, Exception>>(
            DataOrException(emptyMap(), false, null)
        )

    var studentId:String = ""

    val classNameSelected:MutableState<String> = mutableStateOf("")
    val monthSelected:MutableState<String> = mutableStateOf("")

    var idToNameMap:MutableMap<String,String> = mutableMapOf()
    fun fetchFeeListForStudent(context: Context, status: String, studentId: String): Unit {
        listReady.value = false
        viewModelScope.launch {
            val feeList = if(status == "pending") _pendingFeeList else _feeHistoryList
            if(feeList.value.loading == false){
                feeList.value.loading = true
                feeList.value = feePaymentRepository.fetchFeeListForStudent(studentId, status)

                if(feeList.value.exception != null){
                    Toast.makeText(context, "Some Error Occured while fetching pending fees list - ${feeList.value.exception}.", Toast.LENGTH_SHORT).show()
                    feeList.value.loading = false
                }
                else{
                    Toast.makeText(context,"Fee list fetched successfully.", Toast.LENGTH_SHORT).show()
                    feeList.value.loading = false
                    listReady.value = true
                }
            }
        }
    }

    fun fetchPaymentPageRelatedData(context: Context, launchActivity: () -> Unit) {
        viewModelScope.launch {
            _paymentPageRelatedData.value.loading = true
            _paymentPageRelatedData.value = feePaymentRepository.fetchPaymentPageRelatedData()

            if(_paymentPageRelatedData.value.exception != null){
                Toast.makeText(context, "Some Error Occured while loading payment page - ${_paymentPageRelatedData.value.exception}.", Toast.LENGTH_SHORT).show()
                _paymentPageRelatedData.value.loading = false
            }
            else{
//                Toast.makeText(context,"Fee list fetched successfully.", Toast.LENGTH_SHORT).show()
                _paymentPageRelatedData.value.loading = false
                paymentPageData = _paymentPageRelatedData.value.data
                launchActivity.invoke()
            }
        }
    }

    fun updateFeePaymentStatus(
        studentId: String,
        paymentId: String,
        feeAmount: Int,
        currentEpochSeconds: Long,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                feePaymentRepository.updateFeePaymentStatus(studentId, paymentId, feeAmount, currentEpochSeconds)
//                Toast.makeText(context,"Update successful", Toast.LENGTH_SHORT).show()
                listReady.value = false
                _pendingFeeList.value.data.remove(currentPayment)
                listReady.value = true
                currentPayment = Payment("","","",0,0,"",0,0,"")
            }
            catch (e:Exception){
                Toast.makeText(context,"Some error occured - "+e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun fetchFeeListForStudent(context: Context, status: String, studentId: String, schoolId: Int): Unit {
        listReady.value = false
        viewModelScope.launch {
            val feeList = if (status == "pending") _pendingFeeList else _feeHistoryList
            if (feeList.value.loading == false) {
                feeList.value.loading = true
                feeList.value =
                    feePaymentRepository.fetchFeeListForStudent(studentId, status, schoolId)

                if (feeList.value.exception != null) {
                    Toast.makeText(
                        context,
                        "Some Error Occured while fetching pending fees list - ${feeList.value.exception}.",
                        Toast.LENGTH_SHORT
                    ).show()
                    feeList.value.loading = false
                } else {
                    Toast.makeText(context, "Fee list fetched successfully.", Toast.LENGTH_SHORT)
                        .show()
                    feeList.value.loading = false
                    listReady.value = true
                }
            }
        }
    }

    fun resetPendingFeeList() {
        _pendingFeeList.value.data = mutableListOf()
        listReady.value = false
    }

    fun fetchClassInfoList(schoolId: Int, context: Context) {
        classInfoListReady.value = false
        viewModelScope.launch {
            if(classInfoList.value.loading == false){
                _classInfoList.value.loading = true
                _classInfoList.value = feePaymentRepository.fetchClassInfoList(schoolId)

                if(_classInfoList.value.exception != null){
                    Toast.makeText(context, "Some Error Occured while fetching class list - ${_classInfoList.value.exception}.", Toast.LENGTH_SHORT).show()
                    _classInfoList.value.loading = false
                }
                else{
                    Toast.makeText(context,"Class list fetched successfully.", Toast.LENGTH_SHORT).show()
                    _classInfoList.value.loading = false
                    classInfoListReady.value = true
                }
            }
        }
    }

    fun fetchAllFeePaymentsForMonthAndClassId(monthEpoch: Long, classId: String, context: Context) {
        monthlyDataListReady.value = false
        viewModelScope.launch {
            if(monthlyDataList.value.loading == false){
                _monthlyDataList.value.loading = true
                _monthlyDataList.value = feePaymentRepository.fetchAllFeePaymentsForMonthAndClassId(monthEpoch,classId)

                if(_monthlyDataList.value.exception != null){
                    Toast.makeText(context, "Some Error Occured while fetching monthly data - ${_monthlyDataList.value.exception}.", Toast.LENGTH_SHORT).show()
                    _monthlyDataList.value.loading = false
                }
                else{
                    Toast.makeText(context,"Monthly Data fetched successfully. Count - ${monthlyDataList.value.data.count()}", Toast.LENGTH_SHORT).show()
                    mapStudentIdToStudentNames(context)
                    _monthlyDataList.value.loading = false
                }
            }
        }
    }

    private suspend fun mapStudentIdToStudentNames(context: Context) {
        for(payment in monthlyDataList.value.data){
            val name = feePaymentRepository.fetchNameOfStudent(payment.studentId)
            idToNameMap[payment.studentId] = name
        }
        monthlyDataListReady.value = true
        Toast.makeText(context,"Id to Name mapping done.", Toast.LENGTH_SHORT).show()
    }

    fun fetchFeeForClassID(classId: String, context: Context) {
        viewModelScope.launch {
            if(selectedClassFee.value.loading == false){
                selectedClassFee.value.loading = true
                selectedClassFee.value = feePaymentRepository.fetchFeeForClassID(classId)

                if(selectedClassFee.value.exception != null){
                    Toast.makeText(context, "Some Error Occured while fetching class fee data - ${selectedClassFee.value.exception}.", Toast.LENGTH_SHORT).show()
                    selectedClassFee.value.loading = false
                }
                else{
                    Toast.makeText(context,"Class fee fetched successfully.", Toast.LENGTH_SHORT).show()
                    selectedClassFee.value.loading = false
                }
            }
        }
    }

    fun fetchAllStudentsForClassId(classId: String, context: Context) {
        studentListReady.value = false
        viewModelScope.launch {
            if(studentsList.value.loading == false){
                _studentsList.value.loading = true
                _studentsList.value = feePaymentRepository.fetchAllStudentsForClassId(classId)

                if(_studentsList.value.exception != null){
                    Toast.makeText(context, "Some Error Occured while fetching students list - ${_studentsList.value.exception}.", Toast.LENGTH_SHORT).show()
                    _studentsList.value.loading = false
                }
                else{
                    Toast.makeText(context,"Students list fetched successfully.", Toast.LENGTH_SHORT).show()
                    _studentsList.value.loading = false
                    studentListReady.value = true
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createFeePaymentsForMonth(
        checkedList: MutableList<MutableState<Boolean>>,
        navController: NavHostController,
        context: Context
    ) {
        viewModelScope.launch {
            createPaymentsObj = CreatePaymentsObj(
                getEpochOfFirstDayOfMonth(monthSelected.value.uppercase(Locale.getDefault()), Year.now().toString().toInt()),
                getClassIdForClassSelected(classNameSelected.value, classInfoList.value.data),
                selectedClassFee.value.data.toInt(),
                listOf())
            val studentIdList = mutableListOf<String>()
            checkedList.forEachIndexed { index, checked ->
                if(checked.value){
                    studentIdList.add(studentsList.value.data[index].studentId)
                }
            }
            createPaymentsObj.listOfStudentId = studentIdList.toList()
            Toast.makeText(context,"Your request has been submitted.", Toast.LENGTH_SHORT).show()
            feePaymentRepository.createFeePaymentsForMonth(createPaymentsObj)
            Toast.makeText(context,"Created", Toast.LENGTH_SHORT).show()
        }
        Log.d("create payments",createPaymentsObj.listOfStudentId.toString())
    }
}