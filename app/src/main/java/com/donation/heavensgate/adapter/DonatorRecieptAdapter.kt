package com.donation.heavensgate.adapter

import android.graphics.Bitmap
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.donation.heavensgate.databinding.SampleDonationListBinding
import com.donation.heavensgate.models.AddOrgModel
import com.donation.heavensgate.models.Transaction
import com.donation.heavensgate.models.User
import com.github.florent37.expansionpanel.ExpansionLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class DonatorRecieptAdapter(var myTransList: List<Transaction>) :
    RecyclerView.Adapter<DonatorRecieptAdapter.DonatorRecieptViewHolder>() {
    class DonatorRecieptViewHolder(val binding:SampleDonationListBinding):
    RecyclerView.ViewHolder(binding.root)
    {
        fun setData(myTrans: Transaction) {

            var database=FirebaseDatabase.getInstance()
            var auth=FirebaseAuth.getInstance()
            var db=FirebaseFirestore.getInstance()
            var don=User()
            var org=AddOrgModel()

            database.reference.child("users")
                .child("donators")
                .child(myTrans.donator)
                .addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        don= snapshot.getValue(User::class.java)!!
                        binding.amount.text="₹"+myTrans.amount.toString()
                        binding.typ.text=myTrans.type
                        binding.time.text=myTrans.time.toString()
                        binding.amountEx.text="₹"+myTrans.amount.toString()

                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
            binding.downloadBtn.setOnClickListener {
                generatePdf(binding.expansionLayout)
            }
            db.collection("Organisations")
                .document(myTrans.fundraiser)
                .get()
                .addOnSuccessListener { value->
                     org= value.toObject(AddOrgModel::class.java)!!
                    Glide.with(binding.root.context.applicationContext)
                        .load(org.Org_Logo)
                        .into(binding.ologo)
                    Glide.with(binding.root.context.applicationContext)
                        .load(org.Org_Logo)
                        .into(binding.ologoEx)

                    binding.onametv.text=org.Org_Name
                    binding.onametvEx.text=org.Org_Name

                }


        }

        

        private fun generatePdf(layout: View) {
            val pdfFile = File(Environment.getExternalStorageDirectory(), "myPdfFile.pdf")
            val document = Document()
            PdfWriter.getInstance(document, FileOutputStream(pdfFile))
            document.open()
            val stream = ByteArrayOutputStream()
            layout.isDrawingCacheEnabled = true
            Bitmap.createBitmap(layout.drawingCache).compress(Bitmap.CompressFormat.PNG, 100, stream)
            val image = Image.getInstance(stream.toByteArray())
            document.add(image)
            document.close()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonatorRecieptViewHolder {

        val binding=SampleDonationListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DonatorRecieptViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return myTransList.size
    }

    override fun onBindViewHolder(holder: DonatorRecieptViewHolder, position: Int) {
        val myTrans=myTransList[position]
        holder.setData(myTrans)
    }
}