package com.example.fooddelight.Fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fooddelight.Adapters.BuyAgainAdapter
import com.example.fooddelight.Models.OrderDetails
import com.example.fooddelight.R
import com.example.fooddelight.databinding.FragmentHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: BuyAgainAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String
    private val listOfItems: MutableList<OrderDetails> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        recentBuyItems()
        //setupRecyclerView()
        return binding.root
    }

    private fun recentBuyItems() {
        binding.recentbuycard.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?: ""
        val databaseRef = database.reference.child("user").child(userId).child("BuyHistory")
        val sortedByTime = databaseRef.orderByChild("currentTime")

        sortedByTime.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val recentBuy = foodSnapshot.getValue(OrderDetails::class.java)
                    recentBuy?.let { listOfItems.add(it) }

                }
                if (listOfItems.isNotEmpty()) {
                    setDataRecentBuyItem()
                    setPreviousItem()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setDataRecentBuyItem() {
        binding.recentbuycard.visibility = View.VISIBLE
        val recentOrderItems = listOfItems.firstOrNull()
        recentOrderItems?.let {
            with(binding) {
                recentfooditem.text = it.foodName?.firstOrNull() ?: ""
                recentprice.text = it.foodPrice?.firstOrNull() ?: ""
                val image = it.foodImage?.firstOrNull() ?: ""
                val uriString = Uri.parse(image)
                Glide.with(requireContext()).load(uriString).into(recentfoodimage)

            }
        }
    }

    private fun setPreviousItem() {
        val buyAgainFoodName: MutableList<String> = mutableListOf()
        val buyAgainFoodPrice :MutableList<String> = mutableListOf()
        val buyAgainFoodImage :MutableList<String> = mutableListOf()
        for ( i in 1 until listOfItems.size){
            listOfItems[i].foodName?.firstOrNull()?.let { buyAgainFoodName.add(it) }
            listOfItems[i].foodPrice?.firstOrNull()?.let { buyAgainFoodPrice.add(it) }
            listOfItems[i].foodImage?.firstOrNull()?.let { buyAgainFoodImage.add(it) }
        }
        val rv = binding.buyagainrv
        rv.layoutManager = LinearLayoutManager(requireContext())
        adapter = BuyAgainAdapter(buyAgainFoodName, buyAgainFoodPrice, buyAgainFoodImage,requireContext())
        rv.adapter = adapter
    }


}