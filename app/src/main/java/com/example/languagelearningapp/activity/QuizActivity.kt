package com.example.languagelearningapp.activity

import android.content.Intent
import android.os.Bundle
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
import com.example.languagelearningapp.model.WordResult
import com.example.languagelearningapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {
    private lateinit var definitionText: TextView
    private lateinit var answerEditText: EditText
    private lateinit var checkButton: Button
    private lateinit var nextButton: Button
    private lateinit var resultText: TextView
    private lateinit var scoreText: TextView

    private var currentQuestion: WordResult? = null
    private var currentDefinition: String? = null
    private var correctWord: String? = null
    private var score = 0

    private val wordList = listOf(
        "hello", "world", "language", "android", "programming", "app", "java",
        "kotlin", "dictionary", "example", "code", "study", "computer",
        "development", "mobile", "tutorial", "internet", "software", "design",
        "education"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI elements
        definitionText = findViewById(R.id.definitionText)
        answerEditText = findViewById(R.id.answerEditText)
        checkButton = findViewById(R.id.checkButton)
        nextButton = findViewById(R.id.nextButton)
        resultText = findViewById(R.id.resultText)
        scoreText = findViewById(R.id.scoreText)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Load random meaning from the API
        loadRandomMeaning()

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