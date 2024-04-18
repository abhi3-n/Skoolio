package com.project.skoolio.screens.Fee

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.activities.PaymentActivity
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.model.Fee.Payment
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.currentPayment
import com.project.skoolio.utils.epochToMonthYearString
import com.project.skoolio.utils.getCurrentEpochSeconds
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.FeePaymentViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PendingFeeScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val context = LocalContext.current
    val feePaymentViewModel = viewModelProvider.getFeePaymentViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PendingFeeScreenContent(navController, context, feePaymentViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PendingFeeScreenContent(
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
                title = "Pending Fees",
                icon = null,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    PendingFeeScreenMainContent(it, context, navController, feePaymentViewModel)
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PendingFeeScreenMainContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    feePaymentViewModel: FeePaymentViewModel
) {
    val paymentLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Handle result, e.g., navigate back to the Composable screen
            val value = result.data?.getStringExtra("razorpayPaymentId")
            Toast.makeText(context,"Payment successful. Payment id - ${currentPayment.paymentId}", Toast.LENGTH_SHORT).show() //TODO:Review toast message
            feePaymentViewModel.updateFeePaymentStatus(studentDetails.studentId.value, currentPayment.paymentId, currentPayment.feeAmount, getCurrentEpochSeconds(), context)
        }
        else if (result.resultCode == Activity.RESULT_CANCELED) {
            // Handle result, e.g., navigate back to the Composable screen
            val value = result.data?.getStringExtra("response")
            Toast.makeText(context,"Payment failed. Response- $value", Toast.LENGTH_SHORT).show()
        }
    }
    val launchActivity = {
        paymentLauncher.launch(Intent(context, PaymentActivity::class.java))
    }
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        if(feePaymentViewModel.listReady.value == true){
            LazyColumn {
                items(feePaymentViewModel.pendingFeeList.value.data.sortedByDescending {payment -> payment.feeMonthEpoch }){payment:Payment->
                    ListItem(
                        itemInfo = {
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(2.dp)) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center) {
                                    Text(text = "For Month:- "+ epochToMonthYearString(payment.feeMonthEpoch),modifier = Modifier.padding(2.dp))
                                    Text(text = "Amount:- "+ payment.feeAmount,modifier = Modifier.padding(2.dp))
                                    Text(text = "Status:- "+ capitalize(payment.status),modifier = Modifier.padding(2.dp))
                                }
                                Column(modifier = Modifier.padding(2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center) {
                                    Button(onClick = {
                                        currentPayment = payment
                                        feePaymentViewModel.fetchPaymentPageRelatedData(context, launchActivity)
//                                        context.startActivity(Intent(context, PaymentActivity::class.java))
                                    }) {
                                        Text(text = "Pay Now")
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
