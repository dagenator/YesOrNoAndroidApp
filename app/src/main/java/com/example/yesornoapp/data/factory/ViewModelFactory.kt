package com.example.yesornoapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yesornoapp.core.db.AppDatabase
import com.example.yesornoapp.data.repository.MainRepository
import com.example.yesornoapp.core.retrofit.ApiService
import com.example.yesornoapp.data.useCases.GetAnswerFromApiUseCase
import com.example.yesornoapp.presenter.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor (val getAnswerFromApi: GetAnswerFromApiUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(getAnswerFromApi) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}