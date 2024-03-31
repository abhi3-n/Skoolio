package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.school.School
import com.project.skoolio.model.singletonObject.schoolDetails
import com.project.skoolio.repositories.SchoolInformationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SchoolInformationViewModel @Inject constructor(private val schoolInformationRepository: SchoolInformationRepository) : ViewModel() {

    private val _schoolInfo: MutableState<DataOrException<School, Boolean, Exception>> =
        mutableStateOf<DataOrException<School, Boolean, Exception>>(
            DataOrException(School(), false, null)
        )
    val schoolInfo:State<DataOrException<School, Boolean, Exception>> = _schoolInfo

    fun getSchoolInformation(schoolId: Int, context: Context) {
        viewModelScope.launch {
            _schoolInfo.value.loading = true
            _schoolInfo.value = schoolInformationRepository.getSchoolInformation(schoolId)
            if(_schoolInfo.value.exception != null){
                _schoolInfo.value.loading = false
                Toast.makeText(context,"Some Error Occured while School Information - ${_schoolInfo.value.exception}.", Toast.LENGTH_SHORT).show()
            }
            else{
                schoolDetails.populateSchoolDetails(_schoolInfo.value.data)
                _schoolInfo.value.loading = false
                Toast.makeText(context,"School Information fetched successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun resetSchoolInfo() {
        _schoolInfo.value.data.resetStudent()
    }

}