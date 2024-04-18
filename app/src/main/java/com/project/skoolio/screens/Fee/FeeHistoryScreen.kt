package com.project.skoolio.screens.Fee

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.model.Fee.Payment
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.convertEpochToDateString
import com.project.skoolio.utils.currentPayment
import com.project.skoolio.utils.epochToMonthYearString
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.FeePaymentViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun FeeHistoryScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val context = LocalContext.current
    val feePaymentViewModel = viewModelProvider.getFeePaymentViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FeeHistoryScreenContent(navController, context, feePaymentViewModel)
    }
}

@Composable
fun FeeHistoryScreenContent(
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
                title = "Payment History",
                icon = null,
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    FeeHistoryScreenMainContent(it, context, navController, feePaymentViewModel)
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FeeHistoryScreenMainContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    feePaymentViewModel: FeePaymentViewModel
) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        if(feePaymentViewModel.listReady.value == true) {
            LazyColumn {
                items(feePaymentViewModel.feeHistoryList.value.data){payment: Payment ->
                    ListItem(
                        itemInfo = {
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(2.dp)) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center) {
                                    Text(text = "For Month:- "+ epochToMonthYearString(payment.feeMonthEpoch),modifier = Modifier.padding(2.dp))
                                    Text(text = "Fee Amount:- "+ payment.feeAmount,modifier = Modifier.padding(2.dp))
                                    Text(text = "Status:- "+ capitalize(payment.status),modifier = Modifier.padding(2.dp))
                                    Text(text = "Paid Amount:- "+ payment.paidAmount,modifier = Modifier.padding(2.dp))
                                    Text(text = "Paid On:- "+ convertEpochToDateString(payment.paymentDate*1000),modifier = Modifier.padding(2.dp))
                                    Text(text = "Payment Id:- "+ payment.paymentId,modifier = Modifier.padding(2.dp))
                                }
                                Column(modifier = Modifier.padding(2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center) {
                                    Icon(imageVector = Icons.Default.Check, contentDescription = "Fee Paid", tint = Color.Green)
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
