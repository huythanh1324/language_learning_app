package com.example.languagelearningapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.languagelearningapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val dictionaryBtn : ConstraintLayout = findViewById(R.id.dictionary_btn)
        val flashCardBtn : ConstraintLayout = findViewById(R.id.flash_card_btn)
        val quizBtn : ConstraintLayout = findViewById(R.id.quiz_btn)
        val translateBtn : ConstraintLayout = findViewById(R.id.translate_btn)

        dictionaryBtn.setOnClickListener {
            val intent = Intent(this, DictionaryActivity::class.java)
            startActivity(intent)
        }
        flashCardBtn.setOnClickListener {
            val intent = Intent(this, FlashCardActivity::class.java)
            startActivity(intent)
        }
        quizBtn.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
        translateBtn.setOnClickListener {
            val intent = Intent(this, TranslateActivity::class.java)
            startActivity(intent)
        }
    }
}