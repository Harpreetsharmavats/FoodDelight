package com.example.fooddelight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fooddelight.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SigninActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding : ActivitySigninBinding by lazy {
        ActivitySigninBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.loginbtn.setOnClickListener {
            email = binding.Email.editText?.text.toString().trim()
            password = binding.pass.editText?.text.toString().trim()
            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please fill the details",Toast.LENGTH_SHORT).show()
            }else{
                signIn(email,password)
            }
        }
        binding.textView8.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if (task.isSuccessful){
                Toast.makeText(this,"Signed in",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"Failed to sign in",Toast.LENGTH_SHORT).show()
                Log.d("Account","Error: Authentication failed",task.exception)
            }
        }
    }
}