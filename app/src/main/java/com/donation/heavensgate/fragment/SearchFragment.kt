package com.donation.heavensgate.fragment

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.donation.heavensgate.adapter.DonatorRecieptAdapter
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

        transList = ArrayList()
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val grantResults = permissions.map {
            ContextCompat.checkSelfPermission(this.requireContext(), it)
        }.toIntArray()

        if (grantResults.contains(PackageManager.PERMISSION_DENIED)) {
            ActivityCompat.requestPermissions(this.requireActivity(), permissions, 1)
        }else{

        }
        db.collection("trans")
            .whereEqualTo("donator",auth.uid.toString())
            .get()
            .addOnSuccessListener {
                if (it.isEmpty)
                binding.TVDonation.setText("No Donations till now")
                else{
                    it.documents.forEach {it1->
                        val trans = it1.toObject(Transaction::class.java)!!
                        transList.add(trans)
                    }
                    binding.transListRv.adapter = DonatorRecieptAdapter(transList)
                }
            }
        return binding.root
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, do something with the storage
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(context, "Storage permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}