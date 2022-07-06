package com.example.yesornoapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.yesornoapp.core.db.AppDatabase
import com.example.yesornoapp.core.db.MIGRATION_1_2
import com.example.yesornoapp.core.db.MIGRATION_2_3
import dagger.Module
import dagger.Provides

@Module
class DBModule {

    @Provides
    fun provideAppDatabase(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
    }
}