package com.example.languagelearningapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.languagelearningapp.R
import com.example.languagelearningapp.activity.QuizActivity
import com.example.languagelearningapp.databinding.QuizCategoryItemBinding
import com.example.languagelearningapp.model.Meaning
import com.example.languagelearningapp.model.QuizCategory

class QuizCategoryAdapter(private var categoryList: List<QuizCategory>): RecyclerView.Adapter<QuizCategoryAdapter.QuizCategoryViewHolder>() {
    class QuizCategoryViewHolder(private val binding: QuizCategoryItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: QuizCategory){
            binding.categoryTitle.text = category.title
            binding.categoryImage.setImageResource(category.picPath)
            binding.categoryBackground.setImageResource(category.backgroundPath)
            binding.backgroundOutline.setBackgroundResource(category.outlinePath)
            binding.backgroundOutline.setOnClickListener {
                val context = it.context
                val intent = Intent(context, QuizActivity::class.java)
                intent.putExtra("category_quiz", category.title.lowercase())
                context.startActivity(intent)
            }
        }
    }

    fun updateNewData(newMeaningList: List<QuizCategory>){
        categoryList = newMeaningList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizCategoryViewHolder {
        val binding = QuizCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: QuizCategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }
}