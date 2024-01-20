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