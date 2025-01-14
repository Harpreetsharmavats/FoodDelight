package com.example.fooddelight.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.car.ui.toolbar.MenuItem.OnClickListener
import com.example.fooddelight.DetailsActivity
import com.example.fooddelight.databinding.PopularBinding

class PopularAdapter(
    private val items: List<String>,
    private val prices: List<String>,
    private val images: List<Int>,private val requireContext: Context
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

private lateinit var itemClick: OnClickListener
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularViewHolder {
        return PopularViewHolder(PopularBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularAdapter.PopularViewHolder, position: Int) {
        val items = items[position]
        val images = images[position]
        val prices = prices[position]
        holder.bind(items, prices, images)

        holder.itemView.setOnClickListener {
            // setOnClickListner to Open Details
            val intent = Intent(requireContext,DetailsActivity::class.java)
            intent.putExtra("items",items)
            intent.putExtra("images",images)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PopularViewHolder(private val binding: PopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.foodimagePopular



        fun bind(items: String, prices: String, images: Int) {
            binding.foodnamePopular.text = items
            binding.pricePopular.text = prices
            image.setImageResource(images)

        }

    }
}
