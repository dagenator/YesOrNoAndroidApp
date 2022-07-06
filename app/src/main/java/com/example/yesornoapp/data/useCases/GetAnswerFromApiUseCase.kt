package com.example.yesornoapp.data.useCases

import com.example.yesornoapp.core.Resource
import com.example.yesornoapp.data.model.Answer
import kotlinx.coroutines.flow.Flow

interface GetAnswerFromApiUseCase {
    suspend operator fun invoke(): Flow<Resource<Answer>>
}