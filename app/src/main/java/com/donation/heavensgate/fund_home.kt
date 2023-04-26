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
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentFundHomeBinding
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore
    lateinit var database : FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFundHomeBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()


        db.collection("transactions")
            .get()
            .addOnSuccessListener { value ->
                var transList = ArrayList<Transaction>()
                for (i in value) {
                    var trans: Transaction? = i.toObject(Transaction::class.java)
                    transList.add(i.toObject<Transaction>())
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fund_home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fund_home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}