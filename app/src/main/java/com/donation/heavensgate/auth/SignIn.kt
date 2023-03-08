package com.donation.heavensgate.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivitySignUpMainBinding

class SignIn : AppCompatActivity() {
    lateinit var binding:ActivitySignUpMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUpMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnfund.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@SignIn,fundsignup::class.java))

        })
        binding.imageView3.setImageResource(R.drawable.supimg)
        binding.btndon.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this@SignIn,donsignup::class.java))
        })
    }
}