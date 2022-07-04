package com.example.yesornoapp.data.repository

import com.example.yesornoapp.data.retrofit.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(val apiService: ApiService) {
    suspend fun getAnswer() = apiService.getAnswer()
}