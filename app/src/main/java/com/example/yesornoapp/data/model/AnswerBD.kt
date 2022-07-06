package com.example.yesornoapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnswerBD(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "answer") val answer: String,
    @ColumnInfo(name = "image") val image: String
    )