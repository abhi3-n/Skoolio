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
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Attendance
import com.project.skoolio.model._Class
import com.project.skoolio.model.school.School
import com.project.skoolio.model.singletonObject.schoolDetails
import com.project.skoolio.model.userType.SchoolAdministrator
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher
import com.project.skoolio.repositories.SchoolInformationRepository
import com.project.skoolio.utils.getEpochValuesForMonth
import com.project.skoolio.utils.getMonthNameFromEpoch
import kotlinx.coroutines.launch
import javax.inject.Inject

class SchoolInformationViewModel @Inject constructor(private val schoolInformationRepository: SchoolInformationRepository) : ViewModel() {
    private val selectedClass:MutableState<String> = mutableStateOf("")
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

    private val _classList: MutableState<DataOrException<MutableList<_Class>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<_Class>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val classList:State<DataOrException<MutableList<_Class>, Boolean, Exception>> = _classList
    fun getClassListForSchool(schoolId: Int, context: Context) {
        viewModelScope.launch {
            _classList.value = schoolInformationRepository.getClassListForSchool(schoolId)
            if(_classList.value.exception != null){
                Toast.makeText(context,"Some Error Occured while fetching the class list - ${_classList.value.exception}.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Class list fetched successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val _studentList: MutableState<DataOrException<MutableList<Student>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Student>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val studentList:State<DataOrException<MutableList<Student>, Boolean, Exception>> = _studentList
    fun getStudentsListForClass(classId: String, context: Context) {
        viewModelScope.launch {
            _studentList.value = schoolInformationRepository.getStudentsListForClass(classId)
            if(_studentList.value.exception != null){
                Toast.makeText(context,"Some Error Occured while fetching the student list - ${_studentList.value.exception}.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Student list fetched successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setSelectedClass(className:String){
        selectedClass.value = className
    }
    fun getSelectedClass():String{
        return selectedClass.value
    }

    private val _attendanceList: MutableState<DataOrException<MutableList<Attendance>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Attendance>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val attendanceList:State<DataOrException<MutableList<Attendance>, Boolean, Exception>> = _attendanceList
    val listReady = mutableStateOf(false)
    var firstAndLastEpochOfMonth:Pair<Long,Long> = Pair(0,0);
    var firstDayEpochOfMonth:MutableState<Long> = mutableStateOf(0L)
    var nameOfMonth:MutableState<String> = mutableStateOf("")
    var monthEpochValues:List<Long> = listOf()
    fun getAttendanceListForRange(context: Context, studentId: MutableState<String>) {
        if(attendanceList.value.data.isEmpty()){
            Log.d("Attendance Info", " attendance list empty." )
        }
        listReady.value = false
        viewModelScope.launch {
            if(attendanceList.value.loading == false)
            {
                attendanceList.value.loading = true
                _attendanceList.value =
                    schoolInformationRepository.getAttendanceListForRange(firstAndLastEpochOfMonth, studentId)
                if (_attendanceList.value.exception != null) {
                    Toast.makeText(context, "Some Error Occured while fetching the attendance list - ${_attendanceList.value.exception}.", Toast.LENGTH_SHORT).show()
                    attendanceList.value.loading = false
                } else {
                    Toast.makeText(context,"Attendance list fetched successfully.",Toast.LENGTH_SHORT).show()
                    _attendanceList.value.data = _attendanceList.value.data.sortedBy { it.date }.toMutableList()
                    Log.d("Attendance Info", "Fetched attendance list - ${attendanceList.value.data} and size - ${attendanceList.value.data.size}" )
                    attendanceList.value.loading = false
                    listReady.value = true
                }
            }
        }

    }

    fun resetAttendanceList() {
        _attendanceList.value.data = mutableListOf()
    }
    fun setFirstDayEpochOfMonth(){
        firstDayEpochOfMonth.value = firstAndLastEpochOfMonth.first
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setNameOfMonth(){
        nameOfMonth.value = getMonthNameFromEpoch(firstDayEpochOfMonth.value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setMonthEpochValues(){
        monthEpochValues = getEpochValuesForMonth(firstDayEpochOfMonth.value)
    }
}