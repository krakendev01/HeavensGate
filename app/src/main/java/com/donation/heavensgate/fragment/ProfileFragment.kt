package com.donation.heavensgate.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.donation.heavensgate.Activities.SignIn
import com.donation.heavensgate.databinding.FragmentProfileBinding
import com.donation.heavensgate.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore
    lateinit var database : FirebaseDatabase
    lateinit var user : User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()


        FirebaseStorage.getInstance().reference

        database.reference.child("users")
            .child("donators")
            .child(auth.uid.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(User::class.java)!!
                    binding.curProfile.text = user.userName!!.substring(0,1).toString()
                    binding.curEmail.text = user.userEmail
                    binding.curName.text = user.userName
                    binding.curPhone.text = user.userPhoneNo
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })

        val curUid = auth.uid.toString()

        binding.LogOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(),SignIn::class.java))
        }
        binding.Update.setOnClickListener {
            binding.ViewProfileView.visibility= GONE
            binding.UpdateProfileView.visibility= VISIBLE

            database.reference.child("users")
                .child("donators")
                .child(auth.uid.toString())
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        user = snapshot.getValue(User::class.java)!!
                        binding.ETProfile.setText(user.userName!!.substring(0,1).toString())
                        binding.ETEmail.setText(user.userEmail)
                        binding.ETName.setText(user.userName)
                        binding.ETPhone.setText(user.userPhoneNo)
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(requireContext(),error.message,Toast.LENGTH_SHORT).show()
                    }
                })

        }
        binding.UpdateProfile.setOnClickListener {
            database.reference.child("users")
                .child("donators")
                .child(auth.uid.toString())
                .setValue(User(binding.ETPhone.text.toString(),user.userEmail,binding.ETName.text.toString(), user.userType))
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Profile Updated Successfully",Toast.LENGTH_SHORT).show()
                    binding.UpdateProfileView.visibility= GONE
                    binding.ViewProfileView.visibility= VISIBLE

                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT).show()
                }
        }



        return binding.root


    }

}