package com.project.skoolio.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.project.skoolio.data.DataOrException
import com.project.skoolio.model.Issue.Issue
import com.project.skoolio.model.Issue.IssueCloseRequest
import com.project.skoolio.model.Issue.IssueMessage
import com.project.skoolio.model.Issue.IssueMessageRequest
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.network.Backend
import com.project.skoolio.repositories.IssueRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueViewModel @Inject constructor(private val issueRepository: IssueRepository,
                                         private val backend: Backend // TODO:Direct usage of backend to be reviewed. Through Repository?
):ViewModel(){
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

    var currentIssue:Issue = Issue("",0,'s',"",0,"","","", listOf(), 'o')
    var isOpenIssueSelected = false

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

    var listOfMessages:MutableList<IssueMessage> = mutableListOf()
    fun initializeListOfMessages() {
        listOfMessages = currentIssue.issueMessages.toMutableList()
        listOfMessages.add(0, IssueMessage(studentDetails.studentId.value,'s',currentIssue.description,currentIssue.creationTime))
//        listOfMessages.add(IssueMessage("123231",'s',"dbenbjanjcbaejnwqjbdeqjdbhvebjndjw",currentIssue.creationTime))
//        listOfMessages.add(0, IssueMessage(studentDetails.studentId.value,'s',currentIssue.description,currentIssue.creationTime))
//        listOfMessages.add(IssueMessage(studentDetails.studentId.value,'s',currentIssue.description,currentIssue.creationTime))
    }

    fun updateListOfMessages(
        issueMessage: IssueMessage,
        context: Context,
        reply: MutableState<String>
    ) {
        viewModelScope.launch {
            try {
                listOfMessages.add(issueMessage) //TODO:Review whether to add in local list after adding in backend or before
                backend.addIssueMessageToList(IssueMessageRequest.getMessageRequest(issueMessage, currentIssue.issueId));
                val index = _openIssuesList.value.data.indexOf(currentIssue)
                val issueToUpdate = _openIssuesList.value.data[index]
                val mutableIssueMessages = issueToUpdate.issueMessages.toMutableList()
                mutableIssueMessages.add(issueMessage)
                issueToUpdate.issueMessages = mutableIssueMessages.toList()
                Toast.makeText(context,"The message is added to list.", Toast.LENGTH_SHORT).show()
                reply.value = ""
            }
            catch (e:Exception){
                Toast.makeText(context,"The message could not be added to list.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun closeCurrentIssue(navController: NavHostController, context: Context) {
        viewModelScope.launch {
            try {
                val index = _openIssuesList.value.data.indexOf(currentIssue)
                _openIssuesList.value.data.removeAt(index)
                backend.closeIssue(IssueCloseRequest(currentIssue.issueId))
                Toast.makeText(context,"Issue Closed.", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            catch (e:Exception){
                Log.d("closeIssue","${e.message}")
                Toast.makeText(context,"The message could not be removed from the list. ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}