package com.project.skoolio.utils

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ExitApp(navController: NavHostController, context: Context) {
    val activity = context as? Activity
    androidx.activity.compose.BackHandler(enabled = true) {
        navController.popBackStack(navController.graph.startDestinationId, false)
        activity?.finish()
    }
}


fun convertEpochToDateString(epoch: Long?): String? {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = epoch?.let { Date(it) }
    return date?.let { dateFormat.format(it) }
}

object statesList{
    val list = listOf(
        "Andhra Pradesh",
        "Arunachal Pradesh",
        "Assam",
        "Bihar",
        "Chhattisgarh",
        "Goa",
        "Gujarat",
        "Haryana",
        "Himachal Pradesh",
        "Jharkhand",
        "Karnataka",
        "Kerala",
        "Madhya Pradesh",
        "Maharashtra",
        "Manipur",
        "Meghalaya",
        "Mizoram",
        "Nagaland",
        "Odisha",
        "Punjab",
        "Rajasthan",
        "Sikkim",
        "Tamil Nadu",
        "Telangana",
        "Tripura",
        "Uttar Pradesh",
        "Uttarakhand",
        "West Bengal"
    )
}

object StudentRules{
    val rulesList:List<String> =  listOf(
        "School Timings\n"+
                "Summer - 9AM to 12PM\n"+
                "Winter - 9:30AM to 12:30PM\n",
        "Deposit Fees (in advance) within first 7 days of every month.\n",
        "Second Saturday will be holiday.\n",
        "Every government holiday will be a holiday in the school.\n"
    )
}