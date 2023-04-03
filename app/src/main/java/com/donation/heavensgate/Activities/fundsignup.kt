package com.donation.heavensgate.Activities

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.donation.heavensgate.MainActivity
import com.donation.heavensgate.models.AddOrgModel
import com.donation.heavensgate.adapter.AddOrgImageAdapter
import com.donation.heavensgate.databinding.ActivityFundsignupBinding
import com.donation.heavensgate.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class fundsignup : AppCompatActivity() {
    lateinit var binding: ActivityFundsignupBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    private lateinit var list:ArrayList<Uri>
    private lateinit var listImages:ArrayList<String>
    private lateinit var adapter: AddOrgImageAdapter
    private var latterImage:Uri?=null
    private lateinit var dialog: Dialog
    private var latterImageUri:String?=""
    private var launchLatterActivity=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode== Activity.RESULT_OK){
            latterImage= it.data!!.data
            binding.LaterImage.setImageURI(latterImage)
            binding.LaterImage.visibility=VISIBLE
        }
    }
    private var launchOrgActivity=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode== Activity.RESULT_OK){
            val imageUrl= it.data!!.data
            list.add(imageUrl!!)
            adapter.notifyDataSetChanged()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFundsignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        list= ArrayList()
        listImages= ArrayList()

        binding.BtnUpload.setOnClickListener {
            val intent=Intent("android.intent.action.GET_CONTENT")
            intent.type="image/*"
            launchOrgActivity.launch(intent)
        }
        binding.LaterImage.setOnClickListener {
            val intent=Intent("android.intent.action.GET_CONTENT")
            intent.type="image/*"
            launchLatterActivity.launch(intent)
        }



        // ...
        // Initialize Firebase Auth
        auth = Firebase.auth
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success")
                    val user = auth.currentUser
                    updateUI(currentUser)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(currentUser)
                }
            }


        adapter= AddOrgImageAdapter(list)
        binding.OrgImages.adapter=adapter

        binding.BtnVerify.setOnClickListener {

            validateAllData()
        }
        binding.BtnNext.setOnClickListener {
            if (validateBasicData()) {

                createuser(binding.Email.text.toString(),binding.Pass.text.toString())

                binding.BasicCard.visibility = INVISIBLE
                binding.DetailedCard.visibility = VISIBLE
            }
        }

    }

    private fun createuser(Email: String, Pass: String) {
        auth.createUserWithEmailAndPassword(Email, Pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(baseContext, "Authentication Successfull.",
                        Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed."+task.exception,
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        Toast.makeText(this@fundsignup,"current user : \n $currentUser",Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateAllData() {
        if (binding.Details.text.toString().isEmpty()){
            binding.Details.requestFocus()
            binding.Details.error="Please Enter a Detail"
        }else if (binding.Address.text.toString().isEmpty()){
            binding.Address.requestFocus()
            binding.Address.error="Please Enter a Address"
        }else if (binding.City.text.toString().isEmpty()){
            binding.City.requestFocus()
            binding.City.error="Please Enter a City"
        }else if (binding.State.text.toString().isEmpty()){
            binding.State.requestFocus()
            binding.State.error="Please Enter a State"
        }else if (binding.Country.autofillValue.toString().isEmpty()){
            binding.OrgName.requestFocus()
            binding.OrgName.error="Please Enter a Country"
        }else if(latterImage==null){
            Toast.makeText(this@fundsignup,"please upload Latter of Assuarance",Toast.LENGTH_SHORT).show()
        }else if(list.size<1){
            Toast.makeText(this@fundsignup,"please upload organisation Images",Toast.LENGTH_SHORT).show()
        }
        else{
            uploadImage()
        }
    }

    private fun uploadImage() {
        val filename=UUID.randomUUID().toString()+".jpg"
        val refStorage=FirebaseStorage.getInstance().reference.child("Latter $filename")
        refStorage.putFile(latterImage!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener{ image ->
                    latterImageUri=image.toString()
                    uploadOrgImage()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this@fundsignup,"error in upload latter image",Toast.LENGTH_SHORT).show()
            }
    }

    private var i = 0
    private fun uploadOrgImage() {
        val filename=UUID.randomUUID().toString()+".jpg"
        val refStorage=FirebaseStorage.getInstance().reference.child("Organisations $filename")
        refStorage.putFile(list[i]!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener{image ->
                    listImages.add(image.toString())
                    if (list.size==listImages.size){
                        storeData()
                    }
                    else
                    {
                        i+=1
                        uploadOrgImage()

                    }

                }
            }
            .addOnFailureListener{
                Toast.makeText(this@fundsignup,"error in upload org image",Toast.LENGTH_SHORT).show()
            }

    }

    private fun storeData() {
        val db= Firebase.firestore.collection("Organisations")
        val key = db.document().id
        val data= AddOrgModel(
            key,
            binding.OrgName.text.toString(),
            binding.Email.text.toString(),
            binding.Pass.text.toString(),
            binding.Phone.text.toString(),
            binding.Details.text.toString(),
            binding.Address.text.toString(),
            binding.City.text.toString(),
            binding.State.text.toString(),
            binding.Country.text.toString(),
            latterImageUri.toString(),
            listImages)
        db.document(key).set(data)
            .addOnSuccessListener{

            binding.OrgName.text=null
            binding.Email.text=null
            binding.Pass.text=null
            binding.Phone.text=null
            binding.Details.text=null
            binding.Address.text=null
            binding.City.text=null
            binding.State.text=null
            binding.Country.text=null
            binding.LaterImage.setImageURI(null)



                Toast.makeText(this,"Organisation Added Successfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@fundsignup,MainActivity::class.java))
        }

            .addOnFailureListener {
                Toast.makeText(this,"Organisation add failed",Toast.LENGTH_SHORT).show()

            }
    }

    private fun validateBasicData() :Boolean{
        if (binding.OrgName.text.toString().isEmpty()){
            binding.OrgName.requestFocus()
            binding.OrgName.error="Please Enter a Name"
        }
        else if (binding.Phone.text.toString().isEmpty()){
            binding.Phone.requestFocus()
            binding.Phone.error="Please Enter a Phone"
        }else if (binding.Email.text.toString().isEmpty()){
            binding.Email.requestFocus()
            binding.Email.error="Please Enter a Email"
        }
        else if (binding.Pass.text.toString().isEmpty()){
            binding.Pass.requestFocus()
            binding.Pass.error="Please Create A New Password"
        }
        else if (binding.CnPass.text.toString().isEmpty() || binding.CnPass.text != binding.Pass.text){
            binding.CnPass.requestFocus()
            binding.CnPass.error="Password Doesn't Match"
        }
        else
        {
            return true
        }
        return false
    }


}


