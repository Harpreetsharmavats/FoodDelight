package com.example.fooddelight.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.example.fooddelight.Adapters.PopularAdapter
import com.example.fooddelight.R
import com.example.fooddelight.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: PopularAdapter
    private val foodName = listOf("Ice cream", "Soup", "Pasta", "Roll")
    private val Price = listOf("$1", "$4", "$7", "$5")
    private val foodImage =
        listOf(R.drawable.menu3, R.drawable.menu4, R.drawable.menu5, R.drawable.menu6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private val filterMenuFood = mutableListOf<String>()
    private val filterMenuPrice = mutableListOf<String>()
    private val filterMenuImage = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        /*adapter = PopularAdapter(filterMenuFood,filterMenuPrice,filterMenuImage,requireContext())
        binding.searchrv.layoutManager = LinearLayoutManager(requireContext())
        binding.searchrv.adapter = adapter*/

        //setup for searchview
        setupSearchView()
        //show menu items
        showMenu()
        return binding.root
    }

    private fun showMenu() {
        filterMenuFood.clear()
        filterMenuPrice.clear()
        filterMenuImage.clear()

        filterMenuFood.addAll(foodName)
        filterMenuPrice.addAll(Price)
        filterMenuImage.addAll(foodImage)

        adapter.notifyDataSetChanged()

    }

    private fun setupSearchView() {
        binding.menusearchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return TODO("Provide the return value")
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }


        })
    }

    private fun filterMenuItems(query: String) {
        filterMenuFood.clear()
        filterMenuPrice.clear()
        filterMenuImage.clear()

        foodName.forEachIndexed { index, food ->
            if (food.contains(query, ignoreCase = true)) {
                filterMenuFood.add(food)
                filterMenuPrice.add(Price[index])
                filterMenuImage.add(foodImage[index])
            }
        }
        adapter.notifyDataSetChanged()

    }

    companion object {

    }
}