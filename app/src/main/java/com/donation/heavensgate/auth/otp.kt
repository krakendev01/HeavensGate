package com.donation.heavensgate.auth

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.donation.heavensgate.MainActivity
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class otp : AppCompatActivity() {
    lateinit var binding: ActivityOtpBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOtpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()


        var otp=intent.getStringExtra("OTP").toString()
        binding.btnsmt.setOnClickListener(View.OnClickListener {
            if (binding.edopt.text.isEmpty()){
                binding.edopt.error="Please enter OTP"
            }
            else {

                val cradential : PhoneAuthCredential = PhoneAuthProvider.getCredential(otp,binding.edopt.text.toString())
                signInWithPhoneAuthCredential(cradential)
            }
        })
    }

    // [START sign_in_with_phone]
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    Toast.makeText(this@otp,"success",Toast.LENGTH_SHORT).show()
                    sendtoMain()

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    // [END sign_in_with_phone]
    fun sendtoMain() {

        startActivity(Intent(this,MainActivity::class.java))

    }
}