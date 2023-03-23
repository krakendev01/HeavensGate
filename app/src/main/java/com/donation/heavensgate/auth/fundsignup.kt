package com.donation.heavensgate.auth

import android.content.Intent
import android.net.MailTo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.donation.heavensgate.MainActivity
import com.donation.heavensgate.R
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

        binding.button2.setOnClickListener{
            if (binding.Name.text.isEmpty()){
                binding.Name.error="feild cant be emptey"
            }
            if (binding.Email.text.isEmpty()){
                binding.Email.error="feild cant be emptey"
            }
            if (binding.Phone.text.isEmpty()){
                binding.Phone.error="feild cant be emptey"
            }
            if (binding.Pass.text.isEmpty()){
                binding.Pass.error="feild cant be emptey"
            }
            if (binding.ConfirmPass.text.isEmpty())
                binding.ConfirmPass.error="feild cant be emptey"

            auth.createUserWithEmailAndPassword(binding.Email.text.toString(),binding.Pass.text.toString())
                .addOnSuccessListener {

                     users= FundUsers(binding.Name.text.toString(),binding.Email.text.toString(),binding.Phone.text.toString(),binding.Pass.text.toString())
                    database.reference.child("fundusers")
                        .child(binding.Email.text.toString())
                        .setValue(users).addOnSuccessListener {
                            Toast.makeText(this,"successfully inserted", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@fundsignup,MainActivity::class.java))
                        }
                }

        }





    }
}


