package com.example.languagelearningapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.languagelearningapp.R

class FlashCardActivity : AppCompatActivity() {

    private lateinit var flashCardText: TextView
    private lateinit var flashCard: CardView
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var addButton: Button
    private lateinit var viewFlashCardListButton: Button
    private lateinit var deleteButton: Button
    private lateinit var englishInput: EditText
    private lateinit var vietnameseInput: EditText
    private lateinit var deleteWordInput: EditText

    companion object {
        val flashcards = mutableListOf(
            Pair("Hello", "Xin chào"),
            Pair("Thank You", "Cảm ơn"),
            Pair("Goodbye", "Tạm biệt"),
            Pair("Please", "Làm ơn")
        )
    }

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
        addButton = findViewById(R.id.addButton)
        viewFlashCardListButton = findViewById(R.id.viewFlashCardListButton)
        englishInput = findViewById(R.id.englishInput)
        vietnameseInput = findViewById(R.id.vietnameseInput)
        deleteButton = findViewById(R.id.deleteButton)
        deleteWordInput = findViewById(R.id.deleteWordInput)

        // Set up button listeners
        backButton.setOnClickListener { finish() }
        nextButton.setOnClickListener { moveToNextCard() }
        addButton.setOnClickListener { addNewFlashCard() }
        viewFlashCardListButton.setOnClickListener {
            val intent = Intent(this, FlashCardListActivity::class.java)
            startActivity(intent)
        }
        deleteButton.setOnClickListener { deleteFlashCard() }

        // Initialize Handler for auto-switching between English and Vietnamese
        handler = Handler(Looper.getMainLooper())
        switchCardRunnable = object : Runnable {
            override fun run() {
                toggleCard()
                handler.postDelayed(this, 5000)  // Switch every 5 seconds
            }
        }

        // Start the flashcard logic
        updateFlashCard()
        handler.postDelayed(switchCardRunnable, 5000) // Start auto-switch after 5 seconds
    }

    private fun updateFlashCard() {
        if (flashcards.isNotEmpty()) {
            flashCardText.text = flashcards[currentCardIndex].first
            flashCard.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.card_english)
            ) // Default to English background color (Blue)
            showingQuestion = true
        } else {
            flashCardText.text = "No flashcards available"
            flashCard.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.card_empty)
            ) // Set a different color for empty state
        }
    }

    private fun toggleCard() {
        if (flashcards.isNotEmpty()) {
            showingQuestion = !showingQuestion
            if (showingQuestion) {
                flashCardText.text = flashcards[currentCardIndex].first
                flashCard.setCardBackgroundColor(
                    ContextCompat.getColor(this, R.color.card_english)
                ) // English: Blue background
            } else {
                flashCardText.text = flashcards[currentCardIndex].second
                flashCard.setCardBackgroundColor(
                    ContextCompat.getColor(this, R.color.card_vietnamese)
                ) // Vietnamese: Green background
            }
        }
    }

    private fun moveToNextCard() {
        if (flashcards.isNotEmpty()) {
            // Move to the next word and reset the timer
            currentCardIndex = (currentCardIndex + 1) % flashcards.size
            updateFlashCard()

            // Reset the handler to start the timer again after clicking Next
            handler.removeCallbacks(switchCardRunnable)
            handler.postDelayed(switchCardRunnable, 5000) // Reset the timer
        }
    }

    private fun addNewFlashCard() {
        val englishWord = englishInput.text.toString().trim()
        val vietnameseWord = vietnameseInput.text.toString().trim()

        if (englishWord.isNotEmpty() && vietnameseWord.isNotEmpty()) {
            flashcards.add(Pair(englishWord, vietnameseWord))
            englishInput.text.clear()
            vietnameseInput.text.clear()
        }
    }

    private fun deleteFlashCard() {
        val wordToDelete = deleteWordInput.text.toString().trim()

        if (wordToDelete.isNotEmpty()) {
            val cardToDelete = flashcards.find { it.first.equals(wordToDelete, ignoreCase = true) }
            cardToDelete?.let {
                flashcards.remove(it)
                deleteWordInput.text.clear()
                if (flashcards.isNotEmpty()) {
                    // Reset to the first card if it's deleted
                    currentCardIndex = 0
                    updateFlashCard()
                } else {
                    // If no cards are left
                    flashCardText.text = "No flashcards available"
                    flashCard.setCardBackgroundColor(
                        ContextCompat.getColor(this, R.color.card_empty)
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the runnable when the activity is destroyed to prevent memory leaks
        handler.removeCallbacks(switchCardRunnable)
    }
}
