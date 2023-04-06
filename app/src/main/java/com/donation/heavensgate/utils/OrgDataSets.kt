package com.donation.heavensgate.utils

import android.app.Activity
import android.util.Log
import com.donation.heavensgate.models.AddOrgModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage

class OrgDataSets(val context : Activity) {

    // Don't use this class and don't make any object of this class
    // made for just testing perpose
        val database = FirebaseDatabase.getInstance()
        val storage = FirebaseStorage.getInstance()
        val db = FirebaseFirestore.getInstance()
        public var orgList: MutableList<AddOrgModel> = mutableListOf()
        fun getOrgDataList(): MutableList<AddOrgModel> {
            var list = mutableListOf<AddOrgModel>()
            db.collection("Organisations")
                .get()
                .addOnSuccessListener { value ->
                    context.runOnUiThread {
                        for (i in value) {
                            var org: AddOrgModel? = i.toObject(AddOrgModel::class.java)
                            orgList.add(org!!)
                        }
                        Log.d("ORG LIST in paret", this.orgList.toString())
//                        Log.d("Org list in interface",orgList.toString())
                    }
                }.addOnFailureListener { exception ->
                    Log.d("DB ERROR", "Error getting documents: ", exception)
                }
            return orgList
        }

    private fun setOrgDataList(orgList: MutableList<AddOrgModel>) {
        this.orgList = orgList
        Log.d("ORG LIST", this.orgList.toString())
    }
}