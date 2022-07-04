package com.example.yesornoapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yesornoapp.data.repository.MainRepository
import com.example.yesornoapp.data.retrofit.ApiService
import com.example.yesornoapp.presenter.MainViewModel

class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiService = apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}