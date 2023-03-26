package com.donation.heavensgate.Activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.donation.heavensgate.MainActivity
import com.donation.heavensgate.adapter.AddOrgImageAdapter
import com.donation.heavensgate.databinding.ActivityFundsignupBinding
import com.donation.heavensgate.models.FundUsers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

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
            val imageUrl= it.data!!.data
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

        adapter= AddOrgImageAdapter(list)
        binding.OrgImages.adapter=adapter

        binding.BtnVerify.setOnClickListener {
            validateAllData()
        }
        binding.BtnNext.setOnClickListener {
            validateBasicData()
        }









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
        }else if(listImages.size<1){
            Toast.makeText(this@fundsignup,"please upload organisation Images",Toast.LENGTH_SHORT).show()
        }
        else{
            uploadImage()
        }
    }

    private fun uploadImage() {

    }

    private fun validateBasicData() {
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
    }

}


