package com.example.yesornoapp.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.yesornoapp.data.Resource
import com.example.yesornoapp.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    fun getAnswer() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getAnswer()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}