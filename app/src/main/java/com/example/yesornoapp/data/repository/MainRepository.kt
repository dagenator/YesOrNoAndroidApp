package com.example.yesornoapp.data.repository

import com.example.yesornoapp.data.retrofit.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getAnswer() = apiHelper.getAnswer()
}