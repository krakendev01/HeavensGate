package com.donation.heavensgate.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.donation.heavensgate.Activities.DonateNowActivity
import com.donation.heavensgate.databinding.OrglistLayoutBinding
import com.donation.heavensgate.models.AddOrgModel

class DonatorMainOrgDisplayAdapter(var orgList: List<AddOrgModel>) :
    RecyclerView.Adapter<DonatorMainOrgDisplayAdapter.DonatorMainOrgDisplayViewHolder>() {


    class DonatorMainOrgDisplayViewHolder(val binding: OrglistLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun setData(org : AddOrgModel){
            Glide.with(binding.root.context.applicationContext)
                .load(org.Org_Logo)
                .into(binding.orgLogoIv)
            binding.orgNameTv.text = org.Org_Name
            binding.orgPrincipalNameTv.text = org.Org_Principal
            binding.orgNoOfStudentTv.text = org.Org_NoStudent.toString()
            binding.orgDonateNowBtn.setOnClickListener {
                //Handle on Click
                val intent = Intent(binding.root.context, DonateNowActivity::class.java)
                intent.putExtra("uid",org.Org_Id)
                binding.root.context.startActivity(intent)
            }

        }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DonatorMainOrgDisplayViewHolder {
        val binding =
            OrglistLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DonatorMainOrgDisplayViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return orgList.size
    }

    override fun onBindViewHolder(holder: DonatorMainOrgDisplayViewHolder, position: Int) {
        val org = orgList[position]
        holder.setData(org)


    }
}