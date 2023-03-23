package com.donation.heavensgate.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivityFundsignupBinding

class fundsignup : AppCompatActivity() {
    lateinit var binding: ActivityFundsignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFundsignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}