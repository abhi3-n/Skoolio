package com.project.skoolio.screens.Fee

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.BackToHomeScreen
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.FeePaymentViewModel
import com.project.skoolio.viewModels.ViewModelProvider

@Composable
fun FeePaymentScreen(navController: NavHostController, viewModelProvider: ViewModelProvider) {
    BackToHomeScreen(navController, loginUserType.value)
    val context = LocalContext.current
    val feePaymentViewModel = viewModelProvider.getFeePaymentViewModel()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FeePaymentScreenContent(navController, context, feePaymentViewModel)
    }
}

@Composable
fun FeePaymentScreenContent(
    navController: NavHostController,
    context: Context,
    feePaymentViewModel: FeePaymentViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    CommonModalNavigationDrawer(drawerState, loginUserType.value, getUserDrawerItemsList(loginUserType.value, navController),
        scaffold = {
            CommonScaffold(
                title = "Fees section",
                icon = null, //TODO:Icon redo
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    FeePaymentScreenMainContent(it, context, navController, feePaymentViewModel)
                }
            )
        }
    )
}

@Composable
fun FeePaymentScreenMainContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    feePaymentViewModel: FeePaymentViewModel
) {
    val pendingFeesClick = {
        feePaymentViewModel.fetchFeesListForStudent(context, "pending")
        navController.navigate(AppScreens.PendingFeesScreen.name)
    }
    val feeHistoryClick = {
        feePaymentViewModel.fetchFeesListForStudent(context, "paid")
        navController.navigate(AppScreens.FeesHistoryScreen.name)
    }

    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        ListItem(shape = CircleShape,
            itemInfo = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "Pending Fees",
                        style = TextStyle(fontSize = 32.sp),
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                pendingFeesClick.invoke()
                            })
                }
            },
            onClick = {
                pendingFeesClick.invoke()
            })
        Spacer(modifier = Modifier.height(2.dp))
        ListItem(shape = CircleShape,
            itemInfo = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(text = "History",
                        style = TextStyle(fontSize = 32.sp),
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                feeHistoryClick.invoke()
                            })
                }
            },
            onClick = {
                feeHistoryClick.invoke()
            })
    }
}
