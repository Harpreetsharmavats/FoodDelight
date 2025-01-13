package com.example.fooddelight.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fooddelight.MainActivity
import com.example.fooddelight.R
import com.example.fooddelight.databinding.FragmentCongratulationBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CongratulationBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentCongratulationBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCongratulationBottomSheetBinding.inflate(layoutInflater,container,false)
        binding.gohomebtn.setOnClickListener {
            val intent = Intent(requireContext(),MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    companion object {

    }
}