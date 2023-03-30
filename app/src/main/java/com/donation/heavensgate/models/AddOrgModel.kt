package com.donation.heavensgate.models

data class AddOrgModel(
    val Org_Id:String? = "",
    val Org_Name:String? = "",
    val Org_Email:String? = "",
    val Org_Phone:String? = "",
    val Org_Description:String? = "",
    val Org_Address:String? = "",
    val Org_City:String? = "",
    val Org_State:String? = "",
    val Org_Country:String? = "",
    val Org_Latter:String? = "",
    val Org_Images:ArrayList<String>

)
