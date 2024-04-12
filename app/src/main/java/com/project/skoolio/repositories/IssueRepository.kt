package com.project.skoolio.repositories

import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.ClassInfo
import com.project.skoolio.model.Issue.Issue
import com.project.skoolio.network.Backend
import javax.inject.Inject

class IssueRepository @Inject constructor(private val backend: Backend) {
    private val issuesList: DataOrException<MutableList<Issue>, Boolean, Exception> =
        DataOrException<MutableList<Issue>, Boolean, Exception>(mutableListOf())
    private val classInfoList: DataOrException<MutableList<ClassInfo>, Boolean, Exception> =
        DataOrException<MutableList<ClassInfo>, Boolean, Exception>(mutableListOf())

    suspend fun createIssue(issue:Issue):Map<String,String>{
        return backend.createIssue(issue)
    }

    suspend fun fetchIssuesListForStudent(studentId: String, status: String): DataOrException<MutableList<Issue>, Boolean, Exception> {
        val response =
            try {
                backend.getIssuesListForStudent(studentId, status)
            }
            catch (e:Exception){
                issuesList.exception = e
                return issuesList
            }
        issuesList.data = response.toMutableList()
        return issuesList
    }

    suspend fun fetchIssuesListForAdmin(schoolId: Int, status: String): DataOrException<MutableList<Issue>, Boolean, Exception> {
        val response =
            try {
                backend.getIssuesListForAdmin(schoolId.toString(), status)
            }
            catch (e:Exception){
                issuesList.exception = e
                return issuesList
            }
        issuesList.data = response.toMutableList()
        return issuesList
    }

    suspend fun fetchIssuesListForTeacher(classId: String, status: String): DataOrException<MutableList<Issue>, Boolean, Exception> {
        val response =
            try {
                backend.getIssuesListForTeacher(classId, status)
            }
            catch (e:Exception){
                issuesList.exception = e
                return issuesList
            }
        issuesList.data = response.toMutableList()
        return issuesList
    }

    suspend fun fetchClassInfoList(teacherId: String): DataOrException<MutableList<ClassInfo>, Boolean, Exception> {
        val response =
            try {
                backend.getClassInfoList(teacherId)
            }
            catch (e:Exception){
                classInfoList.exception = e
                return classInfoList
            }
        classInfoList.data = response.toMutableList()
        return classInfoList
    }


}