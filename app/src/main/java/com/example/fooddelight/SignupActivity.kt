package com.example.fooddelight

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import com.example.fooddelight.Models.Users

import com.example.fooddelight.databinding.ActivitySignupBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManger: CallbackManager
    private val binding : ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val googleSignOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        auth = Firebase.auth

        database = Firebase.database.reference
        googleSignInClient = GoogleSignIn.getClient(this,googleSignOption)
        callbackManger = CallbackManager.Factory.create()

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
        binding.googlebtn.setOnClickListener {
            val signinIntent = googleSignInClient.signInIntent
            launcher.launch(signinIntent)
        }
        binding.facebookbtn.setOnClickListener {
            loginFacebook()
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
    private fun loginFacebook() {
        val loginManager = LoginManager.getInstance()
        LoginManager.getInstance()
            .logInWithReadPermissions(this, listOf("email", "public_profile"))

        LoginManager.getInstance().registerCallback(
            callbackManger,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$result")
                    handleFacebookAccessToken(result.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "facebook:onError", error)
                }
            },
        )
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    Toast.makeText(this, "Authentication successful", Toast.LENGTH_SHORT).show()
                    //val user = auth.currentUser
                    //updateUI(user)
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI()
                }
            }
    }
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            if (result.resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful){
                    val account : GoogleSignInAccount = task.result
                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    auth.signInWithCredential(credential).addOnCompleteListener { authTask->
                        if (authTask.isSuccessful){
                            Toast.makeText(this,"Google sign in successful",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this,"Google sign in failed",Toast.LENGTH_SHORT).show()

                        }
                    }
                }else{
                    Toast.makeText(this,"Google sign in failed",Toast.LENGTH_SHORT).show()

                }
            }
        }
    companion object {
        private const val TAG = "FacebookLogin"
    }
}