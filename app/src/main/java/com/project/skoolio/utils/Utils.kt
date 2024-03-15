package com.project.skoolio.utils

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.project.skoolio.model.SchoolInfo
import com.project.skoolio.navigation.AppScreens
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

fun BackToLoginScreen(navController: NavHostController) {
    navController.navigate(AppScreens.LoginScreen.name) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}


fun convertEpochToDateString(epoch: Long?): String? {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = epoch?.let { Date(it) }
    return date?.let { dateFormat.format(it) }
}

object statesList{
    val list:List<String> = listOf(
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

object TeacherRules{
    val rulesList:List<String> =  listOf(
        "School Timings\n"+
                "Summer - 9AM to 12PM\n"+
                "Winter - 9:30AM to 12:30PM\n",
        "Deposit Fees (in advance) within first 7 days of every month.\n",
        "Second Saturday will be holiday.\n",
        "Every government holiday will be a holiday in the school.\n"
    )
}

object SchoolList{
    var listForCity:String = ""
    var listOfSchools:List<SchoolInfo> = listOf(
//        "Innocent Heart Playway School"
    )

    fun getSchoolNames(): List<String> {
        return listOfSchools.map {
            it.schoolName
        }
    }
}

fun getCityList(): List<String> {
    //TODO:Later we will fetch city list dynamically as more schools get added
    return listOf("Khanna")
}