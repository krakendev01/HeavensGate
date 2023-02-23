package com.donation.heavensgate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView

class spalsh : AppCompatActivity() {
    lateinit var  imageview:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_spalsh)
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN

        imageview =findViewById(R.id.image)
        val slidanimation= AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in)
        imageview.startAnimation(slidanimation)

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },2500)
    }
}