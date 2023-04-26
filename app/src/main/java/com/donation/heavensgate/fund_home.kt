package com.donation.heavensgate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.donation.heavensgate.adapter.DonatorMainOrgDisplayAdapter
import com.donation.heavensgate.adapter.OrgDonationAdapter
import com.donation.heavensgate.databinding.FragmentFundHomeBinding
import com.donation.heavensgate.databinding.FragmentHomeBinding
import com.donation.heavensgate.models.AddOrgModel
import com.donation.heavensgate.models.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fund_home.newInstance] factory method to
 * create an instance of this fragment.
 */
class fund_home : Fragment() {
    private lateinit var binding:FragmentFundHomeBinding
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
            .get()
            .addOnSuccessListener { value ->
                var transList = ArrayList<Transaction>()
                value.forEach {
                    val trans: Transaction? = it.toObject(Transaction::class.java)
                    transList.add(trans!!)
                }
                Log.d("ORG LIST", transList.toString())
                Log.d("value",value.toString())
                binding.donatorlist.adapter = OrgDonationAdapter(transList)
//                        Log.d("Org list in interface",orgList.toString())
            }.addOnFailureListener { exception ->
                Log.d("DB ERROR", "Error getting documents: ", exception)
            }

        // Inflate the layout for this fragment
        return binding.root
    }
}