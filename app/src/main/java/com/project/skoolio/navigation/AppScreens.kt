package com.project.skoolio.navigation

enum class AppScreens{
    SplashScreen,
    LoginScreen,
    SelectAccountTypeScreen,
    RegistrationFormScreen,
    OtpValidationScreen,
    SetPasswordScreen,

    //Common To students, teachers and admins
    HomeScreen,
    SettingsScreen,


    //Common to teachers and admins
    TakeAttendanceScreen, //Only teacher and admin can access this page;
    IssueReportingScreen,
    ClassStudentsAttendanceScreen,

    //Only Accessible to Admins
    SchoolInformationScreen,
    PendingApprovalsScreen
}