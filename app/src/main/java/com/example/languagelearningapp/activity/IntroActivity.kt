package com.example.languagelearningapp.activity


import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.languagelearningapp.R
import com.example.languagelearningapp.databinding.ActivityIntroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class IntroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val loginBtn = binding.loginBtn
        val signUpRedirectText = binding.signUpRedirectText
        val email =  binding.loginEmail
        val password = binding.loginPassword

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance("https://language-learning-c682b-default-rtdb.asia-southeast1.firebasedatabase.app/")

        // set onClick Listener for login button
        loginBtn.setOnClickListener {

            if (email.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()){

                auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener({
                    if(it.isSuccessful){

                        val userId = auth.currentUser?.uid
                        if (userId != null){

                            // save uid in SharedPreferences
                            val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("uid", userId)
                            editor.apply()

                            val intent = Intent(this, MainActivity::class.java)
                            reference = database.getReference("users/$userId")

                            reference.get().addOnCompleteListener{ dbtask ->
                                if (dbtask.isSuccessful){
                                    val dataSnapshot = dbtask.result
                                    val name  = dataSnapshot.child("name").getValue(String::class.java)
                                    if (name != null) {
                                        intent.putExtra("name",name)
                                    }
                                    startActivity(intent)
                                }else{
                                    Toast.makeText(this, "Failed to retrieve user data.", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }

                    }else {
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                })

            }

        }

        signUpRedirectText.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}