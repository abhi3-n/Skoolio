package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Issue.Issue
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.repositories.IssueRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueViewModel @Inject constructor(private val issueRepository: IssueRepository):ViewModel(){
    private val _openIssuesList: MutableState<DataOrException<MutableList<Issue>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Issue>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val openIssuesList: State<DataOrException<MutableList<Issue>, Boolean, Exception>> = _openIssuesList

    private val _closedIssuesList: MutableState<DataOrException<MutableList<Issue>, Boolean, Exception>> =
        mutableStateOf<DataOrException<MutableList<Issue>, Boolean, Exception>>(
            DataOrException(mutableListOf(), false, null)
        )
    val closedIssuesList: State<DataOrException<MutableList<Issue>, Boolean, Exception>> = _closedIssuesList

    val currentIssue:MutableState<Issue> = mutableStateOf(Issue("",0,'s',"",0,"","","", listOf(), 'o'))
    val isOpenIssueSelected = mutableStateOf(false)

    val listReady:MutableState<Boolean> = mutableStateOf(false)
    val issueTitle = mutableStateOf("")

    val issueDescription = mutableStateOf("")
    fun createIssue(navController: NavHostController, context: Context) {
        viewModelScope.launch {
            try {
                val response =  issueRepository.createIssue(
                    Issue(
                        "",
                        0L,
                        's',
                        studentDetails.studentId.value,
                        studentDetails.schoolId.value,
                        studentDetails.classId.value,
                        issueTitle.value,
                        issueDescription.value,
                        listOf(),
                        'o'
                    )
                )
                Toast.makeText(context,"The issue has been raised. Issue Id - ${response["issueId"]}", Toast.LENGTH_LONG).show()
                navController.navigate(AppScreens.IssuesScreen.name) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
            catch (e:Exception){
                Toast.makeText(context,"Some error occured while saving issue - ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun fetchIssuesList(context: Context,
                        status: String) {
        listReady.value = false
        viewModelScope.launch {
            val issuesList = if(status == "o") _openIssuesList else _closedIssuesList
            if(issuesList.value.loading == false){
                issuesList.value.loading = true
                issuesList.value =
//                    if(loginUserType.value == "Student")
                        issueRepository.fetchIssuesListForStudent(studentDetails.studentId.value, status)
//                    else if(loginUserType.value == "Teacher") issueRepository.fetchOpenIssuesListForTeacher()
//                    else issueRepository.fetchOpenIssuesListForAdmin()

                if(issuesList.value.exception != null){
                    Toast.makeText(context,"Some Error Occured while fetching the open issues list - ${issuesList.value.exception}.", Toast.LENGTH_SHORT).show()
                    issuesList.value.loading = false
                }
                else{
                    Toast.makeText(context,"Issues list fetched successfully.", Toast.LENGTH_SHORT).show()
                    issuesList.value.loading = false
                    listReady.value = true
                }
            }
        }
    }

    fun resetIssuesList(context: Context) {
        _openIssuesList.value.data = mutableListOf()
        _closedIssuesList.value.data = mutableListOf()
        if(_openIssuesList.value.data.isEmpty() && _closedIssuesList.value.data.isEmpty()) {
            Toast.makeText(context, "lists cleared", Toast.LENGTH_SHORT).show()
        }
    }
}