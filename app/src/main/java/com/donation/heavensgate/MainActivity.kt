package com.donation.heavensgate

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.donation.heavensgate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val naveHostFragment = supportFragmentManager.findFragmentById(R.id.fundfragmentcontainer)
        val navController = naveHostFragment!!.findNavController()

        val popupMenu= PopupMenu(this, null)
        popupMenu.inflate(R.menu.fund_bottom_nav)
        binding.fundbottomBar.setupWithNavController(popupMenu.menu,navController)

        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                title = when(destination.id){
                    R.id.FundHomeFragment -> "Home"
                    else -> "Profile"
                }
            }

        })

    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }
}