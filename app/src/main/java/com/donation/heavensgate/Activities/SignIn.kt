package com.donation.heavensgate.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.donation.heavensgate.MainActivity
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivitySigninBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit

class SignIn : AppCompatActivity() {
    lateinit var binding:ActivitySigninBinding
    private lateinit var auth : FirebaseAuth

    private var verificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySigninBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET),10)
        binding.BtnOrgSignin.setOnClickListener {
            startActivity(Intent(this@SignIn,Fund_SignIn::class.java))
        }

        binding.btnotp.setOnClickListener(View.OnClickListener {
            if (binding.phn.text.isEmpty())
            {
                binding.phn.error="Please Enter a phone number"
            }
            else
            {

                sendotp(binding.phn.text.toString())
            }
        })
        binding.imageView.setImageResource(R.drawable.logo)
        binding.imageView4.setImageResource(R.drawable.loginillu)
        binding.signup.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this@SignIn,Choice::class.java))
        })
        binding.VerifyOTP.setOnClickListener(View.OnClickListener {
            verifyotp(binding.etOTP.text.toString())
        })


    }


    // [START sign_in_with_phone]
    private fun verifyotp(otp: String) {

        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun sendotp(number: String) {

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                this@SignIn.verificationId = verificationId



                binding.materialCardView.visibility = GONE
                binding.verifyMaterialCardView.visibility = VISIBLE
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$number")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    checkuserExist(binding.phn.text.toString())
                } else {

                    Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun checkuserExist(number: String) {
        FirebaseDatabase.getInstance().getReference("users").child("+91$number")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@SignIn,p0.message,Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        startActivity(Intent(this@SignIn,MainActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@SignIn,MainActivity::class.java))
                        finish()
                    }
                }
            })

    }


}