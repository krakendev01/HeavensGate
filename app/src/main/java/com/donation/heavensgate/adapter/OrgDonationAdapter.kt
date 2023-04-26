package com.donation.heavensgate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.donation.heavensgate.databinding.SampleDonatorListBinding
import com.donation.heavensgate.models.Transaction

class OrgDonationAdapter(var translist: List<Transaction>):
    RecyclerView.Adapter<OrgDonationAdapter.OrgDonationViewHolder>() {

    class OrgDonationViewHolder(val binding:SampleDonatorListBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun setData(trans: Transaction) {
            binding.DonAmount.text=trans.amount.toString()
            binding.DonName.text=trans.donator
            binding.DonType.text=trans.type

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