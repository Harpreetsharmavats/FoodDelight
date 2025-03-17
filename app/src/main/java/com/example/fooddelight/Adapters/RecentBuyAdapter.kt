package com.example.fooddelight.Adapters

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelight.databinding.RecentbuyitemBinding

class RecentBuyAdapter(
    private var recentFoodName: MutableList<String>,
    private var recentFoodPrice: MutableList<String>,
    private var recentFoodImage: MutableList<String>,
    private var recentFoodQuantity: MutableList<Int>,
    private var isOrderAccepted: Boolean,
    private var paymentReceived: Boolean,
    private var context: Context,

    private val itemClicked : OnItemClicked,
    private val itemViewClicked : OnItemClicked
) : RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>() {
interface OnItemClicked{
    fun onItemClickListener(position: Int)
    fun onItemViewClickListener(position: Int)
}

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
            binding.receivedbtn.setOnClickListener {
                itemClicked.onItemClickListener(position)
            }

            if (isOrderAccepted){
                binding.statusbtn.background.setTint(Color.GREEN)
                binding.receivedbtn.visibility = View.VISIBLE
            }else{
                binding.statusbtn.background.setTint(Color.GRAY)

            }
            if (paymentReceived){
                binding.receivedbtn.visibility = View.GONE

            }
            itemView.setOnClickListener{
                itemViewClicked.onItemViewClickListener(position)
            }

        }

    }
}