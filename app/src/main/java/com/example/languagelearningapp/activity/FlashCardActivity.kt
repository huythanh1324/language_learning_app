package com.example.languagelearningapp.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.languagelearningapp.R

class FlashCardActivity : AppCompatActivity() {

    private lateinit var flashCardText: TextView
    private lateinit var flashCard: CardView
    private lateinit var nextButton: Button
    private lateinit var backButton: Button

    private val flashcards = listOf(
        Pair("Hello", "Xin chào"),
        Pair("Thank You", "Cảm ơn"),
        Pair("Goodbye", "Tạm biệt"),
        Pair("Please", "Làm ơn")
    )
    private var currentCardIndex = 0
    private var showingQuestion = true
    private lateinit var handler: Handler
    private lateinit var switchCardRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)

        // Initialize views
        flashCardText = findViewById(R.id.flashCardText)
        flashCard = findViewById(R.id.flashCard)
        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)

        // Handle back button click
        backButton.setOnClickListener { finish() }

        // Handle next button click
        nextButton.setOnClickListener {
            moveToNextCard()
        }

        // Initialize Handler for auto-switching between English and Vietnamese
        handler = Handler(Looper.getMainLooper())
        switchCardRunnable = object : Runnable {
            override fun run() {
                toggleCard()
                handler.postDelayed(this, 5000)
            }
        }

        // Start the flashcard logic
        updateFlashCard()
        handler.postDelayed(switchCardRunnable, 5000)
    }

    private fun updateFlashCard() {
        // Display the English word first and set the background color
        flashCardText.text = flashcards[currentCardIndex].first
        flashCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_english)) // Default to English background
        showingQuestion = true
    }

    private fun toggleCard() {
        // Toggle between English and Vietnamese and update background color
        showingQuestion = !showingQuestion
        if (showingQuestion) {
            flashCardText.text = flashcards[currentCardIndex].first
            flashCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_english)) // English: Default color
        } else {
            flashCardText.text = flashcards[currentCardIndex].second
            flashCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card_vietnamese)) // Vietnamese: Green
        }
    }

    private fun moveToNextCard() {
        // Move to the next word and reset to English side
        currentCardIndex = (currentCardIndex + 1) % flashcards.size
        updateFlashCard()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the runnable when the activity is destroyed to prevent memory leaks
        handler.removeCallbacks(switchCardRunnable)
    }
}
