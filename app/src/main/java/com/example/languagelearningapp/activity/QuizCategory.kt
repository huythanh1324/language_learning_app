package com.example.languagelearningapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.languagelearningapp.R
import com.example.languagelearningapp.adapter.QuizCategoryAdapter
import com.example.languagelearningapp.databinding.ActivityQuizCategoryBinding
import com.example.languagelearningapp.model.QuizCategory

class QuizCategory : AppCompatActivity() {

    private lateinit var binding: ActivityQuizCategoryBinding
    private lateinit var adapter: QuizCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryList : List<QuizCategory> = initRecyclerView()

        adapter = QuizCategoryAdapter(categoryList)
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.categoryRecyclerView.adapter = adapter
    }


    private fun initRecyclerView():List<QuizCategory>{
        return listOf(
            QuizCategory("Animals",R.drawable.animal_quiz,R.drawable.bg_1,R.drawable.quiz_item_background_1),
            QuizCategory("Colors",R.drawable.color_quiz,R.drawable.bg_2,R.drawable.quiz_item_background_2),
            QuizCategory("Actions",R.drawable.action_quiz,R.drawable.bg_3,R.drawable.quiz_item_background_3),
            QuizCategory("Fruits",R.drawable.fruit_quiz,R.drawable.bg_4,R.drawable.quiz_item_background_4),
            QuizCategory("Professions",R.drawable.profession_quiz,R.drawable.bg_5,R.drawable.quiz_item_background_5),
        )
    }
}