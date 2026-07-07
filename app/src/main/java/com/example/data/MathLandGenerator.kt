package com.example.data

import com.squareup.moshi.Json

data class MathLandLevel(
    val id: Int,
    val name: String,
    val description: String,
    val island: String,
    val rewardStars: Int,
    val rewardXp: Int,
    val isBoss: Boolean,
    val bossEmoji: String = "",
    val bossName: String = ""
)

object MathLandGenerator {

    val levels = listOf(
        MathLandLevel(
            id = 1,
            name = "Lembah Tambah Kurang 🏔️",
            description = "Penjumlahan & Pengurangan Dasar. Sangat cocok untuk mengasah fondasi berhitung!",
            island = "Pulau Pemula 🌴",
            rewardStars = 10,
            rewardXp = 50,
            isBoss = false
        ),
        MathLandLevel(
            id = 2,
            name = "Kastil Bersusun 🏰",
            description = "Tantangan penjumlahan & pengurangan puluhan dan ratusan bersusun.",
            island = "Pulau Pemula 🌴",
            rewardStars = 15,
            rewardXp = 60,
            isBoss = false
        ),
        MathLandLevel(
            id = 3,
            name = "Hutan Perkalian Dasar 🌲",
            description = "Petualangan melintasi rimba perkalian angka 1 sampai 10.",
            island = "Kepulauan Perkalian ⚓",
            rewardStars = 15,
            rewardXp = 65,
            isBoss = false
        ),
        MathLandLevel(
            id = 4,
            name = "Danau Pembagian Rata 🌊",
            description = "Belajar membagi sama rata di pesisir danau es yang sejuk.",
            island = "Kepulauan Perkalian ⚓",
            rewardStars = 20,
            rewardXp = 70,
            isBoss = false
        ),
        MathLandLevel(
            id = 5,
            name = "Golem Batu Raksasa 🧌 [BOSS]",
            description = "Gunakan kecepatan menghitung perkalian dan pembagianmu untuk mengalahkan sang Golem Penjaga!",
            island = "Kepulauan Perkalian ⚓",
            rewardStars = 30,
            rewardXp = 100,
            isBoss = true,
            bossEmoji = "🧌",
            bossName = "Stone Golem"
        ),
        MathLandLevel(
            id = 6,
            name = "Gurun Pecahan Misterius 🏜️",
            description = "Ungkap rahasia pecahan dan angka desimal di gurun pasir magis.",
            island = "Samudra Pembagian 🏴‍☠️",
            rewardStars = 20,
            rewardXp = 80,
            isBoss = false
        ),
        MathLandLevel(
            id = 7,
            name = "Kuil KPK & FPB 🏛️",
            description = "Selesaikan puzzle mencari kelipatan terkecil dan faktor terbesar di kuil kuno.",
            island = "Samudra Pembagian 🏴‍☠️",
            rewardStars = 25,
            rewardXp = 90,
            isBoss = false
        ),
        MathLandLevel(
            id = 8,
            name = "Pegunungan Negatif ❄️",
            description = "Berhati-hatilah dengan suhu ekstrem dan angka bilangan bulat di bawah nol!",
            island = "Kubah Es Aljabar 🌌",
            rewardStars = 25,
            rewardXp = 95,
            isBoss = false
        ),
        MathLandLevel(
            id = 9,
            name = "Jembatan Aljabar Kuno 🌉",
            description = "Selesaikan misteri nilai variabel 'x' untuk melewati jembatan gantung.",
            island = "Kubah Es Aljabar 🌌",
            rewardStars = 30,
            rewardXp = 100,
            isBoss = false
        ),
        MathLandLevel(
            id = 10,
            name = "Naga Api Matematika 🐉 [BOSS]",
            description = "Pertempuran akhir melawan Naga Api Merah! Kuasai seluruh ilmu matematikamu demi menyelamatkan Math Land!",
            island = "Kubah Es Aljabar 🌌",
            rewardStars = 50,
            rewardXp = 200,
            isBoss = true,
            bossEmoji = "🐉",
            bossName = "Ignis the Fire Dragon"
        )
    )

    fun getLevelById(id: Int): MathLandLevel? {
        return levels.find { it.id == id }
    }

    fun generateMathLandQuestions(levelId: Int): List<QuizQuestion> {
        return when (levelId) {
            1 -> listOf(
                QuizQuestion(
                    question = "Budi memiliki 5 apel, lalu diberi oleh ayah 3 apel lagi. Berapa jumlah apel Budi sekarang? 🍎",
                    options = listOf("7", "8", "9", "10"),
                    answerIndex = 1,
                    explanation = "Apel yang Budi miliki bertambah: 5 + 3 = 8 apel. Kamu memulainya dengan sangat hebat! 🌟"
                ),
                QuizQuestion(
                    question = "Ibu membeli 10 telur di pasar, tetapi pecah 3 telur saat di jalan. Berapa butir telur Ibu yang masih utuh? 🥚",
                    options = listOf("5", "6", "7", "8"),
                    answerIndex = 2,
                    explanation = "Jumlah telur yang utuh berkurang karena pecah: 10 - 3 = 7 butir telur. Pintar sekali! 🌱"
                ),
                QuizQuestion(
                    question = "Berapakah hasil penjumlahan sederhana dari 7 + 6? ➕",
                    options = listOf("11", "12", "13", "14"),
                    answerIndex = 2,
                    explanation = "Hasil dari 7 + 6 adalah 13. Latihan yang bagus untuk ketangkasan berhitungmu! ✨"
                ),
                QuizQuestion(
                    question = "Adit mempunyai 15 balon indah. Tiba-tiba balonnya terbang tertiup angin sebanyak 5 balon. Berapakah sisa balon Adit? 🎈",
                    options = listOf("8", "9", "10", "11"),
                    answerIndex = 2,
                    explanation = "Balon terbang artinya berkurang: 15 - 5 = 10 balon tersisa. Kerja bagus! 🚀"
                ),
                QuizQuestion(
                    question = "Ayo hitung penjumlahan berpasangan berikut: 8 + 8 = ... 🔥",
                    options = listOf("14", "15", "16", "17"),
                    answerIndex = 2,
                    explanation = "Angka kembar! 8 + 8 = 16. Sempurna, kamu menyelesaikan level pertama! 🎉"
                )
            )
            2 -> listOf(
                QuizQuestion(
                    question = "Berapakah hasil dari penjumlahan bersusun 38 + 25? 🔢",
                    options = listOf("53", "63", "65", "73"),
                    answerIndex = 1,
                    explanation = "Mari kita hitung secara bersusun: 8 + 5 = 13 (tulis 3, simpan 1). Lalu 3 + 2 + 1 (simpanan) = 6. Jadi jawabannya adalah 63! 🏰"
                ),
                QuizQuestion(
                    question = "Hitunglah pengurangan bersusun berikut dengan meminjam: 85 - 47 = ... ➖",
                    options = listOf("32", "38", "42", "48"),
                    answerIndex = 1,
                    explanation = "Karena 5 kurang dari 7, kita meminjam 1 puluhan dari 8 sehingga menjadi 15. Lalu 15 - 7 = 8. Sisa angka puluhan adalah 7 - 4 = 3. Jadi jawabannya adalah 38. Luar biasa! 🌟"
                ),
                QuizQuestion(
                    question = "Berapakah hasil penjumlahan ratusan berikut: 124 + 56? ➕",
                    options = listOf("170", "180", "190", "200"),
                    answerIndex = 1,
                    explanation = "Mari menjumlahkan: 124 + 56 = 180. Sangat mudah bagi kesatria matematika sepertimu!"
                ),
                QuizQuestion(
                    question = "Toko buku Makmur memiliki stok 150 buku cerita. Hari ini laku terjual 65 buku. Berapa sisa stok buku saat ini? 📖",
                    options = listOf("75", "85", "95", "105"),
                    answerIndex = 1,
                    explanation = "Stok awal dikurangi terjual: 150 - 65 = 85 buku tersisa. Hebat sekali! 📘"
                ),
                QuizQuestion(
                    question = "Berapakah sisa pengurangan dari 210 - 75? 🏔️",
                    options = listOf("125", "135", "145", "155"),
                    answerIndex = 1,
                    explanation = "Hasil pengurangan dari 210 - 75 adalah 135. Jembatan Kastil Matematika kini terbuka lebar! ⚔️"
                )
            )
            3 -> listOf(
                QuizQuestion(
                    question = "Hasil dari perkalian dasar berikut 6 × 7 adalah... ✖️",
                    options = listOf("36", "41", "42", "48"),
                    answerIndex = 2,
                    explanation = "6 dikalikan 7 sama dengan menjumlahkan angka 7 sebanyak 6 kali: 7+7+7+7+7+7 = 42. Tepat! 🌲"
                ),
                QuizQuestion(
                    question = "Pak Tani menanam jagung dalam 4 baris lurus. Setiap baris berisi 9 buah jagung. Berapa total seluruh jagung Pak Tani? 🌽",
                    options = listOf("32", "36", "40", "42"),
                    answerIndex = 1,
                    explanation = "Perkalian baris: 4 × 9 = 36 buah jagung secara keseluruhan. Kerja cerdas! 🏕️"
                ),
                QuizQuestion(
                    question = "Berapakah hasil perkalian besar dari 8 × 9? ⚡",
                    options = listOf("64", "72", "80", "81"),
                    answerIndex = 1,
                    explanation = "8 dikalikan dengan 9 menghasilkan 72. Kamu sangat cepat menghafal perkalian! ⭐"
                ),
                QuizQuestion(
                    question = "Jika sebuah sepeda motor memiliki 2 roda, berapa banyak jumlah roda dari 12 sepeda motor? 🏍️",
                    options = listOf("20", "22", "24", "26"),
                    answerIndex = 2,
                    explanation = "Perkalian jumlah motor dan roda: 12 × 2 = 24 roda. Jawaban yang luar biasa cerdik!"
                ),
                QuizQuestion(
                    question = "Ayo taklukkan tanjakan hutan ini: 7 × 8 = ... 🔥",
                    options = listOf("49", "54", "56", "62"),
                    answerIndex = 2,
                    explanation = "7 dikalikan 8 adalah 56. Semua tanaman hutan menari menyambut kemenanganmu! 🌳🎉"
                )
            )
            4 -> listOf(
                QuizQuestion(
                    question = "Budi ingin membagi 24 buah jeruk manis kepada 4 orang temannya secara adil. Berapakah jeruk yang didapat masing-masing anak? 🍊",
                    options = listOf("5", "6", "7", "8"),
                    answerIndex = 1,
                    explanation = "Pembagian adil: 24 dibagi 4 ditulis 24 ÷ 4 = 6 jeruk per anak. Kamu sangat adil! 🤝"
                ),
                QuizQuestion(
                    question = "Ayo hitung pembagian matematika berikut: 56 ÷ 8 = ... ➗",
                    options = listOf("6", "7", "8", "9"),
                    answerIndex = 1,
                    explanation = "Karena 8 × 7 = 56, maka kebalikannya adalah 56 ÷ 8 = 7. Mantap sekali! 🌊"
                ),
                QuizQuestion(
                    question = "Sebuah kelas mempunyai 30 siswa yang akan dibagi menjadi 5 kelompok belajar sama banyak. Berapa banyak siswa tiap kelompok? 🎒",
                    options = listOf("5", "6", "7", "8"),
                    answerIndex = 1,
                    explanation = "Jumlah siswa per kelompok: 30 ÷ 5 = 6 siswa. Kelompok belajar siap melakukan petualangan ilmiah! 🧪"
                ),
                QuizQuestion(
                    question = "Hasil pembagian kuadrat dari 81 ÷ 9 adalah... ⚓",
                    options = listOf("7", "8", "9", "10"),
                    answerIndex = 2,
                    explanation = "Pembagian istimewa: 81 ÷ 9 = 9 karena 9 × 9 = 81. Menakjubkan! ⛵"
                ),
                QuizQuestion(
                    question = "Bila 45 buku pelajaran disusun rapi di 5 rak buku dengan jumlah sama rata, maka tiap rak buku berisi... 📚",
                    options = listOf("8", "9", "10", "11"),
                    answerIndex = 1,
                    explanation = "Buku di setiap rak: 45 ÷ 5 = 9 buku. Perpustakaan kini terlihat sangat rapi bagai istanamu! 👑"
                )
            )
            5 -> listOf(
                QuizQuestion(
                    question = "Kalahkan Golem Batu! Cepat, berapakah hasil perkalian dari 12 × 5? 🧌⚔️",
                    options = listOf("50", "60", "70", "80"),
                    answerIndex = 1,
                    explanation = "Duar! Pukulanmu telak. 12 × 5 = 60. Golem Batu mulai retak! 💥"
                ),
                QuizQuestion(
                    question = "Serang balik sebelum Golem memukul! Berapakah hasil dari pembagian 72 ÷ 6? ⚡",
                    options = listOf("11", "12", "13", "14"),
                    answerIndex = 1,
                    explanation = "Hebat! Tangkisanmu berhasil. 72 ÷ 6 = 12. Golem terhuyung ke belakang! 🛡️"
                ),
                QuizQuestion(
                    question = "Golem bersiap membanting batu raksasa! Hindari dengan menjawab cepat: 15 × 4 = ... 🏔️",
                    options = listOf("50", "60", "70", "80"),
                    answerIndex = 1,
                    explanation = "Kerja bagus, kamu melompat tepat waktu! 15 × 4 = 60. Golem terkejut melihat kelincahanmu! 🏃💨"
                ),
                QuizQuestion(
                    question = "Kesempatan emas menyerang bagian energinya! Hancurkan kristal itu: 90 ÷ 5 = ... 🔮",
                    options = listOf("16", "17", "18", "19"),
                    answerIndex = 2,
                    explanation = "Kena! 90 ÷ 5 = 18. Energinya merosot tajam! Golem melepaskan batu besarnya! ✨"
                ),
                QuizQuestion(
                    question = "Serangan pemungkas penentu kemenangan! Keluarkan kekuatan penuh: 14 × 3 = ... 🗡️💥",
                    options = listOf("36", "40", "42", "48"),
                    answerIndex = 2,
                    explanation = "Subhanallah! Kekuatan matematika menghempaskannya! 14 × 3 = 42. Golem runtuh menjadi tumpukan batu kecil yang aman! 🏆🎉"
                )
            )
            6 -> listOf(
                QuizQuestion(
                    question = "Berapakah hasil dari penjumlahan pecahan berikut: 1/2 + 1/4? 🍰",
                    options = listOf("2/4", "3/4", "3/8", "2/6"),
                    answerIndex = 1,
                    explanation = "Samakan penyebutnya menjadi 4: 1/2 menjadi 2/4. Kemudian jumlahkan pembilangnya: 2/4 + 1/4 = 3/4. Sempurna! Desierto Magico menyambutmu! 🏜️"
                ),
                QuizQuestion(
                    question = "Sederhanakanlah pecahan berikut ini ke bentuk paling sederhana: 12/16 = ... 🔍",
                    options = listOf("2/3", "3/4", "4/5", "3/5"),
                    answerIndex = 1,
                    explanation = "Bagi pembilang dan penyebut dengan FPB keduanya, yaitu 4. Jadi, (12÷4) / (16÷4) = 3/4. Jawaban yang luar biasa jeli! 🌟"
                ),
                QuizQuestion(
                    question = "Berapakah hasil perkalian desimal dari angka berikut: 0.5 × 8? 🐫",
                    options = listOf("3.5", "4", "4.5", "5"),
                    answerIndex = 1,
                    explanation = "Perkalian 0.5 sama saja dengan setengah dari bilangan tersebut: Setengah dari 8 adalah 4 (0.5 × 8 = 4). Selamat melangkah maju! 🐾"
                ),
                QuizQuestion(
                    question = "Jika nilai sebuah pecahan adalah 3/5, berapakah nilainya bila diubah ke format desimal? 💎",
                    options = listOf("0.3", "0.5", "0.6", "0.8"),
                    answerIndex = 2,
                    explanation = "Ubah penyebut menjadi per sepuluh dengan mengalikan pembilang dan penyebut dengan 2: 3/5 = 6/10 = 0.6. Sangat cerdas! ✨"
                ),
                QuizQuestion(
                    question = "Tantangan oasis terakhir! Berapakah hasil pengurangan pecahan berikut: 1 - 2/5? 💧",
                    options = listOf("1/5", "2/5", "3/5", "4/5"),
                    answerIndex = 2,
                    explanation = "Anggap angka 1 sebagai pecahan 5/5: 5/5 - 2/5 = 3/5. Dahagamu di gurun pasir kini teratasi dengan air matematika yang segar! 🐫🎉"
                )
            )
            7 -> listOf(
                QuizQuestion(
                    question = "Berapakah Kelipatan Persekutuan Terkecil (KPK) dari angka 6 dan 8? 🔍",
                    options = listOf("12", "16", "18", "24"),
                    answerIndex = 3,
                    explanation = "Kelipatan 6: 6, 12, 18, 24, 30... Kelipatan 8: 8, 16, 24, 32... Kelipatan terkecil yang sama dari keduanya adalah 24. Pintar sekali! 🏛️"
                ),
                QuizQuestion(
                    question = "Berapakah Faktor Persekutuan Terbesar (FPB) dari bilangan 12 dan 18? 📐",
                    options = listOf("3", "4", "6", "9"),
                    answerIndex = 2,
                    explanation = "Faktor 12: 1, 2, 3, 4, 6, 12. Faktor 18: 1, 2, 3, 6, 9, 18. Faktor pembagi terbesar yang sama dari keduanya adalah 6. Sungguh menakjubkan! ✨"
                ),
                QuizQuestion(
                    question = "Lampu rambu merah menyala setiap 4 menit, lampu rambu hijau menyala setiap 6 menit. Kedua lampu akan menyala bersamaan di menit ke-... 🚥",
                    options = listOf("8", "10", "12", "24"),
                    answerIndex = 2,
                    explanation = "Cari KPK dari 4 dan 6. Kelipatan 4: 4, 8, 12, 16... Kelipatan 6: 6, 12, 18... Keduanya menyala bersama setiap kelipatan 12 menit. Jawaban brilian! 🚀"
                ),
                QuizQuestion(
                    question = "Budi memiliki 15 permen susu dan Roni memiliki 20 permen cokelat. Mereka ingin membagikan permen tersebut kepada teman dalam paket plastik sama rata tanpa sisa. Berapa bungkus plastik terbanyak yang bisa dibuat? 🍬",
                    options = listOf("3", "5", "6", "10"),
                    answerIndex = 1,
                    explanation = "Gunakan konsep FPB dari 15 dan 20. Faktor persekutuannya adalah 5 (tiap paket isi 3 permen susu dan 4 permen cokelat). Jadi maksimal 5 paket. Sangat bermanfaat! 🎁"
                ),
                QuizQuestion(
                    question = "Berapakah KPK dari bilangan 10 dan 15? 🏛️🎇",
                    options = listOf("20", "25", "30", "60"),
                    answerIndex = 2,
                    explanation = "KPK dari 10 (10, 20, 30, ...) dan 15 (15, 30, ...) adalah 30. Pintu rahasia Kuil FPB kini bergeser terbuka! 🌟🏆"
                )
            )
            8 -> listOf(
                QuizQuestion(
                    question = "Suhu udara dingin membeku! Berapakah hasil perhitungan berikut: 5 + (-8) = ... ❄️",
                    options = listOf("-3", "3", "-13", "13"),
                    answerIndex = 0,
                    explanation = "Menjumlahkan bilangan negatif sama seperti pengurangan: 5 - 8 = -3. Suhu berada di bawah titik beku! 🥶"
                ),
                QuizQuestion(
                    question = "Hitunglah pengurangan bilangan negatif berikut ini: -12 - (-4) = ... 🏔️",
                    options = listOf("-16", "-8", "8", "16"),
                    answerIndex = 1,
                    explanation = "Dua lambang negatif berurutan menjadi positif: -12 + 4 = -8. Logika bilangan negatifmu sangat tajam! 🗡️"
                ),
                QuizQuestion(
                    question = "Berapakah hasil perkalian tanda dari bilangan negatif: (-6) × (-7)? ✖️",
                    options = listOf("-42", "42", "-36", "36"),
                    answerIndex = 1,
                    explanation = "Ingat hukum tanda perkalian! Negatif dikali negatif bernilai positif. Maka (-6) × (-7) = 42. Hebat! ✨"
                ),
                QuizQuestion(
                    question = "Suhu di puncak gunung mula-mula adalah -2°C. Malam harinya suhu turun lagi sebesar 5°C. Berapa suhu udara puncak gunung malam ini? 🌡️",
                    options = listOf("3°C", "-3°C", "7°C", "-7°C"),
                    answerIndex = 3,
                    explanation = "Suhu turun dari posisi negatif: -2 - 5 = -7°C. Hangatkan dirimu dengan jaket matematika! 🧥"
                ),
                QuizQuestion(
                    question = "Hasil pembagian tanda berikut: (-20) ÷ 4 adalah... ☃️",
                    options = listOf("-5", "5", "-4", "4"),
                    answerIndex = 0,
                    explanation = "Negatif dibagi positif menghasilkan negatif: -20 ÷ 4 = -5. Badai salju berhasil dilewati dengan selamat! ⛷️❄️"
                )
            )
            9 -> listOf(
                QuizQuestion(
                    question = "Jika x + 7 = 15, berapakah nilai variabel x? 🌉",
                    options = listOf("6", "7", "8", "9"),
                    answerIndex = 2,
                    explanation = "Kurangkan angka 7 dari kedua ruas: x = 15 - 7, maka x = 8. Jembatan kayu bersinar menyala satu langkah! 🌉"
                ),
                QuizQuestion(
                    question = "Selesaikan persamaan aljabar berikut ini: 3x - 5 = 10. Nilai x adalah... 🔬",
                    options = listOf("3", "4", "5", "6"),
                    answerIndex = 2,
                    explanation = "Pindahkan -5 ke kanan: 3x = 10 + 5 -> 3x = 15. Lalu bagi kedua ruas dengan 3: x = 5. Luar biasa tanggap! ✨"
                ),
                QuizQuestion(
                    question = "Jika 2y + 4 = 12, berapakah nilai dari y? ⚖️",
                    options = listOf("3", "4", "5", "6"),
                    answerIndex = 1,
                    explanation = "Kurangkan kedua ruas dengan 4: 2y = 8. Maka y = 4. Jembatan semakin stabil menahan bebanmu!"
                ),
                QuizQuestion(
                    question = "Budi membeli x buah pensil seharga Rp 2.000 per pensil. Total belanjaannya Rp 10.000 (2000x = 10000). Berapa banyak pensil x yang dibeli Budi? ✏️",
                    options = listOf("3", "4", "5", "6"),
                    answerIndex = 2,
                    explanation = "Bagi total biaya dengan harga satuan: x = 10.000 ÷ 2.000, maka x = 5 pensil. Matematika belanja yang sangat praktis! 🛍️"
                ),
                QuizQuestion(
                    question = "Selesaikan teka-teki aljabar ini untuk melangkah ke seberang: 18 - 2p = 8. Berapakah p? 🌉🏆",
                    options = listOf("3", "4", "5", "6"),
                    answerIndex = 2,
                    explanation = "Kurangkan 18 dari kedua ruas atau pindahkan p: 2p = 18 - 8 -> 2p = 10. Maka p = 5. Jembatan aljabar kuno terlampaui dengan megah! 🎉🚀"
                )
            )
            10 -> listOf(
                QuizQuestion(
                    question = "Naga Api menyemburkan api berkobar! Tangkal cepat: 15 × 6 = ... 🐉🔥",
                    options = listOf("80", "90", "100", "110"),
                    answerIndex = 1,
                    explanation = "Duar! Mantra es milikmu berhasil memadamkan semburan naga! 15 × 6 = 90. Naga menggeram kaget! 🛡️❄️"
                ),
                QuizQuestion(
                    question = "Naga mengepakkan sayap dan terbang tinggi! Hitunglah sasarannya: 144 ÷ 12 = ... 🏹🌌",
                    options = listOf("10", "11", "12", "13"),
                    answerIndex = 2,
                    explanation = "Tembakan panah anginmu akurat! 144 ÷ 12 = 12. Sayap naga melemah dan ia mendarat terkejut! 🏹✨"
                ),
                QuizQuestion(
                    question = "Naga mengguncang tanah! Gunakan logika menyeimbangkan diri: 25 + (-17) = ... ⚖️💫",
                    options = listOf("8", "-8", "42", "-42"),
                    answerIndex = 0,
                    explanation = "Penyelamatan diri yang luar biasa seimbang! 25 ditambah -17 sama dengan 25 - 17, yaitu 8. Naga terperangkap tumpukan salju! 🏔️"
                ),
                QuizQuestion(
                    question = "Semburkan pedang petir berkekuatan aljabar! Selesaikan: 4x = 36. Berapa x? 🗡️⚡",
                    options = listOf("7", "8", "9", "10"),
                    answerIndex = 2,
                    explanation = "Sabetan petir spektakuler! x = 36 ÷ 4 = 9. Armor naga pecah berkeping-keping! 🥋💥"
                ),
                QuizQuestion(
                    question = "Serangan legendaris terkuat penutup perang abadi! Selesaikan pecahan: hitunglah 3/4 dari 80! 🐉👑🎇",
                    options = listOf("50", "55", "60", "65"),
                    answerIndex = 2,
                    explanation = "Subhanallah! Cahaya suci menyinari Math Land! 3/4 × 80 = (80 ÷ 4) × 3 = 20 × 3 = 60. Naga Api Merah tunduk bersahabat, kegelapan lenyap, dan perdamaian abadi kembali ke benua Math Land! Selamat, Kesatria Legendaris! 🏆🏁🎉"
                )
            )
            else -> emptyList()
        }
    }
}
