package com.example.fooddelight.Adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddelight.Models.CartItems
import com.example.fooddelight.databinding.CartviewcardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(
    private val cartItems: MutableList<String>,
    private val cartPrice: MutableList<String>,
    private val cartImage: MutableList<String>,
    private val cartDescription: MutableList<String>,
    private val cartIngredients: MutableList<String>,
    private val quantity: MutableList<Int>,
    private val context: Context
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference()

    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid ?: ""
        val cartNumber = cartItems.size

        itemsQuantities = IntArray(cartNumber) { 1 }
        cartItemsReference = database.reference.child("user").child(userId).child("cartItems")
    }

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

    override fun getItemCount(): Int = cartItems.size

    inner class CartViewHolder(private val binding: CartviewcardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemsQuantities[position]
                cartfooditems.text = cartItems[position]
                cartprice.text = cartPrice[position]
                val uriString = cartImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartfoodimage)
                cartquantity.text = quantity.toString()
                minusbtn.setOnClickListener {
                    decrementItems(position)
                }
                plusbtn.setOnClickListener {
                    incrementItems(position)
                }
                deleteitems.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItems(position)
                    }
                }
            }

        }

        private fun decrementItems(position: Int) {
            if (itemsQuantities[position] > 1) {
                itemsQuantities[position]--
                quantity[position] = itemsQuantities[position]
                binding.cartquantity.text = itemsQuantities[position].toString()
            }
        }

        private fun incrementItems(position: Int) {
            if (itemsQuantities[position] < 20) {
                itemsQuantities[position]++
                quantity[position] = itemsQuantities[position]
                /*updateQuantity(position) { uniqueKey ->
                    if (uniqueKey != null) {
                        update(position, uniqueKey)
                    }
                }*/
                binding.cartquantity.text = itemsQuantities[position].toString()
            }
        }

        private fun deleteItems(position: Int) {
            val positionRetrieve = position
            uniqueKeyAtPosition(positionRetrieve) { uniqueKey ->
                if (uniqueKey != null) {
                    removeItems(position, uniqueKey)
                }
            }
        }

        private fun removeItems(position: Int, uniqueKey: String) {
            if (uniqueKey != null) {
                cartItemsReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    cartItems.removeAt(position)
                    cartPrice.removeAt(position)
                    cartImage.removeAt(position)
                    cartDescription.removeAt(position)
                    cartIngredients.removeAt(position)
                    quantity.removeAt(position)
                    Toast.makeText(context, "Item Deleted Successfully ", Toast.LENGTH_SHORT).show()

                    itemsQuantities =
                        itemsQuantities.filterIndexed { index, i -> index != position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, cartItems.size)
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to delete ", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun uniqueKeyAtPosition(positionRetrieve: Int, onComplete: (String?) -> Unit) {
            cartItemsReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey: String? = null
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == positionRetrieve) {
                            uniqueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("UniqueKey", "Error: $error")
                }

            })

        }

    }

    private fun update(position: Int, uniqueKey: String) {
        val updateQuantity = mutableListOf("foodQuantity" to quantity)
        cartItemsReference.child(uniqueKey).setValue(updateQuantity)
    }

    private fun updateQuantity(position : Int, onComplete: (String?) -> Unit) {

        //val userId = auth.currentUser?.uid ?: ""

        //val quantityRef =
            //database.child("user").child(userId).child("cartItems")//.child("foodQuantity")

            cartItemsReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey: String? = null
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == position) {
                            uniqueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)
                }






                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun getUpdateItemsQuantity(): MutableList<Int> {
        val itemQuantity = mutableListOf<Int>()
        itemQuantity.addAll(quantity)
        return itemQuantity
    }


    companion object {

        private var itemsQuantities: IntArray = intArrayOf()
        private lateinit var cartItemsReference: DatabaseReference
    }
}


