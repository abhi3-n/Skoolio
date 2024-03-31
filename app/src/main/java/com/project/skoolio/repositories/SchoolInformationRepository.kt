package com.project.skoolio.repositories

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model._Class
import com.project.skoolio.model.school.School
import com.project.skoolio.model.userType.SchoolAdministrator
import com.project.skoolio.network.Backend
import javax.inject.Inject

class SchoolInformationRepository @Inject constructor(private val backend: Backend) {
    private val schoolInfo: DataOrException<School, Boolean, Exception> =
        DataOrException<School, Boolean, Exception>(School())
    private val adminList: DataOrException<MutableList<SchoolAdministrator>, Boolean, Exception> =
        DataOrException<MutableList<SchoolAdministrator>, Boolean, Exception>(mutableListOf())
    suspend fun getSchoolInformation(schoolId: Int): DataOrException<School, Boolean, Exception> {
        val response =
            try {
                backend.getSchoolInformation(schoolId.toString())
            }
            catch (e:Exception){
                schoolInfo.exception = e
                return schoolInfo
            }
        schoolInfo.data = response
        return schoolInfo
    }

    suspend fun getAdminListForSchool(schoolId: Int): DataOrException<MutableList<SchoolAdministrator>, Boolean, Exception> {
        val response =
            try {
                backend.getAdminListForSchool(schoolId.toString())
            }
            catch (e:Exception){
                adminList.exception = e
                return adminList
            }
        adminList.data = response.toMutableList()
        return adminList
    }
}