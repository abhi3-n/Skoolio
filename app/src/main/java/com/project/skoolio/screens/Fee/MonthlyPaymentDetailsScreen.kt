package com.project.skoolio.screens.Fee

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.FeePaymentViewModel
import com.project.skoolio.viewModels.ViewModelProvider

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

    }
}
