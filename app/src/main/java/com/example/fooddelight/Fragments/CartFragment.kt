package com.example.fooddelight.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.objectListOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelight.Adapter.CartAdapter
import com.example.fooddelight.Adapters.PopularAdapter
import com.example.fooddelight.PayoutActivity
import com.example.fooddelight.R
import com.example.fooddelight.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding

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

        binding.proceedbtn.setOnClickListener {
            val intent = Intent(requireContext(),PayoutActivity::class.java)
            startActivity(intent)
        }

        val cartFoodName = listOf("Ice cream", "Soup", "Pasta", "Roll")
        val cartPrice = listOf("$1", "$4", "$7", "$5")
        val cartFoodImage =
            listOf(R.drawable.menu3, R.drawable.menu4, R.drawable.menu5, R.drawable.menu6)
        val adapter = CartAdapter(ArrayList(cartFoodName),ArrayList(cartPrice),ArrayList(cartFoodImage))
        binding.cartrv.layoutManager = LinearLayoutManager(requireContext())
        binding.cartrv.adapter = adapter

        return binding.root
    }

    companion object {

    }
}