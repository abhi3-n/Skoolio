package com.project.skoolio.viewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.project.skoolio.model.Settings.UpdateAddressDetails
import com.project.skoolio.model.Settings.UpdateContactDetails
import com.project.skoolio.model.singletonObject.adminDetails
import com.project.skoolio.model.singletonObject.studentDetails
import com.project.skoolio.model.singletonObject.teacherDetails
import com.project.skoolio.network.Backend
import com.project.skoolio.utils.loginUserType
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(private val backend: Backend):ViewModel() {
    fun updateAddressDetails(
        updateAddressDetails: UpdateAddressDetails,
        context: Context,
        navController: NavHostController
    ) {
        try {
            viewModelScope.launch {
                if (loginUserType.value == "Student") {
                    backend.updateStudentAddressDetails(updateAddressDetails)
                    studentDetails.addressLine.value = updateAddressDetails.addressLine
                    studentDetails.city.value = updateAddressDetails.city
                    studentDetails.state.value = updateAddressDetails.state
                } else if (loginUserType.value == "Teacher") {
                    backend.updateTeacherAddressDetails(updateAddressDetails)
                    teacherDetails.addressLine.value = updateAddressDetails.addressLine
                    teacherDetails.city.value = updateAddressDetails.city
                    teacherDetails.state.value = updateAddressDetails.state
                } else {
                    backend.updateAdminAddressDetails(updateAddressDetails)
                    adminDetails.addressLine.value = updateAddressDetails.addressLine
                    adminDetails.city.value = updateAddressDetails.city
                    adminDetails.state.value = updateAddressDetails.state
                }
                Toast.makeText(context,"Address updated successfully", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
        catch (e:Exception){
            Toast.makeText(context,"Some error occurred in updating address - ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateContactDetails(
        updateContactDetails: UpdateContactDetails,
        context: Context,
        navController: NavHostController
    ) {
        try {
            viewModelScope.launch {
                if (loginUserType.value == "Student") {
                    backend.updateStudentContactDetails(updateContactDetails)
                    studentDetails.primaryContact.value = updateContactDetails.primaryContact
                    studentDetails.primaryContactName.value = updateContactDetails.primaryContactName
                    studentDetails.primaryContactRelation.value = updateContactDetails.primaryContactRelation!!
                    studentDetails.alternativeContact.value = updateContactDetails.alternativeContact
                    studentDetails.alternativeContactName.value = updateContactDetails.alternativeContactName
                    studentDetails.alternativeContactRelation.value = updateContactDetails.alternativeContactRelation!!
                } else if (loginUserType.value == "Teacher") {
                    backend.updateTeacherContactDetails(updateContactDetails)
                    teacherDetails.primaryContact.value = updateContactDetails.primaryContact
                    teacherDetails.primaryContactName.value = updateContactDetails.primaryContactName
                    teacherDetails.alternativeContact.value = updateContactDetails.alternativeContact
                    teacherDetails.alternativeContactName.value = updateContactDetails.alternativeContactName
                } else {
                    backend.updateAdminContactDetails(updateContactDetails)
                    adminDetails.primaryContact.value = updateContactDetails.primaryContact
                    adminDetails.primaryContactName.value = updateContactDetails.primaryContactName
                    adminDetails.alternativeContact.value = updateContactDetails.alternativeContact
                    adminDetails.alternativeContactName.value = updateContactDetails.alternativeContactName
                }
                Toast.makeText(context,"Contacts updated successfully", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
        catch (e:Exception){
            Toast.makeText(context,"Some error occurred in updating contact - ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}