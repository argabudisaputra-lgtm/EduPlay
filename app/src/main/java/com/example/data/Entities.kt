package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val id: Int = 1,
    val name: String = "Sobat Edu 🛸",
    val starsCount: Int = 10,  // start with a tiny reward!
    val level: Int = 1,
    val xp: Int = 0,
    val lastDailyChallengeDate: String = "",
    val age: Int = 7,
    val language: String = "id"
)

@Entity(tableName = "quiz_history")
data class QuizHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val topic: String,
    val score: Int,
    val totalQuestions: Int,
    val starsEarned: Int,
    val timestamp: Long = System.currentTimeMillis()
)
