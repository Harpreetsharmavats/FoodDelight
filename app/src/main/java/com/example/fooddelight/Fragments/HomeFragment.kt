package com.example.fooddelight.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.fooddelight.Adapters.PopularAdapter
import com.example.fooddelight.R
import com.example.fooddelight.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
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

        val foodName = listOf("Ice cream", "Soup", "Pasta", "Roll")
        val Price = listOf("$1", "$4", "$7", "$5")
        val foodImage =
            listOf(R.drawable.menu3, R.drawable.menu4, R.drawable.menu5, R.drawable.menu6)
        val adapter = PopularAdapter(ArrayList(foodName),ArrayList(Price),ArrayList(foodImage))
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter


    }



    companion object {


    }
}