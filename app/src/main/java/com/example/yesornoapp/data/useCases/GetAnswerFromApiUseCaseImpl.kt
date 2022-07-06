package com.example.yesornoapp.data.useCases

import com.example.yesornoapp.core.Resource
import com.example.yesornoapp.data.model.Answer
import com.example.yesornoapp.data.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnswerFromApiUseCaseImpl @Inject constructor(val mainRepository: MainRepository):GetAnswerFromApiUseCase {
    override suspend fun invoke(): Flow<Resource<Answer>>  = mainRepository.getAnswer()
}