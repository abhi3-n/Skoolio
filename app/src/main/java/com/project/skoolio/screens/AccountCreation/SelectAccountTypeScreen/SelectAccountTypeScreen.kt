package com.project.skoolio.screens.AccountCreation.SelectAccountTypeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.R
import com.project.skoolio.components.CircularProgressIndicatorCustom
import com.project.skoolio.components.CustomButton
import com.project.skoolio.components.CustomDropDownMenu
import com.project.skoolio.navigation.AppScreens
import com.project.skoolio.utils.CityList
import com.project.skoolio.utils.SchoolList
import com.project.skoolio.utils.UserType
import com.project.skoolio.viewModels.ViewModelProvider

//@Preview
//@Composable
//fun select(){
//    SelectAccountTypeScreen()
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAccountTypeScreen(
    navController: NavHostController,
    viewModelProvider: ViewModelProvider
){
    val selectedAccountType = rememberSaveable {
        mutableStateOf("")
    }
    val loading = rememberSaveable() {
        mutableStateOf(false)
    }

    val img = rememberSaveable {
        mutableStateOf(0)
    }
    val context = LocalContext.current
//    val accountTypeList = UserType.types
    val cityList = CityList.getCities()
    val registrationScreenViewModel = viewModelProvider.getRegistrationScreenViewModel()
    val selectedSchoolCity = registrationScreenViewModel.selectedSchoolCity

    img.value = when(selectedAccountType.value){
        "Student"->R.drawable.students
        "Teacher"->R.drawable.teacher
//        "Admin"->R.drawable.admin
        else-> 0
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        if(img.value != 0){
            Image(painter = painterResource(id = img.value ),
                contentDescription = null /*TODO:implement Image description*/)
        }
        Text(text = "Select Account Type")
        Spacer(modifier = Modifier.height(20.dp))
        CustomDropDownMenu(
            selectedValue =  selectedAccountType,
            dataList =  UserType.types,
            registrationScreenViewModel = null
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Select City")
        Spacer(modifier = Modifier.height(20.dp))
        CustomDropDownMenu(
            selectedValue =  selectedSchoolCity,
            dataList =  cityList,
            registrationScreenViewModel = null
        )
        Spacer(modifier = Modifier.height(20.dp))

        CustomButton(onClick = {
            val goToFormScreen:(NavHostController, MutableState<String>)->Unit ={navController:NavHostController,selectedAccountType:MutableState<String>->
                navController.navigate(AppScreens.RegistrationFormScreen.name + "/${selectedAccountType.value}")
            }
            if(SchoolList.listOfSchools.isEmpty() || SchoolList.listForCity != selectedSchoolCity.value) {
                registrationScreenViewModel.getCitySchools(context, goToFormScreen, navController, selectedAccountType, loading)
            }
            else{
                goToFormScreen(navController, selectedAccountType)
            }
        },
            enabled =  if (selectedAccountType.value.isNotEmpty()
                && selectedSchoolCity.value.isNotEmpty()
                ) true
            else false,
            content = {
                if(loading.value){
                    CircularProgressIndicatorCustom()
                }
                else {
                    Text(text = "Next")
                }
            })
    }
}




