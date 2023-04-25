package com.donation.heavensgate.Activities

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.donation.heavensgate.databinding.ActivityDonsignupBinding
import com.donation.heavensgate.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class donsignup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityDonsignupBinding
    lateinit var database: FirebaseDatabase
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDonsignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()

        binding.btnotp.setOnClickListener {

            if (validateBasicData()) {

                EmailSignup(binding.UserEmail.text.toString(), binding.Pass.text.toString())
                user= User(binding.UserPhone.text.toString(),binding.UserEmail.text.toString(),binding.UserName.text.toString(),"Don")
                database.getReference("users")
                    .child("donators")
                    .child(auth.uid.toString())
                    .setValue(user)
            }


        }

    }

    private fun validateBasicData() :Boolean{
        if (binding.UserName.text.toString().isEmpty()){
            binding.UserName.requestFocus()
            binding.UserName.error="Please Enter a Name"
        }
        else if (binding.UserPhone.text.toString().isEmpty()){
            binding.UserPhone.requestFocus()
            binding.UserPhone.error="Please Enter a Phone"
        }else if (binding.UserEmail.text.toString().isEmpty()){
            binding.UserEmail.requestFocus()
            binding.UserEmail.error="Please Enter a Email"
        }
        else if (binding.Pass.text.toString().isEmpty()){
            binding.Pass.requestFocus()
            binding.Pass.error="Please Enter a Email"
        }
        else if (binding.CnPass.text.toString().isEmpty()){
            binding.CnPass.requestFocus()
            binding.CnPass.error="Please Enter a Email"
        }
        else
        {
            return true
        }
        return false
    }


    private fun EmailSignup(Email: String, Pass: String) {

        auth.createUserWithEmailAndPassword(Email, Pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}
