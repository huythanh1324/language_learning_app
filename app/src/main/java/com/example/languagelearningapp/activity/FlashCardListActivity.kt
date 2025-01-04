package com.example.languagelearningapp.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.languagelearningapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FlashCardListActivity : AppCompatActivity() {

    private lateinit var flashCardListView: ListView
    private lateinit var backToFlashCardButton: Button

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var userId: String
    private var flashcards: MutableList<Pair<String, String>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card_list)

        // Initialize views
        flashCardListView = findViewById(R.id.flashCardListView)
        backToFlashCardButton = findViewById(R.id.backToFlashCardButton)

        // Get user ID and database reference
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        userId = sharedPreferences.getString("uid", "default uid") ?: "default uid"
        database = FirebaseDatabase.getInstance("https://language-learning-c682b-default-rtdb.asia-southeast1.firebasedatabase.app/")
        reference = database.getReference("users/$userId/flashcards")

        // Load flashcards from database
        loadFlashcards()

        // Back button to return to FlashCardActivity
        backToFlashCardButton.setOnClickListener { finish() }
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

                    // Set up ListView with flashcards
                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        flashcards.map { "${it.first} - ${it.second}" }
                    )
                    flashCardListView.adapter = adapter

                    // Handle item removal on long click
                    flashCardListView.setOnItemLongClickListener { _, _, position, _ ->
                        val cardToDelete = flashcards[position]
                        flashcards.removeAt(position)
                        reference.setValue(flashcards).addOnSuccessListener {
                            Toast.makeText(this, "Flashcard deleted", Toast.LENGTH_SHORT).show()
                            adapter.notifyDataSetChanged()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed to delete flashcard: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                        true
                    }
                }
            } else {
                Toast.makeText(this, "Failed to load flashcards: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
