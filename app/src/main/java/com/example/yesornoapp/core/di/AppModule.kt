package com.example.yesornoapp.core.di

import android.content.Context
import com.example.yesornoapp.data.useCases.GetAnswerFromApiUseCase
import com.example.yesornoapp.data.useCases.GetAnswerFromApiUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module()
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun providesGetAnswerFromApiUseCases( getAnswerFromApiImpl: GetAnswerFromApiUseCaseImpl): GetAnswerFromApiUseCase{
        return getAnswerFromApiImpl
    }


}

