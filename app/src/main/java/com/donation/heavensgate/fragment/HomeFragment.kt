package com.donation.heavensgate.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.donation.heavensgate.R
import com.donation.heavensgate.adapter.DonatorMainOrgDisplayAdapter
import com.donation.heavensgate.databinding.FragmentHomeBinding
import com.donation.heavensgate.models.AddOrgModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore
    lateinit var database : FirebaseDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()

//        db.collection("Organisations")
//            .get()
//            .addOnCompleteListener { p0 ->
//                if (p0.isSuccessful) {
//                    if (p0.result.documents.isNotEmpty()) {
//                        for (i in p0.result.documents) {
//                            val org = i.toObject(AddOrgModel::class.java)
//                            orgList.add(org!!)
//                        }
//                    }
//                    binding.OrgList.adapter = DonatorMainOrgDisplayAdapter(orgList)
//                }
//            }
        db.collection("Organisations")
            .get()
            .addOnSuccessListener { value ->
                var orgList = ArrayList<AddOrgModel>()
                for (i in value) {
                        var org: AddOrgModel? = i.toObject(AddOrgModel::class.java)
                        orgList.add(i.toObject<AddOrgModel>())
                    }
                Log.d("ORG LIST", orgList.toString())
                Log.d("value",value.toString())
                binding.OrgList.adapter = DonatorMainOrgDisplayAdapter(orgList)
//                        Log.d("Org list in interface",orgList.toString())
            }.addOnFailureListener { exception ->
                Log.d("DB ERROR", "Error getting documents: ", exception)
            }
        return binding.root

    }


}