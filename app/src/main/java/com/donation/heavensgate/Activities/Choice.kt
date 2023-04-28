package com.donation.heavensgate.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        binding.textView4.setOnClickListener {
            startActivity(Intent(this,SignIn::class.java))
        }
        binding.imageView3.setImageResource(R.drawable.join)
        binding.btndon.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@Choice,donsignup::class.java))
        })
    }

}