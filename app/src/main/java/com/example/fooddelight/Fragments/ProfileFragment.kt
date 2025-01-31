package com.example.fooddelight.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fooddelight.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setUserData()
        with(binding){
            name.isEnabled = false
            email.isEnabled = false
            address.isEnabled = false
            phone.isEnabled = false
        }
        binding.editbtn.setOnClickListener {
          with(binding) {
              name.isEnabled = !name.isEnabled
              email.isEnabled = !email.isEnabled
              address.isEnabled = !address.isEnabled
              phone.isEnabled = !phone.isEnabled
          }
        }
        binding.savedetailsbtn.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val address = binding.address.text.toString().trim()
            val phone = binding.phone.text.toString().trim()
            updateUserData(name,email,address,phone)
        }
        return binding.root
    }

    private fun updateUserData(name: String, email: String, address: String, phone: String) {
        val userId = auth.currentUser?.uid
        if (userId != null){
            val userRef = database.getReference("user").child(userId)
            val userData = hashMapOf(
                "name" to name,
                "email" to email,
                "address" to address,
                "phone" to phone
            )
            userRef.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(),"Data Uploaded",Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Data Failed",Toast.LENGTH_SHORT).show()

                }
        }
    }



   private fun setUserData() {
       val user = auth.currentUser
       if (user != null){
           val userId = user.uid
           val userRef = database.getReference("user").child(userId)
           userRef.addListenerForSingleValueEvent(object : ValueEventListener{
               override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot.exists()){
                       val names = snapshot.child("name").getValue(String::class.java)?:""
                       val emails = snapshot.child("email").getValue(String::class.java)?:""
                       val addresses = snapshot.child("address").getValue(String::class.java)?:""
                       val phones = snapshot.child("phone").getValue(String::class.java)?:""
                       binding.apply {
                           name.setText(names)
                           email.setText(emails)
                           address.setText(addresses)
                           phone.setText(phones)

                       }
                   }
               }

               override fun onCancelled(error: DatabaseError) {
                   //Toast.makeText(,"Data not Fetched",Toast.LENGTH_SHORT).show()
               }

           })
       }
   }

    companion object {


    }
}