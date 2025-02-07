package com.example.fooddelight.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelight.databinding.RecentorderdetailsallitemscardBinding

class RecentOrderDetailsAdapter( private var foodName: MutableList<String>,
                                 private var foodImage: MutableList<String>,
                                 private var foodQuantity: MutableList<Int>,
                                 private var foodPrice: MutableList<String>,
                                 private var context: Context
    ):RecyclerView.Adapter<RecentOrderDetailsAdapter.RecentOrderDetailsViewHolder> (){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentOrderDetailsViewHolder {
        return RecentOrderDetailsViewHolder(RecentorderdetailsallitemscardBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = foodName.size

    override fun onBindViewHolder(holder: RecentOrderDetailsViewHolder, position: Int) {
       holder.bind(position)
    }

    inner class RecentOrderDetailsViewHolder(private val binding: RecentorderdetailsallitemscardBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.recentfooditem.text =foodName[position]
            val uriString = foodImage[position]
            val uri = Uri.parse(uriString)
            Glide.with(context).load(uri).into(binding.recentfoodimage)
            binding.quantity.text = foodQuantity[position].toString()
            binding.recentprice.text = foodPrice[position]
        }

    }
}