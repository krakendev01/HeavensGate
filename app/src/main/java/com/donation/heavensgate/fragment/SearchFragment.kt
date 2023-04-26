package com.donation.heavensgate.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.donation.heavensgate.adapter.DonatorRecieptAdapter
import com.donation.heavensgate.databinding.FragmentSearchBinding
import com.donation.heavensgate.models.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

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

        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()

        db.collection("trans")

            .whereEqualTo("donator",auth.uid.toString())
            .get()
            .addOnSuccessListener { value ->

                if (value.isEmpty)
                    Toast.makeText(requireContext(),"error::",Toast.LENGTH_SHORT).show()
                else{
                    var myTransList=ArrayList<Transaction>()
                    for (i in value){

                        myTransList.add(i.toObject())
                    }
//                    it.documents.forEach {
//                    val trans = it.toObject(Transaction::class.java)
//                        if (trans != null) {
//                            transList.add(trans)
//                        }
//                    }
                    binding.transListRv.adapter = DonatorRecieptAdapter(myTransList)
                }
            }
            .addOnFailureListener{exception->
                Log.d("DB ERROR", "Error getting documents: ", exception)
            }





        return binding.root
    }
}