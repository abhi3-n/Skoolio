package com.project.skoolio.screens.Fee

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.model.StudentInfo
import com.project.skoolio.utils.formatName
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.FeePaymentViewModel
import com.project.skoolio.viewModels.ViewModelProvider
import java.util.function.UnaryOperator

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CreateFeesScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val context = LocalContext.current
    val feePaymentViewModel = viewModelProvider.getFeePaymentViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CreateFeesScreenContent(navController, context, feePaymentViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CreateFeesScreenContent(
    navController: NavHostController,
    context: Context,
    feePaymentViewModel: FeePaymentViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState, loginUserType.value, getUserDrawerItemsList(
        loginUserType.value, navController),
        scaffold = {
            CommonScaffold(
                title = "Create Fees",
                icon = null, //TODO:Icon redo
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    CreateFeesScreenMainContent(it, context, navController, feePaymentViewModel)
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CreateFeesScreenMainContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    feePaymentViewModel: FeePaymentViewModel
) {
    val allSelected = rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top) {
        Text(text = "Month - ${feePaymentViewModel.monthSelected.value}", modifier = Modifier.padding(4.dp))
        if(feePaymentViewModel.studentListReady.value == true) {
            val studentCount = feePaymentViewModel.studentsList.value.data.count()
            val checkedList by rememberSaveable{ mutableStateOf(mutableListOf<MutableState<Boolean>>().apply {
                repeat(studentCount){
                    add(mutableStateOf(false))
                }
            })}

            Row( verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Fee - Rs.${feePaymentViewModel.selectedClassFee.value.data}",
                    modifier = Modifier.padding(4.dp, )
                )
                Checkbox(modifier = Modifier.padding(4.dp),
                    checked = allSelected.value,
                    onCheckedChange = {
                        if(!it){
                            allSelected.value = false
                            checkedList.replaceAll(UnaryOperator { mutableStateOf(false) })
                        }
                        else{
                            allSelected.value = true
                            checkedList.replaceAll(UnaryOperator { mutableStateOf(true) })
                        }
                    })
            }
            LazyColumn {
                itemsIndexed(feePaymentViewModel.studentsList.value.data){idx,student:StudentInfo->
                    ListItem(
                        itemInfo = {
                                   Row(modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(4.dp),
                                       horizontalArrangement = Arrangement.SpaceBetween,
                                       verticalAlignment = Alignment.CenterVertically) {
                                       Text(text = formatName(student.firstName, student.middleName, student.lastName))
                                       Checkbox(checked = checkedList[idx].value,
                                           onCheckedChange = {
                                               if(!it){
                                                   checkedList[idx].value = false
                                                   Toast.makeText(context,"Unselected", Toast.LENGTH_SHORT).show()
                                               }
                                               else{
                                                   checkedList[idx].value = true
                                                   Toast.makeText(context,"Selected", Toast.LENGTH_SHORT).show()
                                               }
                                           })
                                   }
                        },
                        shape = CircleShape
                    )
                }
            }
        }
    }
}

