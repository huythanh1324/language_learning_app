package com.example.languagelearningapp.activity


import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.languagelearningapp.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)

        val imageView1: ImageView = findViewById(R.id.imageView1)
        val imageView2: ImageView = findViewById(R.id.imageView2)
        val imageView3: ImageView = findViewById(R.id.imageView3)
        val textView: TextView = findViewById(R.id.textView)
        val  loginBtn : Button  = findViewById(R.id.login_btn)

        // Set initial alpha to 0 (invisible) for the fade-in effect
        imageView1.alpha = 0f
        imageView2.alpha = 0f
        imageView3.alpha = 0f
        textView.alpha = 0f
        loginBtn.alpha = 0f

        // Create ObjectAnimator to animate the alpha property (fade-in effect)
        val fadeInDuration = 5000L // Duration of 1 second

        val fadeInImageView1 = ObjectAnimator.ofFloat(imageView1, "alpha", 0f, 1f)
        fadeInImageView1.duration = fadeInDuration

        val fadeInImageView2 = ObjectAnimator.ofFloat(imageView2, "alpha", 0f, 1f)
        fadeInImageView2.duration = fadeInDuration

        val fadeInImageView3 = ObjectAnimator.ofFloat(imageView3, "alpha", 0f, 1f)
        fadeInImageView3.duration = fadeInDuration

        val fadeInTextView = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f)
        fadeInTextView.duration = fadeInDuration

        val fadeInLoginBtn = ObjectAnimator.ofFloat(loginBtn, "alpha", 0f, 1f)
        fadeInLoginBtn.duration = fadeInDuration

        // Start the animations
        fadeInImageView1.start()
        fadeInImageView2.start()
        fadeInImageView3.start()
        fadeInTextView.start()
        fadeInLoginBtn.start()

        // set onClick Listener for login button
        loginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}