package com.example.fooddelight

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelight.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private val binding : ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.textView8.setOnClickListener {
            val intent = Intent(this,SigninActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}