package com.example.data

import android.content.Context
import kotlin.random.Random

object WordPuzzleGenerator {

    private val sainsPuzzles = listOf(
        WordScramblePuzzle(
            clue = "Proses tumbuhan hijau memasak makanan menggunakan energi cahaya matahari ☀️",
            scrambledWord = "SITSOFNSIET",
            correctWord = "FOTOSINTESIS"
        ),
        WordScramblePuzzle(
            clue = "Gaya tarik bumi yang membuat benda jatuh ke bawah dan mencegah kita melayang 🌍",
            scrambledWord = "IVRGASATI",
            correctWord = "GRAVITASI"
        ),
        WordScramblePuzzle(
            clue = "Pergerakan bumi mengitari matahari dalam waktu satu tahun penuh 🔄",
            scrambledWord = "IUSEOLVR",
            correctWord = "REVOLUSI"
        ),
        WordScramblePuzzle(
            clue = "Gas penting yang dihirup oleh manusia dan hewan saat bernapas 🌬️",
            scrambledWord = "GSEIONK",
            correctWord = "OKSIGEN"
        ),
        WordScramblePuzzle(
            clue = "Hubungan timbal balik antara makhluk hidup dengan lingkungan sekitarnya 🍃",
            scrambledWord = "TSOIMESKE",
            correctWord = "EKOSISTEM"
        ),
        WordScramblePuzzle(
            clue = "Proses penguapan air permukaan bumi akibat energi panas dari matahari 💧",
            scrambledWord = "ASIEPROAV",
            correctWord = "EVAPORASI"
        ),
        WordScramblePuzzle(
            clue = "Alat pembesar yang digunakan untuk mengamati makhluk hidup mikroba bersel satu 🔬",
            scrambledWord = "OKSPRSKMI",
            correctWord = "MIKROSKOP"
        ),
        WordScramblePuzzle(
            clue = "Alat pengukur derajat panas atau dinginnya sebuah ruangan atau tubuh 🌡️",
            scrambledWord = "ROEREMMTTE",
            correctWord = "TERMOMETER"
        )
    )

    private val sejarahPuzzles = listOf(
        WordScramblePuzzle(
            clue = "Pernyataan kemerdekaan resmi Indonesia pada tanggal 17 Agustus 1945 🇮🇩",
            scrambledWord = "MSALIOPARK",
            correctWord = "PROKLAMASI"
        ),
        WordScramblePuzzle(
            clue = "Candi Buddha terbesar di Indonesia yang terletak di daerah Magelang 🛕",
            scrambledWord = "URDOBROBU",
            correctWord = "BOROBUDUR"
        ),
        WordScramblePuzzle(
            clue = "Nama kuno wilayah kepulauan yang kini menjadi negara kesatuan Indonesia 🗺️",
            scrambledWord = "ARANSANUT",
            correctWord = "NUSANTARA"
        ),
        WordScramblePuzzle(
            clue = "Lima pilar dasar negara serta ideologi resmi Republik Indonesia 🌟",
            scrambledWord = "ISLPNAACA",
            correctWord = "PANCASILA"
        ),
        WordScramblePuzzle(
            clue = "Tokoh emansipasi wanita Indonesia, penulis kumpulan surat 'Habis Gelap Terbitlah Terang' 👩",
            scrambledWord = "IINTRKA",
            correctWord = "KARTINI"
        ),
        WordScramblePuzzle(
            clue = "Kerajaan Hindu-Buddha terbesar di nusantara dengan patih Gajah Mada ⚔️",
            scrambledWord = "THAIMPAAJ",
            correctWord = "MAJAPAHIT"
        ),
        WordScramblePuzzle(
            clue = "Perang mempertahankan kemerdekaan pada 10 November 1945 di kota pahlawan 🦁",
            scrambledWord = "BAAYASUR",
            correctWord = "SURABAYA"
        ),
        WordScramblePuzzle(
            clue = "Gelaran perdana konferensi persatuan Asia Afrika tahun 1955 di kota kembang 🏔️",
            scrambledWord = "UNGANDB",
            correctWord = "BANDUNG"
        )
    )

    private val bahasaPuzzles = listOf(
        WordScramblePuzzle(
            clue = "Persamaan kata atau kata-kata yang memiliki makna serupa atau mirip 📝",
            scrambledWord = "ISMINON",
            correctWord = "SINONIM"
        ),
        WordScramblePuzzle(
            clue = "Lawan kata atau kata yang memiliki makna saling bertolak belakang 🔄",
            scrambledWord = "MOINTNA",
            correctWord = "ANTONIM"
        ),
        WordScramblePuzzle(
            clue = "Bagian tulisan yang berisi rangkaian beberapa kalimat dengan satu ide pokok utama 📖",
            scrambledWord = "AAFPGRAR",
            correctWord = "PARAGRAF"
        ),
        WordScramblePuzzle(
            clue = "Gaya bahasa kiasan membandingkan sesuatu secara langsung tanpa konjungsi pembanding 🗣️",
            scrambledWord = "AAREMOTF",
            correctWord = "METAFORA"
        ),
        WordScramblePuzzle(
            clue = "Buku rujukan tebal yang memuat daftar kosakata bahasa lengkap beserta definisinya 📚",
            scrambledWord = "UMSKA",
            correctWord = "KAMUS"
        ),
        WordScramblePuzzle(
            clue = "Kaidah cara penulisan kata-kata secara resmi sesuai standar tata bahasa 🖋️",
            scrambledWord = "NEAAJE",
            correctWord = "EJAAN"
        ),
        WordScramblePuzzle(
            clue = "Karya sastra tulisan indah berirama yang diungkapkan penuh dengan perasaan mendalam 🌸",
            scrambledWord = "UIIESP",
            correctWord = "PUISI"
        ),
        WordScramblePuzzle(
            clue = "Kalimat ringkas bersajak merdu berupa sindiran atau nasihat cerdas di Nusantara 🗣️",
            scrambledWord = "NUPTAN",
            correctWord = "PANTUN"
        )
    )

    fun getRandomPuzzle(context: Context, category: String): WordScramblePuzzle {
        val rand = Random(System.currentTimeMillis())
        val cleanCat = category.lowercase()
        val candidatesPool = when {
            cleanCat.contains("sains") || cleanCat.contains("ipa") -> sainsPuzzles
            cleanCat.contains("sejarah") || cleanCat.contains("ips") -> sejarahPuzzles
            cleanCat.contains("bahasa") || cleanCat.contains("indo") -> bahasaPuzzles
            else -> sainsPuzzles + sejarahPuzzles + bahasaPuzzles
        }

        val categoryKey = "scramble_${cleanCat}"
        val unseenCount = candidatesPool.count { !QuestionHistoryTracker.isQuestionSeen(context, "word_scramble_${it.correctWord}", categoryKey) }
        if (unseenCount < 1) {
            QuestionHistoryTracker.clearCategoryHistory(context, categoryKey)
        }

        val shuffled = candidatesPool.shuffled(rand).toMutableList()
        var chosen: WordScramblePuzzle? = null
        var attempts = 0

        while (shuffled.isNotEmpty() && attempts < 50) {
            val candidate = shuffled.first()
            shuffled.removeAt(0)
            attempts++

            val isSeen = QuestionHistoryTracker.isQuestionSeen(context, "word_scramble_${candidate.correctWord}", categoryKey)
            if (!isSeen || attempts > 20) {
                chosen = candidate
                QuestionHistoryTracker.markQuestionAsSeen(context, "word_scramble_${candidate.correctWord}", categoryKey)
                break
            } else {
                shuffled.add(candidate)
            }
        }

        return chosen ?: candidatesPool.random(rand)
    }
}
