package com.donation.heavensgate.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.donation.heavensgate.R
import com.donation.heavensgate.adapter.DonatorMainOrgDisplayAdapter
import com.donation.heavensgate.databinding.ActivityDonatormainBinding
import com.donation.heavensgate.models.AddOrgModel
import com.donation.heavensgate.utils.OrgDataSets
import com.google.firebase.firestore.FirebaseFirestore

class donatornmain : AppCompatActivity() {
    lateinit var binding : ActivityDonatormainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonatormainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val list = mutableListOf<AddOrgModel>()
        val db = FirebaseFirestore.getInstance()
        db.collection("Organisations")
            .get()
            .addOnSuccessListener { value ->
                binding.progressBarOrgList.visibility = View.GONE
                runOnUiThread {
                    for (i in value) {
                        var org: AddOrgModel? = i.toObject(AddOrgModel::class.java)
                        list.add(org!!)
                    }
                    Log.d("ORG LIST", list.toString())
//                        Log.d("Org list in interface",orgList.toString())
                }
                var donAdapter = DonatorMainOrgDisplayAdapter(list)
                binding.OrgList.adapter = donAdapter
                donAdapter.notifyDataSetChanged()
            }.addOnFailureListener { exception ->
                Log.d("DB ERROR", "Error getting documents: ", exception)
            }
        binding.OrgList.layoutManager = LinearLayoutManager(this)
    }
}