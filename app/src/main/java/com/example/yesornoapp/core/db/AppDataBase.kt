package com.example.yesornoapp.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.yesornoapp.data.model.AnswerBD

@Database(
    entities = [AnswerBD::class],
    version = 3,
    exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun answerDao(): AnswerDAO
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE 'AnswerBD' RENAME COLUMN 'text'  TO 'answer';")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE 'AnswerBD' RENAME COLUMN 'url'  TO 'image';")
    }
}
//
//val MIGRATION_3_4 = object : Migration(3, 4) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL(
//            "RENAME TABLE 'AnswerBD' TO 'AnswerDB';")
//    }
//}