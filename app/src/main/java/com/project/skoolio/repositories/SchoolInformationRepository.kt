package com.project.skoolio.repositories

import androidx.compose.runtime.MutableState
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Attendance
import com.project.skoolio.model._Class
import com.project.skoolio.model.school.School
import com.project.skoolio.model.userType.SchoolAdministrator
import com.project.skoolio.model.userType.Student
import com.project.skoolio.model.userType.Teacher
import com.project.skoolio.network.Backend
import javax.inject.Inject

class SchoolInformationRepository @Inject constructor(private val backend: Backend) {
    private val _schoolInfo: DataOrException<School, Boolean, Exception> =
        DataOrException<School, Boolean, Exception>(School())
    private val _adminList: DataOrException<MutableList<SchoolAdministrator>, Boolean, Exception> =
        DataOrException<MutableList<SchoolAdministrator>, Boolean, Exception>(mutableListOf())
    private val _teacherList: DataOrException<MutableList<Teacher>, Boolean, Exception> =
        DataOrException<MutableList<Teacher>, Boolean, Exception>(mutableListOf())
    private val _studentList: DataOrException<MutableList<Student>, Boolean, Exception> =
        DataOrException<MutableList<Student>, Boolean, Exception>(mutableListOf())
    private val _classList: DataOrException<MutableList<_Class>, Boolean, Exception> =
        DataOrException<MutableList<_Class>, Boolean, Exception>(mutableListOf())
    private val _attendanceList: DataOrException<MutableList<Attendance>, Boolean, Exception> =
        DataOrException<MutableList<Attendance>, Boolean, Exception>(mutableListOf())
    suspend fun getSchoolInformation(schoolId: Int): DataOrException<School, Boolean, Exception> {
        val response =
            try {
                backend.getSchoolInformation(schoolId.toString())
            }
            catch (e:Exception){
                _schoolInfo.exception = e
                return _schoolInfo
            }
        _schoolInfo.data = response
        return _schoolInfo
    }

    suspend fun getAdminListForSchool(schoolId: Int): DataOrException<MutableList<SchoolAdministrator>, Boolean, Exception> {
        val response =
            try {
                backend.getAdminListForSchool(schoolId.toString())
            }
            catch (e:Exception){
                _adminList.exception = e
                return _adminList
            }
        _adminList.data = response.toMutableList()
        return _adminList
    }

    suspend fun getTeacherListForSchool(schoolId: Int): DataOrException<MutableList<Teacher>, Boolean, Exception> {
        val response =
            try {
                backend.getTeacherListForSchool(schoolId.toString())
            }
            catch (e:Exception){
                _teacherList.exception = e
                return _teacherList
            }
        _teacherList.data = response.toMutableList()
        return _teacherList
    }

    suspend fun getClassListForSchool(schoolId: Int): DataOrException<MutableList<_Class>, Boolean, Exception> {
        val response =
            try {
                backend.getClassListForSchoolAdmin(schoolId.toString())
            }
            catch (e:Exception){
                _classList.exception = e
                return _classList
            }
        _classList.data = response.toMutableList()
        return _classList
    }

    suspend fun getStudentsListForClass(classId: String): DataOrException<MutableList<Student>, Boolean, Exception> {
        val response =
            try {
                backend.getStudentsListForClass(classId)
            }
            catch (e:Exception){
                _studentList.exception = e
                return _studentList
            }
        _studentList.data = response.toMutableList()
        return _studentList
    }

    suspend fun getAttendanceListForRange(
        firstAndLast: Pair<Long, Long>,
        studentId: MutableState<String>
    ): DataOrException<MutableList<Attendance>, Boolean, Exception> {
        val response =
            try {
                backend.getAttendanceListForRange(firstAndLast.first.toString(), firstAndLast.second.toString(), studentId.value)
            }
            catch (e:Exception){
                _attendanceList.exception = e
                return _attendanceList
            }
        _attendanceList.data = response.toMutableList()
        return _attendanceList
    }
}