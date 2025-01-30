package com.example.fooddelight

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelight.Fragments.CongratulationBottomSheet
import com.example.fooddelight.Models.OrderDetails
import com.example.fooddelight.databinding.ActivityPayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayoutActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var totalAmount: String
    private lateinit var foodItemName: ArrayList<String>
    private lateinit var foodItemPrice: ArrayList<String>
    private lateinit var foodItemImage: ArrayList<String>
    private lateinit var foodItemDescription: ArrayList<String>
    private lateinit var foodItemIngredient: ArrayList<String>
    private lateinit var foodItemQuantity: MutableList<Int>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String
    private val binding: ActivityPayoutBinding by lazy {
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
        foodItemDescription =
            intent.getStringArrayListExtra("FoodItemDescription") as ArrayList<String>
        foodItemIngredient =
            intent.getStringArrayListExtra("FoodItemIngredient") as ArrayList<String>
        foodItemQuantity = intent.getIntegerArrayListExtra("FoodItemQuantity") as ArrayList<Int>

        totalAmount = calculateTotal().toString() + "$"
        binding.total.text = totalAmount

        binding.payoutbackbtn.setOnClickListener {
            finish()
        }

        binding.placemyorderbtn.setOnClickListener {
            // get data from edittext
            name = binding.name.text.toString().trim()
            address = binding.address.text.toString().trim()
            phone = binding.phone.text.toString().trim()

            if (name.isBlank() && address.isBlank() && phone.isBlank()) {
                Toast.makeText(this, "Please Fill The Details", Toast.LENGTH_SHORT).show()
            } else {
                placeOrder()
            }


        }

    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid ?: ""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetails").push().key
        val orderDetails = OrderDetails(
            userId,
            name,
            foodItemName,
            foodItemPrice,
            foodItemImage,
            foodItemQuantity,
            b = false,
            b1 = false,
            address = address,
            totalAmount = totalAmount,
            phone = phone,
            time = time,
            itemPushKey = itemPushKey
        )
        val orderRef = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderRef.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetFragment = CongratulationBottomSheet()
            bottomSheetFragment.show(supportFragmentManager, "Test")
            addItemToBuyHistory(orderDetails)
            removeCart()
        }
    }

    private fun addItemToBuyHistory(orderDetails: OrderDetails) {
        databaseReference.child("user").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!).setValue(orderDetails).addOnSuccessListener {

            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed To Place Order", Toast.LENGTH_SHORT).show()
            }
    }

    private fun removeCart() {
        val cartRef = databaseReference.child("user").child(userId).child("cartItems")
        cartRef.removeValue()
    }

    private fun calculateTotal(): Int {
        var totalAmount = 0
        for (i in 0 until foodItemPrice.size) {
            val price = foodItemPrice[i]
            val amount = price.replace("$", "").toInt()
            /*val lastChar = price.first()
            val priceToInt = if(lastChar == '$'){
                price.dropLast(1).toInt()
            } else {
                price.toInt()
            }*/
            val quantity = foodItemQuantity[i]
            totalAmount += amount * quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val userRef = databaseReference.child("user").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val names = snapshot.child("name").getValue(String::class.java) ?: ""
                        val addresses = snapshot.child("address").getValue(String::class.java) ?: ""
                        val phones = snapshot.child("phone").getValue(String::class.java) ?: ""
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