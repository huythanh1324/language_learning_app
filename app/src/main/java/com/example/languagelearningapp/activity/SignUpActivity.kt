package com.example.languagelearningapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.languagelearningapp.R
import com.example.languagelearningapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivitySignupBinding
    private lateinit var database : FirebaseDatabase
    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance("https://language-learning-c682b-default-rtdb.asia-southeast1.firebasedatabase.app/")

        binding.signUpBtn.setOnClickListener{
            val email = binding.signUpEmail.text.toString()
            val name = binding.signUpName.text.toString()
            val pass = binding.signUpPassword.text.toString()
            val pass2 = binding.signUpPassword2.text.toString()

            if (email.isEmpty()){
                binding.signUpEmail.error = "Email cannot be empty!"

            }else if (name.isEmpty()) {
                binding.signUpName.error = "Password cannot be empty!"
            }else if (pass.isEmpty()){
                binding.signUpPassword.error = "Password cannot be empty!"
            } else if (pass2 != pass){
                binding.signUpPassword.error = "Password does not match!"
            } else {
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        val userId = auth.currentUser?.uid
                        if (userId != null){
                            reference = database.getReference("users/$userId")
                            val userProfile = hashMapOf(
                                "name" to name,
                            )
                            reference.setValue(userProfile)

                            Toast.makeText(this, "SignUp Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, IntroActivity::class.java))
                        }


                    } else {
                        Toast.makeText(
                            this,
                            "SignUp Failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


        binding.loginRedirectText.setOnClickListener({
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        })
    }



}