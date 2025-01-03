package com.example.languagelearningapp.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.languagelearningapp.R
import com.example.languagelearningapp.databinding.ActivityTranslateBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class TranslateActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTranslateBinding
    private lateinit var translator : Translator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val inputEditText = binding.inputEditText
        val outputTextview = binding.outputTextview
        val translateBtn = binding.translateBtn
        val progressionBar = binding.progressBar

        translateBtn.visibility = View.GONE
        progressionBar.visibility = View.VISIBLE

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.VIETNAMESE)
            .build()
        translator = Translation.getClient(options)

        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                translateBtn.visibility = View.VISIBLE
                progressionBar.visibility = View.GONE

                translateBtn.setOnClickListener {
                    val textToTranslate = inputEditText.text.toString()
                    tranlateText(textToTranslate, outputTextview)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this,"Occur an error $exception",Toast.LENGTH_LONG).show()
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        translator.close()
    }
    private fun tranlateText(inputText:String, outputTextview : TextView){
        translator.translate(inputText)
            .addOnSuccessListener { translatedText ->
                outputTextview.text = translatedText
            }
            .addOnFailureListener { exception ->
                outputTextview.text = "Translation Failed"
                Toast.makeText(this,"Occur an error $exception",Toast.LENGTH_LONG).show()
            }
    }


}