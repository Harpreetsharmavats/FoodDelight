package com.example.fooddelight.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelight.databinding.BuyagainBinding

class BuyAgainAdapter(private var buyAgainFoodName :ArrayList<String>,private var buyAgainFoodPrice :ArrayList<String>,private var buyAgainFoodImage :ArrayList<Int>) :RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        return BuyAgainViewHolder(BuyagainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }



    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(buyAgainFoodName[position],buyAgainFoodPrice[position],buyAgainFoodImage[position])
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

    class BuyAgainViewHolder(private val binding: BuyagainBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(foodName: String, Price: String, foodImage: Int) {
            binding.buyagainfooditems.text = foodName
            binding.buyagainprice.text = Price
            binding.buyagainfoodimage.setImageResource(foodImage)
        }


    }

}