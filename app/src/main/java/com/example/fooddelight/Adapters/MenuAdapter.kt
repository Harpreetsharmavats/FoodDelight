package com.example.fooddelight.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelight.databinding.MenuBinding
import com.example.fooddelight.databinding.PopularBinding

class MenuAdapter(
    private val items: MutableList<String>,
    private val prices: MutableList<String>,
    private val images: MutableList<Int>
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {
//return MenuViewHolder(PopularBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        val binding = PopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val items = items[position]
        val images = images[position]
        val prices = prices[position]
        holder.bind(items, prices, images)
    }

    override fun getItemCount(): Int = items.size


    inner class MenuViewHolder(private val binding: PopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.foodimagePopular
        fun bind(items: String, prices: String, images: Int) {
            binding.foodnamePopular.text = items
            binding.pricePopular.text = prices
            image.setImageResource(images)

        }

    }

}