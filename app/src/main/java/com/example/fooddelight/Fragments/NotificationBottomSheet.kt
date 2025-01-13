package com.example.fooddelight.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelight.Adapters.NotificationAdapter
import com.example.fooddelight.R
import com.example.fooddelight.databinding.FragmentNotificationBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBottomSheetBinding
    private lateinit var adapter: NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentNotificationBottomSheetBinding.inflate(inflater, container, false)
        binding.imageButton.setOnClickListener {
            dismiss()
        }
        val notification = arrayListOf(
            "Your order has been Canceled Successfully",
            "Order has been taken by the driver",
            "Congrats Your Order Placed"
        )
        val notificationImage =
            arrayListOf(R.drawable.sademoji, R.drawable.truck, R.drawable.congrats)
        adapter = NotificationAdapter(ArrayList(notification), ArrayList(notificationImage))
        binding.notificationrv.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationrv.adapter = adapter

        return binding.root
    }

    companion object {

    }
}