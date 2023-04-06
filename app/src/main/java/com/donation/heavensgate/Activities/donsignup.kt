package com.donation.heavensgate.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.donation.heavensgate.databinding.ActivityDonsignupBinding
import com.donation.heavensgate.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class donsignup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityDonsignupBinding
    lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDonsignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnotp.setOnClickListener {
            FirebaseDatabase.getInstance().reference.child("users")
                .child(binding.edphn.text.toString())
                .setValue(User(binding.edphn.text.toString(),binding.edml.text.toString(),binding.ednm.text.toString(),"DON"))
        }

    }

}
