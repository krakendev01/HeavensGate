package com.donation.heavensgate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.donation.heavensgate.databinding.SampleDonatorListBinding
import com.donation.heavensgate.models.Transaction
import com.donation.heavensgate.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrgDonationAdapter(var translist: List<Transaction>):
    RecyclerView.Adapter<OrgDonationAdapter.OrgDonationViewHolder>() {

    class OrgDonationViewHolder(val binding:SampleDonatorListBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun setData(trans: Transaction) {
            var database = FirebaseDatabase.getInstance()
            var user = User()
            database.reference.child("users")
                .child("donators")
                .child(trans.donator)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        user = snapshot.getValue(User::class.java)!!
                        binding.DonLogo.setText(user.userName!!.substring(0,1).toString())
                        binding.DonAmount.text=trans.amount.toString()
                        binding.DonName.text=user.userName
                        binding.DonType.text=trans.type
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgDonationViewHolder {
        val binding=SampleDonatorListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrgDonationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrgDonationViewHolder, position: Int) {
        val trans=translist[position]
        holder.setData(trans)
    }

    override fun getItemCount(): Int {
        return translist.size

    }


}