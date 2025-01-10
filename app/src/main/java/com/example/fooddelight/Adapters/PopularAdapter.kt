package com.example.fooddelight.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelight.databinding.PopularBinding

class PopularAdapter(private val items:List<String>,private val prices:List<String>,private val images:List<Int>) :RecyclerView.Adapter<PopularAdapter.PopularViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularAdapter.PopularViewHolder {
return PopularViewHolder(PopularBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularAdapter.PopularViewHolder, position: Int) {
        val items = items[position]
        val images = images[position]
        val prices = prices[position]
        holder.bind(items,prices,images)
    }

    override fun getItemCount(): Int {
return items.size
    }
    class PopularViewHolder(private val binding: PopularBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.foodimagePopular
        fun bind(items: String, prices: String, images: Int) {
            binding.foodnamePopular.text= items
            binding.pricePopular.text= prices
            image.setImageResource(images)

        }

    }
}
