package com.example.languagelearningapp.activity

import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
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
    private lateinit var englishInput: EditText
    private lateinit var vietnameseInput: EditText

    private val flashcards = mutableListOf(
        Pair("Hello", "Xin chào"),
        Pair("Thank You", "Cảm ơn"),
        Pair("Goodbye", "Tạm biệt"),
        Pair("Please", "Làm ơn")
    )
    private var currentCardIndex = 0
    private var showingQuestion = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card)

        // Initialize views
        flashCardText = findViewById(R.id.flashCardText)
        flashCard = findViewById(R.id.flashCard)
        nextButton = findViewById(R.id.nextButton)
        backButton = findViewById(R.id.backButton)
        addButton = findViewById(R.id.addButton)
        englishInput = findViewById(R.id.englishInput)
        vietnameseInput = findViewById(R.id.vietnameseInput)

        backButton.setOnClickListener { finish() }
        nextButton.setOnClickListener { moveToNextCard() }
        addButton.setOnClickListener { addNewFlashCard() }

        updateFlashCard()
    }

    private fun updateFlashCard() {
        flashCardText.text = flashcards[currentCardIndex].first
        showingQuestion = true
    }

    private fun toggleCard() {
        showingQuestion = !showingQuestion
        flashCardText.text = if (showingQuestion) {
            flashcards[currentCardIndex].first
        } else {
            flashcards[currentCardIndex].second
        }
    }

    private fun moveToNextCard() {
        currentCardIndex = (currentCardIndex + 1) % flashcards.size
        updateFlashCard()
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
}
