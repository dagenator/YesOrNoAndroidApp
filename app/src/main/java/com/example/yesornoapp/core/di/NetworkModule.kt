package com.example.yesornoapp.core.di

import com.example.yesornoapp.core.retrofit.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideApiService(): ApiService {

        return Retrofit.Builder()
            .baseUrl("https://yesno.wtf/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}