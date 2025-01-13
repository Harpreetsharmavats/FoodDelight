package com.example.fooddelight.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelight.databinding.NotificationBinding

class NotificationAdapter(private val notification : ArrayList<String>,private val notificationImage: ArrayList<Int>): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(NotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }



    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
holder.bind(notification[position],notificationImage[position])    }

    override fun getItemCount(): Int =notification.size

    class NotificationViewHolder(private val binding:NotificationBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: String, image: Int) {
            binding .notificationtext.text = notification
            binding.notificationimage.setImageResource(image)
        }

    }
}