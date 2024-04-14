package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Fees.Payment
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.repositories.FeePaymentRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeePaymentViewModel @Inject constructor(private val feePaymentRepository: FeePaymentRepository) : ViewModel() {
    private val _pendingFeesList: MutableState<DataOrException<MutableList<Payment>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Payment>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val pendingFeesList: State<DataOrException<MutableList<Payment>, Boolean, Exception>> = _pendingFeesList

    private val _feesHistoryList: MutableState<DataOrException<MutableList<Payment>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Payment>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val feesHistoryList: State<DataOrException<MutableList<Payment>, Boolean, Exception>> = _feesHistoryList

    val listReady:MutableState<Boolean> = mutableStateOf(false)

    fun fetchFeesListForStudent(context: Context, status: String): Unit {
        listReady.value = false
        viewModelScope.launch {
            val feesList = if(status == "pending") _pendingFeesList else _feesHistoryList
            if(feesList.value.loading == false){
                feesList.value.loading = true
                feesList.value = feePaymentRepository.fetchFeesListForStudent(studentDetails.studentId.value, status)

                if(feesList.value.exception != null){
                    Toast.makeText(context, "Some Error Occured while fetching pending fees list - ${feesList.value.exception}.", Toast.LENGTH_SHORT).show()
                    feesList.value.loading = false
                }
                else{
                    Toast.makeText(context,"Issues list fetched successfully.", Toast.LENGTH_SHORT).show()
                    feesList.value.loading = false
                    listReady.value = true
                }
            }
        }
    }

}