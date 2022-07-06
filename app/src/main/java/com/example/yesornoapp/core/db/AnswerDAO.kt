package com.example.yesornoapp.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.yesornoapp.data.model.AnswerBD

@Dao
interface AnswerDAO {

    @Query("SELECT  * FROM AnswerBD as bd where bd.id = (SELECT MAX(id) FROM AnswerBD)")
    fun getLastAnswer(): List<AnswerBD>

    @Insert
    fun insertNewAnswer(vararg answer: AnswerBD)

    @Query("DELETE FROM AnswerBD where id = (SELECT MIN(id) FROM AnswerBD)")
    fun deleteAnswerBeforeLastOne()

    @Query("SELECT MAX(id) FROM AnswerBD")
    fun getMaxIdFromTable(): Int

    @Query("SELECT COUNT(id) FROM AnswerBD")
    fun getSize(): Int

}