package com.example.yesornoapp.data.retrofit

import com.example.yesornoapp.data.model.Answer
import retrofit2.http.GET

interface ApiService {

    @GET("api")
    suspend fun getAnswer(): Answer
}