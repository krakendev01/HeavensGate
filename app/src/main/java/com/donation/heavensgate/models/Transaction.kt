package com.donation.heavensgate.models

import java.time.LocalDateTime

data class Transaction(
    var transId:String,
    var donator : String,
    var fundraiser : String,
    var time : Long,
    var amount : Double,
    var type : String
){
    constructor() : this("","","",0,0.0,"")
}