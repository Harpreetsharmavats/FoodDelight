package com.example.fooddelight.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelight.Adapters.PopularAdapter
import com.example.fooddelight.Models.Menu
import com.example.fooddelight.databinding.FragmentSearchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: PopularAdapter
   private lateinit var database: FirebaseDatabase
   private val menuItems = mutableListOf<Menu>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

       retrieveMenuItem()

        //setup for searchview
        setupSearchView()


        return binding.root
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef : DatabaseReference = database.reference.child("menu")
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(Menu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                showMenu()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun showMenu() {
       val filteredMenuItem = ArrayList(menuItems)
        setAdapter(filteredMenuItem)

    }

    private fun setAdapter(filteredMenuItem: List<Menu>) {
        adapter = PopularAdapter(filteredMenuItem,requireContext())
        binding.searchrv.layoutManager = LinearLayoutManager(requireContext())
        binding.searchrv.adapter = adapter
    }

    private fun setupSearchView() {
        binding.menusearchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }


        })
    }

    private fun filterMenuItems(query: String) {
       val filterMenuItem = menuItems.filter {
           it.foodName?.contains(query,ignoreCase = true) == true
       }
       setAdapter(filterMenuItem)

    }

}