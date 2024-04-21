package com.project.skoolio.model.userDetails

import com.project.skoolio.utils.capitalize

open class AddressDetails(
    val addressLine:String,
    val city:String,
    val state:String
){
    fun getAddress():String{
        return capitalize(addressLine) +", " +
                capitalize(city) + ", " +
                capitalize(state)
    }

}
