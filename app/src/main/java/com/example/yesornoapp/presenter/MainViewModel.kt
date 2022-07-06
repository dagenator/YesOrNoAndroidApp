package com.example.yesornoapp.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.yesornoapp.core.Resource
import com.example.yesornoapp.core.Status
import com.example.yesornoapp.data.model.Answer
import com.example.yesornoapp.data.useCases.GetAnswerFromApiUseCase
import kotlinx.coroutines.Dispatchers

class MainViewModel (val getAnswerFromApi: GetAnswerFromApiUseCase) :
    ViewModel() {

    fun getAnswer() = liveData(Dispatchers.IO) {
        getAnswerFromApi().collect { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let {
                        emit(resource)
                    }
                }
                Status.ERROR -> {
                    resource.data?.let {
                        val loadAnswer = Answer("last:" + it.answer, it.forced, it.image)
                        emit(Resource.error(loadAnswer, resource.message ?: "error happened" ))
                    }
                    if(resource.data ==null)
                        emit(Resource.error(null, "DB empty + "+resource.message ))

                }
                Status.LOADING -> {
                    emit(Resource.loading(null))
                }
            }
        }
    }
}
