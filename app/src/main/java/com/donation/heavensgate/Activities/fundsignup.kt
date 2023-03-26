package com.donation.heavensgate.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.donation.heavensgate.MainActivity
import com.donation.heavensgate.databinding.ActivityFundsignupBinding
import com.donation.heavensgate.models.FundUsers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class fundsignup : AppCompatActivity() {
    lateinit var binding: ActivityFundsignupBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var users: FundUsers
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFundsignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()







    }
}


