package com.project.skoolio.repositories

import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Issue.Issue
import com.project.skoolio.network.Backend
import javax.inject.Inject

class IssueRepository @Inject constructor(private val backend: Backend) {
    private val openIssuesList: DataOrException<MutableList<Issue>, Boolean, Exception> =
        DataOrException<MutableList<Issue>, Boolean, Exception>(mutableListOf())
    private val closedIssuesList: DataOrException<MutableList<Issue>, Boolean, Exception> =
        DataOrException<MutableList<Issue>, Boolean, Exception>(mutableListOf())

    suspend fun createIssue(issue:Issue):Map<String,String>{
        return backend.createIssue(issue)
    }

    suspend fun fetchIssuesListForStudent(studentId: String, status: String): DataOrException<MutableList<Issue>, Boolean, Exception> {
        val response =
            try {
                backend.getIssuesListForStudent(studentId, status)
            }
            catch (e:Exception){
                openIssuesList.exception = e
                return openIssuesList
            }
        openIssuesList.data = response.toMutableList()
        return openIssuesList
    }

//    suspend fun fetchOpenIssuesListForTeacher(): DataOrException<MutableList<Issue>, Boolean, Exception> {
//    }
//
//    suspend fun fetchOpenIssuesListForAdmin(): DataOrException<MutableList<Issue>, Boolean, Exception> {
//
//    }

}