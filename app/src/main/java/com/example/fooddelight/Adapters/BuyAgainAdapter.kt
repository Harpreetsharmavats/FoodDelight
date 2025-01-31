package com.example.fooddelight.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelight.databinding.BuyagainBinding

class BuyAgainAdapter(
    private var buyAgainFoodName: MutableList<String>,
    private var buyAgainFoodPrice: MutableList<String>,
    private var buyAgainFoodImage: MutableList<String>,
    private var requireContext: Context
) : RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        return BuyAgainViewHolder(
            BuyagainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = buyAgainFoodName.size

   inner class BuyAgainViewHolder(private val binding: BuyagainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.buyagainfooditems.text = buyAgainFoodName[position]
            binding.buyagainprice.text = buyAgainFoodPrice[position]
            val uriString = buyAgainFoodImage[position]
            val uri = Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.buyagainfoodimage)

        }


    }

}