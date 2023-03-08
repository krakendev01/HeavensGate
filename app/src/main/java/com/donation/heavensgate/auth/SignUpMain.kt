package com.donation.heavensgate.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivitySignUpMainBinding
import com.donation.heavensgate.databinding.ActivitySigninBinding

class SignUpMain : AppCompatActivity() {
    lateinit var binding:ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setImageResource(R.drawable.logo)
        binding.imageView4.setImageResource(R.drawable.loginillu)
        binding.signup.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this@SignUpMain,donsignup::class.java))
        })
    }
}