package com.donation.heavensgate.models

class FundUsers {


        lateinit  var Name:String
        lateinit var Email:String
        lateinit var Phone:String
        lateinit  var Pass:String


    constructor(Name: String, Email: String, Phone: String, Pass: String) {
        this.Name = Name
        this.Email = Email
        this.Phone = Phone
        this.Pass = Pass
    }
}