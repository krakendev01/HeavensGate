package com.donation.heavensgate.models

data class AddOrgModel(
    var Org_Id: String? ,
    var Org_Name: String? ,
    var Org_Email: String? ,
    var Org_Pass: String? ,
    var Org_Phone: String? ,
    var Org_Description: String? ,
    var Org_Address: String? ,
    var Org_City: String? ,
    var Org_State: String? ,
    var Org_Country: String? ,
    var Org_Latter: String ,
    var Org_Principal : String,
    var Org_IsApproved : Boolean,
    var Org_NoStaff : Int,
    var Org_NoStudent : Int,
    var Org_Logo : String,
    var Org_Images: ArrayList<String>
) {
    constructor() : this("", "", "", "", "", "", "", "", "", "", "","",false,0,0,"", ArrayList())
}