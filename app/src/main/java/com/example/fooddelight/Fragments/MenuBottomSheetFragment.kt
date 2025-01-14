package com.example.fooddelight.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelight.Adapters.PopularAdapter
import com.example.fooddelight.R
import com.example.fooddelight.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
   private lateinit var binding:FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container,false)

        binding.backbtn.setOnClickListener {
            dismiss()
        }

        val foodName = listOf("Ice cream", "Soup", "Pasta", "Roll")
        val Price = listOf("$1", "$4", "$7", "$5")
        val foodImage =
            listOf(R.drawable.menu3, R.drawable.menu4, R.drawable.menu5, R.drawable.menu6)
        val adapter = PopularAdapter(ArrayList(foodName),ArrayList(Price),ArrayList(foodImage),requireContext())
        binding.menurv.layoutManager = LinearLayoutManager(requireContext())
        binding.menurv.adapter = adapter
        return binding.root
    }

    companion object {


    }
}