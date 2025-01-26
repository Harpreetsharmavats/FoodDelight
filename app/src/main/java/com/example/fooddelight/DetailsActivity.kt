package com.example.fooddelight

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fooddelight.Models.CartItems
import com.example.fooddelight.databinding.ActivityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private var foodName: String? = null
    private var foodPrice: String? = null
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodIngredient: String? = null
    private lateinit var auth: FirebaseAuth
    private val binding: ActivityDetailsBinding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        foodName = intent.getStringExtra("ItemName")
        foodPrice = intent.getStringExtra("ItemPrice")
        foodDescription = intent.getStringExtra("ItemDescription")
        foodIngredient = intent.getStringExtra("ItemIngredients")
        foodImage = intent.getStringExtra("ItemImage")

        with(binding) {
            detailfoodname.text = foodName
            detaildescription.text = foodDescription
            detailingredients.text = foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailfoodimage)

        }
        binding.detailbackbtn.setOnClickListener {
            finish()
        }
        binding.detailsaddtocart.setOnClickListener {
            addToCart()
        }
    }

    private fun addToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""

        val cartItem = CartItems(
            foodName.toString(),
            foodIngredient.toString(),
            foodImage.toString(),
            foodDescription.toString(),
            foodPrice.toString(),
            1
        )
        database.child("user").child(userId).child("cartItems").push().setValue(cartItem)
            .addOnSuccessListener {
                Toast.makeText(this, "Item added to cart successfully", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
            Toast.makeText(this, "Failed add to cart ", Toast.LENGTH_SHORT).show()

        }
    }
}