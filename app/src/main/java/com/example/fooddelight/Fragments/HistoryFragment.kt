package com.example.fooddelight.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelight.Adapters.BuyAgainAdapter
import com.example.fooddelight.R
import com.example.fooddelight.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
   private lateinit var binding: FragmentHistoryBinding
   private lateinit var adapter: BuyAgainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater,container,false)

       setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val foodName = arrayListOf("Ice cream", "Soup", "Pasta", "Roll")
        val Price = arrayListOf("$1", "$4", "$7", "$5")
        val foodImage =
            arrayListOf(R.drawable.menu3, R.drawable.menu4, R.drawable.menu5, R.drawable.menu6)
        adapter = BuyAgainAdapter(foodName,Price,foodImage)
binding.buyagainrv.layoutManager = LinearLayoutManager(requireContext())
        binding.buyagainrv.adapter =adapter
    }

    companion object {

    }
}