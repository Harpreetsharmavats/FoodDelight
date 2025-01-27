package com.example.fooddelight.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelight.Adapter.CartAdapter
import com.example.fooddelight.Models.CartItems
import com.example.fooddelight.PayoutActivity
import com.example.fooddelight.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var cartItem: MutableList<String>
    private lateinit var cartPrice: MutableList<String>
    private lateinit var cartImage: MutableList<String>
    private lateinit var cartDescription: MutableList<String>
    private lateinit var cartIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCartBinding.inflate(inflater,container,false)

        auth = FirebaseAuth.getInstance()
        retrieveCartItems()


        binding.proceedbtn.setOnClickListener {
            getOrderDetails()

        }





        return binding.root
    }

    private fun getOrderDetails() {
        val orderIdRef: DatabaseReference = database.reference.child("user").child(userId).child("cartItems")
      val foodNames = mutableListOf<String>()
      val foodPrices = mutableListOf<String>()
      val foodImages = mutableListOf<String>()
      val foodDescriptions = mutableListOf<String>()
      val foodIngredients = mutableListOf<String>()
        val foodQuantities = cartAdapter.getUpdateItemsQuantity()

        orderIdRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    orderItems?.foodName?.let { foodNames.add(it) }
                    orderItems?.foodPrice?.let { foodPrices.add(it) }
                    orderItems?.foodImage?.let { foodImages.add(it) }
                    orderItems?.foodDescription?.let { foodDescriptions.add(it) }
                    orderItems?.foodIngredient?.let { foodIngredients.add(it) }
                }
                orderNow(foodNames,foodPrices,foodImages,foodDescriptions,foodIngredients,foodQuantities)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Failed to Place Order",Toast.LENGTH_SHORT).show()

            }

        })
    }

    private fun orderNow(
        foodNames: MutableList<String>,
        foodPrices: MutableList<String>,
        foodImages: MutableList<String>,
        foodDescriptions: MutableList<String>,
        foodIngredients: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
        if (isAdded && context != null){
            val intent = Intent(requireContext(),PayoutActivity::class.java)
            intent.putExtra("FoodItemName",foodNames as ArrayList<String>)
            intent.putExtra("FoodItemPrice",foodPrices as ArrayList<String>)
            intent.putExtra("FoodItemImage",foodImages as ArrayList<String>)
            intent.putExtra("FoodItemDescription",foodDescriptions as ArrayList<String>)
            intent.putExtra("FoodItemIngredient",foodIngredients as ArrayList<String>)
            intent.putExtra("FoodItemQuantity",foodQuantities as ArrayList<Int>)
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid?:""
        val foodReference = database.reference.child("user").child(userId).child("cartItems")
        cartItem = mutableListOf()
         cartPrice = mutableListOf()
         cartImage = mutableListOf()
         cartDescription = mutableListOf()
         cartIngredients = mutableListOf()
         quantity = mutableListOf()
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val cartItems = foodSnapshot.getValue(CartItems::class.java)

                    cartItems?.foodName?.let { cartItem.add(it) }
                    cartItems?.foodPrice?.let { cartPrice.add(it) }
                    cartItems?.foodImage?.let { cartImage.add(it) }
                    cartItems?.foodDescription?.let { cartDescription.add(it) }
                    cartItems?.foodIngredient?.let { cartIngredients.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Data not fetched",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setAdapter() {
        cartAdapter = CartAdapter(cartItem,cartPrice,cartImage,cartDescription,cartIngredients,quantity,requireContext())
        binding.cartrv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.cartrv.adapter = cartAdapter
    }

    companion object {

    }
}