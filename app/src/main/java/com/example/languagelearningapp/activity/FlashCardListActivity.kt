package com.example.languagelearningapp.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.languagelearningapp.R

class FlashCardListActivity : AppCompatActivity() {

    private lateinit var flashCardListView: ListView
    private lateinit var backToFlashCardButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card_list)

        // Initialize views
        flashCardListView = findViewById(R.id.flashCardListView)
        backToFlashCardButton = findViewById(R.id.backToFlashCardButton)

        // Set up ListView with flashcards
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            FlashCardActivity.flashcards.map { "${it.first} - ${it.second}" }
        )
        flashCardListView.adapter = adapter

        // Handle item removal on long click
        flashCardListView.setOnItemLongClickListener { _, _, position, _ ->
            FlashCardActivity.flashcards.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Flashcard removed", Toast.LENGTH_SHORT).show()
            true
        }

        // Back button to return to FlashCardActivity
        backToFlashCardButton.setOnClickListener { finish() }
    }
}
