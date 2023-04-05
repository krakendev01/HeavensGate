package com.donation.heavensgate.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.donation.heavensgate.R
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
        holder.binding.Orgdetail.text = org.Org_Description
        holder.binding.Orgname.text = org.Org_Name
        Glide.with(holder.binding.root.context.applicationContext)
            .load(org.Org_Images[0])
            .into(holder.binding.Orgimg)
    }
}