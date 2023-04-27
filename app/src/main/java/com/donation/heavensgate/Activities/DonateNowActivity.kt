package com.donation.heavensgate.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivityDonateNowBinding
import com.donation.heavensgate.models.AddOrgModel
import com.donation.heavensgate.models.Transaction
import com.donation.heavensgate.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import com.razorpay.PayloadHelper
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DonateNowActivity : AppCompatActivity() ,PaymentResultListener{
    lateinit var binding: ActivityDonateNowBinding
    lateinit var auth : FirebaseAuth
    lateinit var db : FirebaseFirestore
    lateinit var database : FirebaseDatabase
    lateinit var user : User
    lateinit var org : AddOrgModel
    lateinit var type : String
    var oId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateNowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()

        val orgUid = intent.getStringExtra("uid").toString()
        database.reference.child("users")
            .child("donators")
            .child(auth.uid.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(User::class.java)!!
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })

        db.collection("Organisations")
            .document(orgUid)
            .get()
            .addOnSuccessListener {
                if (it.exists()){
                    org = it.toObject(AddOrgModel::class.java)!!
                    Glide.with(this)
                        .load(org.Org_Logo)
                        .placeholder(R.drawable.account)
                        .into(binding.orgLogoDn)
                    var flag = false
                    binding.tasdeekNamaBtn.setOnClickListener {

                        if (flag){
                            binding.orgAssuranceLaterIv.visibility = View.GONE
                            flag = false
                        }else{
                            binding.orgAssuranceLaterIv.visibility = View.VISIBLE
                            flag = true
                        }
                    }
                    binding.orgPrincipalNameDn.text = "Principal Name : " + org.Org_Principal
                    binding.orgNameDn.text = org.Org_Name
                    binding.orgDescription.text=org.Org_Description
                    binding.orgStaffNoDn.text = "No. of Staff : "+org.Org_NoStaff.toString()
                    binding.orgStudentNoDn.text = "No. of Student : " + org.Org_NoStudent.toString()
                    binding.orgAddressDn.text = "No. of Address :  " + org.Org_Address + "," + org.Org_City + "," + org.Org_State + "," + org.Org_Country
                    Glide.with(this)
                        .load(org.Org_Latter)
                        .placeholder(R.drawable.addlogo)
                        .into(binding.orgAssuranceLaterIv)
                }
            }



        binding.orgDonateNowBtnDn.setOnClickListener {
            binding.donateNowMainCard.visibility = View.GONE
            binding.orgNameSb.text = binding.orgNameDn.text
            binding.donateNowSubCard.visibility = View.VISIBLE
            binding.lillah.isChecked = true


            binding.paynowSbBtn.setOnClickListener {

                if (binding.zakat.isChecked) {
                    type = binding.zakat.text.toString()
                } else if (binding.sadkah.isChecked) {
                    type = binding.sadkah.text.toString()
                }
                else
                    type=binding.lillah.text.toString()

                if (binding.donAmountSb.text.isEmpty()) {
                    binding.donAmountSb.requestFocus()
                    binding.donAmountSb.error = "Please Enter Amount"
                } else {

                    val co = Checkout()
                    // apart from setting it in AndroidManifest.xml, keyId can also be set
                    // programmatically during runtime
                    oId = Integer.toHexString(Random().nextInt(99999999))
                    co.setKeyID("rzp_test_5FCHZjoDEbaqUW")
                    val payloadHelper = PayloadHelper(
                        "INR",
                        Math.round(binding.donAmountSb.text.toString().toFloat() * 100).toInt(),
                        oId
                    )
                    payloadHelper.name = R.string.app_name.toString()
                    payloadHelper.description = "Description"
                    payloadHelper.prefillEmail = user.userEmail
                    payloadHelper.prefillContact = user.userPhoneNo
                    payloadHelper.prefillName = org.Org_Name
                    payloadHelper.sendSmsHash = true
                    payloadHelper.retryMaxCount = 4
                    payloadHelper.retryEnabled = true
                    payloadHelper.color = "#000000"
                    payloadHelper.allowRotation = true
                    payloadHelper.rememberCustomer = true
                    payloadHelper.timeout = 10
                    payloadHelper.redirect = true
                    payloadHelper.recurring = "1"
                    payloadHelper.subscriptionCardChange = true
                    payloadHelper.customerId = "cust_XXXXXXXXXX"
                    payloadHelper.callbackUrl = "https://accepts-posts.request"
                    payloadHelper.subscriptionId = "sub_XXXXXXXXXX"
                    payloadHelper.modalConfirmClose = true
                    payloadHelper.backDropColor = "#ffffff"
                    payloadHelper.hideTopBar = true
                    payloadHelper.notes = JSONObject("{\"remarks\":\"Discount to cusomter\"}")
                    payloadHelper.readOnlyEmail = true
                    payloadHelper.readOnlyContact = false
                    payloadHelper.readOnlyName = true
                    payloadHelper.image =
                        "https://firebasestorage.googleapis.com/v0/b/heaven-s-gate-4621a.appspot.com/o/logo.png?alt=media&token=c1527dd3-d4cf-4c69-8b33-f03c38a94fdd"
                    // these values are set mandatorily during object initialization. Those values can be overridden like this
                    payloadHelper.amount =
                        Math.round(binding.donAmountSb.text.toString().toFloat() * 100).toInt()
                    payloadHelper.currency = "INR"

                    startPayment()
                    /*GlobalScope.launch {
                    checkout.setKeyID("rzp_test_5FCHZjoDEbaqUW")
                    checkout.setImage(R.drawable.logo_icon)
                    val obj = JSONObject()
                    try {
                        // to put name
                        obj.put("name", R.string.app_name.toString())

                        // put description
                        obj.put("description", "You're paying to " + org.Org_Name)

                        // to set theme color
                        obj.put("send_sms_hash",true);
                        // put the currency
                        obj.put("currency", "INR")

                        // put amount
                        obj.put("amount", Math.round(binding.donAmountSb.text.toString().toFloat() * 100).toInt())

                        // put mobile number
                        obj.put("prefill.contact", user.userPhoneNo)

                        // put email
                        obj.put("prefill.email", user.userEmail)

                        // open razorpay to checkout activity
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    checkout.open(this@DonateNowActivity, obj)
                }*/
                }
            }
        }
    }
    private fun startPayment() {
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Heaven's Gate")
            options.put("description","You're Donating Rs" + Math.round(binding.donAmountSb.text.toString().toFloat() * 100).toInt() + " to " + org.Org_Name)
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","https://firebasestorage.googleapis.com/v0/b/heaven-s-gate-4621a.appspot.com/o/logo.png?alt=media&token=c1527dd3-d4cf-4c69-8b33-f03c38a94fdd")
            options.put("theme.color", R.color.colorPrimary);
            options.put("currency","INR");
            options.put("amount",Math.round(binding.donAmountSb.text.toString().toFloat() * 100).toInt())//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email",user.userEmail)
            prefill.put("contact",user.userPhoneNo)

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPaymentSuccess(p0: String?) {

        val now = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val formattedDateTime = now.format(formatter).toString()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            db.collection("trans")
                .document(oId)
                .set(Transaction(oId,auth.uid.toString(),org.Org_Id!!,formattedDateTime.toLong(),binding.donAmountSb.text.toString().toDouble(),type))
            // Create an AlertDialog.Builder instance
            val builder = AlertDialog.Builder(this)

// Set the dialog title
            builder.setTitle("Payment Status")

// Set the dialog message
            builder.setMessage("SUCCESSFULL")

// Add a button to the dialog
            builder.setPositiveButton("OK") { dialog, which ->
                // Do something when the user clicks the OK button
                startActivity(Intent(this,donatornmain::class.java))
            }

// Create and show the AlertDialog
            val dialog = builder.create()
            dialog.show()
        }
    }
    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,p0,Toast.LENGTH_LONG).show()
    }
}