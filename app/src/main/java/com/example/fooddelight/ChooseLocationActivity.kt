package com.example.fooddelight

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelight.databinding.ActivityChooseLocationBinding
import com.google.android.material.textfield.TextInputEditText

class ChooseLocationActivity : AppCompatActivity() {
    private val binding : ActivityChooseLocationBinding by lazy{
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locationlist = arrayOf("Delhi", "New Delhi","Gurgaon","Sonipat","Rohtak","Charkhi Dadri")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,locationlist)
        val  autoCompleteTextView = binding.listofcity
        autoCompleteTextView.setAdapter(adapter)


    }
}