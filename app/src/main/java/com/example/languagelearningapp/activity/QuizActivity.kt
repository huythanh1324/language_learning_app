package com.example.languagelearningapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.languagelearningapp.R
import com.example.languagelearningapp.databinding.ActivityQuizBinding
import com.example.languagelearningapp.model.WordResult
import com.example.languagelearningapp.retrofit.RetrofitInstance
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    private lateinit var binding : ActivityQuizBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var definitionText: TextView
    private lateinit var answerEditText: EditText
    private lateinit var checkButton: Button
    private lateinit var nextButton: Button
    private lateinit var resultText: TextView
    private lateinit var scoreText: TextView

    private lateinit var wordList : List<String>

    private var currentQuestion: WordResult? = null
    private var currentDefinition: String? = null
    private var correctWord: String? = null
    private var score = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // connect to db and get word list
        database = FirebaseDatabase.getInstance("https://language-learning-c682b-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val category = intent.getStringExtra("category_quiz")
        reference = database.getReference("quiz/$category")
        reference.get().addOnCompleteListener{ dbtask ->
            if (dbtask.isSuccessful){
                val result = dbtask.result
                if (result != null && result.exists()) {
                    wordList = result.children.mapNotNull { it.getValue(String::class.java) }
                    // Load random meaning from the API
                    loadRandomMeaning()
                } else {
                    Toast.makeText(this, "No words available in this category", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Failed to retrieve data.", Toast.LENGTH_SHORT).show()
            }
        }

        // Initialize UI elements
        definitionText = binding.definitionText
        answerEditText = binding.answerEditText
        checkButton = binding.checkButton
        nextButton = binding.nextButton
        resultText = binding.resultText
        scoreText = binding.scoreText





        // Check the user's input
        checkButton.setOnClickListener {
            val userAnswer = answerEditText.text.toString().trim()

            if (userAnswer.isEmpty()) {
                Toast.makeText(this, "Please enter a word", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userAnswer.equals(correctWord, ignoreCase = true)) {
                score++
                resultText.text = "Correct! The word is $correctWord."
            } else {
                resultText.text = "Incorrect! The correct word is $correctWord."
            }
            scoreText.text = "Score: $score"
            nextButton.visibility = View.VISIBLE

            // Handle "Back to Menu" button click
            val backToMenuButton = findViewById<Button>(R.id.backToMenuButton)
            backToMenuButton.setOnClickListener {
                // Create an Intent to go back to the main menu (or any desired activity)
                val intent = Intent(this, MainActivity::class.java) // Replace MainActivity with your main activity class
                startActivity(intent)
                finish() // Optionally call finish() if you want to close this activity
            }
        }

        // Load next question
        nextButton.setOnClickListener {
            nextButton.visibility = View.GONE
            answerEditText.text.clear()
            loadRandomMeaning()
        }
    }

    private fun loadRandomMeaning() {
        lifecycleScope.launch {
            try {
                val response: Response<List<WordResult>> = RetrofitInstance.dictionaryApi.getMeaning(wordList.random())

                if (response.isSuccessful) {
                    val wordResults = response.body()
                    if (wordResults != null && wordResults.isNotEmpty()) {
                        // Randomly select a WordResult from the list
                        currentQuestion = wordResults[Random.nextInt(wordResults.size)]

                        // Randomly select a meaning from the chosen WordResult
                        val meaning = currentQuestion!!.meanings[Random.nextInt(currentQuestion!!.meanings.size)]
                        val definition = meaning.definitions[Random.nextInt(meaning.definitions.size)]

                        // Set the question for the user
                        currentDefinition = definition.definition
                        correctWord = currentQuestion!!.word

                        // Update the UI with the meaning
                        definitionText.text = "Definition: ${definition.definition}"

                        // Hide the result text initially
                        resultText.text = ""
                    }
                } else {
                    Toast.makeText(this@QuizActivity, "Error fetching data", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@QuizActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}