package com.donation.heavensgate.models

import com.google.type.DateTime

data class Transaction(
    var transId:String,
    var donator: String,
    var fundraiser: String,
    var time: String,
    var amount: Double,
    var type: String
){
    constructor() : this("","","","",0.0,"")
}