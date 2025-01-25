package com.example.fooddelight

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.fooddelight.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private  var foodName:String?= null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodIngredient: String? = null
    private val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        foodName = intent.getStringExtra("ItemName")
        //foodPrice =intent.getStringExtra("ItemPrice")
        foodDescription = intent.getStringExtra("ItemDescription")
        foodIngredient = intent.getStringExtra("ItemIngredients")
        foodImage = intent.getStringExtra("ItemImage")

        with(binding){
            detailfoodname.text = foodName
            detaildescription.text = foodDescription
            detailingredients.text = foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailfoodimage)

        }
        binding.detailbackbtn.setOnClickListener {
           finish()
        }
    }
}