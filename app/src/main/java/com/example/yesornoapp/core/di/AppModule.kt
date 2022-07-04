package com.example.yesornoapp.core.di

import android.content.Context
import com.example.yesornoapp.data.factory.ViewModelFactory
import com.example.yesornoapp.data.retrofit.ApiService
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context:Context) {

    @Provides
    fun provideContext():Context{
        return context
    }

    @Provides
    fun provideMainViewModelFactory(apiService: ApiService):ViewModelFactory{
        return ViewModelFactory(apiService)
    }

}