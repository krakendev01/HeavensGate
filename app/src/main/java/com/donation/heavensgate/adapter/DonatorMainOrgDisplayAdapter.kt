package com.donation.heavensgate.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.donation.heavensgate.DonateNowActivity
import com.donation.heavensgate.databinding.OrglistLayoutBinding
import com.donation.heavensgate.models.AddOrgModel

class DonatorMainOrgDisplayAdapter(var orgList: List<AddOrgModel>) :
    RecyclerView.Adapter<DonatorMainOrgDisplayAdapter.DonatorMainOrgDisplayViewHolder>() {


    inner class DonatorMainOrgDisplayViewHolder(val binding: OrglistLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

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
        Glide.with(holder.binding.root.context.applicationContext)
            .load(org.Org_Logo)
            .into(holder.binding.orgLogoIv)
        holder.binding.orgNameTv.text = org.Org_Name
        holder.binding.orgPrincipalNameTv.text = org.Org_Principal
        holder.binding.orgNoOfStudentTv.text = org.Org_NoStudent.toString()
        holder.binding.orgDonateNowBtn.setOnClickListener {
            //Handle on Click
            val intent = Intent(holder.binding.root.context,DonateNowActivity::class.java)
            intent.putExtra("uid",org.Org_Id)
            holder.binding.root.context.startActivity(intent)
        }

    }
}