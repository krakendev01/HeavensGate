package com.donation.heavensgate.adapter

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.donation.heavensgate.databinding.SampleDonationListBinding
import com.donation.heavensgate.models.AddOrgModel
import com.donation.heavensgate.models.Transaction
import com.donation.heavensgate.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import java.io.*
import java.util.*

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
                        binding.time.text= myTrans.time
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
            val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Heavans_Gate")
            if (!dir.exists()) {
                dir.mkdirs()
            }

            val file = File(dir, "${Date().time}.pdf")
            val pdfFile = file
            try {
                if (!pdfFile.exists())
                    pdfFile.createNewFile()
                val document = Document()
                PdfWriter.getInstance(document, FileOutputStream(pdfFile))
                document.open()
                val stream = ByteArrayOutputStream()
                layout.isDrawingCacheEnabled = true

// Measure and layout the view to its regular size
                val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(layout.width, View.MeasureSpec.EXACTLY)
                val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(layout.height, View.MeasureSpec.EXACTLY)
                layout.measure(widthMeasureSpec, heightMeasureSpec)
                layout.layout(0, 0, layout.measuredWidth, layout.measuredHeight)

                // Enable drawing cache before creating bitmap
                layout.isDrawingCacheEnabled = true

                // Create a bitmap of the entire layout
                val bitmap = Bitmap.createBitmap(layout.width, layout.height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                layout.draw(canvas)
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
                val img = Image.getInstance(stream.toByteArray())
                val scaler: Float = (document.pageSize.width - document.leftMargin()
                        - document.rightMargin() - 0) / img.getWidth() * 100
                img.scalePercent(scaler)
                img.setAlignment(Image.ALIGN_CENTER or Image.ALIGN_TOP)
                document.add(img)
                document.close()
                Toast.makeText(layout.context,"Check out Downloads/Heavans_Gate folder",Toast.LENGTH_LONG).show()

                Log.d("PDF","Generated Successfully")

                // Save the PDF file to the device's media store
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val resolver = layout.context.contentResolver
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
                        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/Heavans_Gate")
                    }
                    resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                } else {
                    val mediaScannerIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                    mediaScannerIntent.data = Uri.fromFile(pdfFile)
                        layout.context.sendBroadcast(mediaScannerIntent)

                }
            } catch (e :Exception){
                e.printStackTrace()
            }
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