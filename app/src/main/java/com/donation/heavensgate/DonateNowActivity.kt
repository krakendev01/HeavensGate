package com.donation.heavensgate

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.donation.heavensgate.databinding.ActivityDonateNowBinding
import com.donation.heavensgate.models.AddOrgModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class DonateNowActivity : AppCompatActivity() {
    lateinit var binding: ActivityDonateNowBinding
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore
    lateinit var database : FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateNowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()

        val orgUid = intent.getStringExtra("uid").toString()

        db.collection("Organisations")
            .document(orgUid)
            .get()
            .addOnSuccessListener {
                if (it.exists()){
                    val org:AddOrgModel = it.toObject(AddOrgModel::class.java)!!
                    Glide.with(this)
                        .load(org.Org_Logo)
                        .placeholder(R.drawable.account)
                        .into(binding.orgLogoDn)
                    var flag = false
                    binding.tasdeekNamaBtn.setOnClickListener {

                        if (flag){
                            binding.orgAssuranceLaterIv.visibility = View.GONE
                            flag = false
                        }else{
                            binding.orgAssuranceLaterIv.visibility = View.VISIBLE
                            flag = true
                        }
                    }
                    binding.orgPrincipalNameDn.text = "Principal Name : " + org.Org_Principal
                    binding.orgNameDn.text = org.Org_Name
                    binding.orgStaffNoDn.text = "No. of Staff : "+org.Org_NoStaff.toString()
                    binding.orgStudentNoDn.text = "No. of Student : " + org.Org_NoStudent.toString()
                    binding.orgAddressDn.text = "No. of Address :  " + org.Org_Address + "," + org.Org_City + "," + org.Org_State + "," + org.Org_Country
                    Glide.with(this)
                        .load(org.Org_Latter)
                        .placeholder(R.drawable.addlogo)
                        .into(binding.orgAssuranceLaterIv)
                }
            }

        binding.orgDonateNowBtnDn.setOnClickListener {
            binding.donateNowMainCard.visibility = View.GONE
            binding.orgNameSb.text = binding.orgNameDn.text
            binding.donateNowSubCard.visibility = View.VISIBLE
            var type = "Lillah"
            if (binding.zakat.isChecked) {
                type = "Zakat"
            } else if (binding.sadkah.isChecked) {
                type = "Sadkah"
            }
        }
    }
}