package com.example.yesornoapp.data.retrofit

class ApiHelper(private val apiService: ApiService) {

    suspend fun getAnswer() = apiService.getAnswer()
}