package com.donation.heavensgate.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.donation.heavensgate.Activities.SignIn
import com.donation.heavensgate.databinding.FragmentFundProfileBinding
import com.donation.heavensgate.models.AddOrgModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 * Use the [fund_profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fund_profile : Fragment() {

    lateinit var binding:FragmentFundProfileBinding
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore
    lateinit var database : FirebaseDatabase
    lateinit var org:AddOrgModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding= FragmentFundProfileBinding.inflate(layoutInflater)


        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        db.collection("Organisations")
            .document(auth.uid.toString())
            .get()
            .addOnSuccessListener {
                value->
                org= value.toObject<AddOrgModel>(AddOrgModel::class.java)!!
                binding.fundEmailpro.text=org.Org_Email
                Glide.with(binding.root.context.applicationContext)
                    .load(org.Org_Logo)
                    .into(binding.fundLogo)
                binding.fundNamepro.text=org.Org_Name
                binding.fundPhonepro.text=org.Org_Phone
                binding.fundPrinciplepro.text=org.Org_Principal
                binding.fundDescription.text=org.Org_Description
                Glide.with(binding.root.context.applicationContext)
                    .load(org.Org_Latter)
                    .into(binding.fundLatterPro)

                //Edit Text data loading
                binding.fundEmailpro.text=org.Org_Email
                binding.ETPhone.setText(org.Org_Phone)
                Glide.with(binding.root.context.applicationContext)
                    .load(org.Org_Logo)
                    .into(binding.updateFundLogo)
                binding.ETName.setText(org.Org_Name)
                binding.ETEmail.setText(org.Org_Email)
                binding.ETPrincipal.setText(org.Org_Principal)
                binding.ETDescription.setText(org.Org_Description)
                Glide.with(binding.root.context.applicationContext)
                    .load(org.Org_Latter)
                    .into(binding.updateFundLatterPro)

                binding.ETStaff.setText(org.Org_NoStaff.toString())
                binding.ETNoStudent.setText(org.Org_NoStaff.toString())


            }
            .addOnFailureListener{exception->
                Toast.makeText(requireContext(),exception.message,Toast.LENGTH_SHORT).show()
            }
        binding.fundProLogoutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(),SignIn::class.java))
        }
        binding.fundProUpdateBtn.setOnClickListener {
            binding.fundProfileView.visibility=View.GONE
            binding.UpdateFundProfileView.visibility=View.VISIBLE
        }
        binding.BtnUpdate.setOnClickListener {
            db.collection("Organisations")
                .document(auth.uid.toString())
                .set(AddOrgModel(
                    org.Org_Id.toString(),
                    org.Org_Name,
                    org.Org_Email,
                    org.Org_Pass,
                    binding.ETPhone.text.toString(),
                    binding.ETDescription.text.toString(),
                    org.Org_Address,
                    org.Org_City,
                    org.Org_State,
                    org.Org_Country,
                    org.Org_Latter,
                    binding.ETPrincipal.text.toString(),
                    false,
                    binding.ETStaff.text.toString().toInt(),
                    binding.ETNoStudent.text.toString().toInt(),
                    org.Org_Logo,
                    org.Org_Images
                ))
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Organisation Updated Successfully",Toast.LENGTH_SHORT).show()
                    binding.UpdateFundProfileView.visibility=View.GONE
                    binding.fundProfileView.visibility=View.VISIBLE

                }
                .addOnFailureListener{exception->
                    Toast.makeText(requireContext(),exception.message,Toast.LENGTH_SHORT).show()
                }
        }








        // Inflate the layout for this fragment
        return binding.root
    }


}