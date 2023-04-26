package com.donation.heavensgate.Activities

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.donation.heavensgate.R
import com.donation.heavensgate.databinding.ActivityDonatormainBinding

class donatornmain : AppCompatActivity() {
    private lateinit var binding : ActivityDonatormainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonatormainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val naveHostFragment = supportFragmentManager.findFragmentById(R.id.frafmentcontainer)
        val navController = naveHostFragment!!.findNavController()

        val popupMenu= PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav)
        binding.bottomBar.setupWithNavController(popupMenu.menu,navController)

        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                title = when(destination.id){
                    R.id.DonationFragment -> "Donations"
                    R.id.ProfileFragment -> "My Profile"
                    else -> "Home"
                }
            }

        })
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }
}