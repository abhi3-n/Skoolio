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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.components.TextCustomTextField
import com.project.skoolio.components.TextDatePicker
import com.project.skoolio.model.Fee.Payment
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.currentPayment
import com.project.skoolio.utils.epochToMonthYearString
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.FeePaymentViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdatePaymentScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val context = LocalContext.current
    val feePaymentViewModel = viewModelProvider.getFeePaymentViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UpdatePaymentScreenContent(navController, context, feePaymentViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdatePaymentScreenContent(
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
                title = "Update Payment",
                icon = null, //TODO:Icon redo
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    UpdatePaymentScreenMainContent(it, context, navController, feePaymentViewModel)
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdatePaymentScreenMainContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    feePaymentViewModel: FeePaymentViewModel
) {
    val studentIdState = rememberSaveable { mutableStateOf("AevEtRgcAF") }
    val fetchButtonClicked = rememberSaveable { mutableStateOf(false) }
    val trailingIcon: @Composable ()->Unit = {
        IconButton(onClick = {
            feePaymentViewModel.resetPendingFeeList()
            fetchButtonClicked.value = false
        }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Student Id")
        }
    }
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        TextCustomTextField(text = "Enter Student Id",
            valueState = studentIdState,
            imeAction = ImeAction.Done,
            enabled = !fetchButtonClicked.value, trailingIcon = trailingIcon)
        Button(enabled = studentIdState.value.isNotEmpty(),onClick = {
            fetchButtonClicked.value = true
//            Toast.makeText(context,"Fetching pending fee list for student.", Toast.LENGTH_SHORT).show()
            feePaymentViewModel.fetchFeeListForStudent(context,"pending", studentIdState.value, adminDetails.schoolId.value)
            feePaymentViewModel.studentId = studentIdState.value
        }) {
            Text(text = "Get Fee Details")
        }
        if(feePaymentViewModel.listReady.value == true){
            LazyColumn {
                items(feePaymentViewModel.pendingFeeList.value.data.sortedByDescending {payment -> payment.feeMonthEpoch }){payment: Payment ->
                    val updateMode = rememberSaveable { mutableStateOf(false) }
                    val paymentDate = rememberDatePickerState()
                    val openDialog = rememberSaveable {
                        mutableStateOf(false)
                    }
                    ListItem(
                        itemInfo = {
                            Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center) {
                                Text(
                                    text = "For Month:- " + epochToMonthYearString(payment.feeMonthEpoch),
                                    modifier = Modifier.padding(4.dp)
                                )
                                Text(
                                    text = "Amount:- " + payment.feeAmount,
                                    modifier = Modifier.padding(4.dp)
                                )
                                Text(
                                    text = "Status:- " + capitalize(payment.status),
                                    modifier = Modifier.padding(4.dp)
                                )
                                if (updateMode.value == true) {
                                    Text(
                                        text = "Payment Id:- " + payment.paymentId,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                    Text(
                                        text = "Fee amount:- " + payment.feeAmount,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                    TextDatePicker(field = "Select Payment Date:",dobState = paymentDate, startPadding = 4.dp)
                                }
                                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                                    if (updateMode.value == false) {
                                        Button(onClick = {
                                            updateMode.value = true
                                        }) {
                                            Text(text = "Update")
                                        }
                                    } else {
                                        Button(enabled = paymentDate.selectedDateMillis != null,colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                                            onClick = {
//                                                Toast.makeText(context,"Update request submitted.", Toast.LENGTH_SHORT).show()
                                                currentPayment = payment
                                                feePaymentViewModel.updateFeePaymentStatus(feePaymentViewModel.studentId,payment.paymentId,payment.feeAmount, paymentDate.selectedDateMillis!! /1000, context)
                                            }) {
                                            Text(text = "Save")
                                        }
                                        Button(
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                            onClick = {
                                                updateMode.value = false
                                            }) {
                                            Text(text = "Cancel")
                                        }
                                    }
                                }
                            }
                        },
                        onClick = {},
                        shape = RectangleShape
                    )
                }
            }
        }
    }
}
