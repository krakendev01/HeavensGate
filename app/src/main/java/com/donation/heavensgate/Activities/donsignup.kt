package com.donation.heavensgate.Activities

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable.ProgressDrawableSize
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivityDonsignupBinding
import com.donation.heavensgate.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class donsignup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityDonsignupBinding
    lateinit var database: FirebaseDatabase
    lateinit var user: User
    lateinit var pd:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDonsignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        pd = ProgressDialog(this)
        pd.setTitle("signin IN")
        binding.imageView2.setImageResource(R.drawable.signuppic)

        binding.btnotp.setOnClickListener {

            if (validateBasicData()) {

                EmailSignup(binding.UserEmail.text.toString(), binding.Pass.text.toString())

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
            binding.Pass.error="Please Enter a password"
        }
        else if (binding.CnPass.text.toString().isEmpty()){
            binding.CnPass.requestFocus()
            binding.CnPass.error="Please Enter a password"
        }
        else if (binding.CnPass.editableText.toString() != binding.Pass.editableText.toString()){
            Toast.makeText(this,"Password Does not Match",Toast.LENGTH_SHORT).show()
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
                pd.show()
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    user= User(binding.UserPhone.text.toString(),binding.UserEmail.text.toString(),binding.UserName.text.toString(),"Don")
                    database.getReference("users")
                        .child("donators")
                        .child(task.result.user!!.uid.toString())
                        .setValue(user).addOnSuccessListener {
                            startActivity(Intent(this,donatornmain::class.java))
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                pd.hide()
                Toast.makeText(baseContext, "Authentication failed. Try again",
                    Toast.LENGTH_SHORT).show()

            }
    }

}
