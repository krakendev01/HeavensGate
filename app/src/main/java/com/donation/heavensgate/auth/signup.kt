package com.donation.heavensgate.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivitySigninBinding
import com.donation.heavensgate.databinding.ActivitySignupBinding

class signup : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnfund.setOnClickListener(View.OnClickListener {
        })
        startActivity(Intent(this@signup,fundsignup::class.java))
        binding.btndon.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this@signup,donsignup::class.java))
        })
    }
}