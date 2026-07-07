package com.example.data

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// --- Moshi Data Classes for Gemini API ---

data class GeminiPart(
    @Json(name = "text") val text: String
)

data class GeminiContent(
    @Json(name = "parts") val parts: List<GeminiPart>,
    @Json(name = "role") val role: String? = null
)

data class ResponseFormatSchema(
    @Json(name = "type") val type: String,
    @Json(name = "properties") val properties: Map<String, Any>? = null,
    @Json(name = "required") val required: List<String>? = null
)

data class ResponseFormat(
    @Json(name = "mimeType") val mimeType: String,
    @Json(name = "responseSchema") val responseSchema: ResponseFormatSchema? = null
)

data class GenerationConfig(
    @Json(name = "temperature") val temperature: Float? = null,
    @Json(name = "responseMimeType") val responseMimeType: String? = null
)

data class GeminiRequest(
    @Json(name = "contents") val contents: List<GeminiContent>,
    @Json(name = "systemInstruction") val systemInstruction: GeminiContent? = null,
    @Json(name = "generationConfig") val generationConfig: GenerationConfig? = null
)

data class GeminiCandidate(
    @Json(name = "content") val content: GeminiContent
)

data class GeminiResponse(
    @Json(name = "candidates") val candidates: List<GeminiCandidate>?
)

// --- Local Educational App Data Models ---

data class QuizQuestion(
    @Json(name = "question") val question: String,
    @Json(name = "options") val options: List<String>,
    @Json(name = "answerIndex") val answerIndex: Int,
    @Json(name = "explanation") val explanation: String
)

data class WordScramblePuzzle(
    @Json(name = "clue") val clue: String,
    @Json(name = "scrambledWord") val scrambledWord: String,
    @Json(name = "correctWord") val correctWord: String
)

data class HangmanWord(
    @Json(name = "word") val word: String,
    @Json(name = "clue") val clue: String,
    @Json(name = "funFact") val funFact: String,
    @Json(name = "category") val category: String
)

data class DailyChallengeQuestion(
    @Json(name = "topic") val topic: String,
    @Json(name = "question") val question: String,
    @Json(name = "options") val options: List<String>,
    @Json(name = "answerIndex") val answerIndex: Int,
    @Json(name = "explanation") val explanation: String,
    @Json(name = "funFact") val funFact: String,
    @Json(name = "clue") val clue: String
)

// --- Retrofit Service ---

interface GeminiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

object GeminiClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val service: GeminiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        retrofit.create(GeminiService::class.java)
    }

    // --- Helper to execute API calls and clean JSON markdown formatting ---
    private fun cleanJsonString(rawText: String): String {
        return rawText.trim()
            .removePrefix("```json")
            .removePrefix("```")
            .removeSuffix("```")
            .trim()
    }

    // 1. EduBot Chat - Explains topics to kids with emojis and trivia
    suspend fun askEduBot(prompt: String, conversationHistory: List<GeminiContent> = emptyList()): String = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext "Kunci API Gemini belum diatur! Mohon atur kunci API Anda di panel Secrets di AI Studio."
        }

        val systemPrompt = """
            Kamu adalah 'EduBot', robot pendamping belajar yang sangat ceria, bersahabat, penuh energi, dan humoris untuk anak-anak. 
            Jelaskan topik atau pertanyaan anak dengan analogi dunia nyata yang menyenangkan, visual, dan gunakan bahasa Indonesia santai yang sangat mudah dipahami.
            Gunakan banyak emoji yang relevan agar teks terlihat menarik.
            Di akhir penjelasanmu, selalu sertakan bagian tepat:
            
            💡 **3 Fakta Kilat EP Knowledge Land:**
            1. [Fakta seru 1]
            2. [Fakta seru 2]
            3. [Fakta seru 3]
        """.trimIndent()

        // Combine system instruction + user query
        val contents = conversationHistory.toMutableList().apply {
            add(GeminiContent(parts = listOf(GeminiPart(prompt)), role = "user"))
        }

        val request = GeminiRequest(
            contents = contents,
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(systemPrompt))),
            generationConfig = GenerationConfig(temperature = 0.7f)
        )

        try {
            val response = service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text 
                ?: "Aduh, sepertinya EduBot sedang melamun di luar angkasa. Coba tanyakan sekali lagi ya! 🚀"
        } catch (e: Exception) {
            "Gagal menghubungi EduBot: ${e.message}. Periksa koneksi internetmu ya!"
        }
    }

    // 2. Quiz Arena - Generates custom 5-question MCQ quizzes in JSON format
    suspend fun generateQuiz(topic: String, ageGroup: String = "SD Kelas Rendah (Umur 7-9)"): List<QuizQuestion>? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") return@withContext null

        val systemPrompt = """
            Kamu adalah Quiz Master EP Knowledge Land. Kamu bertugas menghasilkan kuis interaktif berupa 5 soal pilihan ganda tentang topik yang diminta dengan tingkat kesulitan ramah anak dan disesuaikan khusus untuk kelompok sasaran: $ageGroup.
            Sangat penting membuat pertanyaan, kosakata, tingkat kesulitan, opsi jawaban, dan penjelasan yang sesuai dengan pemahaman kognitif usia atau tingkat $ageGroup.
            - Jika untuk TK/PAUD: bahasanya sangat sederhana, penuh canda ceria, soalnya mudah mengenai hal mendasar.
            - Jika untuk SD Rendah: bahasa komunikatif, seru, menyertakan analogi sederhana sehari-hari.
            - Jika untuk SD Tinggi: menyajikan konsep sains/matematika dasar yang menarik, menantang keingintahuan mereka.
            - Jika untuk SMP/Remaja: berikan penjelasan ilmiah yang lebih kokoh, tantangan logika yang merangsang cara berpikir kritis terstruktur.

            Kamu HARUS menjawab HANYA dengan JSON array mentah. JANGAN gunakan block format markdown (seperti ```json) atau penjelasan tambahan apa pun. Teks harus langsung bisa di-parse sebagai JSON.
            
            Struktur JSON Array:
            [
              {
                "question": "Contoh pertanyaan?",
                "options": ["Pilihan 0", "Pilihan 1", "Pilihan 2", "Pilihan 3"],
                "answerIndex": 1,
                "explanation": "Penjelasan mengapa Pilihan 1 benar dengan gaya bahasa menyenangkan disesuaikan tingkat kognitif usianya."
              }
            ]
            Pastikan index jawaban sesuai dengan opsi (0, 1, 2, atau 3), pilihan kata kreatif, opsi bervariasi, dan penjelasannya edukatif.
        """.trimIndent()

        val prompt = "Buatkan kuis seru tentang topik: $topic (Untuk tingkat: $ageGroup)"

        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(systemPrompt))),
            generationConfig = GenerationConfig(temperature = 0.8f, responseMimeType = "application/json")
        )

        try {
            val response = service.generateContent(apiKey, request)
            val rawText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: return@withContext null
            val cleanedJson = cleanJsonString(rawText)

            val listType = com.squareup.moshi.Types.newParameterizedType(List::class.java, QuizQuestion::class.java)
            val adapter = moshi.adapter<List<QuizQuestion>>(listType)
            adapter.fromJson(cleanedJson)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 2b. Math Quiz -- Generates custom mathematical 5-question quizzes tailored to Grade 1 to 12.
    suspend fun generateMathQuiz(grade: Int, category: String): List<QuizQuestion>? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") return@withContext null

        val systemPrompt = """
            Kamu adalah Guru Matematika Cerdas EP Knowledge Land. Tugasmu membuat kuis Matematika khusus berupa 5 soal pilihan ganda interaktif.
            Pembelajaran disesuaikan dengan kurikulum sekolah Indonesia tingkat: Kelas $grade.
            Kategori soal yang harus dihasilkan: $category.
            
            Kategori khusus bisa berupa:
            - 'Perkalian' (Multiplication)
            - 'Pembagian' (Division)
            - 'Campuran' (Penambahan & Pengurangan dikombinasikan dalam satu persamaan matematika, misalnya: 14 + 12 - 5)
            - 'Cerita' (Soal cerita kehidupan sehari-hari bernilai matematika yang disesuaikan tingkat nalar usia Kelas $grade)

            Harap sesuaikan tingkat kesulitan materi matematika dengan kognitif Kelas $grade:
            - Kelas 1-6 SD: sangat visual dan menggunakan angka sederhana, analogi benda sehari-hari (mainan, buah, permen).
            - Kelas 7-9 SMP: menyertakan variabel aljabar sederhana, bilangan bulat negatif, perbandingan, atau himpunan dasar.
            - Kelas 10-12 SMA: menyertakan polinomial, persamaan kuadrat, trigonometri, barisan deret, peluang, eksponen, logaritma, atau hitungan tingkat tinggi yang sesuai.

            Kamu HARUS menjawab HANYA dengan JSON array mentah. JANGAN gunakan block format markdown (seperti ```json) atau penjelasan tambahan apa pun. Teks harus langsung bisa di-parse sebagai JSON.
            
            Struktur JSON Array:
            [
              {
                "question": "Soal matematika atau cerita?",
                "options": ["Opsi 0", "Opsi 1", "Opsi 2", "Opsi 3"],
                "answerIndex": 2,
                "explanation": "Penjelasan detail, ramah anak, dan informatif yang menguraikan cara hitung dari awal hingga menemukan jawaban."
              }
            ]
            Pastikan index jawaban sesuai dengan opsi (0, 1, 2, atau 3), pilihan kata kreatif, opsi bervariasi, dan penjelasannya edukatif.
        """.trimIndent()

        val prompt = "Buatkan kuis matematika seru untuk tingkat Kelas $grade dengan jenis: $category"

        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(systemPrompt))),
            generationConfig = GenerationConfig(temperature = 0.7f, responseMimeType = "application/json")
        )

        try {
            val response = service.generateContent(apiKey, request)
            val rawText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: return@withContext null
            val cleanedJson = cleanJsonString(rawText)

            val listType = com.squareup.moshi.Types.newParameterizedType(List::class.java, QuizQuestion::class.java)
            val adapter = moshi.adapter<List<QuizQuestion>>(listType)
            adapter.fromJson(cleanedJson)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 2c. English Quiz -- Generates custom English 5-question quizzes tailored to Indonesian school curriculum Grade 1 to 12.
    suspend fun generateEnglishQuiz(grade: Int, category: String): List<QuizQuestion>? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") return@withContext null

        val systemPrompt = """
            Kamu adalah Guru Bahasa Inggris Cerdas EP Knowledge Land. Tugasmu membuat kuis Bahasa Inggris interaktif berupa 5 soal pilihan ganda.
            Pembelajaran disesuaikan dengan kurikulum nasional Indonesia untuk: Kelas $grade.
            Kategori soal yang harus dihasilkan: $category.
            
            Kategori bisa berupa:
            - 'Vocabulary' (Kosa Kata & arti kata dasar hingga akademis)
            - 'Grammar' (Tata Bahasa, tenses, preposisi, pronouns, conditionals, modal perfect)
            - 'Conversation' (Percakapan Sehari-hari, tata cara merespon percakapan secara sopan / profesional / akrab)
            - 'Reading' (Pemahaman Bacaan singkat atau cerita fiksi / ilmiah sederhana dengan pertanyaan kritis)

            Harap sesuaikan tingkat kesulitan materi bahasa dengan kognitif Kelas $grade:
            - Kelas 1-2 SD: kosakata dasar (buah, hewan, warna, mainan), subjek dasar (I, you, they, we, he, she), sapaan super mudah.
            - Kelas 3-4 SD: perlengkapan sekolah, bagian tubuh, preposisi sederhana (in, on, under), bentuk jamak dasar (-s).
            - Kelas 5-6 SD: profesi, transportasi, tenses masa kini (Simple Present, Present Continuous), memberikan pendapat sederhana.
            - Kelas 7-9 SMP: Simple Past vs Present Perfect, kalimat pasif dasar, sinonim/antonim fungsional, beraliansi dalam pendapat.
            - Kelas 10-12 SMA: Conditionals type 1/2/3, modal perfect, teks ilmiah/akademis tinggi dengan pemahaman bacaan tingkat kritis.

            Kamu HARUS menjawab HANYA dengan JSON array mentah. JANGAN gunakan block format markdown (seperti ```json) atau penjelasan tambahan apa pun. Teks harus langsung bisa di-parse sebagai JSON.

            Struktur JSON Array:
            [
              {
                "question": "Question text in English? (Include Indonesian context/guideline if necessary, especially for elementary grades)",
                "options": ["Option A", "Option B", "Option C", "Option D"],
                "answerIndex": 0,
                "explanation": "Penjelasan bersahabat dan edukatif dalam bahasa Indonesia tentang mengapa jawaban tersebut benar dan arti kalimatnya."
              }
            ]
            Pastikan index jawaban sesuai dengan opsi (0, 1, 2, atau 3), pilihan kata kreatif, opsi bervariasi, dan penjelasannya edukatif.
        """.trimIndent()

        val prompt = "Buatkan kuis bahasa Inggris edukatif untuk tingkat Kelas $grade dengan kategori: $category"

        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(systemPrompt))),
            generationConfig = GenerationConfig(temperature = 0.7f, responseMimeType = "application/json")
        )

        try {
            val response = service.generateContent(apiKey, request)
            val rawText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: return@withContext null
            val cleanedJson = cleanJsonString(rawText)

            val listType = com.squareup.moshi.Types.newParameterizedType(List::class.java, QuizQuestion::class.java)
            val adapter = moshi.adapter<List<QuizQuestion>>(listType)
            adapter.fromJson(cleanedJson)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 2d. Islamic and Al-Qur'an Quiz -- Generates custom Islamic quizzes tailored to Indonesian school curriculum Grade 1 to 12.
    suspend fun generateIslamicQuiz(grade: Int, category: String): List<QuizQuestion>? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") return@withContext null

        val systemPrompt = """
            Kamu adalah Guru Pendidikan Agama Islam & Al-Qur'an Cerdas EP Knowledge Land. Tugasmu membuat kuis Agama Islam & Al-Qur'an interaktif berupa 5 soal pilihan ganda.
            Pembelajaran disesuaikan dengan kurikulum nasional Indonesia untuk: Kelas $grade.
            Kategori soal yang harus dihasilkan: $category.
            
            Kategori bisa berupa:
            - 'Aqidah & Akhlak' (Tauhid, Rukun Iman, Sifat Wajib Allah, Sifat terpuji rasul, berbakti kepada orang tua, akhlak remaja/sosmed)
            - 'Fiqih & Ibadah' (Thaharah, Wudhu, Shalat wajib/sunnah, Puasa, Zakat, Haji/Qurban, muamalah syariah)
            - 'Al-Qur'an & Tajwid' (Harakat dasar, huruf hijaiyah, hukum nun sukun/tanwin (Izhar, Ikhfa, Idgham, Iqlab), Mim Sukun, Qalqalah, Mad asli/far'i, Waqaf, Surah-surah juz amma, sejarah Rasm Usmani)
            - 'Sejarah Islam' (Kelahiran Nabi Muhammad, silsilah nabi, masa dakwah Mekkah/Madinah, Khulafaur Rasyidin, Dinasti Abbasiyah/Umayyah, Wali Songo, sejarah kerajaan Islam Nusantara)

            Harap sesuaikan tingkat kesulitan materi dengan tingkat kognitif anak Kelas $grade:
            - Kelas 1-2 SD: dasar rukun iman/islam yang sangat sederhana, harakat fathah/kasrah/dhommah, nama ibu/ayah Nabi Muhammad, adab berteman dan patuh orang tua.
            - Kelas 3-4 SD: gerakan & urutan wudhu, sifat wajib Allah/rasul, tayamum, surah pendek (Al-Ikhlas/Al-Falaq), hukum iqlab dan izhar dasar.
            - Kelas 5-6 SD: shalat jamak/qashar & sunnah, sejarah Hijrah ke Madinah/perang Badar, Asmaul Husna, hukum mad thabi'i dan qalqalah.
            - Kelas 7-9 SMP: zakat & qurban, sejarah Khulafaur Rasyidin/Bani Umayyah, tanda wakaf mushaf, makharijul huruf, tayamum detail.
            - Kelas 10-12 SMA: hukum waris (mawaris), pernikahan/munakahat, transaksi syariah (mudharabah/syirkah), Baitul Hikmah (Abbasiyah), sejarah Islam masuk ke Indonesia (Wali Songo, Pasai), akhlak sosiokultural modern.

            Kamu HARUS menjawab HANYA dengan JSON array mentah. JANGAN gunakan block format markdown (seperti ```json) atau penjelasan tambahan apa pun. Teks harus langsung bisa di-parse sebagai JSON.

            Struktur JSON Array:
            [
              {
                "question": "Pertanyaan kuis dalam bahasa Indonesia? (misal untuk Kelas 1 pastikan bahasanya sederhana dan ramah anak)",
                "options": ["Opsi A", "Opsi B", "Opsi C", "Opsi D"],
                "answerIndex": 0,
                "explanation": "Penjelasan bersahabat dan edukatif yang mendalam dalam bahasa Indonesia tentang mengapa jawaban tersebut benar serta hikmah pembelajarannya."
              }
            ]
            Pastikan index jawaban sesuai dengan opsi (0, 1, 2, atau 3), pilihan kata kreatif, opsi bervariasi, dan penjelasannya edukatif.
        """.trimIndent()

        val prompt = "Buatkan kuis Pendidikan Agama Islam & Al-Qur'an edukatif untuk tingkat Kelas $grade dengan kategori: $category"

        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(systemPrompt))),
            generationConfig = GenerationConfig(temperature = 0.7f, responseMimeType = "application/json")
        )

        try {
            val response = service.generateContent(apiKey, request)
            val rawText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: return@withContext null
            val cleanedJson = cleanJsonString(rawText)

            val listType = com.squareup.moshi.Types.newParameterizedType(List::class.java, QuizQuestion::class.java)
            val adapter = moshi.adapter<List<QuizQuestion>>(listType)
            adapter.fromJson(cleanedJson)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 3. Puzzle Maker - Generates spelling word scramble puzzle clues
    suspend fun generateWordPuzzle(category: String): WordScramblePuzzle? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") return@withContext null

        val systemPrompt = """
            Kamu adalah Puzzle Maker EP Knowledge Land. Buatlah petunjuk game tebak-kata acak huruf untuk anak-anak dalam Bahasa Indonesia.
            Pilih kata rahasia yang mendidik bersesuaian dengan kategori yang diminta (misal: "Hewan", "Tumbuhan", "Benda Langit", "Geografi", "Tubuh Manusia", dll).
            Acak huruf-huruf kata tersebut menjadi scrambledWord dalam huruf KAPITAL.
            Berikan sebuah petunjuk (clue) kreatif dan ramah anak.
            Kamu HARUS mengembalikan hanya satu objek JSON murni, tanpa block markdown (seperti ```json).
            
            Struktur JSON:
            {
              "clue": "Aku adalah hewan mamalia darat terbesar, berbelalai panjang, dan telingaku lebar.",
              "scrambledWord": "AHJAG",
              "correctWord": "GAJAH"
            }
            Pastikan kata asli (correctWord) adalah kata yang valid dalam bahasa Indonesia (atau serapan yang sangat umum untuk anak), huruf kapital, dan jumlah serta susunan hurufnya pas dengan huruf acak.
        """.trimIndent()

        val prompt = "Buat tebak kata kategori: $category"

        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(systemPrompt))),
            generationConfig = GenerationConfig(temperature = 0.9f, responseMimeType = "application/json")
        )

        try {
            val response = service.generateContent(apiKey, request)
            val rawText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: return@withContext null
            val cleanedJson = cleanJsonString(rawText)

            val adapter = moshi.adapter(WordScramblePuzzle::class.java)
            adapter.fromJson(cleanedJson)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 3b. Word Scramble Hint Generator - Generates a smart educational hint for a word without revealing it fully
    suspend fun generateTebakKataHint(correctWord: String, clue: String): String? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") return@withContext null

        val systemPrompt = """
            Kamu adalah Asisten Pintar Tebak Kata EP Knowledge Land. 
            Tugasmu adalah memberikan sebuah petunjuk tambahan (HINT) untuk membantu anak-anak menebak kata rahasia yang sedang diacak.
            Kata rahasia asli: $correctWord
            Petunjuk awal: "$clue"

            Berikan satu petunjuk tambahan yang kreatif, memicu rasa ingin tahu, dan mendidik, tanpa pernah menyebutkan kata rahasia atau huruf aslinya!
            Misalnya:
            - Beritahukan huruf pertama atau jumlah suku kata.
            - Kaitkan dengam objek yang sangat populer bagi anak-anak.
            - Berikan fakta mikro bermanfaat terkait objek tersebut.

            Kamu wajib menjawab HANYA berisi teks petunjuk tersebut, dalam 1 atau maksimal 2 kalimat pendek berbahasa Indonesia yang santun, aktif, dan asyik. Jangan menyelipkan kode JSON, markdown, atau pembungkus apa pun.
        """.trimIndent()

        val prompt = "Berikan petunjuk tamabahan kognitif untuk menebak kata tersebut."

        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(systemPrompt))),
            generationConfig = GenerationConfig(temperature = 0.8f)
        )

        try {
            val response = service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text?.trim()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 4. Hangman Word Generator - Generates a hidden word, educational clue, and fun fact for learning
    suspend fun generateHangmanWord(category: String): HangmanWord? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") return@withContext null

        val systemPrompt = """
            Kamu adalah Ahli Kosakata EP Knowledge Land. Tugasmu membuat tebak kata gaya Hangman yang mendidik untuk anak-anak dalam Bahasa Indonesia.
            Pilih sebuah kosakata yang positif dan mendidik berkesesuaian dengan kategori yang diajukan (misal: "Sains 🧪", "Sejarah 🏛️", "Geografi 🗺️", "Bahasa Inggris 🇬🇧", "Kesehatan 🍎").
            Kosakata asli (word) harus berupa kata tunggal valid, tanpa spasi, menggunakan karakter A-Z (huruf besar/kapital). Panjang kata antara 4 sampai 11 huruf.
            Berikan sebuah petunjuk (clue) kosakata yang ramah anak dalam Bahasa Indonesia.
            Berikan sebuah fakta seru (funFact) singkat yang bernilai edukatif tentang kata tersebut untuk memperluas pengetahuan anak.
            Kamu HARUS mengembalikan hanya satu objek JSON murni, tanpa format bungkusan block markdown (seperti ```json).
            
            Struktur JSON:
            {
              "word": "FOTOSINTESIS",
              "clue": "Proses tumbuhan hijau memasak makanannya sendiri menggunakan bantuan sinar matahari.",
              "funFact": "Selain menghasilkan makanan untuk tumbuh, proses ini juga memproduksi oksigen yang kita hirup setiap hari!",
              "category": "Sains 🧪"
            }
            Pastikan kata tersebut mendidik, menantang tapi bisa ditebak, dan fakta seru bernilai penjelas kognitif yang hebat.
        """.trimIndent()

        val prompt = "Buat kosakata tebak kata kategori: $category"

        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(systemPrompt))),
            generationConfig = GenerationConfig(temperature = 0.8f, responseMimeType = "application/json")
        )

        try {
            val response = service.generateContent(apiKey, request)
            val rawText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: return@withContext null
            val cleanedJson = cleanJsonString(rawText)

            val adapter = moshi.adapter(HangmanWord::class.java)
            adapter.fromJson(cleanedJson)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // 5. Daily Challenge Generator - Generates a single unique, educational daily topic-based quiz question
    suspend fun generateDailyChallenge(date: String): DailyChallengeQuestion? = withContext(Dispatchers.IO) {
        val apiKey = com.example.BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") return@withContext null

        val systemPrompt = """
            Kamu adalah Guru Cerdas Kurator EP Knowledge Land. Tugasmu membuat satu soal Tantangan Harian (Daily Challenge) yang unik, mendalam, dan mendidik untuk anak-anak sekolah.
            Topik harian harus bervariasi secara dramatis berdasarkan tanggal agar setiap hari terasa segar (misalnya: ekosistem hutan hujan, awal mula penulisan sejarah, misteri lubang hitam, sejarah candi prambanan, penemuan kapal selam, cara kerja internet, gaya magnet, dll).
            Untuk tanggal: $date, racik satu topik khusus yang luar biasa dan relevan dengan suasana belajar yang menyenangkan.
            
            Tingkat kesulitan harus ramah anak (SD Kelas Tinggi - SMP, umur 10-14 tahun).
            Kamu HARUS mengembalikan hanya satu objek JSON murni, tanpa format bungkusan block markdown (seperti ```json).
            
            Struktur JSON:
            {
              "topic": "Nama Topik Kreatif (misal: Keajaiban Terumbu Karang 🪸)",
              "question": "Satu pertanyaan pilihan ganda bermutu tentang topik tersebut.",
              "options": ["Opsi A", "Opsi B", "Opsi C", "Opsi D"],
              "answerIndex": 0,
              "explanation": "Penjelasan mengapa opsi tersebut benar dengan bahasa ramah anak, inspiratif, dan penuh wawasan.",
              "funFact": "Fakta unik tambahan yang mengejutkan tentang topik ini.",
              "clue": "Petunjuk kecil bersahabat untuk membantu menjawab jika anak kebingungan."
            }
            Pastikan index jawaban sesuai dengan opsi (0, 1, 2, atau 3), pilihan kata kreatif, opsi bervariasi, dan penjelasannya edukatif.
        """.trimIndent()

        val prompt = "Buat Tantangan Harian untuk tanggal hari ini: $date"

        val request = GeminiRequest(
            contents = listOf(GeminiContent(parts = listOf(GeminiPart(prompt)))),
            systemInstruction = GeminiContent(parts = listOf(GeminiPart(systemPrompt))),
            generationConfig = GenerationConfig(temperature = 0.7f, responseMimeType = "application/json")
        )

        try {
            val response = service.generateContent(apiKey, request)
            val rawText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: return@withContext null
            val cleanedJson = cleanJsonString(rawText)

            val adapter = moshi.adapter(DailyChallengeQuestion::class.java)
            adapter.fromJson(cleanedJson)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
