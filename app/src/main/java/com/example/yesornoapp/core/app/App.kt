package com.example.yesornoapp.core.app

import android.app.Application
import com.example.yesornoapp.core.di.AppComponent
import com.example.yesornoapp.core.di.AppModule
import com.example.yesornoapp.core.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()

    }
}