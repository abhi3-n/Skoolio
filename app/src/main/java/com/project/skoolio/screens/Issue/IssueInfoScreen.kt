package com.project.skoolio.screens.Issue

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.CustomTextField
import com.project.skoolio.model.Issue.IssueMessage
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.model.singletonObject.teacherDetails
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.IssueViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun IssueInfoScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider,
) {
    val context = LocalContext.current
    val issueViewModel = viewModelProvider.getIssueViewModel()
    androidx.activity.compose.BackHandler(enabled = true) {
        issueViewModel.isOpenIssueSelected = false
        navController.popBackStack()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IssueInfoScreenContent(navController, context, issueViewModel)
    }
}

@Composable
fun IssueInfoScreenContent(
    navController: NavHostController,
    context: Context,
    issueViewModel: IssueViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState, loginUserType.value, getUserDrawerItemsList(
        loginUserType.value, navController),
        scaffold = {
            CommonScaffold(
                title = "Info",
                icon = Icons.Default.Info,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    IssueInfoScreenMainContent(it, context, navController, issueViewModel)
                },
            )
        }
    )
}

@Composable
fun IssueInfoScreenMainContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    issueViewModel: IssueViewModel
) {
    val issue = issueViewModel.currentIssue
    val reply = rememberSaveable { mutableStateOf("") }
    val currentId = when (loginUserType.value) {
        "Student" -> studentDetails.studentId
        "Teacher" -> teacherDetails.teacherId
        else -> adminDetails.adminId
    }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Title - ${issue.title}", modifier = Modifier.weight(1f).padding(start = 4.dp, top = 4.dp), fontSize = 20.sp)
                if(issue.creatorId == currentId.value) {
                    IconButton(modifier = Modifier.size(40.dp), onClick = {
                        issueViewModel.closeCurrentIssue(navController, context)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Close Issue",
                            tint = Color.Green
                        )
                    }
                }
            }
        Column(modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1F)
//                .height(500.dp)
                .fillMaxSize())
            {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(issueViewModel.listOfMessages){issueMessage:IssueMessage->
                        val showTimeStamp = rememberSaveable { mutableStateOf(false) }
                        Row(horizontalArrangement = if(issueMessage.messageCreatorId == currentId.value) Arrangement.End else Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)) {
                            Column {
                                Surface(shape = RectangleShape,
                                    color = Color(0xFFA5C0E2),
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .fillMaxWidth(0.5f)
                                        .clickable {
                                            showTimeStamp.value = !showTimeStamp.value
                                        }
                                ) {
                                    Text(
                                        text = issueMessage.messageText,
                                        modifier = Modifier
                                            .padding(2.dp)
                                    )
                                }
                                if(showTimeStamp.value) {
                                    Text(text = issueMessage.messageTime.toString(), fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

            }
            if (issueViewModel.isOpenIssueSelected) {
                Row(horizontalArrangement = Arrangement.Center) {
                    CustomTextField(modifier = Modifier.width(300.dp),
                        valueState = reply,
                        label = "Reply",
                        singleLine = false,
                        maxLines = 4,
                        shape = CircleShape
                    )
                    IconButton(modifier = Modifier
                        .padding(top = 8.dp)
                        .size(60.dp),
                        enabled = reply.value.isNotEmpty(),
                        onClick = {
                            issueViewModel.updateListOfMessages(IssueMessage(currentId.value,'s',reply.value,System.currentTimeMillis()/1000), context, reply)
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send Button",
                            tint = if(reply.value.isNotEmpty()) Color.Black else Color.LightGray)
                    }
                }
            }
    }
}
