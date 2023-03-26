package com.donation.heavensgate.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.donation.heavensgate.databinding.ActivityFundsignupBinding
import com.donation.heavensgate.databinding.ImagesItemBinding

class AddOrgImageAdapter(val list: ArrayList<Uri>) :RecyclerView.Adapter<AddOrgImageAdapter.AddOrgImageViewHolder>(){
    inner class AddOrgImageViewHolder(val binding:ImagesItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddOrgImageViewHolder {
        val binding=ImagesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddOrgImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AddOrgImageViewHolder, position: Int) {
        holder.binding.ImageItem.setImageURI(list[position])
    }
}