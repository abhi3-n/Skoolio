package com.project.skoolio.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.project.skoolio.components.adminDrawerItems
import com.project.skoolio.components.studentDrawerItems
import com.project.skoolio.components.teacherDrawerItems
import com.project.skoolio.model.Attendance
import com.project.skoolio.model.SchoolInfo
import com.project.skoolio.model.StudentInfo
import com.project.skoolio.model.userDetails.AddressDetails
import com.project.skoolio.model.userDetails.ContactDetails
import com.project.skoolio.model.userDetails.FatherDetails
import com.project.skoolio.model.userDetails.MotherDetails
import com.project.skoolio.model.userDetails.StudentSchoolDetails
import com.project.skoolio.model.userType.Student
import com.project.skoolio.navigation.AppScreens
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.Period
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Date
import java.util.Locale

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

@Composable
fun BackToHomeScreen(navController: NavHostController, userType: String?
//                     ,context: Context
) {
//    val activity = context as? Activity
    androidx.activity.compose.BackHandler(enabled = true) {
        navController.navigate(AppScreens.HomeScreen.name + "/$userType") {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
}

fun convertEpochToDateString(epoch: Long?): String? { // here value of epoch is expected to be in milli seconds
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = epoch?.let { Date(it) }
    return date?.let { dateFormat.format(it) }
}
@RequiresApi(Build.VERSION_CODES.O)
fun presentDateEpoch():Long{  //returns value in second
    val currentDate = LocalDate.now()
    return currentDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentMonthFromEpoch(): String { //needed to print month name in heading
    val date = Date(presentDateEpoch()*1000)
    val dateFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    return dateFormat.format(date)
}
@RequiresApi(Build.VERSION_CODES.O)
fun getMonthNameFromEpoch(epochValue: Long): String {
    val localDateTime = Instant.ofEpochMilli(epochValue*1000).atZone(ZoneId.systemDefault()).toLocalDateTime()
    return capitalize(Month.of(localDateTime.monthValue).toString().lowercase())
}
@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfMonthFromEpoch(epochSeconds: Long): Int {
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneId.systemDefault())
    return dateTime.dayOfMonth
}
@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfWeekFromEpoch(epochSeconds: Long): String {
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneId.systemDefault())
    val dayOfWeek = dateTime.dayOfWeek
    return dayOfWeek.toString().substring(0, 3)
}
@RequiresApi(Build.VERSION_CODES.O)
fun getFirstAndLastDayOfCurrentMonth(): Pair<Long, Long> { //needed to send range to backend
    val epochSeconds = presentDateEpoch()
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = epochSeconds * 1000
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    setToBeginningOfDay(calendar)
    val firstDayEpoch = calendar.timeInMillis / 1000
    calendar.add(Calendar.MONTH, 1)
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    setToBeginningOfDay(calendar)
    val lastDayEpoch = calendar.timeInMillis / 1000
    return Pair(firstDayEpoch, lastDayEpoch)
}

fun setToBeginningOfDay(calendar: Calendar) {
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getEpochValuesForMonth(epochValue:Long): List<Long> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = epochValue * 1000L // Convert seconds to milliseconds

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val numDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    val daysList = mutableListOf<Long>()
    for (day in 1..numDays) {
        calendar.set(year, month, day, 5 ,30)
        daysList.add(calendar.timeInMillis / 1000L)
    }
    return daysList.toList()
}

fun getPreviousMonthEpochs(currentEpoch: Long): Pair<Long, Long> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = currentEpoch * 1000 // Convert to milliseconds
    calendar.set(DAY_OF_MONTH, 1)
    calendar.add(MONTH, -1)
    val firstDayPrevMonth = calendar.timeInMillis / 1000  // Convert back to seconds
    calendar.set(DAY_OF_MONTH, calendar.getActualMaximum(DAY_OF_MONTH))
    val lastDayPrevMonth = calendar.timeInMillis / 1000
    return Pair(firstDayPrevMonth, lastDayPrevMonth)
}

fun getNextMonthEpochs(currentEpoch: Long): Pair<Long, Long> {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = currentEpoch * 1000 // Convert to milliseconds
    calendar.set(DAY_OF_MONTH, 1)
    calendar.add(MONTH, 1)
    val firstDayNextMonth = calendar.timeInMillis / 1000  // Convert back to seconds
    calendar.set(DAY_OF_MONTH, calendar.getActualMaximum(DAY_OF_MONTH))
    val lastDayNextMonth = calendar.timeInMillis / 1000

    return Pair(firstDayNextMonth, lastDayNextMonth)
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
    var listOfSchools:List<SchoolInfo> = listOf()

    fun getSchoolNames(): List<String> {
        return listOfSchools.map {
            capitalize(it.schoolName)
        }
    }
    fun getSchoolIdForSchoolName(schoolName:String): Int {
        Log.d("SchoolId", "Searched for $schoolName")
        val schoolInfo = listOfSchools.find { it.schoolName == schoolName.lowercase() }
            return schoolInfo!!.schoolId
    }

    fun printListOfSchools() {
        for(school in listOfSchools){
            Log.d("School List -",school.schoolId.toString()+", "+school.schoolName)
        }
    }

    fun setClassInfoOnSchool(schoolId: Int, classInfoList: List<String>) {
        val schoolInfo = listOfSchools.find { it.schoolId == schoolId }
        schoolInfo?.listOfClasses = classInfoList
    }

    fun IsClassListPopulatedForSchoolId(schoolId: Int): Boolean {
        val schoolInfo = listOfSchools.find { it.schoolId == schoolId }
        if(schoolInfo!=null && schoolInfo.listOfClasses == null){
            return false
        }
        return true
    }

    fun getClassNames(schoolName: String): List<String>? {
        val schoolInfo = listOfSchools.find { capitalize(it.schoolName) == schoolName }
        return schoolInfo?.listOfClasses?.map{ _class->
            capitalize(_class)
        }?.sorted()
    }
}


object CityList{
    var listOfCities:List<String> = listOf()
    fun getCities(): List<String> {
        return listOfCities.map { city->
            capitalize(city)
        }
    }
}




object UserType{
    var types = listOf("Student",
        "Teacher",
        "Admin"
    )
}


fun getDefaultStudent():Student{
    return Student("",
        "",
        "",
        "",
        0,'o',
        "",
        "",
        "",
        AddressDetails("","",""),
        ContactDetails("","","","","","",),
        StudentSchoolDetails(-1,"", "", mutableListOf()),
        FatherDetails("","",""),
        MotherDetails("","",""),
        "", "")
}

fun capitalize(string: String):String{
    return string.split(" ").joinToString(" ") {string->
        string.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ENGLISH
            ) else it.toString()
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
fun calculateAge(epochMillis: Long?): String {
    val birthDate = LocalDate.ofEpochDay(epochMillis!! / (24 * 60 * 60 * 1000))
    val currentDate = LocalDate.now()
    val age = Period.between(birthDate, currentDate)

    val years = age.years
    val months = age.months

    return "$years years, "+months + if(months == 1) " month" else " months"
}

fun getUserDrawerItemsList(userType: String?, navController: NavHostController): @Composable () -> Unit {
    if(userType == "Student")
        return studentDrawerItems(navController)
    else if(userType == "Teacher")
        return teacherDrawerItems(navController)

    return adminDrawerItems(navController)
}


val loginUserType = mutableStateOf("Student")


@RequiresApi(Build.VERSION_CODES.O)
fun presentDate():String{
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.ENGLISH)
    return today.format(formatter)
}

object classAttendance{
    var studentsAttendanceList:MutableList<Attendance> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun initializeStudentsAttendanceList(
        classStudentsList: MutableList<StudentInfo>,
        classId: String,
        takenBy: String,
        takerType: Char
    ) {
        for(student in classStudentsList){
            studentsAttendanceList.add(Attendance(presentDateEpoch(), student.studentId, classId, 'X', takenBy, takerType))
        }
    }

    fun addAttendance(studentId: String, attendanceValueSelected: String) {
        studentsAttendanceList.map {attendance->
            if(attendance.studentId == studentId){
                attendance.isPresent = when(attendanceValueSelected){
                    "Present"-> 'P'
                    "Absent"->'A'
                    else->'X'
                }
            }
        }
    }

    fun resetStudentsAttendanceList(){
        studentsAttendanceList = mutableListOf()
    }
}


fun formatName(firstName:String, middleName:String, lastName:String):String{
    return capitalize(firstName) +
            if (middleName.isNotEmpty())  " " + capitalize(middleName) else {""} +
            if (lastName != "-") " " + capitalize(lastName) else {""}
}