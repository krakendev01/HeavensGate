package com.donation.heavensgate.models

data class User(
    val userPhoneNo: String?,
    val userEmail:String?,
    val userName:String?,
    val userType:String?
) {
    constructor() : this(null,null,null,null)
}
