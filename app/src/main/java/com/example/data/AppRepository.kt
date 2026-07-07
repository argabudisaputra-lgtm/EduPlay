package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AppRepository(
    private val userDao: UserDao,
    private val quizHistoryDao: QuizHistoryDao
) {
    val userProgress: Flow<UserProgress?> = userDao.getUserProgress()
    val quizHistory: Flow<List<QuizHistory>> = quizHistoryDao.getAllHistory()

    suspend fun saveProgress(progress: UserProgress) {
        userDao.insertOrUpdate(progress)
    }

    suspend fun addAndSaveQuizResult(topic: String, score: Int, total: Int, starsEarned: Int) {
        // Log the quiz history
        val history = QuizHistory(
            topic = topic,
            score = score,
            totalQuestions = total,
            starsEarned = starsEarned
        )
        quizHistoryDao.insertHistory(history)

        // Calculate and add XP and Stars
        // Correct answers give 20 XP each, completion gives 10 XP base.
        val xpEarned = (score * 20) + 10
        updateUserStats(xpEarned, starsEarned)
    }

    suspend fun addPuzzleSolvedResult(starsEarned: Int) {
        // Word scramble puzzle gives 15 XP + stars
        updateUserStats(15, starsEarned)
    }

    private suspend fun updateUserStats(xpEarned: Int, starsEarned: Int) {
        val currentProgress = userProgress.firstOrNull() ?: UserProgress()
        var newXp = currentProgress.xp + xpEarned
        var newLevel = currentProgress.level
        val newStars = currentProgress.starsCount + starsEarned

        // 100 XP per level
        while (newXp >= 100) {
            newXp -= 100
            newLevel += 1
        }

        val updatedProgress = currentProgress.copy(
            starsCount = newStars,
            level = newLevel,
            xp = newXp
        )
        userDao.insertOrUpdate(updatedProgress)
    }

    suspend fun updateUsername(newName: String) {
        val currentProgress = userProgress.firstOrNull() ?: UserProgress()
        userDao.insertOrUpdate(currentProgress.copy(name = newName))
    }

    suspend fun updateUserAge(newAge: Int) {
        val currentProgress = userProgress.firstOrNull() ?: UserProgress()
        userDao.insertOrUpdate(currentProgress.copy(age = newAge))
    }

    suspend fun updateUserProfile(newName: String, newAge: Int) {
        val currentProgress = userProgress.firstOrNull() ?: UserProgress()
        userDao.insertOrUpdate(currentProgress.copy(name = newName, age = newAge))
    }

    suspend fun updateLanguage(language: String) {
        val currentProgress = userProgress.firstOrNull() ?: UserProgress()
        userDao.insertOrUpdate(currentProgress.copy(language = language))
    }

    suspend fun completeDailyChallenge(dateString: String, starsEarned: Int, xpEarned: Int) {
        val currentProgress = userProgress.firstOrNull() ?: UserProgress()
        var newXp = currentProgress.xp + xpEarned
        var newLevel = currentProgress.level
        val newStars = currentProgress.starsCount + starsEarned

        while (newXp >= 100) {
            newXp -= 100
            newLevel += 1
        }

        val updatedProgress = currentProgress.copy(
            starsCount = newStars,
            level = newLevel,
            xp = newXp,
            lastDailyChallengeDate = dateString
        )
        userDao.insertOrUpdate(updatedProgress)
    }

    suspend fun resetData() {
        quizHistoryDao.clearHistory()
        userDao.insertOrUpdate(UserProgress())
    }
}
