package com.project.skoolio.navigation

enum class AppScreens{
    SplashScreen,
    LoginScreen,
    SelectAccountTypeScreen,
    RegistrationFormScreen,
    OtpValidationScreen,
    SetPasswordScreen,
    ForgotPasswordScreen,

    //Screens After login
    //Common to students and teachers
    NewIssueScreen,

    //For students and admins
    FeePaymentScreen,
    PendingFeeScreen,
    FeeHistoryScreen,


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
    UpdatePaymentScreen,
    MonthlyPaymentDetailsScreen,
    CreateFeesScreen,


    //test
    TestScreen
}