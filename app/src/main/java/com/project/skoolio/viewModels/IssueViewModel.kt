package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.project.skoolio.model.Issue.Issue
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.model.singletonObject.teacherDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.repositories.IssueRepository
import com.project.skoolio.utils.loginUserType
import kotlinx.coroutines.launch
import javax.inject.Inject

class IssueViewModel @Inject constructor(private val issueRepository: IssueRepository):ViewModel(){



    val issueTitle = mutableStateOf("")
    val issueDescription = mutableStateOf("")
    fun createIssue(navController: NavHostController, context: Context) {
        viewModelScope.launch {
            try {

                val response =  issueRepository.createIssue(
                    Issue(
                        "",
                        0L,
                        if (loginUserType.value == "Student") 's' else 't',
                        if (loginUserType.value == "Student") studentDetails.studentId.value else teacherDetails.teacherId.value,
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
}