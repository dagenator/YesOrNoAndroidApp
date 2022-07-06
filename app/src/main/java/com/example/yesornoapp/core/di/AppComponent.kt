package com.example.yesornoapp.core.di

import com.example.yesornoapp.presenter.MainActivity
import dagger.Component

@Component(modules = [AppModule::class, NetworkModule::class, DBModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}