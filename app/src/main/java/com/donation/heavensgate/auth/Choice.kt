package com.donation.heavensgate.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivityChoiceBinding

class Choice : AppCompatActivity() {
    lateinit var binding:ActivityChoiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityChoiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnfund.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@Choice,fundsignup::class.java))

        })
        binding.imageView3.setImageResource(R.drawable.join)
        binding.btndon.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this@Choice,donsignup::class.java))
        })
    }
}