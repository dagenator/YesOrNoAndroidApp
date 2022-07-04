package com.example.yesornoapp.core.di

import com.example.yesornoapp.data.retrofit.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    // @Provides tell Dagger how to create instances of the type that this function
    // returns (i.e. LoginRetrofitService).
    // Function parameters are the dependencies of this type.

    @Provides
    fun provideApiService(): ApiService {

        // Whenever Dagger needs to provide an instance of type LoginRetrofitService,
        // this code (the one inside the @Provides method) is run.
        return Retrofit.Builder()
            .baseUrl("https://yesno.wtf/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}