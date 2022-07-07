package com.example.yesornoapp.data.repository

import com.example.yesornoapp.core.Resource
import com.example.yesornoapp.core.db.AppDatabase
import com.example.yesornoapp.core.retrofit.ApiService
import com.example.yesornoapp.data.model.Answer
import com.example.yesornoapp.data.model.AnswerBD
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(val apiService: ApiService, val answerBD: AppDatabase) {


    suspend fun getAnswer() = flow<Resource<Answer>> {

        emit(Resource.loading(data = null))
        try {
            val answer = apiService.getAnswer()
            saveAnswerToDatabase(answer)
            emit(Resource.success(data = answer))
        } catch (exception: Exception) {
            var data: Answer? = null
            if (getDatabaseSize() > 0) {
                data = getLastAnswerFromDatabase()
            }
            emit(Resource.error(data = data, message = exception.message ?: "Error Occurred!"))

        }

    }


    fun getLastAnswerFromDatabase(): Answer {
        val answerDao = answerBD.answerDao()
        val answerBD: AnswerBD = answerDao.getLastAnswer()[0]
        return Answer(answerBD.answer, false, answerBD.image)
    }


    fun saveAnswerToDatabase(answer: Answer) {
        val answerDao = answerBD.answerDao()
        answerDao.insertNewAnswer(
            AnswerBD(
                answerDao.getMaxIdFromTable() + 1,
                answer.answer,
                answer.image
            )
        )
    }

    fun getDatabaseSize(): Int {
        val answerDao = answerBD.answerDao()
        return answerDao.getSize()
    }


}