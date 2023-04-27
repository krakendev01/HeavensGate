package com.donation.heavensgate.Activities

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.donation.heavensgate.databinding.ActivityFundSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Fund_SignIn : AppCompatActivity() {
    private lateinit var binding:ActivityFundSignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var pd:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityFundSignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // ...
        // Initialize Firebase Auth
        auth = Firebase.auth
        pd = ProgressDialog(this)
        pd.setTitle("Signing..")

        binding.BtnSignin.setOnClickListener {

            if (validateInputs()) {
                FundSingIn(binding.OrgEmail.text.toString(), binding.OrgPass.text.toString())
            }
        }

    }

    private fun validateInputs() :Boolean{
        if (binding.OrgEmail.text.toString().isEmpty()){
            binding.OrgEmail.requestFocus()
            binding.OrgEmail.error="Please Enter Email"
        }
        else if (binding.OrgPass.text.toString().isEmpty()){
            binding.OrgPass.requestFocus()
            binding.OrgPass.error="Please Enter Password"
        }
        else
        {
            return true
        }
        return false
    }

    private fun FundSingIn(Email: String, Pass: String) {
        auth.signInWithEmailAndPassword(Email,Pass )
            .addOnCompleteListener(this) { task ->
                pd.show()
                if (task.isSuccessful) {


                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(this@Fund_SignIn,"SignIn Success",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Fund_SignIn, MainActivity::class.java))


                } else {

                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed. \n Please Check Email Or Passord",
                        Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener{exception->
                pd.hide()
                Toast.makeText(this,exception.message, Toast.LENGTH_SHORT).show()
            }

    }
}