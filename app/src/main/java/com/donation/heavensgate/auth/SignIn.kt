package com.donation.heavensgate.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivitySigninBinding

class SignIn : AppCompatActivity() {
    lateinit var binding:ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySigninBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.imageView.setImageResource(R.drawable.logo)
        binding.imageView4.setImageResource(R.drawable.loginillu)
        binding.signup.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this@SignIn,donsignup::class.java))
        })
    }
}