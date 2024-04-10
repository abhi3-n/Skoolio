package com.project.skoolio.navigation

enum class AppScreens{
    SplashScreen,
    LoginScreen,
    SelectAccountTypeScreen,
    RegistrationFormScreen,
    OtpValidationScreen,
    SetPasswordScreen,

    //Screens After login
    //Common to students and teachers
    NewIssueScreen,


    //Common To students, teachers and admins
    HomeScreen,
    SettingsScreen,
    IssuesScreen,
    IssuesListScreen,
    IssueInfoScreen,

    //Common to teachers and admins
    TakeAttendanceScreen, //Only teacher and admin can access this page,
    ClassStudentsAttendanceScreen,

    //Only Accessible to Admins
    SchoolInformationScreen,
    PendingApprovalsScreen,
    AdminListScreen,
    TeacherListScreen,
    ClassListScreen,
    ClassStudentsListScreen,
    FullInfoScreen,


    //test
    TestScreen
}