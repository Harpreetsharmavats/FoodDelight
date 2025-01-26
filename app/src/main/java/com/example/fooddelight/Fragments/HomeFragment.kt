package com.example.fooddelight.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.fooddelight.Adapters.PopularAdapter
import com.example.fooddelight.Models.Menu
import com.example.fooddelight.R
import com.example.fooddelight.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItem: ArrayList<Menu>
    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewmore.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }
        retrieveData()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1))
        imageList.add(SlideModel(R.drawable.banner2))
        imageList.add(SlideModel(R.drawable.banner3))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

    }

    private fun retrieveData() {
        database = FirebaseDatabase.getInstance()
        val foodRef :DatabaseReference = database.reference.child("menu")
        menuItem = ArrayList()

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodsnapshot in snapshot.children){
                   val menuItems = foodsnapshot.getValue(Menu::class.java)
                    menuItems?.let {
                        menuItem.add(it)
                    }
                    popularItems()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Database","Error${error}")
            }

        })
    }

    private fun popularItems() {
        val index = menuItem.indices.toList().shuffled()
        val numOfItemToShow = 6
        val subsetMenuItem = index.take(numOfItemToShow).map { menuItem[it] }
        setAdapter(subsetMenuItem)

    }

    private fun setAdapter(subsetMenuItem: List<Menu>) {
        val adapter = PopularAdapter(subsetMenuItem,requireContext())
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
    }



}