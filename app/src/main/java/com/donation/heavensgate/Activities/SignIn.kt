package com.donation.heavensgate.Activities

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class SignIn : AppCompatActivity() {
    lateinit var binding:ActivitySigninBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var pd:ProgressDialog
/*
    private var verificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var storedVerificationId: String*/

    private fun validateBasicData() :Boolean{
        if (binding.Email.text.toString().isEmpty()){
            binding.Email.requestFocus()
            binding.Email.error="Please Enter a Name"
        }

        else if (binding.Pass.text.toString().isEmpty()){
            binding.Pass.requestFocus()
            binding.Pass.error="Please Enter a Email"
        }

        else
        {
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
    binding = ActivitySigninBinding.inflate(layoutInflater)
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

        binding.imageView.setImageResource(R.drawable.logo)
        binding.imageView4.setImageResource(R.drawable.loginillu)


        auth = FirebaseAuth.getInstance()



    binding.SignIn.setOnClickListener {
        if (validateBasicData())
        {
            donatorSingIn(binding.Email.text.toString(),binding.Pass.text.toString())
        }
    }
        if (auth.currentUser!=null){
            Log.d("uid",auth.currentUser!!.uid.toString())
            binding.materialCardView.visibility = VISIBLE
            binding.textView.visibility = VISIBLE
            binding.signup.visibility = VISIBLE
            binding.progressBarSignin.visibility = GONE
            checkOrg(auth.uid.toString())
        }



    // in future inshallah

    /*auth.firebaseAuthSettings.forceRecaptchaFlowForTesting(false)
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
                signIn(binding.phn.text.toString().trim())
            }
        })

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
        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$number")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
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

    fun signIn(phone : String){
        val verificationCode = binding.etOTP.text.toString()

        if (verificationCode.isNotEmpty()) {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, verificationCode)
            signInWithPhoneAuthCredentialNew(credential)
        }
       val phoneAuthCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // auto-retrieval of verification code completed, sign-in with credential
                signInWithPhoneAuthCredentialNew(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // verification failed, handle error
                Log.e(TAG, "onVerificationFailed", e)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // verification code sent to the user's phone number
                storedVerificationId = verificationId
                resendToken = token
            }
        }
        if (phone.isNotEmpty()) {
            // use default options to start phone number verification
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91$phone",
                60, // timeout duration
                TimeUnit.SECONDS,
                this,
                phoneAuthCallbacks
            )
        }
    }
    private fun signInWithPhoneAuthCredentialNew(credential: PhoneAuthCredential) {
        auth.firebaseAuthSettings.forceRecaptchaFlowForTesting(false)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // sign-in success, update UI with signed-in user's information
                    Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show()
                    val user = task.result?.user
                } else {
                    // sign-in failed, handle error
                    Log.e(TAG, "signInWithCredential:failure", task.exception)
                }
            }*/
        pd = ProgressDialog(this)
        pd.setTitle("signin IN")
    binding.signup.setOnClickListener {
        startActivity(Intent(this,Choice::class.java))
    }
        binding.BtnOrgSignin.setOnClickListener {
            startActivity(Intent(this,Fund_SignIn::class.java))
        }
}

    private fun checkOrg(uid: String){
        binding.materialCardView.visibility = GONE
        binding.textView.visibility = GONE
        binding.signup.visibility = GONE
        binding.progressBarSignin.visibility = VISIBLE
        val db = FirebaseFirestore.getInstance()
        db.collection("Organisations")
            .document(uid)
            .get()
            .addOnSuccessListener {
                if (it.exists()){
                    startActivity(Intent(this, MainActivity::class.java))
                }else
                    startActivity(Intent(this,donatornmain::class.java))
            }
    }

    override fun onBackPressed() {
        if (auth == null){
         finishAffinity()
        }
    }

    private fun donatorSingIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                pd.show()
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    startActivity(Intent(this,donatornmain::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception->
                pd.hide()
                Toast.makeText(this,exception.message,Toast.LENGTH_SHORT).show()
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
                        startActivity(Intent(this@SignIn,donatornmain::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this@SignIn,donatornmain::class.java))
                        finish()
                    }
                }
            })

    }
}