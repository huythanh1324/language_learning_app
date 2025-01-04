package com.example.languagelearningapp.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.languagelearningapp.databinding.FlashcardItemRecyclerBinding

class FlashCardAdapter(private var flashCardList: List<Pair<String, String>>, private val editHandler : (Int,String,String) -> Unit, private val deleteHandler: (Int) -> Unit): RecyclerView.Adapter<FlashCardAdapter.FlashCardViewHolder>() {
    class FlashCardViewHolder(private val binding:FlashcardItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(flashCard: Pair<String, String>,position: Int,editHandler: (Int,String,String) -> Unit,deleteHandler: (Int) -> Unit) {
            binding.englishText.setText(flashCard.first)
            binding.vietnameseText.setText(flashCard.second)
            binding.editButton.setOnClickListener {
                editHandler(position,  binding.englishText.text.toString(), binding.vietnameseText.text.toString())
            }
            binding.deleteButton.setOnClickListener {
                deleteHandler(position)
            }
        }
    }

    fun updateNewData(newFlashCardList: MutableList<Pair<String, String>>){
        flashCardList = newFlashCardList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardViewHolder {
        val binding = FlashcardItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlashCardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return flashCardList.size
    }

    override fun onBindViewHolder(holder: FlashCardViewHolder, position: Int) {
        holder.bind(flashCardList[position],position,editHandler,deleteHandler)
    }
}