package com.example.fooddelight

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelight.Fragments.CartFragment
import com.example.fooddelight.Fragments.CongratulationBottomSheet
import com.example.fooddelight.databinding.ActivityPayoutBinding

class PayoutActivity : AppCompatActivity() {
    private val binding:ActivityPayoutBinding by lazy {
        ActivityPayoutBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.payoutbackbtn.setOnClickListener {
            val intent = Intent(this,CartFragment::class.java)
            startActivity(intent)
        }
        binding.placemyorderbtn.setOnClickListener {
            val bottomSheetFragment = CongratulationBottomSheet()
            bottomSheetFragment.show(supportFragmentManager,"Test")
        }
    }
}