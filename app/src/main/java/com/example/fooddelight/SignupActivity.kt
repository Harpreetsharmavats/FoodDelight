package com.example.fooddelight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelight.Models.Users
import com.example.fooddelight.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private val binding : ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        database = Firebase.database.reference

        binding.signupbtn.setOnClickListener {
            name = binding.Name.editText?.text.toString().trim()
            email = binding.Email.editText?.text.toString().trim()
            password = binding.pass.editText?.text.toString().trim()
            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this,"Please fill the details",Toast.LENGTH_SHORT).show()
            }else{
                signUP(email,password)
            }
        }
        binding.alreadyhaveanacc.setOnClickListener {
            val intent = Intent(this,SigninActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun signUP(email :String,password:String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if (task.isSuccessful){
                saveDetails()
                Toast.makeText(this,"Account created",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,SigninActivity::class.java))
            }else{
                Toast.makeText(this,"Account creation failed",Toast.LENGTH_SHORT).show()
                Log.d("Account","createaccount : Error",task.exception)
            }

        }
    }

    private fun saveDetails() {
        name = binding.Name.editText?.text.toString().trim()
        email = binding.Email.editText?.text.toString().trim()
        password = binding.pass.editText?.text.toString().trim()
        val user = Users(name,email,password)
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}