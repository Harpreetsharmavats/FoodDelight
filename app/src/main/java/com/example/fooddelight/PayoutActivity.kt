package com.example.fooddelight

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelight.Fragments.CartFragment
import com.example.fooddelight.Fragments.CongratulationBottomSheet
import com.example.fooddelight.databinding.ActivityPayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayoutActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var name : String
    private lateinit var address : String
    private lateinit var phone : String
    private lateinit var totalAmount : String
    private lateinit var foodItemName : ArrayList<String>
    private lateinit var foodItemPrice : ArrayList<String>
    private lateinit var foodItemImage : ArrayList<String>
    private lateinit var foodItemDescription : ArrayList<String>
    private lateinit var foodItemIngredient : ArrayList<String>
    private lateinit var foodItemQuantity : ArrayList<Int>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId : String
    private val binding:ActivityPayoutBinding by lazy {
        ActivityPayoutBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference()

        setUserData()

        val intent = intent
        foodItemName = intent.getStringArrayListExtra("FoodItemName") as ArrayList<String>
        foodItemPrice = intent.getStringArrayListExtra("FoodItemPrice") as ArrayList<String>
        foodItemImage = intent.getStringArrayListExtra("FoodItemImage") as ArrayList<String>
        foodItemDescription = intent.getStringArrayListExtra("FoodItemDescription") as ArrayList<String>
        foodItemIngredient = intent.getStringArrayListExtra("FoodItemIngredient") as ArrayList<String>
        foodItemQuantity = intent.getIntegerArrayListExtra("FoodItemQuantity") as ArrayList<Int>

        totalAmount = calculateTotal().toString() +"$"
        binding.total.text = totalAmount

        binding.payoutbackbtn.setOnClickListener {
            finish()
        }
        binding.placemyorderbtn.setOnClickListener {
            val bottomSheetFragment = CongratulationBottomSheet()
            bottomSheetFragment.show(supportFragmentManager,"Test")
        }
    }

    private fun calculateTotal(): Int {
        var totalAmount = 0
        for ( i in 0 until foodItemPrice.size){
            val price = foodItemPrice[i]
            val amount = price.replace("$","").toInt()
            /*val lastChar = price.first()
            val priceToInt = if(lastChar == '$'){
                price.dropLast(1).toInt()
            } else {
                price.toInt()
            }*/
            val quantity = foodItemQuantity[i]
            totalAmount += amount*quantity
        }
return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user != null){
            val userId = user.uid
            val userRef = databaseReference.child("user").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val names = snapshot.child("name").getValue(String::class.java)?:""
                        val addresses = snapshot.child("address").getValue(String::class.java)?:""
                        val phones = snapshot.child("phone").getValue(String::class.java)?:""
                        binding.apply {
                            name.setText(names)
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
}