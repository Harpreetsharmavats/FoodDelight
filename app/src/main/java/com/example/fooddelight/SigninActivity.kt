package com.example.fooddelight

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelight.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private val binding : ActivitySigninBinding by lazy {
        ActivitySigninBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.textView8.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}