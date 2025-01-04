package com.example.languagelearningapp.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.languagelearningapp.R
import com.example.languagelearningapp.databinding.ActivityFlashCardBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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


    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var binding : ActivityFlashCardBinding

    private var flashcards: MutableList<Pair<String, String>> = mutableListOf()
    private var currentCardIndex = 0
    private var showingQuestion = true

    private lateinit var handler: Handler
    private lateinit var switchCardRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityFlashCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get user uid
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val userId = sharedPreferences.getString("uid", "default uid")
        // Connect to db
        database = FirebaseDatabase.getInstance("https://language-learning-c682b-default-rtdb.asia-southeast1.firebasedatabase.app/")
        reference = database.getReference("users/$userId/flashcards")

        // load all flashcards
        loadFlashcards()

        // Initialize views
        flashCardText = binding.flashCardText
        flashCard = binding.flashCard
        nextButton = binding.nextButton
        backButton = binding.backButton
        addButton = binding.addButton
        viewFlashCardListButton = binding.viewFlashCardListButton
        englishInput = binding.englishInput
        vietnameseInput = binding.vietnameseInput
        deleteButton = binding.deleteButton
        deleteWordInput = binding.deleteWordInput

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
        handler.postDelayed(switchCardRunnable, 5000) // Start auto-switch after 5 seconds
    }

    private fun loadFlashcards() {
        reference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                if (result.exists()) {
                    flashcards = result.children.mapNotNull { snapshot ->
                        val english = snapshot.child("first").getValue(String::class.java)
                        val vietnamese = snapshot.child("second").getValue(String::class.java)
                        if (english != null && vietnamese != null) {
                            english to vietnamese
                        } else {
                            null
                        }
                    }.toMutableList()
                }
                updateFlashCard()
            } else {
                Toast.makeText(this,"Failed to load flashcards: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }

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

        Log.d("current card index", "$currentCardIndex, flash card size: ${flashcards.size}")
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
        val pairData = Pair(englishWord,vietnameseWord)
        flashcards.add(pairData)
        if(englishWord.isNotEmpty() && vietnameseWord.isNotEmpty()){
            reference.setValue(flashcards).addOnSuccessListener {
                englishInput.text.clear()
                vietnameseInput.text.clear()
                loadFlashcards()
            }.addOnFailureListener {
                println("Failed to add flashcard: ${it.message}")
            }
        }

    }

    private fun deleteFlashCard() {
        val wordToDelete = deleteWordInput.text.toString().trim()

        if (wordToDelete.isNotEmpty()) {
            // Find the flashcard to delete
            val cardToDelete = flashcards.find { it.first.equals(wordToDelete, ignoreCase = true) }
            if (cardToDelete != null) {
                // Remove the flashcard locally
                flashcards.remove(cardToDelete)

                // Update the database
                reference.setValue(flashcards).addOnSuccessListener {
                    Toast.makeText(this, "Flashcard deleted successfully", Toast.LENGTH_SHORT).show()
                    deleteWordInput.text.clear()
                    if (flashcards.isNotEmpty()) {
                        currentCardIndex = 0
                        updateFlashCard()
                    } else {
                        flashCardText.text = "No flashcards available"
                        flashCard.setCardBackgroundColor(
                            ContextCompat.getColor(this, R.color.card_empty)
                        )
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to delete flashcard: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Flashcard not found", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Enter a word to delete", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the runnable when the activity is destroyed to prevent memory leaks
        handler.removeCallbacks(switchCardRunnable)
    }
}
