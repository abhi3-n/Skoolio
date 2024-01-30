package com.project.skoolio.data

data class DataOrException<T,Boolean, E:Exception>(
    var data:T,
    var loading:Boolean? = null,
    var exception: E? = null
)
