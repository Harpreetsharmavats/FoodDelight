package com.example.fooddelight

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelight.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val foodName = intent.getStringExtra("items")
        val foodImage = intent.getIntExtra("images",0)
        binding.detailfoodname.text = foodName
        binding.detailfoodimage.setImageResource(foodImage)
        binding.detailbackbtn.setOnClickListener {
           finish()
        }
    }
}