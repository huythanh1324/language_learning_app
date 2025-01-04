package com.example.languagelearningapp.activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.languagelearningapp.R
import com.example.languagelearningapp.adapter.FlashCardAdapter
import com.example.languagelearningapp.adapter.MeaningAdapter
import com.example.languagelearningapp.databinding.ActivityFlashCardListBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FlashCardListActivity : AppCompatActivity() {

    private lateinit var backToFlashCardButton: Button

    private lateinit var binding : ActivityFlashCardListBinding
    private lateinit var adapter: FlashCardAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var userId: String
    private var flashcards: MutableList<Pair<String, String>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashCardListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        backToFlashCardButton = binding.backToFlashCardButton

        // Get user ID and database reference
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        userId = sharedPreferences.getString("uid", "default uid") ?: "default uid"
        database = FirebaseDatabase.getInstance("https://language-learning-c682b-default-rtdb.asia-southeast1.firebasedatabase.app/")
        reference = database.getReference("users/$userId/flashcards")

        // Load flashcards from database
        loadFlashcards()



        adapter = FlashCardAdapter(flashcards,::editHandler,::deleteHandler)
        binding.flashcardItemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.flashcardItemRecyclerView.adapter = adapter

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
                    adapter.updateNewData(flashcards)
                }
            } else {
                Toast.makeText(this, "Failed to load flashcards: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun editHandler(position: Int,englishText: String, vietnameseText: String) : Unit{
        flashcards[position] = Pair(englishText,vietnameseText)
        reference.setValue(flashcards).addOnSuccessListener {
            adapter.updateNewData(flashcards)
        }.addOnFailureListener {
            println("Failed to edit flashcard: ${it.message}")
        }

    }

    fun deleteHandler(position: Int) : Unit{
        flashcards.removeAt(position)
        reference.setValue(flashcards).addOnSuccessListener {
            adapter.updateNewData(flashcards)
        }.addOnFailureListener {
            println("Failed to delete flashcard: ${it.message}")
        }
    }
}
