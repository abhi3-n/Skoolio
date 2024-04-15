package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Fee.Payment
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.repositories.FeePaymentRepository
import com.project.skoolio.utils.paymentPageData
import kotlinx.coroutines.launch
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

    val listReady:MutableState<Boolean> = mutableStateOf(false)


    private val _paymentPageRelatedData: MutableState<DataOrException<Map<String,String>, Boolean, Exception>> =
        mutableStateOf<DataOrException<Map<String,String>, Boolean, Exception>>(
            DataOrException(emptyMap(), false, null)
        )

    fun fetchFeeListForStudent(context: Context, status: String): Unit {
        listReady.value = false
        viewModelScope.launch {
            val feeList = if(status == "pending") _pendingFeeList else _feeHistoryList
            if(feeList.value.loading == false){
                feeList.value.loading = true
                feeList.value = feePaymentRepository.fetchFeeListForStudent(studentDetails.studentId.value, status)

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

}