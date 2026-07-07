package com.example.data

import kotlin.random.Random

object MathQuestionGenerator {

    fun generateLocalMathQuestions(context: android.content.Context, grade: Int, category: String): List<QuizQuestion> {
        val questions = mutableListOf<QuizQuestion>()
        val currentQuestionTexts = mutableSetOf<String>()
        val categoryKey = "math_${category}_${grade}"

        for (i in 1..5) {
            var q: QuizQuestion? = null
            var attempts = 0
            while (attempts < 50) {
                // Ensure unique random choices on retry attempts
                val rand = Random(System.currentTimeMillis() + grade.hashCode() + category.hashCode() + i * 1000 + attempts * 31)
                
                // Map Level 1..15 to appropriate internal grade difficulties (1..12 scale)
                val targetGrade = when {
                    grade <= 12 -> grade
                    grade == 13 -> 12
                    grade == 14 -> 12
                    else -> 12
                }

                val tempQ = when (category) {
                    "Perkalian" -> generatePerkalian(targetGrade, i + attempts, rand)
                    "Pembagian" -> generatePembagian(targetGrade, i + attempts, rand)
                    "Pecahan" -> generatePecahan(targetGrade, i + attempts, rand)
                    "Geometri" -> generateGeometri(targetGrade, i + attempts, rand)
                    "Campuran" -> generateCampuran(targetGrade, i + attempts, rand)
                    "Cerita" -> generateSoalCerita(targetGrade, i + attempts, rand)
                    else -> generatePerkalian(targetGrade, i + attempts, rand)
                }
                
                // Strict duplicate prevention within this quiz instance
                val isDuplicateInQuiz = currentQuestionTexts.contains(tempQ.question)
                val isSeenInHistory = QuestionHistoryTracker.isQuestionSeen(context, tempQ.question, categoryKey)

                if (!isDuplicateInQuiz && (!isSeenInHistory || attempts >= 49)) {
                    q = tempQ
                    currentQuestionTexts.add(tempQ.question)
                    QuestionHistoryTracker.markQuestionAsSeen(context, tempQ.question, categoryKey)
                    break
                }
                attempts++
            }
            // Fallback securely inside the level's range
            val targetGrade = if (grade <= 12) grade else 12
            questions.add(q ?: generatePerkalian(targetGrade, i, Random(System.currentTimeMillis() + i)))
        }

        return questions
    }

    private fun generatePerkalian(grade: Int, index: Int, rand: Random): QuizQuestion {
        return when {
            grade <= 2 -> {
                // Kelas 1-2 SD: Perkalian kecil (1-5)
                val a = rand.nextInt(2, 6)
                val b = rand.nextInt(1, 6)
                val correct = a * b
                val questionText = "Berapakah hasil dari perkalian $a × $b?"
                val options = generateOptions(correct, 1, 30, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Benar! $a dikalikan $b artinya kita menjumlahkan angka $b sebanyak $a kali. $a × $b = $correct ✨"
                )
            }
            grade <= 4 -> {
                // Kelas 3-4 SD: Perkalian puluhan x satuan (11-25 x 2-9)
                val a = rand.nextInt(11, 26)
                val b = rand.nextInt(2, 10)
                val correct = a * b
                val questionText = "Hitunglah perkalian berikut: $a × $b = ..."
                val options = generateOptions(correct, 20, 250, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Hebat! Hasil gabungan perkalian puluhan: $a × $b = $correct. Kamu luar biasa! 🌟"
                )
            }
            grade <= 6 -> {
                // Kelas 5-6 SD: Perkalian pecahan / desimal / puluhan x puluhan
                if (rand.nextBoolean()) {
                    val a = rand.nextInt(12, 30)
                    val b = rand.nextInt(11, 21)
                    val correct = a * b
                    val questionText = "Berapakah hasil perkalian $a × $b?"
                    val options = generateOptions(correct, 100, 600, rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(correct.toString()),
                        explanation = "Luar biasa tepat! Menghitung perkalian bersusun: $a × $b = $correct."
                    )
                } else {
                    val aVal = rand.nextInt(2, 8)
                    val bVal = rand.nextInt(12, 20)
                    val correctDouble = (aVal.toDouble() * bVal.toDouble()) / 10.0
                    val questionText = "Hitunglah perkalian desimal: ${aVal.toDouble()/10.0} × $bVal"
                    val correctStr = if (correctDouble % 1.0 == 0.0) correctDouble.toInt().toString() else correctDouble.toString()
                    val opt1 = if ((correctDouble + 0.5) % 1.0 == 0.0) (correctDouble + 0.5).toInt().toString() else (correctDouble + 0.5).toString()
                    val opt2 = if ((correctDouble - 0.4) % 1.0 == 0.0) (correctDouble - 0.4).toInt().toString() else (correctDouble - 0.4).toString()
                    val opt3 = if ((correctDouble + 1.2) % 1.0 == 0.0) (correctDouble + 1.2).toInt().toString() else (correctDouble + 1.2).toString()
                    val options = listOf(correctStr, opt1, opt2, opt3).shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(correctStr),
                        explanation = "Mantap! Perkalian desimal ${aVal.toDouble()/10.0} × $bVal sama dengan ${aVal * bVal} dibagi 10, yaitu $correctStr. 🌱"
                    )
                }
            }
            grade <= 9 -> {
                // Kelas 7-9 SMP: Aljabar sederhana / Eksponen / Perkalian negatif
                val a = rand.nextInt(-12, -2)
                val b = rand.nextInt(3, 11)
                val correct = a * b
                val questionText = "Hasil kali bilangan bulat negatif dan positif: ($a) × $b"
                val options = generateOptions(correct, -120, -6, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Pintar! Bilangan negatif dikalikan bilangan positif akan selalu menghasilkan bilangan negatif. Jawabannya adalah $correct. 📐"
                )
            }
            else -> {
                // Kelas 10-12 SMA: Eksponen / Logaritma / Persamaan Kuadrat perkalian suku
                val roots = listOf(-3, -2, -1, 1, 2, 3, 4)
                val r1 = roots.random(rand)
                val r2 = roots.random(rand)
                val sumStr = if (-(r1 + r2) >= 0) "+ ${-(r1 + r2)}" else "- ${r1 + r2}"
                val prod = r1 * r2
                val prodStr = if (prod >= 0) "+ $prod" else "- ${-prod}"
                
                val questionText = "Jika dijabarkan, perkalian aljabar (x - $r1)(x - $r2) adalah..."
                val correctAns = "x² $sumStr x $prodStr"
                
                // Construct fake wrong options
                val opt1 = "x² - $sumStr x $prodStr"
                val opt2 = "x² $sumStr x - $prodStr"
                val opt3 = "x² + ${(r1-r2)} x - ${r1*r2}"
                
                val options = listOf(correctAns, opt1, opt2, opt3).distinct().shuffled(rand)
                val finalOptions = if (options.size < 4) {
                    listOf(correctAns, "x² - x + 5", "x² + 2x - 3", "x² - 5x + 6")
                } else options
                
                QuizQuestion(
                    question = questionText,
                    options = finalOptions,
                    answerIndex = finalOptions.indexOf(correctAns),
                    explanation = "Luar biasa cerdas! Menggunakan metode FOIL/distributif: (x - $r1)(x - $r2) = x² - ${r2}x - ${r1}x + ${r1*r2} = x² $sumStr x $prodStr. 🧬"
                )
            }
        }
    }

    private fun generatePembagian(grade: Int, index: Int, rand: Random): QuizQuestion {
        return when {
            grade <= 2 -> {
                // Kelas 1-2 SD: Pembagian genap kecil (hasil 1-5)
                val correct = rand.nextInt(1, 6)
                val b = rand.nextInt(2, 6)
                val a = correct * b
                val questionText = "Berapakah hasil dari pembagian $a ÷ $b?"
                val options = generateOptions(correct, 1, 12, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Sempurna! $a ÷ $b = $correct karena jika kita memiliki $a buah apel dan dibagikan rata untuk $b anak, masing-masing mendapat $correct. 🍎"
                )
            }
            grade <= 4 -> {
                // Kelas 3-4 SD: Pembagian ratusan / puluhan (hasil 6-20)
                val correct = rand.nextInt(6, 21)
                val b = rand.nextInt(3, 10)
                val a = correct * b
                val questionText = "Tentukan hasil pembagian berikut: $a ÷ $b = ..."
                val options = generateOptions(correct, 2, 40, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Luar biasa! $a ÷ $b = $correct. Kamu terus bertambah pintar! 📈"
                )
            }
            grade <= 6 -> {
                // Kelas 5-6 SD: Pembagian bersusun atau pecahan sederhana
                val correct = rand.nextInt(12, 35)
                val b = rand.nextInt(11, 16)
                val a = correct * b
                val questionText = "Selesaikan pembagian bersusun berikut: $a ÷ $b"
                val options = generateOptions(correct, 5, 80, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Tepat sekali! $a dibagi $b menghasilkan nilai bulat tepat sebesar $correct. 🎉"
                )
            }
            grade <= 9 -> {
                // Kelas 7-9 SMP: Pembagian aljabar / pecahan bersusun / bilangan negatif
                val b = rand.nextInt(-10, -2)
                val correct = rand.nextInt(2, 16)
                val a = b * correct
                val questionText = "Hitunglah hasil pembagian bilangan bulat negatif berikut: ($a) ÷ ($b)"
                val options = generateOptions(correct, 1, 30, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Hebat! Bilangan negatif dibagi bilangan negatif memberikan hasil bilangan positif! Maka, ($a) ÷ ($b) = $correct. 🌟"
                )
            }
            else -> {
                // Kelas 10-12 SMA: Menyederhanakan rational polynomial atau logaritma pembagian
                val aVal = rand.nextInt(3, 10)
                val powerText = when (aVal) {
                    3 -> "81 ÷ 9 = 9"
                    4 -> "64 ÷ 16 = 4"
                    else -> "100 ÷ 10 = 10"
                }
                
                val questionText = "Sederhanakan ekspresi rasional berikut:\n(x² - 9) ÷ (x + 3) untuk x ≠ -3."
                val correctAns = "x - 3"
                val options = listOf("x - 3", "x + 3", "x² - 3", "x - 9").shuffled(rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correctAns),
                    explanation = "Hebat sekali! Karena x² - 9 dapat difaktorkan menjadi (x - 3)(x + 3), maka pembagian dengan (x + 3) menyisakan x - 3. 🧪"
                )
            }
        }
    }

    private fun generateCampuran(grade: Int, index: Int, rand: Random): QuizQuestion {
        // Penambahan & Pengurangan dalam 1 soal
        return when {
            grade <= 2 -> {
                // Kelas 1-2 SD: Penambahan dan pengurangan kecil (1-digit / hasil < 20)
                val a = rand.nextInt(5, 11)
                val b = rand.nextInt(3, 10)
                val c = rand.nextInt(2, 6)
                val correct = a + b - c
                val questionText = "Hitunglah: $a + $b - $c = ..."
                val options = generateOptions(correct, 1, 25, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Bagus! Mula-mula lakukan penambahan: $a + $b = ${a+b}. Kemudian dikurangi $c menjadi $correct. 🧸"
                )
            }
            grade <= 4 -> {
                // Kelas 3-4 SD: Penambahan & Pengurangan puluhan (10-99)
                val a = rand.nextInt(20, 80)
                val b = rand.nextInt(15, 60)
                val c = rand.nextInt(10, 50)
                val correct = a + b - c
                val questionText = "Berapakah hasil dari $a + $b - $c?"
                val options = generateOptions(correct, 10, 180, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Hebat! $a + $b = ${a+b}, lalu dikurangi $c menghasilkan $correct. Kamu sungguh jeli! 🌈"
                )
            }
            grade <= 6 -> {
                // Kelas 5-6 SD: Penambahan & Pengurangan ratusan / ribuan
                val a = rand.nextInt(150, 400)
                val b = rand.nextInt(100, 300)
                val c = rand.nextInt(80, 200)
                val correct = a + b - c
                val questionText = "Selesaikan operasi matematika ini: $a + $b - $c"
                val options = generateOptions(correct, 100, 800, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Sempurna! $a ditambah $b adalh ${a+b}, jika dikurangi $c menjadi $correct."
                )
            }
            grade <= 9 -> {
                // Kelas 7-9 SMP: Linear algebra penambahan & pengurangan / Bilangan Bulat Negatif
                val a = rand.nextInt(-20, 10)
                val b = rand.nextInt(5, 30)
                val c = rand.nextInt(-15, 5)
                val correct = a + b - c
                val questionText = "Hitunglah nilai operasi bilangan bulat: ($a) + ($b) - ($c)"
                val options = generateOptions(correct, -50, 80, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Pintar! Diketahui ($a) + $b = ${a+b}. Lalu dikurangi ($c) berarti ditambah ${-c}, hasilnya menjadi $correct. 🧭"
                )
            }
            else -> {
                // Kelas 10-12 SMA: Barisan & Deret Aritmatika / Trigonometri penambahan-pengurangan
                val u1 = rand.nextInt(2, 8)
                val beda = rand.nextInt(3, 7)
                val n = rand.nextInt(4, 7)
                // Un = a + (n-1)b
                val un = u1 + (n - 1) * beda
                // Sn = n/2 (a + Un)
                val sum = n * (u1 + un) / 2
                
                val questionText = "Diketahui barisan aritmatika dengan suku pertama a = $u1 dan beda b = $beda. Tentukan jumlah $n suku pertama (S$n) dikurangi suku ke-$n (U$n)!"
                val correct = sum - un
                val options = generateOptions(correct, 10, 300, rand)
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correct.toString()),
                    explanation = "Luar biasa pintar! Suku ke-$n (U$n) = $u1 + ${(n-1)}×$beda = $un. Jumlah $n suku pertamanya adalah S$n = $sum. Maka S$n - U$n = $sum - $un = $correct. 🔮"
                )
            }
        }
    }

    private fun generateSoalCerita(grade: Int, index: Int, rand: Random): QuizQuestion {
        return when {
            grade <= 2 -> {
                // Kelas 1-2 SD: Soal cerita buah/mainan sederhana
                val stories = listOf(
                    Triple("Budi", "kelereng", "kotak"),
                    Triple("Siti", "boneka", "lemari"),
                    Triple("Kiki", "permen", "kantong")
                )
                val choice = stories.random(rand)
                val name = choice.first
                val item = choice.second
                
                val a = rand.nextInt(5, 12)
                val b = rand.nextInt(3, 8)
                val correct = a + b
                
                QuizQuestion(
                    question = "Kisah $name: $name memiliki $a $item di kamarnya. Kakak kemudian membelikan $name $b $item baru. Berapa total $item yang dimiliki $name sekarang?",
                    options = generateOptions(correct, 2, 25, rand),
                    answerIndex = generateOptions(correct, 2, 25, rand).indexOf(correct.toString()), // Generate consistently
                    explanation = "Yuhuu! Kita menjumlahkan jumlah mula-mula ($a) dengan benda baru ($b). Jadi $a + $b = $correct $item. 🎒"
                ).let {
                    // Reassemble options to guarantee answer is in there
                    val correctStr = correct.toString()
                    val opts = generateOptions(correct, 2, 25, rand)
                    it.copy(options = opts, answerIndex = opts.indexOf(correctStr))
                }
            }
            grade <= 4 -> {
                // Kelas 3-4 SD: Soal cerita beli buku / pensil, pengurangan & perkalian
                val count = rand.nextInt(3, 6)
                val price = rand.nextInt(4, 9) * 1000
                val pay = 50000
                val totalCost = count * price
                val change = pay - totalCost
                
                val name = listOf("Hana", "Doni", "Rian").random(rand)
                val questionText = "$name membeli $count buku tulis di koperasi. Harga satu buku tulis adalah Rp $price. Jika ia membayar dengan selembar uang Rp $pay, berapakah uang kembalian yang diterima $name?"
                val options = listOf("Rp $change", "Rp ${change - 5000}", "Rp ${change + 5000}", "Rp ${change + 12000}").shuffled(rand)
                
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf("Rp $change"),
                    explanation = "Pintar sekali! Total biaya belanja: $count × Rp $price = Rp $totalCost. Uang kembalian yang diterima: Rp $pay - Rp $totalCost = Rp $change. 💸"
                )
            }
            grade <= 6 -> {
                // Kelas 5-6 SD: Soal cerita kecepatan, jarak, waktu atau rata-rata
                val speed = rand.nextInt(4, 8) * 10 // km/jam: 40, 50, 60, 70, 80
                val time = rand.nextInt(2, 5) // jam: 2, 3, 4
                val distance = speed * time
                
                val questionText = "Sebuah bus melaju kencang dari kota A ke kota B dengan kecepatan rata-rata $speed km/jam. Jika bus tersebut memerlukan waktu $time jam untuk tiba di tujuan, tentukan jarak antara kedua kota tersebut!"
                val correctStr = "$distance km"
                val options = listOf("$distance km", "${distance - 30} km", "${distance + 40} km", "${distance + 15} km").shuffled(rand)
                
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correctStr),
                    explanation = "Sempurna! Rumus jarak adalah Kecepatan × Waktu. Jarak = $speed km/jam × $time jam = $distance km. Bus meluncur cepat! 🚌"
                )
            }
            grade <= 9 -> {
                // Kelas 7-9 SMP: Skala peta atau persentase untung/rugi
                val costPrice = rand.nextInt(10, 26) * 10000 // e.g., 150.000
                val profitPercent = listOf(10, 15, 20, 25).random(rand)
                val profitAmount = costPrice * profitPercent / 100
                val sellPrice = costPrice + profitAmount
                
                val questionText = "Seorang pedagang membeli sepeda seharga Rp $costPrice. Sepeda tersebut kemudian dijual kembali dengan memperoleh persentase keuntungan sebesar $profitPercent%. Berapakah harga jual sepeda tersebut?"
                val correctStr = "Rp $sellPrice"
                val options = listOf("Rp $sellPrice", "Rp ${sellPrice - 10000}", "Rp ${sellPrice + 15000}", "Rp ${costPrice + profitAmount/2}").shuffled(rand)
                
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correctStr),
                    explanation = "Luar biasa hebat! Besar keuntungan: $profitPercent% × Rp $costPrice = Rp $profitAmount. Maka harga jual sepedanya: Harga Beli + Untung = Rp $costPrice + Rp $profitAmount = Rp $sellPrice. 🚲"
                )
            }
            else -> {
                // Kelas 10-12 SMA: Bunga majemuk / Probabilitas
                val principal = 1000000
                val rate = 10 // 10%
                val years = 2
                val finalAmount = 1210000 // 1.000.000 * 1.1^2
                
                val questionText = "Pak Ahmad menyimpan uang tabungan sebesar Rp 1.000.000 di bank komersial dengan sistem bunga majemuk 10% per tahun. Berapakah jumlah total uang simpanan Pak Ahmad tersebut setelah lewat $years tahun?"
                val correctStr = "Rp 1.210.000"
                val options = listOf("Rp 1.210.000", "Rp 1.200.000", "Rp 1.100.000", "Rp 1.310.000").shuffled(rand)
                
                QuizQuestion(
                    question = questionText,
                    options = options,
                    answerIndex = options.indexOf(correctStr),
                    explanation = "Hebat! Tahun pertama: Rp 1.000.000 + 10% = Rp 1.100.000. Tahun kedua: Rp 1.100.000 + 10% = Rp 1.210.000. Rumus: M = P(1 + r)^t. 🏦"
                )
            }
        }
    }

    private fun generatePecahan(grade: Int, index: Int, rand: Random): QuizQuestion {
        val type = index % 5 // 1: Penambahan, 2: Pengurangan, 3: Perkalian, 4: Pembagian, 0: Penyederhanaan
        return when (type) {
            1 -> {
                // Penambahan Pecahan
                if (grade <= 3) {
                    val d = listOf(4, 5, 6, 8).random(rand)
                    val a = rand.nextInt(1, 3)
                    val b = rand.nextInt(1, 3)
                    val sum = a + b
                    val correctStr = "$sum/$d"
                    val options = listOf(correctStr, "${a+b+1}/$d", "${if (a-b > 0) a-b else a+1}/$d", "1/$d").distinct().shuffled(rand)
                    QuizQuestion(
                        question = "Berapakah hasil penjumlahan pecahan berikut:\n$a/$d + $b/$d?",
                        options = options,
                        answerIndex = options.indexOf(correctStr),
                        explanation = "Benar! Karena penyebutnya sama ($d), kita langsung jumlahkan pembilangnya: $a + $b = $sum. Hasilnya adalah $sum/$d! ✨"
                    )
                } else if (grade <= 6) {
                    // Different denominator addition
                    val choices = listOf(
                        Triple("1/2 + 1/4", "3/4", listOf("3/4", "2/6", "1/6", "2/4")),
                        Triple("1/2 + 1/3", "5/6", listOf("5/6", "2/5", "1/5", "3/6")),
                        Triple("1/3 + 1/4", "7/12", listOf("7/12", "2/7", "1/12", "5/12")),
                        Triple("2/3 + 1/4", "11/12", listOf("11/12", "3/7", "3/12", "7/12")),
                        Triple("1/2 + 1/5", "7/10", listOf("7/10", "2/7", "3/10", "1/10"))
                    ).random(rand)
                    val questionText = "Berapakah hasil penjumlahan pecahan berbeda penyebut berikut:\n${choices.first}?"
                    val options = choices.third.shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(choices.second),
                        explanation = "Luar biasa! Samakan penyebutnya terlebih dahulu menggunakan KPK. Sebagai contoh, 1/2 + 1/3 = 3/6 + 2/6 = 5/6. 🌱"
                    )
                } else if (grade <= 9) {
                    // Mixed/Negative addition
                    val choices = listOf(
                        Triple("1 1/2 + 2 1/4", "3 3/4", listOf("3 3/4", "3 1/4", "3 1/2", "4")),
                        Triple("2 1/3 + 1 1/6", "3 1/2", listOf("3 1/2", "3 2/9", "3 1/3", "3 2/3")),
                        Triple("-1/2 + 3/4", "1/4", listOf("1/4", "-1/4", "1/2", "-1/2")),
                        Triple("-1/3 + 4/3", "1", listOf("1", "5/3", "-5/3", "2/3"))
                    ).random(rand)
                    val questionText = "Hitunglah hasil operasi pecahan berikut:\n${choices.first}"
                    val options = choices.third.shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(choices.second),
                        explanation = "Hebat! Ubah pecahan campuran menjadi pecahan biasa dulu: Contoh, 1 1/2 + 2 1/4 = 3/2 + 9/4 = 6/4 + 9/4 = 15/4 = 3 3/4. 🚀"
                    )
                } else {
                    // SMA: Algebraic addition
                    val qText = "Sederhanakan hasil penjumlahan pecahan aljabar berikut:\n2/(x-1) + 3/(x+1)"
                    val correct = "(5x - 1) / (x² - 1)"
                    val options = listOf(correct, "(5x + 1) / (x² - 1)", "(5x - 1) / (x - 1)", "5 / (x² - 1)").shuffled(rand)
                    QuizQuestion(
                        question = qText,
                        options = options,
                        answerIndex = options.indexOf(correct),
                        explanation = "Cerdas sekali! Samakan penyebut menjadi (x-1)(x+1) = x²-1. Pembilangnya adalah 2(x+1) + 3(x-1) = 2x + 2 + 3x - 3 = 5x - 1. Jawabannya adalah $correct. 🧬"
                    )
                }
            }
            2 -> {
                // Pengurangan Pecahan
                if (grade <= 3) {
                    val d = listOf(4, 5, 6, 8).random(rand)
                    val a = rand.nextInt(3, 6)
                    val b = rand.nextInt(1, a)
                    val diff = a - b
                    val correctStr = "$diff/$d"
                    val options = listOf(correctStr, "${a+b}/$d", "1/$d", "${a+1}/$d").distinct().shuffled(rand)
                    QuizQuestion(
                        question = "Berapakah hasil pengurangan pecahan berikut:\n$a/$d - $b/$d?",
                        options = options,
                        answerIndex = options.indexOf(correctStr),
                        explanation = "Sempurna! Karena penyebutnya sama ($d), kita tinggal kurangkan pembilangnya: $a - $b = $diff. Hasilnya adalah $diff/$d! 🍕"
                    )
                } else if (grade <= 6) {
                    // Different denominator subtraction
                    val choices = listOf(
                        Triple("1/2 - 1/4", "1/4", listOf("1/4", "1/2", "3/4", "2/6")),
                        Triple("1/2 - 1/3", "1/6", listOf("1/6", "1/5", "2/5", "3/6")),
                        Triple("2/3 - 1/2", "1/6", listOf("1/6", "1/3", "1/5", "1/12")),
                        Triple("3/4 - 1/3", "5/12", listOf("5/12", "2/1", "1/12", "7/12")),
                        Triple("4/5 - 1/2", "3/10", listOf("3/10", "3/5", "1/10", "2/5"))
                    ).random(rand)
                    val questionText = "Berapakah hasil dari pengurangan pecahan berikut:\n${choices.first}?"
                    val options = choices.third.shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(choices.second),
                        explanation = "Pintar! Samakan penyebutnya menggunakan KPK terlebih dahulu. Contoh, 1/2 - 1/3 = 3/6 - 2/6 = 1/6. 🍰"
                    )
                } else if (grade <= 9) {
                    // Mixed/Negative subtraction
                    val choices = listOf(
                        Triple("3 1/2 - 1 1/4", "2 1/4", listOf("2 1/4", "2 1/2", "1 3/4", "2")),
                        Triple("2 1/3 - 1 1/2", "5/6", listOf("5/6", "1 1/6", "1/3", "2/3")),
                        Triple("-2/3 - 1/4", "-11/12", listOf("-11/12", "-5/12", "-3/7", "-11/6"))
                    ).random(rand)
                    val questionText = "Selesaikan hasil pengurangan pecahan berikut:\n${choices.first}"
                    val options = choices.third.shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(choices.second),
                        explanation = "Hebat! Contoh: 2 1/3 - 1 1/2 = 7/3 - 3/2 = 14/6 - 9/6 = 5/6. Sangat teliti! ✨"
                    )
                } else {
                    // SMA: Algebraic subtraction
                    val qText = "Sederhanakan hasil pengurangan pecahan aljabar berikut:\n3/(x-2) - 2/(x+1)"
                    val correct = "(x + 7) / (x² - x - 2)"
                    val options = listOf(correct, "(x - 1) / (x² - x - 2)", "(x + 7) / (x² + x - 2)", "1 / (x² - x - 2)").shuffled(rand)
                    QuizQuestion(
                        question = qText,
                        options = options,
                        answerIndex = options.indexOf(correct),
                        explanation = "Sangat pintar! Samakan penyebut menjadi (x-2)(x+1) = x²-x-2. Pembilang menjadi: 3(x+1) - 2(x-2) = 3x + 3 - 2x + 4 = x + 7. 🔮"
                    )
                }
            }
            3 -> {
                // Perkalian Pecahan
                if (grade <= 6) {
                    val choices = listOf(
                        Triple("1/2 × 1/3", "1/6", listOf("1/6", "2/5", "1/5", "2/6")),
                        Triple("2/3 × 1/4", "1/6", listOf("1/6", "2/7", "3/12", "3/7")),
                        Triple("3/4 × 2/5", "3/10", listOf("3/10", "5/9", "6/9", "6/20")),
                        Triple("4/5 × 2/3", "8/15", listOf("8/15", "6/8", "6/15", "8/10"))
                    ).random(rand)
                    val questionText = "Berapakah hasil perkalian pecahan berikut:\n${choices.first}?"
                    val options = choices.third.shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(choices.second),
                        explanation = "Benar! Untuk perkalian pecahan, kita langsung kalikan Pembilang × Pembilang, dan Penyebut × Penyebut. Contoh: 3/4 × 2/5 = 6/20, disederhanakan menjadi 3/10. 🍊"
                    )
                } else if (grade <= 9) {
                    // Mixed multiplication
                    val choices = listOf(
                        Triple("1 1/2 × 2/3", "1", listOf("1", "3/4", "1 1/3", "2/3")),
                        Triple("2 1/4 × 1 1/3", "3", listOf("3", "2 1/12", "4", "3 1/3")),
                        Triple("(-1/2) × 4/5", "-2/5", listOf("-2/5", "-1/5", "-1/10", "2/5"))
                    ).random(rand)
                    val questionText = "Berapakah hasil dari perkalian pecahan berikut:\n${choices.first}?"
                    val options = choices.third.shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(choices.second),
                        explanation = "Pintar! Ubah pecahan campuran ke pecahan biasa dulu. Contoh: 2 1/4 × 1 1/3 = 9/4 × 4/3 = 36/12 = 3. 🎯"
                    )
                } else {
                    // SMA: Algebraic mult
                    val qText = "Sederhanakan hasil perkalian aljabar pecahan berikut:\n(x - 2)/3 × 6/(x² - 4)"
                    val correct = "2 / (x + 2)"
                    val options = listOf(correct, "2 / (x - 2)", "1 / (x + 2)", "6 / (x² - 4)").shuffled(rand)
                    QuizQuestion(
                        question = qText,
                        options = options,
                        answerIndex = options.indexOf(correct),
                        explanation = "Luar biasa! Faktorkan x² - 4 menjadi (x-2)(x+2). Lalu coret (x-2). Persamaan menjadi: 1/3 × 6/(x+2) = 6 / (3(x+2)) = 2 / (x+2). 🧪"
                    )
                }
            }
            4 -> {
                // Pembagian Pecahan
                if (grade <= 6) {
                    val choices = listOf(
                        Triple("1/2 ÷ 1/4", "2", listOf("2", "1/8", "1/2", "4/2")),
                        Triple("1/3 ÷ 1/6", "2", listOf("2", "1/18", "1/2", "3")),
                        Triple("2/3 ÷ 1/2", "4/3", listOf("4/3", "1/3", "2/6", "3/4")),
                        Triple("3/4 ÷ 1/3", "9/4", listOf("9/4", "1/4", "3/12", "1")),
                        Triple("1/2 ÷ 2", "1/4", listOf("1/4", "1", "4", "2"))
                    ).random(rand)
                    val questionText = "Berapakah hasil dari pembagian pecahan berikut:\n${choices.first}?"
                    val options = choices.third.shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(choices.second),
                        explanation = "Mantap! Pembagian pecahan diselesaikan dengan membalik pecahan pembagi dan merubahnya menjadi perkalian: a/b ÷ c/d = a/b × d/c. Contoh: 1/2 ÷ 2 = 1/2 × 1/2 = 1/4. 🌟"
                    )
                } else if (grade <= 9) {
                    // Mixed division
                    val choices = listOf(
                        Triple("1 1/2 ÷ 3/4", "2", listOf("2", "9/8", "1 1/8", "3/4")),
                        Triple("2 1/4 ÷ 1 1/2", "3/2", listOf("3/2", "2/3", "27/8", "3")),
                        Triple("(-2/3) ÷ 1/2", "-4/3", listOf("-4/3", "-1/3", "-3/4", "4/3"))
                    ).random(rand)
                    val questionText = "Selesaikan hasil pembagian pecahan berikut:\n${choices.first}"
                    val options = choices.third.shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(choices.second),
                        explanation = "Hebat! Contoh: 1 1/2 ÷ 3/4 = 3/2 ÷ 3/4 = 3/2 × 4/3 = 12/6 = 2. Kamu pandai sekali! 🚀"
                    )
                } else {
                    // SMA: Algebraic div
                    val qText = "Sederhanakan pembagian aljabar pecahan berikut:\n(x² - 9)/(x + 2) ÷ (x - 3)/(2x + 4)"
                    val correct = "2x + 6"
                    val options = listOf(correct, "2x - 6", "x + 3", "2 / (x + 2)").shuffled(rand)
                    QuizQuestion(
                        question = qText,
                        options = options,
                        answerIndex = options.indexOf(correct),
                        explanation = "Sempurna! Faktorkan pembilang: (x²-9) = (x-3)(x+3), dan faktorkan penyebut pembagi: (2x+4) = 2(x+2). Tulis pembagian sebagai perkalian terbalik: [(x-3)(x+3)]/(x+2) × [2(x+2)]/(x-3) = 2(x+3) = 2x + 6. 🎓"
                    )
                }
            }
            else -> {
                // Penyederhanaan Pecahan
                if (grade <= 6) {
                    val choices = listOf(
                        Triple("4/8", "1/2", listOf("1/2", "2/3", "1/4", "3/4")),
                        Triple("6/9", "2/3", listOf("2/3", "1/3", "3/4", "1/2")),
                        Triple("8/12", "2/3", listOf("2/3", "3/4", "1/2", "4/6")),
                        Triple("12/16", "3/4", listOf("3/4", "2/3", "5/8", "1/2")),
                        Triple("15/20", "3/4", listOf("3/4", "3/5", "4/5", "2/3"))
                    ).random(rand)
                    val questionText = "Bentuk paling sederhana dari pecahan ${choices.first} adalah..."
                    val options = choices.third.shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(choices.second),
                        explanation = "Luar biasa! Bagi pembilang dan penyebut dengan FPB-nya. Contoh, FPB dari 8 da 12 adalah 4. Maka 8 ÷ 4 = 2 dan 12 ÷ 4 = 3, menghasilkan 2/3! ✨"
                    )
                } else if (grade <= 9) {
                    // Decimals or percent reductions
                    val choices = listOf(
                        Triple("0.375", "3/8", listOf("3/8", "1/3", "3/4", "2/5")),
                        Triple("85%", "17/20", listOf("17/20", "4/5", "18/25", "13/15")),
                        Triple("24/36", "2/3", listOf("2/3", "3/4", "1/2", "5/6")),
                        Triple("45/105", "3/7", listOf("3/7", "5/12", "9/21", "1/3"))
                    ).random(rand)
                    val questionText = "Sederhanakan nilai ${choices.first} menjadi bentuk pecahan biasa terkecil..."
                    val options = choices.third.shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf(choices.second),
                        explanation = "Wow, kamu cerdik! Mengubah desimal ke pecahan: 0.375 = 375 / 1000. Bagi pembilang dan penyebut dengan FPB-nya yaitu 125, didapatkan 3/8. Untuk 85% = 85/100 = 17/20. ⭐"
                    )
                } else {
                    // SMA: Rationalizing denominators with roots
                    val qText = "Sederhanakan bentuk rasional pecahan berikut dengan merasionalkan penyebutnya:\n6 / (√5 - √2)"
                    val correct = "2√5 + 2√2"
                    val options = listOf(correct, "2√5 - 2√2", "6√5 + 6√2", "3√5 + 3√2").shuffled(rand)
                    QuizQuestion(
                        question = qText,
                        options = options,
                        answerIndex = options.indexOf(correct),
                        explanation = "Sangat cerdas! Rasionalkan penyebut dengan mengalikannya dengan sekawan: 6/(√5-√2) × (√5+√2)/(√5+√2) = 6(√5+√2) / (5 - 2) = 6(√5+√2) / 3 = 2(√5+√2) = 2√5 + 2√2. 🪐"
                    )
                }
            }
        }
    }

    private fun generateGeometri(grade: Int, index: Int, rand: Random): QuizQuestion {
        if (grade <= 2) {
            // PAUD s.d SD Kelas 2: Pengenalan nama Segi (bangun datar) dan bangun ruang.
            val type = index % 6
            return when (type) {
                0 -> {
                    val questionText = "Aku adalah bangun datar yang memiliki 3 buah sisi dan 3 buah sudut. Siapakah aku? 🔺"
                    val options = listOf("Segitiga", "Persegi", "Lingkaran", "Trapesium").shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf("Segitiga"),
                        explanation = "Hebat! Sesuai namanya, Segi-Tiga adalah bangun datar yang dibatasi oleh tiga buah sisi dan memiliki tiga buah sudut! 🔺"
                    )
                }
                1 -> {
                    val questionText = "Bentuk roti mari, koin perak, roda sepeda, dan donat cokelat adalah contoh dari bangun datar... 🍩"
                    val options = listOf("Lingkaran", "Segitiga", "Persegi Panjang", "Segilima").shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf("Lingkaran"),
                        explanation = "Benar sekali! Benda bulat seperti donat, roda, dan koin tidak memiliki sudut dan berbentuk Lingkaran sempurna! 🔵"
                    )
                }
                2 -> {
                    val questionText = "Aku adalah bangun ruang. Semua enam sisiku berbentuk persegi sama besar, seperti dadu atau rubik permainanmu. Siapakah aku? 🎲"
                    val options = listOf("Kubus", "Balok", "Tabung", "Bola").shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf("Kubus"),
                        explanation = "Hebat! Kubus adalah bangun ruang tiga dimensi bersisi enam yang semuanya berbentuk bujur sangkar (persegi) sama besar, contohnya dadu! 🎲"
                    )
                }
                3 -> {
                    val questionText = "Sebuah kaleng susu kental manis, celengan koin kaleng, atau botol semprot memiliki bentuk bangun ruang yang disebut... 🥫"
                    val options = listOf("Tabung", "Kerucut", "Bola", "Kubus").shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf("Tabung"),
                        explanation = "Pintar! Bangun ruang yang berbentuk tabung memiliki alas dan tutup berbentuk lingkaran dengan selimut melengkung di sisinya. 🧴"
                    )
                }
                4 -> {
                    val questionText = "Papan tulis di kelas, buku gambar, dan layar televisi berbentuk bangun datar segi empat dengan dua pasang sisi berhadapan sama panjang, yaitu..."
                    val options = listOf("Persegi Panjang", "Segitiga", "Lingkaran", "Kubus").shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf("Persegi Panjang"),
                        explanation = "Benar! Persegi panjang merupakan bangun datar segi empat yang mempunyai panjang dan lebar tidak sama besar, namun sisi berhadapan sejajar dan sama panjang! 📺"
                    )
                }
                else -> {
                    val questionText = "Sebuah semangka bulat, buah jeruk manis, dan mainan kelereng memiliki bentuk bangun ruang... ⚽"
                    val options = listOf("Bola", "Kerucut", "Tabung", "Limas").shuffled(rand)
                    QuizQuestion(
                        question = questionText,
                        options = options,
                        answerIndex = options.indexOf("Bola"),
                        explanation = "Hebat! Semua benda bulat tiga dimensi tersebut berbentuk bangun ruang Bola karena permukaannya memiliki jarak yang sama ke titik pusat. ⚽"
                    )
                }
            }
        } else {
            // SD Kelas 3 s.d SMA Kelas 3 (Grade 3 - 12): menghitung rumus Luas, Keliling, Volume.
            // Makin tinggi grade, makin sulit tingkat kesulitan soalnya.
            val subType = index % 3 // 0: Keliling, 1: Luas, 2: Volume
            return when {
                grade <= 4 -> {
                    // Kelas 3-4 SD (Simple Area/Perimeter of rectangles/triangles, Simple Volume of cubes)
                    when (subType) {
                        0 -> {
                            val questionText = "Sebuah persegi panjang memiliki panjang 10 cm dan lebar 5 cm. Hitunglah kelilingnya!"
                            val options = listOf("30 cm", "15 cm", "50 cm", "25 cm").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("30 cm"),
                                explanation = "Keren! Rumus keliling persegi panjang adalah 2 × (panjang + lebar). Jadi, Keliling = 2 × (10 + 5) = 2 × 15 = 30 cm. ✨"
                            )
                        }
                        1 -> {
                            val questionText = "Udin menggambar sebuah persegi dengan panjang sisi 6 cm. Berapakah luas gambar persegi tersebut?"
                            val options = listOf("36 cm²", "24 cm²", "12 cm²", "18 cm²").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("36 cm²"),
                                explanation = "Benar! Luas persegi dihitung dari Sisi × Sisi. Luas = 6 cm × 6 cm = 36 cm². 🟩"
                            )
                        }
                        else -> {
                            val questionText = "Sebuah bak mandi berbentuk kubus dengan panjang rusuk bagian dalam 4 dm. Berapa liter volume air di dalam bak jika terisi penuh?"
                            val options = listOf("64 liter", "16 liter", "12 liter", "48 liter").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("64 liter"),
                                explanation = "Hebat! Rumus volume kubus adalah rusuk × rusuk × rusuk. Volume = 4 × 4 × 4 = 64 dm³ (1 dm³ = 1 liter). 💧"
                            )
                        }
                    }
                }
                grade <= 6 -> {
                    // Kelas 5-6 SD (Circles, Triangles, Cylinders with pi)
                    when (subType) {
                        0 -> {
                            val questionText = "Sebuah taman berbentuk lingkaran dengan jari-jari 7 m. Berapakah keliling taman tersebut? (Gunakan π = 22/7)"
                            val options = listOf("44 m", "22 m", "154 m", "88 m").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("44 m"),
                                explanation = "Pintar! Keliling lingkaran dihitung dengan rumus 2 × π × r. Keliling = 2 × 22/7 × 7 = 44 meter. 🌲"
                            )
                        }
                        1 -> {
                            val questionText = "Sebuah segitiga mempunyai panjang alas 12 cm dan tinggi 8 cm. Berapakah luas segitiga tersebut?"
                            val options = listOf("48 cm²", "96 cm²", "20 cm²", "40 cm²").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("48 cm²"),
                                explanation = "Tepat! Rumus luas segitiga adalah 1/2 × alas × tinggi. Luas = 1/2 × 12 × 8 = 48 cm². 🔺"
                            )
                        }
                        else -> {
                            val questionText = "Sebuah tabung silinder memiliki jari-jari lingkaran alas 7 cm dan tinggi tabung 10 cm. Berapakah volume tabung tersebut? (Gunakan π = 22/7)"
                            val options = listOf("1540 cm³", "154 cm³", "770 cm³", "2200 cm³").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("1540 cm³"),
                                explanation = "Luar biasa! Volume tabung dihitung dengan luas alas dikali tinggi: π × r² × t. Volume = 22/7 × 7 × 7 × 10 = 1540 cm³. 🥫"
                            )
                        }
                    }
                }
                grade <= 9 -> {
                    // Kelas 7-9 SMP (Trapesium, Jajar genjang, Limas, Pythagoras)
                    when (subType) {
                        0 -> {
                            val questionText = "Sebuah jajar genjang memiliki panjang sisi sejajar masing-masing 12 cm dan 8 cm. Berapa keliling jajar genjang tersebut?"
                            val options = listOf("40 cm", "20 cm", "96 cm", "80 cm").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("40 cm"),
                                explanation = "Mantap! Keliling jajar genjang dihitung dengan menjumlahkan seluruh keempat sisinya: 2 × (sisi a + sisi b) = 2 × (12 + 8) = 40 cm. 📐"
                            )
                        }
                        1 -> {
                            val questionText = "Sebuah trapesium memiliki panjang sisi sejajar a = 6 cm, b = 10 cm, dan tinggi t = 5 cm. Hitunglah luas trapesium tersebut!"
                            val options = listOf("40 cm²", "80 cm²", "30 cm²", "50 cm²").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("40 cm²"),
                                explanation = "Hebat! Rumus luas trapesium adalah 1/2 × (jumlah sisi sejajar) × tinggi. Luas = 1/2 × (6 + 10) × 5 = 1/2 × 16 × 5 = 8 cm × 5 = 40 cm². 📐"
                            )
                        }
                        else -> {
                            val questionText = "Sebuah wadah berbentuk limas dengan alas berbentuk persegi bersisi 6 cm. Jika tinggi limas adalah 10 cm, berapakah volume limas tersebut?"
                            val options = listOf("120 cm³", "360 cm³", "60 cm³", "180 cm³").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("120 cm³"),
                                explanation = "Sempurna! Rumus volume limas adalah 1/3 × luas alas × tinggi. Karena alas persegi dengan luas = 6 × 6 = 36 cm², maka Volume = 1/3 × 36 × 10 = 120 cm³. 📦"
                            )
                        }
                    }
                }
                else -> {
                    // Kelas 10-12 SMA (Coordinate geometry, Spheres volume from coordinates, Dimensi Tiga)
                    when (subType) {
                        0 -> {
                            val questionText = "Sebuah lingkaran digambarkan dengan persamaan x² + y² = 49. Berapakah keliling lingkaran tersebut? (Gunakan π = 22/7)"
                            val options = listOf("44 satuan", "154 satuan", "22 satuan", "88 satuan").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("44 satuan"),
                                explanation = "Cerdas! Persamaan lingkaran x² + y² = r² memiliki jari-jari r = √49 = 7. Keliling lingkaran tersebut adalah 2 × π × r = 2 × 22/7 × 7 = 44 satuan. 🪐"
                            )
                        }
                        1 -> {
                            val questionText = "Sebuah segitiga pada bidang kartesius dibatasi oleh koordinat titik A(0,0), B(6,0), dan C(3,5). Berapakah luas segitiga ABC tersebut?"
                            val options = listOf("15 satuan luas", "30 satuan luas", "18 satuan luas", "10 satuan luas").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("15 satuan luas"),
                                explanation = "Luar biasa! Panjang alas (sisi AB) adalah 6 satuan (dari x=0 ke x=6). Tinggi segitiga adalah 5 satuan (jarak vertikal ke koordinat Y titik C). Luas = 1/2 × alas × tinggi = 1/2 × 6 × 5 = 15 satuan luas. 📐"
                            )
                        }
                        else -> {
                            val questionText = "Sebuah bola basket berpusat sempurna di udara. Jika volume bola tersebut adalah 288π cm³, berapakah panjang jari-jarinya?"
                            val options = listOf("6 cm", "4 cm", "8 cm", "12 cm").shuffled(rand)
                            QuizQuestion(
                                question = questionText,
                                options = options,
                                answerIndex = options.indexOf("6 cm"),
                                explanation = "Hebat sekali! Rumus volume bola: V = (4/3) × π × r³. Maka 288π = (4/3) × π × r³ => r³ = 288 × (3/4) = 216. Jari-jari r = ∛216 = 6 cm! 🏀"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun generateOptions(correct: Int, min: Int, max: Int, rand: Random): List<String> {
        val optionsSet = mutableSetOf<String>()
        optionsSet.add(correct.toString())

        while (optionsSet.size < 4) {
            val offset = rand.nextInt(1, 10)
            val wrongNum = if (rand.nextBoolean()) correct + offset else correct - offset
            if (wrongNum != correct) {
                optionsSet.add(wrongNum.toString())
            }
        }

        return optionsSet.shuffled(rand)
    }
}
