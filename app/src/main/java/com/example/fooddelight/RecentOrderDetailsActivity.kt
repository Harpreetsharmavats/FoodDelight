package com.example.fooddelight

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelight.Adapters.RecentOrderDetailsAdapter
import com.example.fooddelight.Models.OrderDetails
import com.example.fooddelight.databinding.ActivityRecentOrderDetailsBinding

class RecentOrderDetailsActivity : AppCompatActivity() {
    private val binding:ActivityRecentOrderDetailsBinding by lazy {
        ActivityRecentOrderDetailsBinding.inflate(layoutInflater)
    }
    private var foodName:MutableList<String> = mutableListOf()
    private var foodImage:MutableList<String> = mutableListOf()
    private var foodQuantity:MutableList<Int> = mutableListOf()
    private var foodPrice:MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        getOrdersData()
binding.payoutbackbtn.setOnClickListener {
    finish()
}
    }

    private fun getOrdersData() {
val orderDetails = intent.getSerializableExtra("ListOfItems") as OrderDetails
        orderDetails.let {
            foodName = orderDetails.foodName!!
            foodImage = orderDetails.foodImage!!
            foodQuantity = orderDetails.foodQuantity!!
            foodPrice = orderDetails.foodPrice!!
        }
        setAdapter()
    }

    private fun setAdapter() {
val adapter = RecentOrderDetailsAdapter(foodName,foodImage,foodQuantity,foodPrice,this)
        binding.recentorderdetailrv.layoutManager = LinearLayoutManager(this)
        binding.recentorderdetailrv.adapter = adapter
    }
}