package com.example.fooddelight.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelight.databinding.RecentbuyitemBinding

class RecentBuyAdapter(
    private var recentFoodName: MutableList<String>,
    private var recentFoodPrice: MutableList<String>,
    private var recentFoodImage: MutableList<String>,
    private var recentFoodQuantity: MutableList<Int>,
    private var context: Context
) : RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        return RecentViewHolder(
            RecentbuyitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = recentFoodName.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class RecentViewHolder(private val binding: RecentbuyitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.recentfooditem.text = recentFoodName[position]
            binding.recentprice.text = recentFoodPrice[position]
            val uriString = recentFoodImage[position]
            val uri = Uri.parse(uriString)
            Glide.with(context).load(uri).into(binding.recentfoodimage)
            binding.quantity.text = recentFoodQuantity[position].toString()
        }

    }
}