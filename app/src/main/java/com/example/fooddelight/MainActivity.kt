package com.example.fooddelight

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fooddelight.Fragments.NotificationBottomSheet
import com.example.fooddelight.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*val navView: BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.fragmentContainerView)
        navView.setupWithNavController(navController)*/
        val NavController = findNavController(R.id.fragmentContainerView)
        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomnav.setupWithNavController(NavController)
binding.notification.setOnClickListener {
    val bottomSheetDialog = NotificationBottomSheet()
    bottomSheetDialog.show(supportFragmentManager,"Test")
}


    }

}