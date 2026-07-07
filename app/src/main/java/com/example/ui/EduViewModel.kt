package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class Screen {
    object Dashboard : Screen()
    object EduBotChat : Screen()
    object QuizSettings : Screen()
    data class QuizPlay(val topic: String) : Screen()
    data class QuizScore(val topic: String, val score: Int, val total: Int, val stars: Int) : Screen()
    object WordPuzzle : Screen()
    object Hangman : Screen()
    object DailyChallenge : Screen()
    object Achievements : Screen()
    object MathSettings : Screen()
    data class MathPlay(val grade: Int, val category: String) : Screen()
    data class MathScore(val grade: Int, val category: String, val score: Int, val total: Int, val stars: Int) : Screen()
    object EnglishSettings : Screen()
    data class EnglishPlay(val grade: Int, val category: String) : Screen()
    data class EnglishScore(val grade: Int, val category: String, val score: Int, val total: Int, val stars: Int) : Screen()
    object IslamicSettings : Screen()
    data class IslamicPlay(val grade: Int, val category: String) : Screen()
    data class IslamicScore(val grade: Int, val category: String, val score: Int, val total: Int, val stars: Int) : Screen()
    object MathLandMap : Screen()
    data class MathLandPlay(val levelId: Int) : Screen()
    data class MathLandScore(val levelId: Int, val score: Int, val total: Int, val stars: Int, val succeeded: Boolean) : Screen()
}

// Chat bubble data model
data class ChatMessage(
    val sender: String, // "user" or "edubot"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

class EduViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = AppRepository(db.userDao(), db.quizHistoryDao())

    // --- State Streams ---
    val userProgress: StateFlow<UserProgress?> = repository.userProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProgress())

    val quizHistory: StateFlow<List<QuizHistory>> = repository.quizHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Currently Selected Screen
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Dashboard)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // --- Theme State (Cerah vs Petang) ---
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        val prefs = application.getSharedPreferences("eduplay_prefs", android.content.Context.MODE_PRIVATE)
        _isDarkTheme.value = prefs.getBoolean("is_dark_theme", false)
    }

    fun setTheme(dark: Boolean) {
        _isDarkTheme.value = dark
        val prefs = getApplication<Application>().getSharedPreferences("eduplay_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit().putBoolean("is_dark_theme", dark).apply()
    }

    // --- EduBot Chat States ---
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(listOf(
        ChatMessage("edubot", "Halo! Aku EduBot 🤖 Robot pendamping belajarmu yang asyik! Tanyakan apa saja yang ingin kamu ketahui hari ini, misalnya: 'Kenapa awan berwarna putih?'")
    ))
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    private val geminiHistory = mutableListOf<GeminiContent>()

    // --- Quiz States ---
    private val _selectedAgeGroup = MutableStateFlow("PAUD & TK 🦄 (Umur 3-6)")
    val selectedAgeGroup: StateFlow<String> = _selectedAgeGroup.asStateFlow()

    private val _quizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions.asStateFlow()

    private val _isQuizLoading = MutableStateFlow(false)
    val isQuizLoading: StateFlow<Boolean> = _isQuizLoading.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedAnswerIndex: StateFlow<Int?> = _selectedAnswerIndex.asStateFlow()

    private val _isAnswerSubmitted = MutableStateFlow(false)
    val isAnswerSubmitted: StateFlow<Boolean> = _isAnswerSubmitted.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    val quizExplanationExplanation: StateFlow<String?> = combine(_currentQuestionIndex, _quizQuestions) { index, questions ->
        if (questions.isNotEmpty() && index in questions.indices) {
            questions[index].explanation
        } else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // --- Math Quiz States ---
    private val _selectedMathGrade = MutableStateFlow(1) // 1..12
    val selectedMathGrade: StateFlow<Int> = _selectedMathGrade.asStateFlow()

    private val _selectedMathCategory = MutableStateFlow("Perkalian") // "Perkalian", "Pembagian", "Campuran", "Cerita"
    val selectedMathCategory: StateFlow<String> = _selectedMathCategory.asStateFlow()

    private val _mathQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val mathQuestions: StateFlow<List<QuizQuestion>> = _mathQuestions.asStateFlow()

    private val _isMathLoading = MutableStateFlow(false)
    val isMathLoading: StateFlow<Boolean> = _isMathLoading.asStateFlow()

    private val _currentMathQuestionIndex = MutableStateFlow(0)
    val currentMathQuestionIndex: StateFlow<Int> = _currentMathQuestionIndex.asStateFlow()

    private val _selectedMathAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedMathAnswerIndex: StateFlow<Int?> = _selectedMathAnswerIndex.asStateFlow()

    private val _isMathAnswerSubmitted = MutableStateFlow(false)
    val isMathAnswerSubmitted: StateFlow<Boolean> = _isMathAnswerSubmitted.asStateFlow()

    private val _mathScore = MutableStateFlow(0)
    val mathScore: StateFlow<Int> = _mathScore.asStateFlow()

    val mathExplanation: StateFlow<String?> = combine(_currentMathQuestionIndex, _mathQuestions) { index, questions ->
        if (questions.isNotEmpty() && index in questions.indices) {
            questions[index].explanation
        } else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // --- English Quiz States ---
    private val _selectedEnglishGrade = MutableStateFlow(1) // 1..12
    val selectedEnglishGrade: StateFlow<Int> = _selectedEnglishGrade.asStateFlow()

    private val _selectedEnglishCategory = MutableStateFlow("Vocabulary") // "Vocabulary", "Grammar", "Conversation", "Reading"
    val selectedEnglishCategory: StateFlow<String> = _selectedEnglishCategory.asStateFlow()

    private val _englishQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val englishQuestions: StateFlow<List<QuizQuestion>> = _englishQuestions.asStateFlow()

    private val _isEnglishLoading = MutableStateFlow(false)
    val isEnglishLoading: StateFlow<Boolean> = _isEnglishLoading.asStateFlow()

    private val _currentEnglishQuestionIndex = MutableStateFlow(0)
    val currentEnglishQuestionIndex: StateFlow<Int> = _currentEnglishQuestionIndex.asStateFlow()

    private val _selectedEnglishAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedEnglishAnswerIndex: StateFlow<Int?> = _selectedEnglishAnswerIndex.asStateFlow()

    private val _isEnglishAnswerSubmitted = MutableStateFlow(false)
    val isEnglishAnswerSubmitted: StateFlow<Boolean> = _isEnglishAnswerSubmitted.asStateFlow()

    private val _englishScore = MutableStateFlow(0)
    val englishScore: StateFlow<Int> = _englishScore.asStateFlow()

    val englishExplanation: StateFlow<String?> = combine(_currentEnglishQuestionIndex, _englishQuestions) { index, questions ->
        if (questions.isNotEmpty() && index in questions.indices) {
            questions[index].explanation
        } else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // --- Islamic Quiz States ---
    private val _selectedIslamicGrade = MutableStateFlow(1) // 1..12
    val selectedIslamicGrade: StateFlow<Int> = _selectedIslamicGrade.asStateFlow()

    private val _selectedIslamicCategory = MutableStateFlow("Aqidah & Akhlak") // "Aqidah & Akhlak", "Fiqih & Ibadah", "Al-Qur'an & Tajwid", "Sejarah Islam"
    val selectedIslamicCategory: StateFlow<String> = _selectedIslamicCategory.asStateFlow()

    private val _islamicQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val islamicQuestions: StateFlow<List<QuizQuestion>> = _islamicQuestions.asStateFlow()

    private val _isIslamicLoading = MutableStateFlow(false)
    val isIslamicLoading: StateFlow<Boolean> = _isIslamicLoading.asStateFlow()

    private val _currentIslamicQuestionIndex = MutableStateFlow(0)
    val currentIslamicQuestionIndex: StateFlow<Int> = _currentIslamicQuestionIndex.asStateFlow()

    private val _selectedIslamicAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedIslamicAnswerIndex: StateFlow<Int?> = _selectedIslamicAnswerIndex.asStateFlow()

    private val _isIslamicAnswerSubmitted = MutableStateFlow(false)
    val isIslamicAnswerSubmitted: StateFlow<Boolean> = _isIslamicAnswerSubmitted.asStateFlow()

    private val _islamicScore = MutableStateFlow(0)
    val islamicScore: StateFlow<Int> = _islamicScore.asStateFlow()

    val islamicExplanation: StateFlow<String?> = combine(_currentIslamicQuestionIndex, _islamicQuestions) { index, questions ->
        if (questions.isNotEmpty() && index in questions.indices) {
            questions[index].explanation
        } else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // --- Math Land States ---
    private val sharedPrefs = application.getSharedPreferences("math_land_prefs", android.content.Context.MODE_PRIVATE)

    private val _unlockedMathLandLevel = MutableStateFlow(1)
    val unlockedMathLandLevel: StateFlow<Int> = _unlockedMathLandLevel.asStateFlow()

    private val _unlockedAgeGroupIndex = MutableStateFlow(0)
    val unlockedAgeGroupIndex: StateFlow<Int> = _unlockedAgeGroupIndex.asStateFlow()

    private val _mathLandLevelStars = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val mathLandLevelStars: StateFlow<Map<Int, Int>> = _mathLandLevelStars.asStateFlow()

    private val _currentMathLandLevel = MutableStateFlow(1)
    val currentMathLandLevel: StateFlow<Int> = _currentMathLandLevel.asStateFlow()

    private val _mathLandQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val mathLandQuestions: StateFlow<List<QuizQuestion>> = _mathLandQuestions.asStateFlow()

    private val _isMathLandLoading = MutableStateFlow(false)
    val isMathLandLoading: StateFlow<Boolean> = _isMathLandLoading.asStateFlow()

    private val _currentMathLandQuestionIndex = MutableStateFlow(0)
    val currentMathLandQuestionIndex: StateFlow<Int> = _currentMathLandQuestionIndex.asStateFlow()

    private val _selectedMathLandAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedMathLandAnswerIndex: StateFlow<Int?> = _selectedMathLandAnswerIndex.asStateFlow()

    private val _isMathLandAnswerSubmitted = MutableStateFlow(false)
    val isMathLandAnswerSubmitted: StateFlow<Boolean> = _isMathLandAnswerSubmitted.asStateFlow()

    private val _mathLandScore = MutableStateFlow(0)
    val mathLandScore: StateFlow<Int> = _mathLandScore.asStateFlow()

    private val _bossHp = MutableStateFlow(1.0f) // 0.0f to 1.0f
    val bossHp: StateFlow<Float> = _bossHp.asStateFlow()

    private val _playerHp = MutableStateFlow(1.0f) // 0.0f to 1.0f
    val playerHp: StateFlow<Float> = _playerHp.asStateFlow()

    val mathLandExplanation: StateFlow<String?> = combine(_currentMathLandQuestionIndex, _mathLandQuestions) { index, questions ->
        if (questions.isNotEmpty() && index in questions.indices) {
            questions[index].explanation
        } else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // --- Word Scramble Puzzle States ---
    private val _currentWordCategory = MutableStateFlow("Sains 🧪")
    val currentWordCategory: StateFlow<String> = _currentWordCategory.asStateFlow()

    private val _currentWordPuzzle = MutableStateFlow<WordScramblePuzzle?>(null)
    val currentWordPuzzle: StateFlow<WordScramblePuzzle?> = _currentWordPuzzle.asStateFlow()

    private val _isPuzzleLoading = MutableStateFlow(false)
    val isPuzzleLoading: StateFlow<Boolean> = _isPuzzleLoading.asStateFlow()

    private val _puzzleAnswerInput = MutableStateFlow("")
    val puzzleAnswerInput: StateFlow<String> = _puzzleAnswerInput.asStateFlow()

    // Feedback: "CORRECT", "INCORRECT", "HELP", "NONE"
    private val _puzzleFeedback = MutableStateFlow("NONE")
    val puzzleFeedback: StateFlow<String> = _puzzleFeedback.asStateFlow()

    private val _scrambleStreak = MutableStateFlow(0)
    val scrambleStreak: StateFlow<Int> = _scrambleStreak.asStateFlow()

    private val _scrambleScore = MutableStateFlow(0)
    val scrambleScore: StateFlow<Int> = _scrambleScore.asStateFlow()

    private val _puzzleHint = MutableStateFlow<String?>(null)
    val puzzleHint: StateFlow<String?> = _puzzleHint.asStateFlow()

    private val _isHintLoading = MutableStateFlow(false)
    val isHintLoading: StateFlow<Boolean> = _isHintLoading.asStateFlow()

    // --- Hangman Game States ---
    private val _currentHangmanCategory = MutableStateFlow("Sains 🧪")
    val currentHangmanCategory: StateFlow<String> = _currentHangmanCategory.asStateFlow()

    private val _currentHangmanWordState = MutableStateFlow<HangmanWord?>(null)
    val currentHangmanWordState: StateFlow<HangmanWord?> = _currentHangmanWordState.asStateFlow()

    private val _isHangmanLoading = MutableStateFlow(false)
    val isHangmanLoading: StateFlow<Boolean> = _isHangmanLoading.asStateFlow()

    private val _guessedLetters = MutableStateFlow<Set<Char>>(emptySet())
    val guessedLetters: StateFlow<Set<Char>> = _guessedLetters.asStateFlow()

    private val _remainingLives = MutableStateFlow(6)
    val remainingLives: StateFlow<Int> = _remainingLives.asStateFlow()

    // "PLAYING", "WON", "LOST"
    private val _hangmanGameStatus = MutableStateFlow("PLAYING")
    val hangmanGameStatus: StateFlow<String> = _hangmanGameStatus.asStateFlow()

    private val _hangmanStreak = MutableStateFlow(0)
    val hangmanStreak: StateFlow<Int> = _hangmanStreak.asStateFlow()

    // --- Daily Challenge States ---
    private val _dailyChallengeState = MutableStateFlow<DailyChallengeQuestion?>(null)
    val dailyChallengeState: StateFlow<DailyChallengeQuestion?> = _dailyChallengeState.asStateFlow()

    private val _isDailyLoading = MutableStateFlow(false)
    val isDailyLoading: StateFlow<Boolean> = _isDailyLoading.asStateFlow()

    private val _dailySelectedAnswer = MutableStateFlow<Int?>(null)
    val dailySelectedAnswer: StateFlow<Int?> = _dailySelectedAnswer.asStateFlow()

    private val _dailyIsAnswerSubmitted = MutableStateFlow(false)
    val dailyIsAnswerSubmitted: StateFlow<Boolean> = _dailyIsAnswerSubmitted.asStateFlow()

    private val _dailyLoadedDate = MutableStateFlow("")
    val dailyLoadedDate: StateFlow<String> = _dailyLoadedDate.asStateFlow()

    // Initialize/Sync Room with a Default profile if empty
    init {
        viewModelScope.launch {
            repository.userProgress.collect { progress ->
                if (progress == null) {
                    repository.saveProgress(UserProgress())
                }
            }
        }
        loadMathLandProgress()
    }

    // --- Actions ---

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun updateName(newName: String) {
        viewModelScope.launch {
            repository.updateUsername(newName)
        }
    }

    fun updateLanguage(lang: String) {
        viewModelScope.launch {
            repository.updateLanguage(lang)
        }
    }

    fun updateUserProfile(newName: String, newAge: Int) {
        viewModelScope.launch {
            repository.updateUserProfile(newName, newAge)
            // auto selected age group based on input age
            val mappedGroup = when {
                newAge in 3..6 -> "PAUD & TK 🦄 (Umur 3-6)"
                newAge in 7..9 -> "SD Kelas Rendah 🎒 (Umur 7-9)"
                newAge in 10..12 -> "SD Kelas Tinggi 📚 (Umur 10-12)"
                newAge >= 13 -> "SMP & Remaja 🧠 (Umur 13+)"
                else -> "SD Kelas Rendah 🎒 (Umur 7-9)"
            }
            _selectedAgeGroup.value = mappedGroup
            
            val mappedIndex = when {
                newAge in 3..6 -> 0
                newAge in 7..9 -> 1
                newAge in 10..12 -> 2
                newAge >= 13 -> 3
                else -> 1
            }
            saveUnlockedAgeGroupIndex(mappedIndex)
        }
    }

    fun resetData() {
        viewModelScope.launch {
            repository.resetData()
            _chatMessages.value = listOf(
                ChatMessage("edubot", "Data berhasil direset! Mari mulai petualangan baru! 🚀 Ada yang ingin kamu pelajari hari ini?")
            )
            geminiHistory.clear()
            _scrambleStreak.value = 0
            resetMathLandProgress()
            _currentScreen.value = Screen.Dashboard
        }
    }

    // --- Math Land Actions ---

    fun loadMathLandProgress() {
        val unlocked = sharedPrefs.getInt("unlocked_level", 1)
        _unlockedMathLandLevel.value = unlocked
        _unlockedAgeGroupIndex.value = sharedPrefs.getInt("unlocked_age_group_index", 0)
        
        val starsMap = mutableMapOf<Int, Int>()
        for (i in 1..10) {
            val stars = sharedPrefs.getInt("level_stars_$i", 0)
            starsMap[i] = stars
        }
        _mathLandLevelStars.value = starsMap
    }

    fun saveUnlockedAgeGroupIndex(newIndex: Int) {
        val current = _unlockedAgeGroupIndex.value
        if (newIndex > current && newIndex <= 3) {
            sharedPrefs.edit().putInt("unlocked_age_group_index", newIndex).apply()
            _unlockedAgeGroupIndex.value = newIndex
        }
    }

    fun saveMathLandProgress(levelId: Int, score: Int, starsEarned: Int) {
        val oldStars = _mathLandLevelStars.value[levelId] ?: 0
        if (starsEarned > oldStars) {
            sharedPrefs.edit().putInt("level_stars_$levelId", starsEarned).apply()
        }
        
        val currentUnlocked = _unlockedMathLandLevel.value
        // Succeeded if earned at least 1 star (score >= 3)
        if (score >= 3 && levelId == currentUnlocked && levelId < 10) {
            val newUnlocked = levelId + 1
            sharedPrefs.edit().putInt("unlocked_level", newUnlocked).apply()
            _unlockedMathLandLevel.value = newUnlocked
        }
        
        loadMathLandProgress()
    }

    fun resetMathLandProgress() {
        sharedPrefs.edit().clear().apply()
        loadMathLandProgress()
    }

    fun selectMathLandAnswer(index: Int) {
        if (_isMathLandAnswerSubmitted.value) return
        _selectedMathLandAnswerIndex.value = index
    }

    fun startPlayMathLandLevel(levelId: Int) {
        _isMathLandLoading.value = true
        _currentMathLandLevel.value = levelId
        _currentMathLandQuestionIndex.value = 0
        _selectedMathLandAnswerIndex.value = null
        _isMathLandAnswerSubmitted.value = false
        _mathLandScore.value = 0
        _bossHp.value = 1.0f
        _playerHp.value = 1.0f

        _currentScreen.value = Screen.MathLandPlay(levelId)

        viewModelScope.launch {
            try {
                // Generate local questions for that level
                val questions = com.example.data.MathLandGenerator.generateMathLandQuestions(levelId)
                _mathLandQuestions.value = questions
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isMathLandLoading.value = false
            }
        }
    }

    fun submitMathLandAnswer() {
        val selected = _selectedMathLandAnswerIndex.value ?: return
        if (_isMathLandAnswerSubmitted.value) return

        _isMathLandAnswerSubmitted.value = true
        val currentQuestion = _mathLandQuestions.value.getOrNull(_currentMathLandQuestionIndex.value)
        val levelInfo = com.example.data.MathLandGenerator.getLevelById(_currentMathLandLevel.value)
        
        if (currentQuestion != null) {
            if (selected == currentQuestion.answerIndex) {
                _mathLandScore.value = _mathLandScore.value + 1
                if (levelInfo?.isBoss == true) {
                    // Deal damage to boss! Each correct answer decreases boss HP by 0.2 (since there are 5 questions)
                    val currentHp = _bossHp.value
                    _bossHp.value = java.lang.Math.max(0.0f, currentHp - 0.2f)
                }
            } else {
                if (levelInfo?.isBoss == true) {
                    // Deal damage to player! Each wrong answer decreases player HP by 0.2
                    val currentHp = _playerHp.value
                    _playerHp.value = java.lang.Math.max(0.0f, currentHp - 0.2f)
                }
            }
        }
    }

    fun nextMathLandQuestion() {
        val currentIdx = _currentMathLandQuestionIndex.value
        val questionsCount = _mathLandQuestions.value.size
        val levelId = _currentMathLandLevel.value

        if (currentIdx + 1 < questionsCount) {
            _currentMathLandQuestionIndex.value = currentIdx + 1
            _selectedMathLandAnswerIndex.value = null
            _isMathLandAnswerSubmitted.value = false
        } else {
            // Level completed!
            val score = _mathLandScore.value
            val total = questionsCount
            
            // Succeed criteria: score >= 3 (60% correct answers) OR player HP > 0 in boss battles
            val levelInfo = com.example.data.MathLandGenerator.getLevelById(levelId)
            val succeeded = if (levelInfo?.isBoss == true) {
                _playerHp.value > 0.0f
            } else {
                score >= 3
            }
            
            val starsEarned = when (score) {
                5 -> 3
                4 -> 2
                3 -> 1
                else -> 0
            }

            if (succeeded) {
                viewModelScope.launch {
                    val label = "Math Land - Level $levelId (${levelInfo?.name})"
                    // Give bonus stars and XP
                    val xpBonus = levelInfo?.rewardXp ?: 50
                    val starsBonus = levelInfo?.rewardStars ?: 10
                    
                    // Add via repository to profile state
                    repository.addAndSaveQuizResult(label, score, total, starsBonus)
                    
                    // Save level progression
                    saveMathLandProgress(levelId, score, starsEarned)
                    
                    _currentScreen.value = Screen.MathLandScore(levelId, score, total, starsEarned, true)
                }
            } else {
                _currentScreen.value = Screen.MathLandScore(levelId, score, total, 0, false)
            }
        }
    }

    // --- EduBot Chat Logic ---
    fun sendChatMessage(text: String) {
        if (text.isBlank()) return

        // 1. Add user message
        val userMsg = ChatMessage("user", text)
        _chatMessages.value = _chatMessages.value + userMsg

        _isChatLoading.value = true

        viewModelScope.launch {
            // Priority 1: Query real Gemini client API
            var edubotReply = ""
            try {
                val apiResponse = GeminiClient.askEduBot(text, geminiHistory)
                if (apiResponse.contains("Kunci API Gemini belum diatur") || apiResponse.startsWith("Gagal menghubungi EduBot")) {
                    edubotReply = OfflineEduBot.getResponse(text)
                } else {
                    edubotReply = apiResponse
                }
            } catch (e: Exception) {
                edubotReply = OfflineEduBot.getResponse(text)
            }

            // Update local memory
            geminiHistory.add(GeminiContent(parts = listOf(GeminiPart(text)), role = "user"))
            geminiHistory.add(GeminiContent(parts = listOf(GeminiPart(edubotReply)), role = "model"))

            // Keep only last 10 dialogs to avoid context window explosion
            if (geminiHistory.size > 20) {
                geminiHistory.removeAt(0)
                geminiHistory.removeAt(0)
            }

            _chatMessages.value = _chatMessages.value + ChatMessage("edubot", edubotReply)
            _isChatLoading.value = false
        }
    }

    fun clearChat() {
        _chatMessages.value = listOf(
            ChatMessage("edubot", "Sapu bersih! 🧹 Mari tanyakan pertanyaan baru ke EduBot!")
        )
        geminiHistory.clear()
    }

    // --- Quiz Logic ---
    fun setAgeGroup(ageGroup: String) {
        _selectedAgeGroup.value = ageGroup
    }

    fun startNewQuiz(topic: String) {
        _isQuizLoading.value = true
        _quizQuestions.value = emptyList()
        _currentQuestionIndex.value = 0
        _selectedAnswerIndex.value = null
        _isAnswerSubmitted.value = false
        _quizScore.value = 0

        _currentScreen.value = Screen.QuizPlay(topic)

        viewModelScope.launch {
            // Generate 100% offline quiz with keyword match and seen history checks!
            _quizQuestions.value = GeneralQuizGenerator.generateQuiz(getApplication(), topic, _selectedAgeGroup.value)
            _isQuizLoading.value = false
        }
    }

    fun selectQuizAnswer(index: Int) {
        if (_isAnswerSubmitted.value) return // can't change after submitting
        _selectedAnswerIndex.value = index
    }

    fun submitQuizAnswer() {
        val selected = _selectedAnswerIndex.value ?: return
        if (_isAnswerSubmitted.value) return

        _isAnswerSubmitted.value = true
        val currentQuestion = _quizQuestions.value.getOrNull(_currentQuestionIndex.value)
        if (currentQuestion != null && selected == currentQuestion.answerIndex) {
            _quizScore.value = _quizScore.value + 1
        }
    }

    fun nextQuizQuestion(topic: String) {
        val currentIdx = _currentQuestionIndex.value
        val questionsCount = _quizQuestions.value.size

        if (currentIdx + 1 < questionsCount) {
            _currentQuestionIndex.value = currentIdx + 1
            _selectedAnswerIndex.value = null
            _isAnswerSubmitted.value = false
        } else {
            // Quiz completed!
            val totalScore = _quizScore.value
            // Stars earned: score * 5 stars!
            val starsEarned = totalScore * 5

            val currentAgeGroup = _selectedAgeGroup.value
            val currentIndex = when {
                currentAgeGroup.contains("PAUD", ignoreCase = true) || currentAgeGroup.contains("3-6") -> 0
                currentAgeGroup.contains("Rendah", ignoreCase = true) || currentAgeGroup.contains("7-9") -> 1
                currentAgeGroup.contains("Tinggi", ignoreCase = true) || currentAgeGroup.contains("10-12") -> 2
                currentAgeGroup.contains("SMP", ignoreCase = true) || currentAgeGroup.contains("Remaja", ignoreCase = true) || currentAgeGroup.contains("13+") -> 3
                else -> 0
            }
            if (totalScore >= 3) {
                saveUnlockedAgeGroupIndex(currentIndex + 1)
            }

            viewModelScope.launch {
                repository.addAndSaveQuizResult(topic, totalScore, questionsCount, starsEarned)
                _currentScreen.value = Screen.QuizScore(topic, totalScore, questionsCount, starsEarned)
            }
        }
    }

    // --- Math Quiz Logic ---
    fun selectMathGrade(grade: Int) {
        _selectedMathGrade.value = grade
    }

    fun selectMathCategory(category: String) {
        _selectedMathCategory.value = category
    }

    fun startNewMathQuiz(grade: Int, category: String) {
        _selectedMathGrade.value = grade
        _isMathLoading.value = true
        _mathQuestions.value = emptyList()
        _currentMathQuestionIndex.value = 0
        _selectedMathAnswerIndex.value = null
        _isMathAnswerSubmitted.value = false
        _mathScore.value = 0

        _currentScreen.value = Screen.MathPlay(grade, category)

        viewModelScope.launch {
            try {
                _mathQuestions.value = MathQuestionGenerator.generateLocalMathQuestions(getApplication(), grade, category)
            } catch (e: Exception) {
                e.printStackTrace()
                _mathQuestions.value = MathQuestionGenerator.generateLocalMathQuestions(getApplication(), grade, category)
            } finally {
                _isMathLoading.value = false
            }
        }
    }

    fun selectMathAnswer(index: Int) {
        if (_isMathAnswerSubmitted.value) return
        _selectedMathAnswerIndex.value = index
    }

    fun submitMathAnswer() {
        val selected = _selectedMathAnswerIndex.value ?: return
        if (_isMathAnswerSubmitted.value) return

        _isMathAnswerSubmitted.value = true
        val currentQuestion = _mathQuestions.value.getOrNull(_currentMathQuestionIndex.value)
        if (currentQuestion != null && selected == currentQuestion.answerIndex) {
            _mathScore.value = _mathScore.value + 1
        }
    }

    fun nextMathQuestion(grade: Int, category: String) {
        val currentIdx = _currentMathQuestionIndex.value
        val questionsCount = _mathQuestions.value.size

        if (currentIdx + 1 < questionsCount) {
            _currentMathQuestionIndex.value = currentIdx + 1
            _selectedMathAnswerIndex.value = null
            _isMathAnswerSubmitted.value = false
        } else {
            // Math Quiz completed!
            val totalScore = _mathScore.value
            val starsEarned = totalScore * 5

            viewModelScope.launch {
                val topicLabel = "Matematika - Level $grade ($category)"
                repository.addAndSaveQuizResult(topicLabel, totalScore, questionsCount, starsEarned)
                _currentScreen.value = Screen.MathScore(grade, category, totalScore, questionsCount, starsEarned)
            }
        }
    }

    // --- English Quiz Logic ---
    fun selectEnglishGrade(grade: Int) {
        _selectedEnglishGrade.value = grade
    }

    fun selectEnglishCategory(category: String) {
        _selectedEnglishCategory.value = category
    }

    fun startNewEnglishQuiz(grade: Int, category: String) {
        _selectedEnglishGrade.value = grade
        _isEnglishLoading.value = true
        _englishQuestions.value = emptyList()
        _currentEnglishQuestionIndex.value = 0
        _selectedEnglishAnswerIndex.value = null
        _isEnglishAnswerSubmitted.value = false
        _englishScore.value = 0

        _currentScreen.value = Screen.EnglishPlay(grade, category)

        viewModelScope.launch {
            try {
                _englishQuestions.value = EnglishQuestionGenerator.generateLocalEnglishQuestions(getApplication(), grade, category)
            } catch (e: Exception) {
                e.printStackTrace()
                _englishQuestions.value = EnglishQuestionGenerator.generateLocalEnglishQuestions(getApplication(), grade, category)
            } finally {
                _isEnglishLoading.value = false
            }
        }
    }

    fun selectEnglishAnswer(index: Int) {
        if (_isEnglishAnswerSubmitted.value) return
        _selectedEnglishAnswerIndex.value = index
    }

    fun submitEnglishAnswer() {
        val selected = _selectedEnglishAnswerIndex.value ?: return
        if (_isEnglishAnswerSubmitted.value) return

        _isEnglishAnswerSubmitted.value = true
        val currentQuestion = _englishQuestions.value.getOrNull(_currentEnglishQuestionIndex.value)
        if (currentQuestion != null && selected == currentQuestion.answerIndex) {
            _englishScore.value = _englishScore.value + 1
        }
    }

    fun nextEnglishQuestion(grade: Int, category: String) {
        val currentIdx = _currentEnglishQuestionIndex.value
        val questionsCount = _englishQuestions.value.size

        if (currentIdx + 1 < questionsCount) {
            _currentEnglishQuestionIndex.value = currentIdx + 1
            _selectedEnglishAnswerIndex.value = null
            _isEnglishAnswerSubmitted.value = false
        } else {
            // English Quiz completed!
            val totalScore = _englishScore.value
            val starsEarned = totalScore * 5

            viewModelScope.launch {
                val topicLabel = "Bahasa Inggris - Level $grade ($category)"
                repository.addAndSaveQuizResult(topicLabel, totalScore, questionsCount, starsEarned)
                _currentScreen.value = Screen.EnglishScore(grade, category, totalScore, questionsCount, starsEarned)
            }
        }
    }

    fun selectIslamicGrade(grade: Int) {
        _selectedIslamicGrade.value = grade
    }

    fun selectIslamicCategory(category: String) {
        _selectedIslamicCategory.value = category
    }

    fun startNewIslamicQuiz(grade: Int, category: String) {
        _selectedIslamicGrade.value = grade
        _isIslamicLoading.value = true
        _islamicQuestions.value = emptyList()
        _currentIslamicQuestionIndex.value = 0
        _selectedIslamicAnswerIndex.value = null
        _isIslamicAnswerSubmitted.value = false
        _islamicScore.value = 0

        _currentScreen.value = Screen.IslamicPlay(grade, category)

        viewModelScope.launch {
            try {
                _islamicQuestions.value = IslamicQuestionGenerator.generateLocalIslamicQuestions(getApplication(), grade, category)
            } catch (e: Exception) {
                e.printStackTrace()
                _islamicQuestions.value = IslamicQuestionGenerator.generateLocalIslamicQuestions(getApplication(), grade, category)
            } finally {
                _isIslamicLoading.value = false
            }
        }
    }

    fun selectIslamicAnswer(index: Int) {
        if (_isIslamicAnswerSubmitted.value) return
        _selectedIslamicAnswerIndex.value = index
    }

    fun submitIslamicAnswer() {
        val selected = _selectedIslamicAnswerIndex.value ?: return
        if (_isIslamicAnswerSubmitted.value) return

        _isIslamicAnswerSubmitted.value = true
        val currentQuestion = _islamicQuestions.value.getOrNull(_currentIslamicQuestionIndex.value)
        if (currentQuestion != null && selected == currentQuestion.answerIndex) {
            _islamicScore.value = _islamicScore.value + 1
        }
    }

    fun nextIslamicQuestion(grade: Int, category: String) {
        val currentIdx = _currentIslamicQuestionIndex.value
        val questionsCount = _islamicQuestions.value.size

        if (currentIdx + 1 < questionsCount) {
            _currentIslamicQuestionIndex.value = currentIdx + 1
            _selectedIslamicAnswerIndex.value = null
            _isIslamicAnswerSubmitted.value = false
        } else {
            // Islamic Quiz completed!
            val totalScore = _islamicScore.value
            val starsEarned = totalScore * 5

            viewModelScope.launch {
                val topicLabel = "Agama Islam - Level $grade ($category)"
                repository.addAndSaveQuizResult(topicLabel, totalScore, questionsCount, starsEarned)
                _currentScreen.value = Screen.IslamicScore(grade, category, totalScore, questionsCount, starsEarned)
            }
        }
    }

    private fun getFallbackQuestions(topic: String): List<QuizQuestion> {
        return listOf(
            QuizQuestion(
                question = "Planet manakah yang dikenal sebagai Planet Merah?",
                options = listOf("Bumi 🔵", "Mars 🔴", "Yupiter 🪐", "Merkurius ☄️"),
                answerIndex = 1,
                explanation = "Mars memiliki banyak besi oksida (karat) di permukaannya, yang membuatnya tampak berwarna merah mencolok!"
            ),
            QuizQuestion(
                question = "Ke manakah matahari terbit setiap pagi?",
                options = listOf("Utara 🧭", "Selatan 🧭", "Timur ☀️", "Barat 🌅"),
                answerIndex = 2,
                explanation = "Bumi berotasi ke arah timur, sehingga dari sudut pandang kita matahari selalu tampak terbit di sebelah timur."
            ),
            QuizQuestion(
                question = "Berapakah hasil dari 7 dikali 8?",
                options = listOf("45", "54", "56", "64"),
                answerIndex = 2,
                explanation = "7 dikali 8 adalah 56. Semangat latihan perkalian terus ya!"
            ),
            QuizQuestion(
                question = "Hewan apa yang bernapas menggunakan insang?",
                options = listOf("Lumba-lumba 🐬", "Burung Elang 🦅", "Ikan Mas 🐟", "Kucing 🐱"),
                answerIndex = 2,
                explanation = "Ikan menyerap oksigen yang terlarut di dalam air langsung menggunakan organ insang di dekat kepalanya!"
            ),
            QuizQuestion(
                question = "Apa benda langit terbesar di Tata Surya kita?",
                options = listOf("Matahari ☀️", "Bulan 🌙", "Bumi 🌍", "Yupiter 🪐"),
                answerIndex = 0,
                explanation = "Matahari adalah bintang raksasa pusat Tata Surya kita yang sangat luar biasa besar dan panas!"
            )
        )
    }

    // --- Word Scramble Logic ---
    fun updateWordPuzzleInput(text: String) {
        _puzzleAnswerInput.value = text
        _puzzleFeedback.value = "NONE"
    }

    fun startNewPuzzle(category: String) {
        _isPuzzleLoading.value = true
        _puzzleAnswerInput.value = ""
        _puzzleFeedback.value = "NONE"
        _currentWordCategory.value = category
        _currentWordPuzzle.value = null
        _puzzleHint.value = null

        viewModelScope.launch {
            // Get offline-first scrambled puzzle and handle history tracking
            _currentWordPuzzle.value = WordPuzzleGenerator.getRandomPuzzle(getApplication(), category)
            _isPuzzleLoading.value = false
        }
    }

    fun requestPuzzleHint() {
        val puzzle = _currentWordPuzzle.value ?: return
        if (_isHintLoading.value || _puzzleHint.value != null) return

        _isHintLoading.value = true
        viewModelScope.launch {
            _puzzleHint.value = getLocalFallbackHint(puzzle.correctWord)
            _isHintLoading.value = false
        }
    }

    private fun getLocalFallbackHint(word: String): String {
        val first = word.firstOrNull() ?: '?'
        val last = word.lastOrNull() ?: '?'
        return "Bantuan EP Knowledge Land: Simak petunjuk lainnya! Kata rahasia terdiri dari ${word.length} huruf, dimulai dengan '$first' dan berakhir dengan '$last'!"
    }

    fun submitWordAnswer() {
        val puzzle = _currentWordPuzzle.value ?: return
        val input = _puzzleAnswerInput.value.trim().uppercase()

        if (input == puzzle.correctWord.uppercase()) {
            _puzzleFeedback.value = "CORRECT"
            _scrambleStreak.value = _scrambleStreak.value + 1
            _scrambleScore.value = _scrambleScore.value + 10
            viewModelScope.launch {
                // Solve puzzle -> earns 3 stars
                repository.addPuzzleSolvedResult(starsEarned = 3)
            }
        } else {
            _puzzleFeedback.value = "INCORRECT"
        }
    }

    fun revealPuzzleAnswer() {
        val puzzle = _currentWordPuzzle.value ?: return
        _puzzleAnswerInput.value = puzzle.correctWord
        _puzzleFeedback.value = "HELP"
        _scrambleStreak.value = 0 // broke the streak on reveal!
    }

    // --- Hangman Game Logic ---
    fun startNewHangman(category: String) {
        _isHangmanLoading.value = true
        _guessedLetters.value = emptySet()
        _remainingLives.value = 6
        _hangmanGameStatus.value = "PLAYING"
        _currentHangmanCategory.value = category
        _currentHangmanWordState.value = null

        viewModelScope.launch {
            // Instantly load offline-first unique unseen Hangman word
            _currentHangmanWordState.value = getFallbackHangmanWord(category)
            _isHangmanLoading.value = false
        }
    }

    fun guessLetter(char: Char) {
        val wordState = _currentHangmanWordState.value ?: return
        if (_hangmanGameStatus.value != "PLAYING") return

        val uppercaseChar = char.uppercaseChar()
        if (_guessedLetters.value.contains(uppercaseChar)) return

        // Add to guessed set
        val updatedGuessed = _guessedLetters.value + uppercaseChar
        _guessedLetters.value = updatedGuessed

        val secretWord = wordState.word.uppercase()
        if (!secretWord.contains(uppercaseChar)) {
            // Incorrect guess!
            val newLives = _remainingLives.value - 1
            _remainingLives.value = newLives
            if (newLives <= 0) {
                _hangmanGameStatus.value = "LOST"
                _hangmanStreak.value = 0
            }
        } else {
            // Correct guess! Check if all unique letters in secretWord are guessed
            val secretLetters = secretWord.filter { it in 'A'..'Z' }.toSet()
            if (secretLetters.all { updatedGuessed.contains(it) }) {
                _hangmanGameStatus.value = "WON"
                _hangmanStreak.value = _hangmanStreak.value + 1
                viewModelScope.launch {
                    // Win hangman -> earns 4 stars!
                    repository.addPuzzleSolvedResult(starsEarned = 4)
                }
            }
        }
    }

    fun revealHangmanAnswer() {
        val wordState = _currentHangmanWordState.value ?: return
        if (_hangmanGameStatus.value != "PLAYING") return

        // Reveal the remaining letters but mark the game as lost due to giving up
        val secretWord = wordState.word.uppercase()
        val allLetters = secretWord.filter { it in 'A'..'Z' }.toSet()
        _guessedLetters.value = _guessedLetters.value + allLetters
        _hangmanGameStatus.value = "LOST"
        _hangmanStreak.value = 0
    }

    private fun getFallbackHangmanWord(category: String): HangmanWord {
        val cleanedCategory = category.replace(Regex("[^a-zA-Z]"), "").lowercase().trim()
        val wordsList = when {
            cleanedCategory.contains("sains") -> listOf(
                HangmanWord("FOTOSINTESIS", "Proses tumbuhan air/darat memasak makanan mandiri menggunakan sinar surya.", "Ini menghasilkan oksigen melimpah di Bumi!", "Sains 🧪"),
                HangmanWord("GRAVITASI", "Gaya tarik raksasa tidak terlihat yang menarik benda jatuh ke bumi dan menjaga planet mengorbit matahari.", "Tanpa gaya ini, kita semua akan melayang tinggi di angkasa!", "Sains 🧪"),
                HangmanWord("MAMALIA", "Kelompok hewan menyusui anaknya yang sebagian besar melahirkan dan berdarah hangat.", "Paus biru adalah mamalia sekaligus makhluk hidup terbesar di planet kita!", "Sains 🧪"),
                HangmanWord("ATMOSFER", "Lapisan tebal gas pelindung bumi dari meteorit berbahaya dan pancaran ekstrem sinar matahari.", "Terdiri dari 78% Nitrogen dan 21% Oksigen!", "Sains 🧪"),
                HangmanWord("OKSIGEN", "Gas murni berharga yang dihirup manusia dan hewan untuk respirasi.", "Dihasilkan secara melimpah oleh dedaunan hijau!", "Sains 🧪"),
                HangmanWord("TELESKOP", "Alat optik canggih pembesar gambar untuk mengamati bintang dan nebula galaksi.", "Galileo memanfaatkannya membuktikan bumi mengelilingi matahari!", "Sains 🧪")
            )
            cleanedCategory.contains("sejarah") -> listOf(
                HangmanWord("CANDI", "Bangunan batu kuno bersejarah tempat ibadah agama atau peninggalan raja leluhur nusantara.", "Candi Borobudur adalah candi Buddha terbesar di seantero jagat raya!", "Sejarah 🏛️"),
                HangmanWord("PIRAMIDA", "Makam raksasa berbentuk segitiga batu megah untuk mengubur dinasti Firaun Mesir Kuno.", "Tersusun atas jutaan keping batu kapur berat berton-ton!", "Sejarah 🏛️"),
                HangmanWord("PRASASTI", "Piagam kuno dari batu tulisan tangan leluhur yang mencatat peristiwa peradaban masa lalu.", "Ini memuat bukti tertulis awal berdirinya sebuah kerajaan!", "Sejarah 🏛️"),
                HangmanWord("KARTINI", "Tokoh pelopor emansipasi wanita Indonesia pejuang kesetaraan pendidikan.", "Terkenal dengan buku 'Habis Gelap Terbitlah Terang'!", "Sejarah 🏛️"),
                HangmanWord("PANCASILA", "Lima pilar ideologi dasar kedaulatan negara kesatuan Republik Indonesia.", "Lahir secara resmi dicetuskan pada tanggal 1 juni 1945!", "Sejarah 🏛️")
            )
            cleanedCategory.contains("geografi") -> listOf(
                HangmanWord("PULAU", "Daratan luas atau sempit yang dikelilingi seluruhnya oleh lautan luas.", "Indonesia adalah negara kepulauan terbesar di dunia dengan 17.000+ pulau!", "Geografi 🗺️"),
                HangmanWord("SAMUDERA", "Lautan air asin yang luasnya tiada tara, mendominasi 70% permukaan bumi.", "Samudera Pasifik adalah samudera terdalam dan terluas di dunia!", "Geografi 🗺️"),
                HangmanWord("VOLKANO", "Gunung api perkasa yang meluncurkan lava pijar dan debu vulkanik dari dalam bumi.", "Gunung Tambora di Indonesia meletus sangat dasyat pada tahun 1815!", "Geografi 🗺️"),
                HangmanWord("BENUA", "Daratan sangat luas di permukaan bumi yang menampung puluhan negara berdaulat.", "Benua Asia adalah benua terluas dengan jumlah penduduk terbanyak!", "Geografi 🗺️"),
                HangmanWord("KOMPAS", "Alat penunjuk arah mata angin memanfaatkan medan magnetik kutub utara dan selatan.", "Sangat membantu pelaut menjelajahi samudra luas!", "Geografi 🗺️")
            )
            cleanedCategory.contains("inggris") -> listOf(
                HangmanWord("RAINBOW", "An elegant natural colors visible after rain in English.", "A rainbow contains 7 major optical colors!", "Bahasa Inggris 🇬🇧"),
                HangmanWord("TEACHER", "The profession of a person who educates pupils in school.", "Teachers are heroes without medals!", "Bahasa Inggris 🇬🇧"),
                HangmanWord("GALAXY", "Huge system of stars, gas, and dark matter bound by gravity.", "Our Earth is in the Milky Way galaxy!", "Bahasa Inggris 🇬🇧"),
                HangmanWord("BUTTERFLY", "Incredibly beautiful flying insect with colorful wings in English.", "It undergoes complete metamorphosis from a caterpillar!", "Bahasa Inggris 🇬🇧"),
                HangmanWord("AIRPLANE", "A heavy flying vehicle with wings and engines in English.", "The Wright brothers invented the first successful one!", "Bahasa Inggris 🇬🇧")
            )
            else -> listOf(
                HangmanWord("VITAMIN", "Zat gizi organik alami dalam makanan yang penting menjaga pertahanan tubuh agar tetap sehat.", "Vitamin C melimpah di jeruk melindungi gigi dan gusi!", "Kesehatan 🍎"),
                HangmanWord("NUTRISI", "Nutrien penting berupa kalsium, protein, lemak sehat yang dibutuhkan tubuh bertumbuh tangguh.", "Susu segar adalah asupan tinggi kalsium untuk memperkuat tulang!", "Kesehatan 🍎"),
                HangmanWord("HIGIENIS", "Keadaan bersih dan bebas dari mikroba kotoran merugikan demi menangkal penularan penyakit.", "Membasuh tangan pakai sabun setelah makan menjaga tubuhmu higienis!", "Kesehatan 🍎")
            )
        }

        // Shuffled selection ensuring no-repetition fallback
        val categoryKey = "hangman_${cleanedCategory}"
        val unseenCount = wordsList.count { !QuestionHistoryTracker.isQuestionSeen(getApplication(), "hangman_${it.word}", categoryKey) }
        if (unseenCount < 1) {
            QuestionHistoryTracker.clearCategoryHistory(getApplication(), categoryKey)
        }

        val shuffledWords = wordsList.shuffled()
        var selectedWordState = shuffledWords.first()
        var foundUnique = false
        var attempts = 0

        while (attempts < 50) {
            val w = shuffledWords[attempts % shuffledWords.size]
            val isSeen = QuestionHistoryTracker.isQuestionSeen(getApplication(), "hangman_${w.word}", categoryKey)
            if (!isSeen || attempts > 30) {
                selectedWordState = w
                QuestionHistoryTracker.markQuestionAsSeen(getApplication(), "hangman_${w.word}", categoryKey)
                foundUnique = true
                break
            }
            attempts++
        }

        return selectedWordState
    }

    // --- Daily Challenge Logic ---
    fun startDailyChallenge(dateString: String) {
        if (_dailyLoadedDate.value == dateString && _dailyChallengeState.value != null) {
            _currentScreen.value = Screen.DailyChallenge
            return
        }

        _isDailyLoading.value = true
        _dailySelectedAnswer.value = null
        _dailyIsAnswerSubmitted.value = false
        _dailyChallengeState.value = null
        _dailyLoadedDate.value = dateString
        _currentScreen.value = Screen.DailyChallenge

        viewModelScope.launch {
            // Bypass server and load fully offline daily challenge questions!
            _dailyChallengeState.value = getFallbackDailyChallenge(dateString)
            _isDailyLoading.value = false
        }
    }

    fun selectDailyAnswer(index: Int) {
        if (_dailyIsAnswerSubmitted.value) return
        _dailySelectedAnswer.value = index
    }

    fun submitDailyAnswer(dateString: String) {
        val selected = _dailySelectedAnswer.value ?: return
        if (_dailyIsAnswerSubmitted.value) return

        _dailyIsAnswerSubmitted.value = true
        val isCorrect = selected == _dailyChallengeState.value?.answerIndex

        if (isCorrect) {
            viewModelScope.launch {
                // Earns 5 stars and 30 XP!
                repository.completeDailyChallenge(dateString, starsEarned = 5, xpEarned = 30)
            }
        }
    }

    private fun getFallbackDailyChallenge(dateString: String): DailyChallengeQuestion {
        val dayIndex = dateString.hashCode().let { if (it < 0) -it else it } % 4
        return when (dayIndex) {
            0 -> DailyChallengeQuestion(
                topic = "Misteri Deep Sea 🌊",
                question = "Di kedalaman laut yang gelap tanpa cahaya matahari, bagaimanakah cara hewan laut seperti anglerfish berburu atau menarik perhatian mangsanya?",
                options = listOf(
                    "Menggunakan kemampuan bioluminesensi (menghasilkan cahaya sendiri)",
                    "Mengepakkan siripnya dengan sangat kencang sehingga berbunyi nyaring",
                    "Mengeluarkan aroma buah-buahan laut yang manis",
                    "Memantulkan cahaya bulan dari permukaan air laut"
                ),
                answerIndex = 0,
                explanation = "Hebat! Anglerfish menggunakan organ khusus di kepalanya yang bersinar berkat bakteri simbiotik. Di laut dalam yang pekat, cahaya ini sangat menarik bagi ikan kecil lainnya.",
                funFact = "Palung Mariana adalah titik terdalam samudera kita, mencapai hampir 11.000 meter ke bawah tanah! Di sana tekanan air sangat ekstrem namun kehidupan unik tetap eksis.",
                clue = "Coba ingat kata 'luminescence' yang menceritakan kemampuan memancarkan cahaya hidup."
            )
            1 -> DailyChallengeQuestion(
                topic = "Sistem Tata Surya kita 🪐",
                question = "Mengapa planet Mars sering kali dijuluki sebagai 'Planet Merah' oleh para astronom?",
                options = listOf(
                    "Karena Mars sangat dekat dengan matahari sehingga terbakar",
                    "Permukaannya kaya akan besi oksida (karat) yang memancarkan warna kemerahan",
                    "Atmosfernya terdiri dari gas neon berwarna merah cerah",
                    "Terdapat lautan lahar raksasa yang menutupi seluruh permukaan"
                ),
                answerIndex = 1,
                explanation = "Luar biasa tepat! Mars meiliki banyak mineral besi oksida (karat) di debu permukaannya, yang membuatnya tampak berwarna kemerahan sewaktu diamati dari bumi.",
                funFact = "Mars memiliki gunung berapi terbesar di seluruh Tata Surya bernama Olympus Mons. Gunung ini tiga kali lebih tinggi dari Gunung Everest!",
                clue = "Pikirkan zat besi yang berkarat akibat lama bersentuhan dengan udara."
            )
            2 -> DailyChallengeQuestion(
                topic = "Sejarah Lampu Pijar 💡",
                question = "Siapakah penemu gigih yang berhasil menyempurnakan bola lampu pijar praktis sehingga malam hari menjadi luar biasa terang?",
                options = listOf(
                    "Albert Einstein",
                    "Isaac Newton",
                    "Thomas Alva Edison",
                    "Alexander Graham Bell"
                ),
                answerIndex = 2,
                explanation = "Kamu pintar! Thomas Alva Edison menyempurnakan bola lampu pijar praktis setelah ribuan kali uji coba filamen karbon.",
                funFact = "Edison pernah berkata: 'Saya tidak gagal, saya hanya menemukan 10.000 cara yang tidak berhasil.' Menakjubkan!",
                clue = "Dia memiliki nama tengah Alva."
            )
            else -> DailyChallengeQuestion(
                topic = "Kehidupan Tumbuhan 🌱",
                question = "Metode memasak makanan mandiri pada daun hijau menggunakan bantuan air, karbondioksida, dan matahari dinamakan...",
                options = listOf(
                    "Transpirasi",
                    "Fotosintesis",
                    "Respirasi",
                    "Fermentasi"
                ),
                answerIndex = 1,
                explanation = "Sempurna! Fotosintesis menggunakan energi matahari untuk menyatukan air dan karbondioksida menjadi glukosa manis padat mineral.",
                funFact = "Dalam proses fotosintesis, tumbuhan tak hanya membuat gula tetapi juga melepaskan oksigen segar untuk pernapasan kita!",
                clue = "Kombinasi kata cahaya (photo) dan penggabungan (synthesis)."
            )
        }
    }
}
