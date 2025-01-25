package com.example.fooddelight.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.car.ui.toolbar.MenuItem.OnClickListener
import com.bumptech.glide.Glide
import com.example.fooddelight.DetailsActivity
import com.example.fooddelight.Models.Menu
import com.example.fooddelight.databinding.PopularBinding

class PopularAdapter(
    private val menuItem:List<Menu>,
    private val requireContext: Context
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularViewHolder {
        return PopularViewHolder(PopularBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularAdapter.PopularViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return menuItem.size
    }

    inner class PopularViewHolder(private val binding: PopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        openDetailsActivity(position)
                    }

                }
            }




        fun bind( position: Int) {
            val image = binding.foodimagePopular
            val menuItem =menuItem[position]
            binding.foodnamePopular.text = menuItem.foodName
            binding.pricePopular.text = menuItem.foodPrice
            val uri = Uri.parse(menuItem.foodImage)
            Glide.with(requireContext).load(uri).into(image)

        }

    }

    private fun openDetailsActivity(position: Int) {
        val menuItem = menuItem[position]
        val intent = Intent(requireContext,DetailsActivity::class.java).apply {
            putExtra("ItemName",menuItem.foodName)
            putExtra("ItemPrice",menuItem.foodPrice)
            putExtra("ItemDescription",menuItem.foodDescription)
            putExtra("ItemIngredients",menuItem.foodIngredient)
            putExtra("ItemImage",menuItem.foodImage)
        }
        requireContext.startActivity(intent)
    }
}
