package com.donation.heavensgate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.donation.heavensgate.databinding.FragmentSearchBinding
import com.donation.heavensgate.models.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {
    lateinit var binding : FragmentSearchBinding
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore
    lateinit var database : FirebaseDatabase
    lateinit var transList : ArrayList<Transaction>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()

        db.collection("trans")

            .whereEqualTo("donator",auth.uid.toString())
            .get()
            .addOnSuccessListener {
                if (it.isEmpty)
                    return@addOnSuccessListener
                else{
                    it.documents.forEach {
                    val trans = it.toObject(Transaction::class.java)!!
                        transList.add(trans)
                    }
                    //binding.transListRv.adapter = DonatorRecieptAdapter(transList)
                }
            }





        return binding.root
    }
}