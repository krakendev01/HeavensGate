package com.donation.heavensgate.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivityOtpBinding

class otp : AppCompatActivity() {

    lateinit var binding: ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
    }
}