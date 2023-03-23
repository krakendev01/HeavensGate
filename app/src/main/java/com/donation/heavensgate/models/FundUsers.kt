package com.donation.heavensgate.models

class FundUsers {


      private  lateinit  var Name:String
      private  lateinit var Email:String
      private  lateinit var Phone:String
      private  lateinit  var Pass:String


    constructor(Name: String, Email: String, Phone: String, Pass: String) {
        this.Name = Name
        this.Email = Email
        this.Phone = Phone
        this.Pass = Pass
    }
}