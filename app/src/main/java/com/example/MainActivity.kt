package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val viewModel: EduViewModel = viewModel()
      val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()
      MyApplicationTheme(darkTheme = isDarkTheme) {
        Surface(modifier = Modifier.fillMaxSize()) {
          val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
          val progress by viewModel.userProgress.collectAsStateWithLifecycle()

          Crossfade(targetState = currentScreen, label = "screen_navigation") { screen ->
            when (screen) {
              is Screen.Dashboard -> DashboardScreen(
                viewModel = viewModel,
                progress = progress
              )
              is Screen.EduBotChat -> EduBotChatScreen(
                viewModel = viewModel,
                progress = progress
              )
               is Screen.QuizSettings -> QuizSettingsScreen(
                viewModel = viewModel,
                progress = progress
              )
              is Screen.QuizPlay -> QuizPlayScreen(
                viewModel = viewModel,
                topic = screen.topic,
                progress = progress
              )
              is Screen.QuizScore -> QuizScoreScreen(
                viewModel = viewModel,
                topic = screen.topic,
                score = screen.score,
                total = screen.total,
                starsEarned = screen.stars,
                progress = progress
              )
              is Screen.MathSettings -> MathSettingsScreen(
                viewModel = viewModel,
                progress = progress
              )
              is Screen.MathPlay -> MathPlayScreen(
                viewModel = viewModel,
                grade = screen.grade,
                category = screen.category,
                progress = progress
              )
              is Screen.MathScore -> MathScoreScreen(
                viewModel = viewModel,
                grade = screen.grade,
                category = screen.category,
                score = screen.score,
                total = screen.total,
                starsEarned = screen.stars,
                progress = progress
              )
              is Screen.EnglishSettings -> EnglishSettingsScreen(
                viewModel = viewModel,
                progress = progress
              )
              is Screen.EnglishPlay -> EnglishPlayScreen(
                viewModel = viewModel,
                grade = screen.grade,
                category = screen.category,
                progress = progress
              )
              is Screen.EnglishScore -> EnglishScoreScreen(
                viewModel = viewModel,
                grade = screen.grade,
                category = screen.category,
                score = screen.score,
                total = screen.total,
                starsEarned = screen.stars,
                progress = progress
              )
              is Screen.IslamicSettings -> IslamicSettingsScreen(
                viewModel = viewModel,
                progress = progress
              )
              is Screen.IslamicPlay -> IslamicPlayScreen(
                viewModel = viewModel,
                grade = screen.grade,
                category = screen.category,
                progress = progress
              )
              is Screen.IslamicScore -> IslamicScoreScreen(
                viewModel = viewModel,
                grade = screen.grade,
                category = screen.category,
                score = screen.score,
                total = screen.total,
                starsEarned = screen.stars,
                progress = progress
              )
              is Screen.WordPuzzle -> WordPuzzleScreen(
                viewModel = viewModel,
                progress = progress
              )
              is Screen.Hangman -> HangmanScreen(
                viewModel = viewModel,
                progress = progress
              )
              is Screen.DailyChallenge -> DailyChallengeScreen(
                viewModel = viewModel,
                progress = progress
              )
              is Screen.Achievements -> AchievementsRoomScreen(
                viewModel = viewModel,
                progress = progress
              )
              is Screen.MathLandMap -> MathLandMapScreen(
                viewModel = viewModel,
                progress = progress
              )
              is Screen.MathLandPlay -> MathLandPlayScreen(
                viewModel = viewModel,
                levelId = screen.levelId,
                progress = progress
              )
              is Screen.MathLandScore -> MathLandScoreScreen(
                viewModel = viewModel,
                levelId = screen.levelId,
                score = screen.score,
                total = screen.total,
                starsEarned = screen.stars,
                succeeded = screen.succeeded,
                progress = progress
              )
            }
          }
        }
      }
    }
  }
}
