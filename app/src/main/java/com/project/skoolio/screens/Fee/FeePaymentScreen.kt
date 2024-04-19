package com.project.skoolio.screens.Fee

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.skoolio.components.CommonModalNavigationDrawer
import com.project.skoolio.components.CommonScaffold
import com.project.skoolio.components.ListItem
import com.project.skoolio.components.TextDropDownMenuRow
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.BackToHomeScreen
import com.project.skoolio.utils.getClassOptionsList
import com.project.skoolio.utils.getEpochValuesOfFirstDayOfMonths
import com.project.skoolio.utils.getUserDrawerItemsList
import com.project.skoolio.utils.loginUserType
import com.project.skoolio.viewModels.FeePaymentViewModel
import com.project.skoolio.viewModels.ViewModelProvider
import java.time.Year

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

@RequiresApi(Build.VERSION_CODES.O)
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
                title = if(loginUserType.value == "Student") "Fee section" else "Fee Dashboard",
                icon = null, //TODO:Icon redo
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                mainContent = {
                    if(loginUserType.value == "Student") {
                        StudentScreenContent(it, context, navController, feePaymentViewModel)
                    }
                    else{ //For Admin
                        AdminScreenContent(it, context, navController, feePaymentViewModel)
                    }
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminScreenContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    feePaymentViewModel: FeePaymentViewModel
) {
    val classDataSelected = rememberSaveable { mutableStateOf(false) }
    val updatePaymentClick = {
        navController.navigate(AppScreens.UpdatePaymentScreen.name)
    }
    val classDataClicked = {
        classDataSelected.value = true
        feePaymentViewModel.fetchClassInfoList(adminDetails.schoolId.value, context)
    }

    Column(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        if (classDataSelected.value == false) {
            ListItem(shape = CircleShape,
                itemInfo = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Create Fees",
                            style = TextStyle(fontSize = 32.sp),
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                })
                    }
                },
                onClick = {
                })
            ListItem(shape = CircleShape,
                itemInfo = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Update a Payment",
                            style = TextStyle(fontSize = 32.sp),
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    updatePaymentClick.invoke()
                                })
                    }
                },
                onClick = {
                    updatePaymentClick.invoke()
                })
        Spacer(modifier = Modifier.height(2.dp))
        ListItem(shape = CircleShape,
            itemInfo = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Class Data",
                        style = TextStyle(fontSize = 32.sp),
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                classDataClicked.invoke()
                            })
                }
            },
            onClick = {
                classDataClicked.invoke()
            })
        }
        else{
            Text(text = "For "+ Year.now().toString())
            TextDropDownMenuRow(
                text = "Select Month",
                dataList = getEpochValuesOfFirstDayOfMonths(),
                valueSelected = feePaymentViewModel.monthSelected,
                registrationScreenViewModel = null
            )
            if(feePaymentViewModel.classInfoListReady.value == true) {
                TextDropDownMenuRow(
                    text = "Select Class",
                    dataList = getClassOptionsList(feePaymentViewModel.classInfoList.value.data),
                    valueSelected = feePaymentViewModel.classNameSelected,
                    registrationScreenViewModel = null
                )
            }

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    onClick = {
                    }) {
                    Text(text = "Get Details")
                }
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    onClick = {
                        classDataSelected.value = false
                    }) {
                    Text(text = "Back")
                }
            }

        }

    }

}

@Composable
fun StudentScreenContent(
    paddingValues: PaddingValues,
    context: Context,
    navController: NavHostController,
    feePaymentViewModel: FeePaymentViewModel
) {
    val pendingFeesClick = {
        feePaymentViewModel.fetchFeeListForStudent(context, "pending", studentDetails.studentId.value)
        navController.navigate(AppScreens.PendingFeeScreen.name)
    }
    val feeHistoryClick = {
        feePaymentViewModel.fetchFeeListForStudent(context, "paid", studentDetails.studentId.value)
        navController.navigate(AppScreens.FeeHistoryScreen.name)
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
