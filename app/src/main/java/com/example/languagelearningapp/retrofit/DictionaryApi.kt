package com.example.languagelearningapp.retrofit

import com.example.languagelearningapp.model.WordResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface DictionaryApi {

    @GET("en/{word}")
    suspend fun getMeaning(@Path("word") word : String) : Response<List<WordResult>>

}