package com.project.skoolio.repositories

import com.project.skoolio.model.Issue.Issue
import com.project.skoolio.network.Backend
import javax.inject.Inject

class IssueRepository @Inject constructor(private val backend: Backend) {


    suspend fun createIssue(issue:Issue):Map<String,String>{
        return backend.createIssue(issue)
    }

}