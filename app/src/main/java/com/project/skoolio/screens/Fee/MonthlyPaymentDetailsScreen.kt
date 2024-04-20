package com.project.skoolio.screens.Fee

import android.content.Context
import android.os.Build
import android.text.method.TextKeyListener.Capitalize
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.model.Fee.Payment
import com.project.skoolio.utils.capitalize
import com.project.skoolio.utils.convertEpochToDateString
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.FeePaymentViewModel
import com.project.skoolio.viewModels.ViewModelProvider
import java.time.Year

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthlyPaymentDetailsScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    val context = LocalContext.current
    val feePaymentViewModel = viewModelProvider.getFeePaymentViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MonthlyPaymentDetailsScreenContent(navController, context, feePaymentViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthlyPaymentDetailsScreenContent(
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
                title = "Monthly Fee Data",
                icon = null, //TODO:Icon redo
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    MonthlyPaymentDetailsScreenMainContent(it, context, navController, feePaymentViewModel)
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthlyPaymentDetailsScreenMainContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    feePaymentViewModel: FeePaymentViewModel
) {
    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        if(feePaymentViewModel.monthlyDataListReady.value == true){
            Text(text = capitalize(feePaymentViewModel.monthSelected.value) +", " +Year.now().toString())

            LazyColumn {
                items(feePaymentViewModel.monthlyDataList.value.data){payment:Payment->
                    ListItem(surfaceColor = if(payment.status == "paid") 0xFF90EE90 else 0xFFFF6347,
                        itemInfo = {
                            Column(modifier = Modifier.padding(4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {
                                Text(text = "Student Name - "+ feePaymentViewModel.idToNameMap[payment.studentId])
                                Text(text = "Status - ${payment.status}")
                                if(payment.status == "paid"){
                                    Text(text = "Payment Date - "+ convertEpochToDateString(payment.paymentDate))
                                }
                                Text(text = "Fee Amount - ${payment.feeAmount}")
                            }
                        },
                        shape = RectangleShape
                    )
                }
            }
        }
    }
}
