package com.example.fooddelight.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelight.Adapters.PopularAdapter
import com.example.fooddelight.R
import com.example.fooddelight.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
   private lateinit var binding:FragmentSearchBinding
private lateinit var adapter: PopularAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        val foodName = listOf("Ice cream", "Soup", "Pasta", "Roll")
        val Price = listOf("$1", "$4", "$7", "$5")
        val foodImage =
            listOf(R.drawable.menu3, R.drawable.menu4, R.drawable.menu5, R.drawable.menu6)
        val adapter = PopularAdapter(ArrayList(foodName),ArrayList(Price),ArrayList(foodImage))
        binding.searchrv.layoutManager = LinearLayoutManager(requireContext())
        binding.searchrv.adapter = adapter
        return binding.root
    }

    companion object {

    }
}