package com.example.data

import android.content.Context
import android.util.Log

object QuestionHistoryTracker {
    private const val PREFS_NAME = "seen_questions_history_prefs"
    private const val SEEN_SET_KEY = "seen_questions_set"

    fun isQuestionSeen(context: Context, questionText: String, categoryKey: String = "general"): Boolean {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val seenSet = sharedPrefs.getStringSet("${SEEN_SET_KEY}_$categoryKey", emptySet()) ?: emptySet()
        val normalized = normalizeText(questionText)
        return seenSet.contains(normalized)
    }

    fun markQuestionAsSeen(context: Context, questionText: String, categoryKey: String = "general") {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val key = "${SEEN_SET_KEY}_$categoryKey"
        val seenSet = sharedPrefs.getStringSet(key, emptySet())?.toMutableSet() ?: mutableSetOf()
        seenSet.add(normalizeText(questionText))
        sharedPrefs.edit().putStringSet(key, seenSet).apply()
        Log.d("QuestionTracker", "Marked as seen: $questionText in category: $categoryKey")
    }

    fun clearCategoryHistory(context: Context, categoryKey: String) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefs.edit().remove("${SEEN_SET_KEY}_$categoryKey").apply()
        Log.d("QuestionTracker", "Category history cleared: $categoryKey")
    }

    fun getSeenCount(context: Context, categoryKey: String): Int {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val seenSet = sharedPrefs.getStringSet("${SEEN_SET_KEY}_$categoryKey", emptySet()) ?: emptySet()
        return seenSet.size
    }

    fun clearHistory(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply()
        Log.d("QuestionTracker", "All seen history cleared!")
    }

    private fun normalizeText(text: String): String {
        return text.trim().lowercase().replace(Regex("[^a-zA-Z0-9]"), "")
    }
}
