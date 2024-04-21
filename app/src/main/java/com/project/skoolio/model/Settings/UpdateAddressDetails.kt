package com.project.skoolio.model.Settings

import com.project.skoolio.model.userDetails.AddressDetails

class UpdateAddressDetails(
    addressLine: String,
    city: String,
    state: String,
    val id :String
) : AddressDetails(addressLine, city, state) {
}