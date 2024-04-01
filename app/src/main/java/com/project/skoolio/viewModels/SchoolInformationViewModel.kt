package com.project.skoolio.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.school.School
import com.project.skoolio.model.singletonObject.schoolDetails
import com.project.skoolio.model.singletonObject.teacherDetails
import com.project.skoolio.model.userType.SchoolAdministrator
import com.project.skoolio.model.userType.Teacher
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
//                Toast.makeText(context,"School Information fetched successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun resetSchoolInfo() {
        _schoolInfo.value.data.resetStudent()
    }


    private val _adminList: MutableState<DataOrException<MutableList<SchoolAdministrator>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<SchoolAdministrator>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val adminList:State<DataOrException<MutableList<SchoolAdministrator>, Boolean, Exception>> = _adminList

    fun getAdminListForSchool(schoolId: Int, context: Context) {
        viewModelScope.launch {
            _adminList.value = schoolInformationRepository.getAdminListForSchool(schoolId)
            if(_adminList.value.exception != null){
                Toast.makeText(context,"Some Error Occured while fetching the admin list - ${_adminList.value.exception}.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Admin list fetched successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private val _teacherList: MutableState<DataOrException<MutableList<Teacher>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Teacher>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val teacherList:State<DataOrException<MutableList<Teacher>, Boolean, Exception>> = _teacherList
    fun getTeacherListForSchool(schoolId: Int, context: Context) {
        viewModelScope.launch {
            _teacherList.value = schoolInformationRepository.getTeacherListForSchool(schoolId)
            if(_teacherList.value.exception != null){
                Toast.makeText(context,"Some Error Occured while fetching the teacher list - ${_teacherList.value.exception}.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Teacher list fetched successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}