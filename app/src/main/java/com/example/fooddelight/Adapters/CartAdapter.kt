package com.example.fooddelight.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelight.databinding.CartviewcardBinding

class CartAdapter(
    private val cartitems: MutableList<String>,
    private val price: MutableList<String>,
    private val foodimage: MutableList<Int>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val itemsQuantities = IntArray(cartitems.size) { 1 }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.CartViewHolder {
        val binding =
            CartviewcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartitems.size

    inner class CartViewHolder(private val binding: CartviewcardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemsQuantities[position]
                cartfooditems.text = cartitems[position]
                cartprice.text = price[position]
                cartfoodimage.setImageResource(foodimage[position])
                cartquantity.text = quantity.toString()
                minusbtn.setOnClickListener {
                    decrementItems(position)
                }
                plusbtn.setOnClickListener {
                    incrementItems(position)
                }
                deleteitems.setOnClickListener {
                    val itemPostion = adapterPosition
                    if (itemPostion != RecyclerView.NO_POSITION) {
                        deleteItems(position)
                    }
                }
            }

        }

        fun decrementItems(position: Int) {
            if (itemsQuantities[position] > 1) {
                itemsQuantities[position]--
                binding.cartquantity.text = itemsQuantities[position].toString()
            }
        }

        fun incrementItems(position: Int) {
            if (itemsQuantities[position] < 20) {
                itemsQuantities[position]++
                binding.cartquantity.text = itemsQuantities[position].toString()
            }
        }

        fun deleteItems(position: Int) {
            cartitems.removeAt(position)
            price.removeAt(position)
            foodimage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartitems.size)
        }

    }
}


