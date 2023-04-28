package com.donation.heavensgate.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.donation.heavensgate.adapter.OrgDonationAdapter
import com.donation.heavensgate.databinding.FragmentFundHomeBinding
import com.donation.heavensgate.models.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 * Use the [fund_home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fund_home : Fragment() {
    private lateinit var binding: FragmentFundHomeBinding
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore
    lateinit var database : FirebaseDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFundHomeBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()


        db.collection("trans")
            .whereEqualTo("fundraiser",auth.uid.toString())
            .get()
            //.whereEqualTo("oId",auth.uid.toString())
            .addOnSuccessListener { value ->
                if (value.isEmpty) {
                    binding.TVDonation.setText("NO DONATIONS FOR YOUR ORGANISATION")
                    Toast.makeText(requireContext(),"no Donations for your Organisation",Toast.LENGTH_SHORT).show()
                } else {

                var transList = ArrayList<Transaction>()
                value.forEach {
                    val trans: Transaction? = it.toObject(Transaction::class.java)
                    transList.add(trans!!)
                }
                Log.d("ORG LIST", transList.toString())
                Log.d("value", value.toString())
                binding.donatorlist.adapter = OrgDonationAdapter(transList)
//                        Log.d("Org list in interface",orgList.toString())

            }
            }.addOnFailureListener { exception ->
                Log.d("DB ERROR", "Error getting documents: ", exception)
            }

        // Inflate the layout for this fragment
        return binding.root
    }
}