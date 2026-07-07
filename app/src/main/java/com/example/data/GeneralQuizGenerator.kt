package com.example.data

import android.content.Context
import kotlin.random.Random

object GeneralQuizGenerator {

    // Detailed structured pool of 160 children-focused questions
    // Map of: SwappedCategory (string) -> Map of AgeIndex (0..3) -> List<QuizQuestion>
    private val flippedPool: Map<String, Map<Int, List<QuizQuestion>>> = mapOf(
        
        // --- 1. SEJARAH KUNO (MAPPED FROM MATEMATIKA INPUT) ---
        "sejarah" to mapOf(
            // Age 0: PAUD & TK (Umur 3-6)
            0 to listOf(
                QuizQuestion(
                    question = "Siapakah Presiden pertama Indonesia yang fotonya ada di kelas kita? 🇮🇩",
                    options = listOf("Bung Karno 😎", "Bung Hatta 🤝", "Pak Jokowi 🎒", "Pak Prabowo 🦁"),
                    answerIndex = 0,
                    explanation = "Bung Karno (Ir. Soekarno) adalah Presiden pertama sekaligus Proklamator Kemerdekaan Indonesia! 🇮🇩"
                ),
                QuizQuestion(
                    question = "Bendera negara kita Indonesia memiliki warna apa saja, adik-adik? 🇮🇩",
                    options = listOf("Kuning Biru 💛💙", "Merah Putih ❤️🤍", "Hijau Kuning 💚💛", "Hitam Putih 🖤🤍"),
                    answerIndex = 1,
                    explanation = "Warna bendera Indonesia adalah Merah di atas (artinya berani) dan Putih di bawah (artinya suci)! ❤️🤍"
                ),
                QuizQuestion(
                    question = "Candi Borobudur itu bangunan batu sangat besar di Jawa Tengah yang dibuat oleh... 🏛️",
                    options = listOf("Dibuat Robot 🤖", "Dibuat Manusia Zaman Dulu 🧱", "Jatuh dari Langit 🌌", "Dibuat Dinosaurus 🦖"),
                    answerIndex = 1,
                    explanation = "Candi Borobudur didirikan oleh orang-orang zaman dahulu bergotong-royong menyusun batu-batu sungai yang kokoh! 🧱"
                ),
                QuizQuestion(
                    question = "Burung gagah apa yang menjadi lambang negara Indonesia merdeka kita? 🦅",
                    options = listOf("Burung Merpati 🕊️", "Burung Garuda 🦅", "Burung Hantu 🦉", "Burung Kakaktua 🦜"),
                    answerIndex = 1,
                    explanation = "Burung Garuda dengan tameng di dadanya adalah lambang resmi persatuan Indonesia! Bhinneka Tunggal Ika! 🦅"
                ),
                QuizQuestion(
                    question = "Hari Kemerdekaan Indonesia kita dirayakan dengan lomba makan kerupuk setiap tanggal... 🎉",
                    options = listOf("1 Januari 🎆", "17 Agustus 🇮🇩🌟", "25 Desember 🎄", "21 April 🌸"),
                    answerIndex = 1,
                    explanation = "Tepat sekali! Setiap 17 Agustus kita merayakan hari lahirnya kemandirian bangsa Indonesia dengan upacara dan lomba seru! 🎉"
                )
            ),
            // Age 1: SD Kelas Rendah (Umur 7-9)
            1 to listOf(
                QuizQuestion(
                    question = "Siapakah tokoh hebat pendamping Ir. Soekarno sebagai Wakil Presiden pertama Indonesia? 🤝",
                    options = listOf("Drs. Mohammad Hatta 🤝", "Jenderal Soedirman ⚔️", "Ki Hajar Dewantara 🎓", "Gajah Mada 🏛️"),
                    answerIndex = 0,
                    explanation = "Bung Hatta mendampingi Bung Karno membacakan Proklamasi dan menjadi Wakil Presiden pertama Republik Indonesia! 🤝"
                ),
                QuizQuestion(
                    question = "Menjaga kehormatan bangsa, siapakah Ibu yang dahulu menjahit bendera pusaka Merah Putih pertama? 🧵",
                    options = listOf("Ibu RA Kartini 🌸", "Ibu Fatmawati Soekarno 🧵", "Ibu Cut Nyak Dhien ⚔️", "Ibu Megawati 🏛️"),
                    answerIndex = 1,
                    explanation = "Ibu Fatmawati mencurahkan penuh kasih sayang menjahit bendera merah putih yang dikibarkan saat proklamasi kemerdekaan! ❤️🤍"
                ),
                QuizQuestion(
                    question = "Lagu kebangsaan kita yang megah \"Indonesia Raya\" diciptakan oleh komponis pintar bernama... 🎵",
                    options = listOf("Ismail Marzuki 🎶", "W.R. Supratman 🎻", "Ibu Sud 🎹", "Koes Plus 🎸"),
                    answerIndex = 1,
                    explanation = "Wage Rudolf Supratman menciptakan lagu Indonesia Raya yang pertama kali diperdengarkan pada Sumpah Pemuda 1928! 🎻"
                ),
                QuizQuestion(
                    question = "Di kota manakah Bung Karno membacakan naskah Proklamasi Kemerdekaan 17 Agustus 1945? 🏙️",
                    options = listOf("Surabaya 🦁", "Jakarta (Pegangsaan Timur) 🏙️", "Bandung ⛰️", "Yogyakarta 🏰"),
                    answerIndex = 1,
                    explanation = "Bung Karno membacakan naskah Proklamasi di halaman rumahnya di Jalan Pegangsaan Timur No. 56, Jakarta! 🏙️"
                ),
                QuizQuestion(
                    question = "Patih terkenal dari Kerajaan Majapahit yang bersumpah menyatukan seluruh Nusantara dikenal dengan nama... 🏛️",
                    options = listOf("Hayam Wuruk 👑", "Gajah Mada ⚔️", "Raden Wijaya 🏹", "Ken Arok 🛡️"),
                    answerIndex = 1,
                    explanation = "Patih Gajah Mada terkenal dengan Sumpah Palapa, yaitu janji agung untuk mempersatukan wilayah-wilayah Nusantara di bawah Majapahit! ⚔️"
                )
            ),
            // Age 2: SD Kelas Tinggi (Umur 10-12)
            2 to listOf(
                QuizQuestion(
                    question = "Kerajaan bercorak Buddha terbesar di Indonesia yang pernah menjadi pusat belajar agama di Asia Tenggara adalah... 🏛️",
                    options = listOf("Kerajaan Tarumanegara 🏺", "Kerajaan Sriwijaya 🚢", "Kerajaan Singasari 🛡️", "Kerajaan Kutai 🪵"),
                    answerIndex = 1,
                    explanation = "Kerajaan Sriwijaya di Sumatera adalah kerajaan maritim Buddha raksasa yang menguasai jalur selat perdagangan dunia! 🚢"
                ),
                QuizQuestion(
                    question = "Peristiwa penyerbuan dan perobekan bendera Belanda di Hotel Yamato Surabaya didasarkan karena berkibarnya warna... 🇳🇱",
                    options = listOf("Merah Putih Biru 🇳🇱", "Merah Putih Hijau 🟩", "Merah Putih Kuning 🟨", "Kuning Hijau Merah 🪐"),
                    answerIndex = 0,
                    explanation = "Arek-arek Surabaya merobek bagian biru dari bendera Belanda (Merah-Putih-Biru) sehingga menyisakan bendera Merah Putih Indonesia! 🇮🇩"
                ),
                QuizQuestion(
                    question = "Siapakah pahlawan wanita legendaris dari Aceh yang memimpin perjuangan rakyat bersenjata melawan tentara Belanda? ⚔️",
                    options = listOf("RA Kartini 🌸", "Cut Nyak Dhien ⚔️", "Dewi Sartika 🎓", "Fatmawati 🧵"),
                    answerIndex = 1,
                    explanation = "Cut Nyak Dhien melanjutkan pimpinan perang suaminya melawan Belanda demi mempertahankan kedaulatan tanah rencong Aceh! ⚔️"
                ),
                QuizQuestion(
                    question = "Perang Diponegoro atau dikenal dengan sebutan Perang Jawa melawan kolonial Belanda meletus pada rentang tahun... 🕰️",
                    options = listOf("1908 - 1912 🗓️", "1825 - 1830 ⚔️", "1511 - 1515 ⛵", "1945 - 1949 🇮🇩"),
                    answerIndex = 1,
                    explanation = "Perang Jawa dipimpin oleh Pangeran Diponegoro menentang penindasan Belanda dari tahun 1825 hingga berakhir ditangkapnya beliau tahun 1830. 🛡️"
                ),
                QuizQuestion(
                    question = "Sumpah Pemuda yang mengikrarkan satu tanah air, satu bangsa, dan satu bahasa dideklarasikan resmi pada tanggal... 📜",
                    options = listOf("17 Agustus 1945 🇮🇩", "28 Oktober 1928 🧠🌍", "20 Mei 1908 🎓", "10 November 1945 🦁"),
                    answerIndex = 1,
                    explanation = "Sumpah Pemuda diadakan pada Kongres Pemuda II tanggal 28 Oktober 1928, menjadi tonggak dasar persatuan bangsa Indonesia! 👥"
                )
            ),
            // Age 3: SMP & Remaja (Umur 13+)
            3 to listOf(
                QuizQuestion(
                    question = "Konferensi Meja Bundar (KMB) yang menghasilkan pengakuan kedaulatan kedaulatan penuh Indonesia oleh Kerajaan Belanda diselenggarakan di... 🇳🇱",
                    options = listOf("Amsterdam 🏛️", "Den Haag 🇳🇱🏰", "Jakarta 🏙️", "Yogyakarta 🏰"),
                    answerIndex = 1,
                    explanation = "KMB dilaksanakan di Den Haag, Belanda pada akhir tahun 1949, menghasilkan pembubaran kendali kolonial di tanah air. 🤝"
                ),
                QuizQuestion(
                    question = "Perjanjian bersejarah antara Indonesia dan Belanda yang dilaksanakan di atas dek kapal perang milik Amerika Serikat adalah... ⚓",
                    options = listOf("Perjanjian Linggadjati 📝", "Perjanjian Renville ⚓⛴️", "Perjanjian Roem-Royen 🏛️", "Perjanjian Bongaya ⚔️"),
                    answerIndex = 1,
                    explanation = "Perjanjian Renville ditandatangani pada 17 Januari 1948 di atas kapal USS Renville demi menyelesaikan pertikaian wilayah kekuasaan. 🚢"
                ),
                QuizQuestion(
                    question = "Siapakah nama penjelajah berkebangsaan Eropa (Portugis) yang pertama kali merintis jalur armada laut luar hingga sukses tiba di Maluku tahun 1512? 🗺️",
                    options = listOf("Vasco da Gama ⛵", "Francisco Serrao 🧭🗺️", "Alfonso de Albuquerque ⚓", "Cornelis de Houtman 🇳🇱"),
                    answerIndex = 1,
                    explanation = "Francisco Serrao memprakarsai ekspedisi Portugis menuju kepulauan Banda dan Ternate, Maluku untuk menguasai perdagangan rempah-rempah yang melimpah! 🧂"
                ),
                QuizQuestion(
                    question = "Organisasi pergerakan nasional modern pertama di Indonesia yang didirikan oleh dr. Soetomo pada tanggal 20 Mei 1908 adalah... 🎓",
                    options = listOf("Sarekat Islam 🕋", "Budi Utomo 🎓🧠", "Indische Partij 🇮🇩", "Partai Nasional Indonesia ✊"),
                    answerIndex = 1,
                    explanation = "Budi Utomo didirikan oleh mahasiswa STOVIA tanggal 20 Mei 1908, kini hari pendiriannya diperingati sebagai Hari Kebangkitan Nasional! 🎓"
                ),
                QuizQuestion(
                    question = "Undang-Undang Dasar (UUD) 1945 pertama kali disahkan secara resmi sebagai konstitusi dasar Indonesia oleh lembaga PPKI pada tanggal... 📜",
                    options = listOf("17 Agustus 1945 🇮🇩", "18 Agustus 1945 📜🖋️", "22 Agustus 1945 🏛️", "1 Juni 1945 🌸"),
                    answerIndex = 1,
                    explanation = "UUD 1945 disahkan satu hari setelah proklamasi yakni pada 18 Agustus 1945 oleh Panitia Persiapan Kemerdekaan Indonesia (PPKI). ⚖️"
                )
            )
        ),

        // --- 2. MATEMATIKA (MAPPED FROM SEJARAH INPUT) ---
        "matematika" to mapOf(
            // Age 0: PAUD & TK
            0 to listOf(
                QuizQuestion(
                    question = "Jika adik mempunyai 2 buah apel manis, lalu Ibu memberikan 1 apel lagi, ada berapa apel adik sekarang? 🍎",
                    options = listOf("2 Apel 🍎🍎", "3 Apel 🍎🍎🍎", "4 Apel 🍎🍎🍎🍎", "1 Apel 🍎"),
                    answerIndex = 1,
                    explanation = "Gampang kan! Tambahkan saja: 2 + 1 = 3 apel! Nyamm lezat! 🍎"
                ),
                QuizQuestion(
                    question = "Bentuk roda sepeda mainan anak-anak yang bundar seperti kue donat dinamakan bentuk apa? 🛞",
                    options = listOf("Segitiga 🔺", "Lingkaran 🟡", "Kotak Persegi ⬛", "Bintang ⭐"),
                    answerIndex = 1,
                    explanation = "Roda sepeda dan donat cokelat berbentuk bundar melingkar sempurna, alias lingkaran! 🟡"
                ),
                QuizQuestion(
                    question = "Berapakah jumlah jari tangan di sebelah kanan adik jika direnggangkan semua? ✋",
                    options = listOf("3 Jari 👌", "4 Jari 🖖", "5 Jari ✋", "10 Jari 👐"),
                    answerIndex = 2,
                    explanation = "Ayo hitung bersama: kelingking, manis, tengah, telunjuk, jempol. Total ada 5 jari manis! ✋"
                ),
                QuizQuestion(
                    question = "Ayo berhitung maju! Setelah angka 7 (tujuh), angka tepat selanjutnya adalah... 🔢",
                    options = listOf("Angka 6 🔢", "Angka 8 🔢🌟", "Angka 9 🔢", "Angka 5 🔢"),
                    answerIndex = 1,
                    explanation = "Urutan angka yang benar adalah: ... 6, 7, 8, 9 ... Jadi sehabis tujuh adalah delapan! 🚀"
                ),
                QuizQuestion(
                    question = "Ibu membeli kotak kue berbentuk persegi. Berapakah jumlah sudut lancip atau pojokan tajam pada kotak kue itu? 📦",
                    options = listOf("3 Pojokan 🔺", "4 Pojokan ⬜🎁", "5 Pojokan ⭐", "6 Pojokan ❄️"),
                    answerIndex = 1,
                    explanation = "Persegi memiliki empat sisi tegak dan mendatar, menghasilkan tepat 4 pojokan atau sudut siku-siku! ⬜"
                )
            ),
            // Age 1: SD Kelas Rendah
            1 to listOf(
                QuizQuestion(
                    question = "Berapakah hasil perkalian seru dari angka 5 dikalikan 4? (5 x 4) 🦁",
                    options = listOf("15", "20 🦁✨", "25", "30"),
                    answerIndex = 1,
                    explanation = "Perkalian 5 sebanyak 4 kali: 5 + 5 + 5 + 5 = 20. Pintar perkalian! ✖️"
                ),
                QuizQuestion(
                    question = "Kakak membeli 15 butir telur, tapi sayangnya pecah 4 butir di jalan. Berapa telur Kakak yang utuh sekarang? 🥚",
                    options = listOf("9 Butir 🍳", "11 Butir 🥚🍽️", "12 Butir 🥚", "10 Butir 🍳"),
                    answerIndex = 1,
                    explanation = "Gunakan pengurangan sederhana: 15 butir dikurangi 4 butir pecah sisa 11 butir telur yang sehat! 🍳"
                ),
                QuizQuestion(
                    question = "Pak Tani membagikan 12 wortel segar ke dalam 3 keranjang kelinci sama banyak. Tiap keranjang berisi... 🐰",
                    options = listOf("3 Wortel 🥕", "4 Wortel 🥕🏎️", "5 Wortel 🥕", "6 Wortel 🥕"),
                    answerIndex = 1,
                    explanation = "Gunakan pembagian dasar: 12 dibagi 3 sama dengan 4 wortel di tiap wadah kelinci! 🐰"
                ),
                QuizQuestion(
                    question = "Manakah angka di bawah ini yang merupakan bilangan GANJIL (tidak habis dibagi 2)? 🔢",
                    options = listOf("10 🔢", "12 🔢", "13 🔢🦁", "16 🔢"),
                    answerIndex = 2,
                    explanation = "13 adalah bilangan ganjil karena jika dibagi 2 akan bersisa 1, berbeda dengan 10, 12, dan 16 yang genap! 🔢"
                ),
                QuizQuestion(
                    question = "Berapakah hasil penjumlahan ratusan dari 120 ditambah dengan 80? 🧮",
                    options = listOf("180", "190", "200 🧮🧠", "210"),
                    answerIndex = 2,
                    explanation = "Penjumlahan ratusan: 120 + 80 = 200 tepat tanpa sisa! Luar biasa cerdas! 🦄"
                )
            ),
            // Age 2: SD Kelas Tinggi
            2 to listOf(
                QuizQuestion(
                    question = "Berapakah hasil kuadrat sempurna dari bilangan matematika 12 dikalikan 12? (12²) 🧮",
                    options = listOf("124", "134", "144 🧮🎯", "154"),
                    answerIndex = 2,
                    explanation = "Perpangkatan dua: 12 dikalikan 12 adalah 144. Kuadrat yang sangat berguna! 🎯"
                ),
                QuizQuestion(
                    question = "Sebuah bentuk segitiga memiliki panjang alas 10 cm dan tinggi 8 cm. Berapakah luas segitiga itu? 📐",
                    options = listOf("80 cm²", "40 cm² 📐✨", "20 cm²", "18 cm²"),
                    answerIndex = 1,
                    explanation = "Rumus luas segitiga = (Alas x Tinggi) / 2 = (10 x 8) / 2 = 80 / 2 = 40 cm²! 📐"
                ),
                QuizQuestion(
                    question = "Berapakah FPB (Faktor Persekutuan Terbesar) dari bilangan angka 18 dan 24? 🧠",
                    options = listOf("3", "4", "6 🧠💥", "8"),
                    answerIndex = 2,
                    explanation = "Faktor 18 = 1,2,3,6,9,18. Faktor 24 = 1,2,3,4,6,8,12,24. Faktor persekutuan terbesar yang sama adalah 6! 🌟"
                ),
                QuizQuestion(
                    question = "Jika sebuah perjalanan bus melaju berkecepatan 60 km/jam selama 2,5 jam, berapakah jarak tempuh lengkapnya? 🚌",
                    options = listOf("120 km", "140 km", "150 km 🚌🗺️", "160 km"),
                    answerIndex = 2,
                    explanation = "Jarak = Kecepatan x Waktu = 60 km/jam x 2,5 jam = 150 km. Wow, perjalanan yang panjang! 🚌"
                ),
                QuizQuestion(
                    question = "Berapakah hasil pecahan dari penjumlahan 3/4 ditambah dengan pecahan 1/2? 🍕",
                    options = listOf("4/6", "1 1/4 (atau 5/4) 🍕🏎️", "1 1/2", "3/8"),
                    answerIndex = 1,
                    explanation = "Samakan penyebutnya terlebih dahulu: 3/4 + 2/4 = 5/4, disederhanakan menjadi pecahan campuran 1 1/4! 🍕"
                )
            ),
            // Age 3: SMP & Remaja
            3 to listOf(
                QuizQuestion(
                    question = "Berapakah nilai turunan d/dx dari ekspresi fungsi aljabar f(x) = 4x³ + 5x² - 3x + 7? 📐",
                    options = listOf("12x² + 5x - 3", "12x² + 10x - 3 📐🧮", "4x² + 10x - 3", "12x³ + 10x² - 3x"),
                    answerIndex = 1,
                    explanation = "Gunakan aturan pangkat kalkulus: f'(x) = 4*3x² + 5*2x - 3 = 12x² + 10x - 3! 🧠"
                ),
                QuizQuestion(
                    question = "Berapakah hasil aritmatika dari nilai trigonometri sin(30°) ditambah dengan cos(60°)? 📐",
                    options = listOf("0.5", "1.0 📐✨", "1.5", "2.0"),
                    answerIndex = 1,
                    explanation = "Trigonometri sudut istimewa: sin(30°) = 0,5 dan cos(60°) = 0,5. Ditambahkan: 0,5 + 0,5 = 1! 📐"
                ),
                QuizQuestion(
                    question = "Manakah nilai x yang memenuhi persamaan kuadrat aljabar x² - 5x + 6 = 0? 🎯",
                    options = listOf("x = 1 atau x = -6", "x = 2 atau x = 3 🎯🧠", "x = -2 atau x = -3", "x = 0 atau x = 5"),
                    answerIndex = 1,
                    explanation = "Faktorkan persamaan: (x - 2)(x - 3) = 0. Maka akar pembuat nolnya adalah x = 2 atau x = 3! 🎯"
                ),
                QuizQuestion(
                    question = "Sebuah wadah tabung memiliki jari-jari alas 7 cm dan tinggi 10 cm. Berapakah volume tabung itu (pi = 22/7)? 📦",
                    options = listOf("154 cm³", "770 cm³", "1540 cm³ 🛢️🏎️", "3080 cm³"),
                    answerIndex = 2,
                    explanation = "Volume Tabung = Luas Alas x Tinggi = pi * r² * t = (22/7) * 7 * 7 * 10 = 154 * 10 = 1540 cm³! 🛢️"
                ),
                QuizQuestion(
                    question = "Jika dua buah garis lurus di bidang kartesius saling tegak lurus, maka hasil perkalian rumus gradien m1 dan m2 adalah... 🔮",
                    options = listOf("Hasilnya Nol", "Hasilnya Satu", "Hasilnya Negatif Satu (-1) 🔮⚖️", "Hasilnya Dua"),
                    answerIndex = 2,
                    explanation = "Kondisi keperpendidikan dua buah garis menyatakan bahwa gradiennya berkebalikan negatif: m1 * m2 = -1! 🪐"
                )
            )
        ),

        // --- 3. LUAR ANGKASA / ASTRONOMI (MAPPED FROM INGGRIS INPUT) ---
        "astronomi" to mapOf(
            // Age 0: PAUD & TK
            0 to listOf(
                QuizQuestion(
                    question = "Benda bulat sangat besar di langit malam hari yang memancarkan cahaya putih temaram adalah... 🌕",
                    options = listOf("Lampu Senter 🔦", "Bulan Indah 🌕🐰", "Balon Gas 🎈", "Pesawat Terbang ✈️"),
                    answerIndex = 1,
                    explanation = "Bulan menemani petualangan malam kita dengan memantulkan cahaya matahari ke bumi yang indah! 🌕"
                ),
                QuizQuestion(
                    question = "Apakah benda raksasa super panas di langit siang hari yang membuat kita berkeringat gembira? ☀️",
                    options = listOf("Gula-Gula 🍭", "Matahari Ceria ☀️🦁", "Awan Mendung ☁️", "Pelangi Warna 🌈"),
                    answerIndex = 1,
                    explanation = "Matahari adalah bintang induk terdekat yang menghangatkan bumi di pagi dan siang hari! ☀️"
                ),
                QuizQuestion(
                    question = "Planet indah tempat tinggal kita, yang banyak pohon hijau, hewan laut, dan manusia bernama... 🌍",
                    options = listOf("Bumi Tercinta 🌍🌈", "Mars Merah 🔴", "Bulan Abu-abu 🌕", "Bintang Kejora ⭐"),
                    answerIndex = 0,
                    explanation = "Bumi adalah planet satu-satunya di tata surya tempat kita bisa tumbuh sehat menghirup udara bersih! 🌍"
                ),
                QuizQuestion(
                    question = "Benda-benda kuning kecil berkelip-kelip di langit malam yang mirip hiasan permata dinamakan... ⭐",
                    options = listOf("Kerikil Jalanan 🪨", "Bintang Gemerlap ⭐✨", "Burung Layang 🦜", "Layang-Layang 🪁"),
                    answerIndex = 1,
                    explanation = "Bintang letaknya sangat-sangat jauh dari bumi kita sehingga terlihat mungil berkelip indah! ⭐"
                ),
                QuizQuestion(
                    question = "Pakaian astronot berwarna putih tebal menyerupai robot digunakan astronot agar bisa... 🧑‍🚀",
                    options = listOf("Bisa Ikut Lari Pagi 🏃", "Bernapas Aman di Ruang Angkasa 👨‍🚀🚀", "Bisa Berenang Air Asin 🏊", "Bisa Tidur Nyenyak 💤"),
                    answerIndex = 1,
                    explanation = "Baju angkasa menyuplai oksigen bersih karena di luar angkasa tidak ada udara seperti di bumi! 🧑‍🚀"
                )
            ),
            // Age 1: SD Kelas Rendah
            1 to listOf(
                QuizQuestion(
                    question = "Planet yang sangat unik dan terkenal memiliki lingkaran cincin es raksasa di sekelilingnya adalah... 🪐",
                    options = listOf("Venus 🌟", "Mars 🔴", "Saturnus Indah 🪐✨", "Merkurius ☄️"),
                    answerIndex = 2,
                    explanation = "Saturnus memiliki sistem cincin debu dan es mengkilap berputar menakjubkan yang tampak sangat indah! 🪐"
                ),
                QuizQuestion(
                    question = "Planet keempat dari Matahari yang berbatu merah karat dan sering dijuluki 'Planet Merah' adalah... 🔴",
                    options = listOf("Venus 🌟", "Mars 🔴🐱", "Jupiter 🪐", "Neptunus 🔵"),
                    answerIndex = 1,
                    explanation = "Anak-anak, Mars berwarna kemerahan karena tanahnya kaya besi berkarat mirip pasir gurun merah! 🔴"
                ),
                QuizQuestion(
                    question = "Batu ruang angkasa yang meluncur masuk ke atmosfer Bumi dan terbakar menyala membentuk garis cahaya disebut... 💫",
                    options = listOf("Awan Panas ☁️", "Meteor / Bintang Jatuh 💫👾", "Komet Ekor ☄️", "UFO Asing 🛸"),
                    answerIndex = 1,
                    explanation = "Meteoroid yang bergesekan kencang dengan lapisan udara atmosfer bumi akan menyala terbakar gembira! ☄️"
                ),
                QuizQuestion(
                    question = "Kelompok bintang di langit malam yang dihubungkan garis khayal membentuk gambar unik singa atau kalajengking disebut... ✨",
                    options = listOf("Awan Kumulus ☁️", "Rasi Bintang (Konstelasi) ✨🏹", "Komet Berekor ☄️", "Galaksi Bima 🌌"),
                    answerIndex = 1,
                    explanation = "Rasi bintang seperti Orion (Pemburu) membantu nelayan zaman dahulu memandu arah kompas pelayaran malam! ✨"
                ),
                QuizQuestion(
                    question = "Alat pengintai berbentuk pipa kaca teropong panjang untuk melihat planet-planet di luar angkasa dinamakan... 🔭",
                    options = listOf("Kacamata 👓", "Teleskop (Teropong Bintang) 🔭🛰️", "Mikroskop 🔬", "Kompas Magnit 🧭"),
                    answerIndex = 1,
                    explanation = "Teleskop mengumpulkan sinar cahaya tipis dari bintang-bintang terjauh agar tampak besar dan jelas di mata kita! 🔭"
                )
            ),
            // Age 2: SD Kelas Tinggi
            2 to listOf(
                QuizQuestion(
                    question = "Gaya tarik raksasa dari benda apakah yang mengikat planet-planet agar tetap berputar tertib di lintasannya? 🌌",
                    options = listOf("Gaya Magnet Bumi 🧭", "Gravitasi Matahari ☀️🪐", "Angin Topan Angkasa 🌬️", "Mesin Jet Bumi 🚀"),
                    answerIndex = 1,
                    explanation = "Matahari bermassa超besar memiliki gaya gravitasi kuat yang membuat semua penghuni tata surya tertib memutari dirinya! ☀️"
                ),
                QuizQuestion(
                    question = "Planet terbesar di tata surya kita yang tersusun atas gas dan terkenal dengan badai merah pusaran legendaris adalah... 🪐",
                    options = listOf("Uranus 🔵", "Yupiter Raksasa 🪐🦁", "Saturnus 🪐", "Bumi 🌍"),
                    answerIndex = 1,
                    explanation = "Jupiter adalah sang raksasa tata surya yang diameternya 11 kali lebih lebar dari ukuran bumi kita! 🪐"
                ),
                QuizQuestion(
                    question = "Apakah nama galaksi spiral raksasa tempat tata surya, matahari, bumi, dan kehidupan kita berada saat ini? 🌌",
                    options = listOf("Galaksi Andromeda 🌌", "Galaksi Bima Sakti (Milky Way) 🌌✨", "Galaksi Sombrero 🪐", "Galaksi Awan Magellan ☁️"),
                    answerIndex = 1,
                    explanation = "Bima Sakti atau Milky Way menampung ratusan miliar bintang dan tata surya kita hanyalah satu titik kecil di tepinya! 🌌"
                ),
                QuizQuestion(
                    question = "Planet terkecil dan terdekat dari matahari yang tidak memiliki atmosfer pelindung panas adalah planet... ☄️",
                    options = listOf("Venus 🌟", "Merkurius ☄️🔍", "Mars 🔴", "Pluto ❄️"),
                    answerIndex = 1,
                    explanation = "Merkurius berputar dekat matahari, bersuhu ekstrem dingin mencekam di malam hari dan hangus membara di siang bolong! ☄️"
                ),
                QuizQuestion(
                    question = "Satelit logam buatan manusia pertama di dunia yang berhasil mengangkasa mengitari Bumi tahun 1957 diluncurkan oleh Uni Soviet bernama... 🛰️",
                    options = listOf("Apollo 11 🚀", "Sputnik 1 🛰️📡", "Palapa A1 🇮🇩", "Voyager 1 🧭"),
                    answerIndex = 1,
                    explanation = "Sputnik 1 berbentuk bola logam kecil dengan pemancar radio antariksa yang memulai sejarah perebutan teknologi luar angkasa! 🛰️"
                )
            ),
            // Age 3: SMP & Remaja
            3 to listOf(
                QuizQuestion(
                    question = "Apakah nama galaksi spiral raksasa tetangga terdekat dari Bima Sakti kita yang diperkirakan akan bertabrakan dalam beberapa miliar tahun mendatang? 🌌",
                    options = listOf("Sagitarius A 🪐", "Andromeda Galaxy 🌌🔬", "Triangulum Galaxy 💫", "Large Magellanic Cloud ☁️"),
                    answerIndex = 1,
                    explanation = "Galaksi Andromeda berjarak 2,5 juta tahun cahaya, bergerak mendekati galaksi Bima Sakti kita secara konstan! 🌌"
                ),
                QuizQuestion(
                    question = "Batas luar di mana kecepatan lepas (escape velocity) dari tarikan gravitasi lubang hitam (black hole) melebihi kecepatan cahaya dinamakan... 🕳️",
                    options = listOf("Singularitas Titik 📍", "Event Horizon (Cakrawala Peristiwa) 🕳️⛓️", "Accretion Disk 💿", "Hawking Boundary 🌌"),
                    answerIndex = 1,
                    explanation = "Event Horizon adalah titik tanpa kembali (point of no return), segala energi dan materi tak dapat lolos keluar melewatinya! 🕳️"
                ),
                QuizQuestion(
                    question = "Menurut rumusan Hukum Kepler yang ketiga, bahwa kuadrat periode revolusi sebuah planet sebanding lurus dengan... 🪐",
                    options = listOf("Sumbu minor elips", "Pangkat tiga dari sumbu semi-mayor orbitnya 📐🔍", "Kuadrat massa planet itu", "Kecepatan linear orbitnya"),
                    answerIndex = 1,
                    explanation = "Hukum Kepler III (Harmoni) menjabarkan relasi kuadrat waktu tempuh orbit (T²) proporsional dengan kubik jarak rata-rata ke matahari (R³)! 📐"
                ),
                QuizQuestion(
                    question = "Fenomena dramatis kematian bintang berukuran besar yang kehabisan elemen fusi nuklir lalu meledak hebat memancarkan radiasi masif disebut... 💥",
                    options = listOf("Nebula Kelahiran 🌌", "Supernova 💥💨", "Kerdil Putih ⚪", "Katai Cokelat 🟫"),
                    answerIndex = 1,
                    explanation = "Supernova menyemprotkan seluruh materi gas logam berat bintang mati ke alam semesta yang nantinya membentuk planet baru! 💥"
                ),
                QuizQuestion(
                    question = "Berapakah taksiran usia konkrit dari alam semesta kita sejak rentetan peristiwa ledakan besar (Big Bang) berhasil diukur teleskop modern? 🧠",
                    options = listOf("4,6 Miliar Tahun 🌍", "13,8 Miliar Tahun 🧠🔥", "100 Miliar Tahun 🪐", "2,5 Juta Tahun 💫"),
                    answerIndex = 1,
                    explanation = "Usia alam semesta dihitung presisi sekitar 13.8 Miliar tahun berdasarkan peta fluktuasi radiasi latar belakang kosmik (CMB) WMAP! 🧠"
                )
            )
        ),

        // --- 4. BAHASA INGGRIS (MAPPED FROM LUAR ANGKASA INPUT) ---
        "inggris" to mapOf(
            // Age 0: PAUD & TK
            0 to listOf(
                QuizQuestion(
                    question = "Adik-adik, apakah bahasa Inggris yang lucu dari hewan kucing berbulu yang mengeong \"Meong\"? 🐱",
                    options = listOf("Dog 🐶", "Cat 🐱✨", "Bird 🐦", "Fish 🐟"),
                    answerIndex = 1,
                    explanation = "Kucing menggemaskan dalam bahasa Inggris adalah CAT! Meowww! 🐱"
                ),
                QuizQuestion(
                    question = "Jika ibu mengenakan baju berwarna Merah menyala seindah apel, warna merah disebut apa dalam bahasa Inggris? ❤️",
                    options = listOf("Blue 💙", "Red ❤️🔥", "Green 💚", "Yellow 💛"),
                    answerIndex = 1,
                    explanation = "Warna merah merona seindah stroberi dan buah apel adalah RED! ❤️"
                ),
                QuizQuestion(
                    question = "Saat berpamitan dengan ibu guru dan teman di sekolah, lambaikan tangan ceria sambil bernyanyi... 👋",
                    options = listOf("Hello 💬", "Goodbye 👋🌸", "Good morning ☀️", "Thank you 🙏"),
                    answerIndex = 1,
                    explanation = "Mengatakan selamat tinggal atau dadah dalam bahasa Inggris adalah GOODBYE! Sampai jumpa lagi! 👋"
                ),
                QuizQuestion(
                    question = "Apakah bahasa Inggris dari buah manis berwarna kuning kesukaan monyet, yaitu buah Pisang? 🍌",
                    options = listOf("Apple 🍎", "Banana 🍌🐒", "Orange 🍊", "Grape 🍇"),
                    answerIndex = 1,
                    explanation = "Pisang kuning yang manis digemari monyet dalam bahasa Inggris disebut BANANA! 🍌"
                ),
                QuizQuestion(
                    question = "Ayo berhitung jari satu, dua, tiga dalam bahasa Inggris! Urutan kata yang merdu adalah... 🔢",
                    options = listOf("One, Two, Three 🔢🦄", "Four, Five, Six 🔢", "Red, Blue, Green 🎨", "Yes, No, Hello 🗣️"),
                    answerIndex = 0,
                    explanation = "Urutan angka 1, 2, 3 dalam bahasa Inggris adalah: One (satu), Two (dua), Three (tiga)! Hebat! 🔢"
                )
            ),
            // Age 1: SD Kelas Rendah
            1 to listOf(
                QuizQuestion(
                    question = "What is the English word for a large yellow wild animal with a golden mane that is called the 'King of Jungle'? 🦁",
                    options = listOf("Tiger 🐯", "Lion 🦁👑", "Elephant 🐘", "Monkey 🐒"),
                    answerIndex = 1,
                    explanation = "The King of the Jungle (Raja Hutan) is the Lion (Singa) with its powerful roar! 🦁"
                ),
                QuizQuestion(
                    question = "How do you say \"Terima kasih banyak atas pertolonganmu\" politely to someone in English? 🙏",
                    options = listOf("Nice to meet you 🤝", "Thank you very much 🙏✨", "Excuse me 🙋", "You are welcome 🤗"),
                    answerIndex = 1,
                    explanation = "Ucapkan 'Thank you very much' (Terima kasih banyak) untuk menghargai kebaikan hati orang lain! 🙏"
                ),
                QuizQuestion(
                    question = "Which day in the week comes right after Tuesday (Hari Selasa) in English? 📅",
                    options = listOf("Monday 📅", "Wednesday 📅💡", "Thursday 📅", "Friday 📅"),
                    answerIndex = 1,
                    explanation = "Urutan hari: Monday (Senin), Tuesday (Selasa), Wednesday (Rabu). Wednesday adalah hari Rabu! 📅"
                ),
                QuizQuestion(
                    question = "What is the English name for the pleasant season when colorful flowers bloom and everything turns green? 🌸",
                    options = listOf("Winter ❄️", "Spring 🌸🌱", "Summer ☀️", "Autumn 🍂"),
                    answerIndex = 1,
                    explanation = "Spring (Musim Semi) adalah waktu yang indah ketika kuncup bunga bermunculan mekar setelah musim dingin! 🌷"
                ),
                QuizQuestion(
                    question = "Listen and complete the sentence: \"The birds are flying high in the blue...\" 🐦",
                    options = listOf("Sea 🌊", "Sky ☁️✨", "Forest 🌲", "River 🏞️"),
                    answerIndex = 1,
                    explanation = "Burung terbang tinggi di langit (sky) yang membentang biru cerah! ☁️"
                )
            ),
            // Age 2: SD Kelas Tinggi
            2 to listOf(
                QuizQuestion(
                    question = "Choose the correct pronoun: \"Rudi is a very smart boy. ... always finishes his homework early.\" 🧑",
                    options = listOf("She 👩", "He 🧑💡", "It 🐶", "They 👥"),
                    answerIndex = 1,
                    explanation = "Karena Rudi adalah anak laki-laki tunggal (boy), pampatan kata ganti diri orang ketiga yang tepat adalah HE! 🧑"
                ),
                QuizQuestion(
                    question = "What is the correct simple past tense form of the action verb \"write\" (menulis)? ✏️",
                    options = listOf("Writed ✏️", "Wrote ✏️🧠", "Written ✏️", "Writing ✏️"),
                    answerIndex = 1,
                    explanation = "Kata kerja 'write' adalah irregular verb. Bentuk lampaunya (V2) adalah wrote, sedangkan V3 adalah written! ✏️"
                ),
                QuizQuestion(
                    question = "If you are very thirsty and want to drink mineral water, which container would you ask for? 🥤",
                    options = listOf("A box of tissues 🧻", "A bottle of water 🥤💧", "A cup of sugar 🍚", "A bag of coins 💰"),
                    answerIndex = 1,
                    explanation = "Untuk minum air mineral segar, tentu kita meminta sebotol air bersih (A bottle of water)! 🥤"
                ),
                QuizQuestion(
                    question = "What is the irregular plural form (kata benda jamak) of the singular noun \"child\" (anak)? 👥",
                    options = listOf("Childs 🧒", "Children 🧒🧒🌟", "Childrens 🧒🧒", "Childes 🧒"),
                    answerIndex = 1,
                    explanation = "Satu anak sebutannya 'child', sedangkan dua anak atau sekelompok anak jamak adalah 'children' tanpa tambahan huruf 's'! 🧒🧒"
                ),
                QuizQuestion(
                    question = "Read the choices and select the closest synonym of the adjective word \"HUGE\" (sangat besar): 🦖",
                    options = listOf("Tiny 🐜", "Giant / Enormous 🦖🗻", "Quiet 🤫", "Smart 🎓"),
                    answerIndex = 1,
                    explanation = "Kata 'huge' bermakna besar sekali. Sinonim atau persamaan kata yang cocok adalah Giant (raksasa) atau Enormous! 🦖"
                )
            ),
            // Age 3: SMP & Remaja
            3 to listOf(
                QuizQuestion(
                    question = "Which sentence correctly expresses a hypothetical advice or thought using the SUBJUNCTIVE mood? 🧠",
                    options = listOf(
                        "If I am you, I will buy the dress.",
                        "If I were you, I would accept the job 🧠🌟",
                        "If I was you, I bought the job.",
                        "If I be you, I can accept the job."
                    ),
                    answerIndex = 1,
                    explanation = "Kalimat pengandaian subjungtif tidak nyata menggunakan auxiliary beda 'were' untuk semua subjek subyektif (If I were you, I would...)! 🧠"
                ),
                QuizQuestion(
                    question = "In advanced English language syntax, what is the precise grammatical definition of a \"Gerund\"? 📝",
                    options = listOf(
                        "A verb that behaves like an adjective",
                        "A verb ending in -ing that functions as a noun 📝🔍",
                        "An auxiliary verb indicating probability",
                        "An adverb clause stating condition"
                    ),
                    answerIndex = 1,
                    explanation = "Gerund adalah kata kerja bersufiks '-ing' yang beralih peran semantik menduduki posisi subjek/objek kata benda (noun), seperti 'Swimming' in 'Swimming is nice'! 📝"
                ),
                QuizQuestion(
                    question = "Identify the correct passive voice construction of: \"The master chef prepared a delicious dinner.\" 🍳",
                    options = listOf(
                        "The delicious dinner has prepared by the chef.",
                        "A delicious dinner was prepared by the chef 🍳🍽️",
                        "The chef was preparing the delicious dinner.",
                        "A delicious dinner prepare by the chef."
                    ),
                    answerIndex = 1,
                    explanation = "Kalimat aktif pasif lampau (Past Tense): Objek didepan + was/were + past participle (V3) = 'A delicious dinner was prepared by...'! 🍽️"
                ),
                QuizQuestion(
                    question = "Choose the appropriate English idiom that means \"to face a difficult and painful situation with determination and courage\": 🎯",
                    options = listOf(
                        "Break a leg 🎭",
                        "Bite the bullet 🎯🌋",
                        "Cry over spilled milk 🥛",
                        "Under the weather 🌧️"
                    ),
                    answerIndex = 1,
                    explanation = "Idiom 'Bite the bullet' (menggigit peluru) mendeskripsikan kegigihan mental luar biasa dalam mengangkut tantangan berat kelam! 💣"
                ),
                QuizQuestion(
                    question = "What is the exact semantic meaning of the scholarly English adjective \"EPHEMERAL\"? ⏳",
                    options = listOf(
                        "Always lasting forever ♾️",
                        "Lasting for a very short, transient time ⏳✨",
                        "Extremely expensive 💎",
                        "Full of joy and energy ⚡"
                    ),
                    answerIndex = 1,
                    explanation = "Adjektiva 'ephemeral' menggambarkan kefanaan atau sesuatu hal yang berlangsung sekejap mata/sementara (transient/short-lived)! ⏳"
                )
            )
        ),

        // --- 5. SENI & MUSIK (MAPPED FROM SAINS & ALAM INPUT) ---
        "seni" to mapOf(
            // Age 0: PAUD & TK
            0 to listOf(
                QuizQuestion(
                    question = "Alat musik gitar kayu yang dimainkan Kakak harus diapakan agar berbunyi neng-neng merdu? 🎸",
                    options = listOf("Dipukul Keras 🔨", "Dipetik Dawainya 🎸🦁", "Ditiup Mulut 🌬️", "Digoyang-goyang 🪇"),
                    answerIndex = 1,
                    explanation = "Gitar menghasilkan melodi indah saat jari kita memetik benang-benang senarnya yang kencang! 🎸"
                ),
                QuizQuestion(
                    question = "Alat berbulu halus bertangkai kayu untuk melukis cat warna-warni di kertas gambar dinamakan... 🖌️",
                    options = listOf("Kuas Lukis 🖌️✨", "Sendok Makan 🥄", "Spidol Item 🖊️", "Penghapus Karet 🧼"),
                    answerIndex = 0,
                    explanation = "Kuas lukis mengalirkan warna cair basah ke kanvas menggambar dengan lentur puitis! 🖌️"
                ),
                QuizQuestion(
                    question = "Alat musik bambu tradisional Jawa Barat yang digetarkan gemercik indah berdua-dua dinamakan... 🪵",
                    options = listOf("Gendang Kulit 🥁", "Angklung Bambu 🪘🦕", "Suling Bambu 🌬️", "Piano Besar 🎹"),
                    answerIndex = 1,
                    explanation = "Angklung adalah warisan budaya gotong royong terbuat dari pipa-pipa bambu bernada harmoni! 🪵"
                ),
                QuizQuestion(
                    question = "Warna ceria apa yang adik peroleh jika mencampurkan warna Merah menyala dan Kuning pisang? 🎨",
                    options = listOf("Warna Hijau Daun 🟢", "Warna Oranye Jingga 🍊🦁", "Warna Ungu Terong 🟣", "Warna Hitam Gelap ⬛"),
                    answerIndex = 1,
                    explanation = "Merah dicampur Kuning melahirkan warna Oranye hangat seperti buah jeruk matang! 🍊"
                ),
                QuizQuestion(
                    question = "Kalau adik mendengarkan lantunan lagu anak-anak gembira, perasaan adik menjadi apa? 😊",
                    options = listOf("Marah-marah 😡", "Senang dan Ceria 😊🦄", "Takut Gelap 😨", "Mengantuk Bobok 💤"),
                    answerIndex = 1,
                    explanation = "Musik ceria merangsang hati kita tersenyum lebar berseri gembira menyambut hari! 😊"
                )
            ),
            // Age 1: SD Kelas Rendah
            1 to listOf(
                QuizQuestion(
                    question = "Lagu penuh semangat perjuangan \"Hari Merdeka\" yang mengalun tiap upacara kemerdekaan diciptakan oleh... 🎵",
                    options = listOf("Ibu Sud 🎹", "H. Mutahar 🎵🏅", "Bung Karno 🇮🇩", "Pak Kasur 🎸"),
                    answerIndex = 1,
                    explanation = "H. Mutahar adalah komposer luhur yang merajut kemeriahan hymne Hari Merdeka 17 Agustus 1945! 🏅"
                ),
                QuizQuestion(
                    question = "Seni patung batu dikerjakan dengan memahat, sedangkan gambar warna di kertas datar dibuat dengan... 🎨",
                    options = listOf("Merajut Benang 🧶", "Menggambar / Melukis 🎨🖌️", "Mencuci Baju 🧼", "Melipat Kertas 📄"),
                    answerIndex = 1,
                    explanation = "Melukis atau menggambar menuangkan imajinasi dua dimensi menggunakan cat warna cerah! 🎨"
                ),
                QuizQuestion(
                    question = "Alat musik tiup plastik seruling putih panjang yang wajib dipelajari di kelas sekolah adalah... 🌬️",
                    options = listOf("Gitar Akustik 🎸", "Rekorder / Seruling Tiup 🌬️🎺", "Drum Perkusi 🥁", "Pianika 🎹"),
                    answerIndex = 1,
                    explanation = "Rekorder adalah suling modern tiup yang melatih pernapasan diafragma dan ketukan jari musik anak! 🌬️"
                ),
                QuizQuestion(
                    question = "Warna primer rahimnya seluruh warna seni rupa terdiri atas tiga lingkaran utama yaitu... 🎨",
                    options = listOf("Merah, Hijau, Kuning 🎨", "Merah, Kuning, Biru 🎨✨", "Hitam, Putih, Abu-abu 🖤", "Ungu, Oranye, Pink 🌸"),
                    answerIndex = 1,
                    explanation = "Warna primer (Merah, Kuning, Biru) tak bisa dibuat dari pencampuran warna lain, tapi bisa melahirkan jutaan warna baru! 🎨"
                ),
                QuizQuestion(
                    question = "Alat musik piano besar dimainkan di panggung dengan menekan deretan tombol kayu berwarna hitam putih yang dinamakan... 🎹",
                    options = listOf("Palu Tukul 🔥", "Tuts Piano 🎹💡", "Senar Kawat 🎻", "Pedal Kaki 🦶"),
                    answerIndex = 1,
                    explanation = "Tuts (keys) berpelat warna hitam putih dipencet menggerakkan palu mekanik dalam resonansi senar dalam piano! 🎹"
                )
            ),
            // Age 2: SD Kelas Tinggi
            2 to listOf(
                QuizQuestion(
                    question = "Tangga nada musik barat yang berurutan susunan interval jarak nadanya: 1 - 1 - 1/2 - 1 - 1 - 1 - 1/2 dinamakan... 🎵",
                    options = listOf("Tangga Nada Pentatonis Pelog 🎋", "Tangga Nada Diatonis Mayor 🎵🎻", "Tangga Nada Diatonis Minor 🎹", "Tangga Nada Slendro 🏮"),
                    answerIndex = 1,
                    explanation = "Susunan jarak nada tersebut adalah ciri khas Diatonis Mayor yang bersifat gembira, tegas, dan optimis! 🎵"
                ),
                QuizQuestion(
                    question = "Teknik seni lukis menggunakan ribuan susunan kerapatan titik-titik kecil warna hingga membangun gelap terang bayangan dinamakan... 🖊️",
                    options = listOf("Arsir Garis ✏️", "Pointilis 🎨🖌️", "Sfumato Kabut 🌫️", "Plakat Basah 🖼️"),
                    answerIndex = 1,
                    explanation = "Pointilis mengandalkan ilusi optik mata yang mencampur titik-titik warna terisolasi menjadi satu paduan kesatuan objek lukisan! 🖌️"
                ),
                QuizQuestion(
                    question = "Alat musik tradisional logam gamelan berbentuk bundar kubah menonjol di tengah yang dipukul berlapis karet bernama... 🥁",
                    options = listOf("Suling 🌬️", "Bonang atau Gong 🥁🏅", "Kendang Kulit 🪘", "Ambiensi Rebab 🎻"),
                    answerIndex = 1,
                    explanation = "Gong dan Bonang adalah instrumen gamelan berbahan kuningan tempa tebal dengan tonjolan pencu di kepalanya! 🥁"
                ),
                QuizQuestion(
                    question = "Seni melipat kertas tipis origami yang sangat mendunia hingga melatih motorik anak berasal dari negara... 🗾",
                    options = listOf("Korea Selatan 🇰🇷", "Jepang 🗾🌸", "Cina 🇨🇳", "Thailand 🇹🇭"),
                    answerIndex = 1,
                    explanation = "Origami (Ori = Lipat, Kami = Kertas) dikembangkan turun temurun di Jepang melambangkan harapan panjang umur peradaban! 🗻"
                ),
                QuizQuestion(
                    question = "Kombinasi dua warna yang posisinya saling berhadapan lurus langsung di dalam roda spektrum lingkaran warna dinamakan... 🎨",
                    options = listOf("Warna Monokromatik 🖤", "Warna Komplementer 🎨🔍", "Warna Analog 💛", "Warna Tersier 💖"),
                    answerIndex = 1,
                    explanation = "Warna komplementer (seperti Merah-Hijau, Biru-Oranye) menghasilkan kontras tinggi yang dinamis dan berenergi saat dijejerkan! 🎨"
                )
            ),
            // Age 3: SMP & Remaja
            3 to listOf(
                QuizQuestion(
                    question = "Siapakah pelukis ekspresionis legendaris Indonesia yang menggunakan jari telapak tangannya langsung mengusir cat minyak pada kanvas kasar potret diri? 🎨",
                    options = listOf("Raden Saleh 🏛️", "Affandi Koesoema 🎨📸", "Basoeki Abdullah 🖼️", "Dullah 👨‍🎨"),
                    answerIndex = 1,
                    explanation = "Affandi melahirkan lukisan agung ekspresionism di kancah internasional dengan mencoretkan langsung pasta cat minyak dari tubenya ke atas kain! 🎨"
                ),
                QuizQuestion(
                    question = "Bagaimanakah pengaturan birama lagu atau ketukan waktu resmi pada lagu kebangsaan agung \"Indonesia Raya\"? 🎼",
                    options = listOf("Ketukan 2/4 🥁", "Ketukan 4/4 🎼🥇", "Ketukan 3/4 🎵", "Ketukan 6/8 🎶"),
                    answerIndex = 1,
                    explanation = "Lagu Indonesia Raya dinyanyikan khidmat berskala birama 4/4 dengan kecepatan berparade marcial (Con Bravura)! 🎼"
                ),
                QuizQuestion(
                    question = "Teknik menempelkan rincian pecahan ubin keramik, marmer, atau butiran kaca warna di bidang semen perekat arsitektur disebut... 🧱",
                    options = listOf("Mosaik 🧱🎨", "Fresko Melukis 🖼️", "Sketsa Arsir ✏️", "Grafiti Tembok 🎨"),
                    answerIndex = 0,
                    explanation = "Seni mosaik sangat populer sejak era Kekaisaran Romawi kuno untuk mendekor kubah gereja dan lantai istana megah! 🧱"
                ),
                QuizQuestion(
                    question = "Rentang register jangkauan vokal tersuara paling tinggi bagi kelompok penyanyi wanita dewasa di paduan koor melodi dinamakan... 🗣️",
                    options = listOf("Mezzo-Sopran 🗣️", "Sopran 🗣️🌟", "Alto 🗣️", "Tenor 🗣️"),
                    answerIndex = 1,
                    explanation = "Sopran adalah register suara termulung tinggi utama wanita, sedangkan rentang suara pria tertinggi dinamakan Tenor! 🗣️"
                ),
                QuizQuestion(
                    question = "Kamus penulisan musik barat yang mendiktekan tempo lagu berjalan santai, lambat, agung penuh kesayangan berbunyi... ⏱️",
                    options = listOf("Allegro (Cepat) ⚡", "Adagio (Lambat Mulia) ⏱️🏛️", "Presto (Sangat Cepat) 🚀", "Moderato (Sedang) 🗓️"),
                    answerIndex = 1,
                    explanation = "Adagio memandu musisi mengayun instrumen dengan perlahan, tenang, ekspresif, mendalam (sekitar 56-65 ketuk per menit)! ⏱️"
                )
            )
        ),

        // --- 6. SAINS & ALAM (MAPPED FROM SENI & MUSIK INPUT) ---
        "sains" to mapOf(
            // Age 0: PAUD & TK
            0 to listOf(
                QuizQuestion(
                    question = "Bagian tanaman yang warnanya hijau segar, tumbuh di ranting pohon dan suka menghirup sinar matahari adalah... 🍃",
                    options = listOf("Akar Tanah 🪵", "Daun Hijau 🍃🦄", "Batang Kayu 🪵", "Batu Tanah 🪨"),
                    answerIndex = 1,
                    explanation = "Daun memiliki warna hijau karena memiliki zat klorofil pembantu memasak makanan pohon dari matahari! 🌱"
                ),
                QuizQuestion(
                    question = "Hewan kelinci imut bertelinga panjang tegak melompat ke sana kemari menyukai makanan... 🥕",
                    options = listOf("Ikan Asin 🐟", "Wortel Orange 🥕🐰", "Daging Ayam 🍗", "Nasi Goreng 🍚"),
                    answerIndex = 1,
                    explanation = "Kelinci adalah herbivora penyuka ubi-ubian manis mentah, terutama wortel segar kaya vitamin A! 🥕"
                ),
                QuizQuestion(
                    question = "Air putih bening yang kamu letakkan di freezer kulkas yang amat sangat dingin membeku menjadi... 🧊",
                    options = listOf("Uap Panas 🌬️", "Es Batu Dingin 🧊🦖", "Air Susu 🥛", "Minyak Kayu 🍃"),
                    answerIndex = 1,
                    explanation = "Suhu dingin ekstrem mengubah air cair menjadi padat mengeras berwujud es batu bening yang segar! 🧊"
                ),
                QuizQuestion(
                    question = "Hewan ternak gemuk berkaki empat penyuka rumput segar yang mengeluarkan air susu sehat bersuara \"Moo\" adalah... 🐄",
                    options = listOf("Ayam Bulu 🐓", "Sapi Perah 🐄🥛", "Kambing Jenggot 🐐", "Bebek Lumpur 🦆"),
                    answerIndex = 1,
                    explanation = "Sapi perah berjasa meremajakan badan anak lewat air susu murni kaya kalsium tinggi pertumbuhan! 🥛"
                ),
                QuizQuestion(
                    question = "Di siang hari, taman luar bermain anak terlihat terang menyala ceria karena disinari oleh... ☀️",
                    options = listOf("Lampu Senter 🔦", "Matahari Cerdas ☀️🦁", "Bulan Temaram 🌕", "Awan Putih ☁️"),
                    answerIndex = 1,
                    explanation = "Matahari menyemburkan sinar ultraviolet yang menerangi dan memberi Vitamin D alami bagi kulit kita! ☀️"
                )
            ),
            // Age 1: SD Kelas Rendah
            1 to listOf(
                QuizQuestion(
                    question = "Proses ulat membalikkan badannya dibungkus dalam kepompong tidur lalu menetas memiliki sayap bercorak cantik dinamakan... 🦋",
                    options = listOf("Pemberdayaan Hewan 🦁", "Metamorfosis Sempurna 🦋✨", "Fotosintesa 🍃", "Evolusi Air 💧"),
                    answerIndex = 1,
                    explanation = "Metamorfosis kupu-kupu bermula dari telur -> larva (ulat) -> pupa (kepompong) -> kupu-kupu cantik terbang bebas! 🦋"
                ),
                QuizQuestion(
                    question = "Mengapa bagian rambut akar tanaman harus menjalar bercabang-cabang masuk jauh di dalam tanah gembur? 🌱",
                    options = listOf("Biar Pohon Bisa Jalan-jalan 🚶", "Menyerap Air dan Unsur Hara Tanah 🌱🔋", "Menghindari Cacing Tanah 🪱", "Membantu Menangkap Angin 🌬️"),
                    answerIndex = 1,
                    explanation = "Akar adalah jangkar hisap yang menyerap air mineral dari bawah ketebalan tanah menuju puncak dedaunan! 🌱"
                ),
                QuizQuestion(
                    question = "Benda padat berkuasa menarik paku, jarum, dan logam peniti besi di hadapannya dinamakan... 🧭",
                    options = listOf("Batu Kali 🪨", "Batu Magnet ⚡🧲", "Kayu Kering 🪵", "Karet Gelang 🧼"),
                    answerIndex = 1,
                    explanation = "Magnet memiliki kutub utara dan selatan yang dikelilingi oleh medan gaya magnet pemikat unsur besi! 🧲"
                ),
                QuizQuestion(
                    question = "Jika minyak makan dan air jernih dicampurkan dalam satu wadah gelas, mengapa cairan minyak berkumpul mengapung di atas? 🧪",
                    options = listOf("Minyak Lebih Manis 🍦", "Massa Jenis Minyak Lebih Kecil dari Air 🧪💧", "Minyak Takut Air 😨", "Air Lebih Lengket 💧"),
                    answerIndex = 1,
                    explanation = "Hukum fisika kerapatan zat: zat bermassa jenis lebih rendah akan selalu terapung berenang di atas zat berkerapatan tinggi! 🧪"
                ),
                QuizQuestion(
                    question = "Gas segar di sekeliling kita yang dihirup hidung manusia masuk menyehatkan paru-paru bersih saat bernapas adalah... 🌬️",
                    options = listOf("Gas Karbondioksida 🛢️", "Gas Oksigen (O2) 🌬️☘️", "Gas Nitrogen 🧬", "Uap Belerang 🌋"),
                    answerIndex = 1,
                    explanation = "Oksigen dihasilkan melimpah oleh tumbuhan saat proses fotosintesis siang hari untuk kehidupan makhluk bumi! 🌬️"
                )
            ),
            // Age 2: SD Kelas Tinggi
            2 to listOf(
                QuizQuestion(
                    question = "Zat pewarna hijau daun pada kantung sel tumbuhan pengolah fotosintesis yang menangkap gelombang energi tampak matahari disebut... 🌿",
                    options = listOf("Karbohidrat 🌾", "Klorofil 🌿✨", "Kromosom 🧬", "Stomata 🕳️"),
                    answerIndex = 1,
                    explanation = "Klorofil adalah pigmen penting penyerap foton cahaya merah dan biru matahari guna diolah menjadi glukosa manis daun! 🌱"
                ),
                QuizQuestion(
                    question = "Keping darah mikro pembantu sistem sirkulasi yang bertugas utama mengering membeku menambal kulit tergores luka luar disebut... 🩹",
                    options = listOf("Sel Darah Putih (Leukosit) ⚪", "Trombosit (Keping Darah) 🩹🩸", "Sel Darah Merah (Eritrosit) 🔴", "Plasma Lemak 🧪"),
                    answerIndex = 1,
                    explanation = "Trombosit langsung menumpuk lengket membentuk anyaman filamen fibrinogen penahan pendarahan saat kapiler kulit pecah! 🩹"
                ),
                QuizQuestion(
                    question = "Gaya tarik massa raksasa bumi kita yang menahan tubuh kita berdiri dan membuat buah jatuh lurus ke bawah adalah... 🌎",
                    options = listOf("Gaya Pegas Melar ⛓️", "Gaya Gravitasi Bumi 🌎💥", "Gaya Gesek Udara 🌬", "Gaya Dorong Tektonik 🌋"),
                    answerIndex = 1,
                    explanation = "Gaya gravitasi bumi dicetuskan pertama kali oleh Sir Isaac Newton saat merenungi apel jatuh dari dahan kebun! 🍎"
                ),
                QuizQuestion(
                    question = "Pada ukuran standar derajat selsius, berapakah tingkat suhu mendidihnya air murni di tekanan udara rata-rata laut? 🌡️",
                    options = listOf("50° Celsius 🌡️", "100° Celsius 🌡️🎯", "180° Celsius 🌡️", "212° Celsius 🌡️"),
                    answerIndex = 1,
                    explanation = "Air berada di titik didih sempurna 100 derajat selsius (100°C) di mana gelembung tekanan uap jenuh lolos bebas ke udara! 💧"
                ),
                QuizQuestion(
                    question = "Peristiwa fisika wujud zat padat langsung menyublim beralih rupa menjadi uap gas tanpa berair basah terlebih dahulu disebut... 🧊",
                    options = listOf("Mencair 💧", "Menyublim 🧊💨", "Mengembun 🌫️", "Mengkristal ❄️"),
                    answerIndex = 1,
                    explanation = "Menyublim terjadi contohnya pada kamper wangi pakaian atau es kering yang habis memadat menguap ke udara kamar! 🧊"
                )
            ),
            // Age 3: SMP & Remaja
            3 to listOf(
                QuizQuestion(
                    question = "Apakah rumus kimiawi akurat bagi cairan asam sulfat pekat korosif penyokong reaksi sel elektrolit aki akumulator motor? 🧪",
                    options = listOf("HCl 🧪", "H2SO4 🧪🔬", "HNO3 ⚗️", "H2O 💧"),
                    answerIndex = 1,
                    explanation = "Asam sulfat (H2SO4) adalah asam mineral kuat yang melepaskan dua proton hidrogen dalam ionisasi sempurna air! 🧪"
                ),
                QuizQuestion(
                    question = "Hukum gravitasi dinamis Newton kedua menguraikan formulasi korelasi vektor gaya, massa, dan percepatan dengan persamaan matematika... 📐",
                    options = listOf("F = m + a", "F = m * a 📐⚖️", "Ek = 0.5 * m * v²", "P = m * v"),
                    answerIndex = 1,
                    explanation = "Hukum II Newton menyatakan percepatan berbanding lurus gaya bersih total dan berbanding terbalik nilai kelembaman massa! ⚖️"
                ),
                QuizQuestion(
                    question = "Organel sel sitoplasma berselaput ganda penuang metabolisme pembakar zat nutrisi glukosa menjadi koin energi utama sel (ATP) bernama... 🔬",
                    options = listOf("Ribosom Piringan 🔬", "Mitokondria 🔬🧬", "Kloroplas Hijau 🍃", "Lisosom Pencerna 🧪"),
                    answerIndex = 1,
                    explanation = "Mitokondria mengoperasikan siklus asam sitrat krebs dan rantai transpor elektron fosforilasi oksidatif guna memompa pasokan listrik sel! 🧬"
                ),
                QuizQuestion(
                    question = "Interaksi erat antara organisme kutu rambut pengisap folikel sel kulit dengan inangnya makhluk kepala manusia dikelompokkan ke... 🦟",
                    options = listOf("Simbiosis Mutualisme 🤝", "Simbiosis Parasitisme 🦟🦂", "Simbiosis Komensalisme 🌱", "Amensalisme Racun 🧪"),
                    answerIndex = 1,
                    explanation = "Kutu untung mendapatkan asupan darah inang, sedangkan manusia dirugikan merasakan gatal parah dan iritasi kepala! 🦟"
                ),
                QuizQuestion(
                    question = "Lapisan atmosfer terbawah melingkupi bumi tempat terjadinya dinamika cuaca, uap badai, awan kondensasi, salju, dan kelembapan adalah... 🌦️",
                    options = listOf("Stratosfer 🛰️", "Troposfer 🌦️🌀", "Mesosfer ☄️", "Termosfer 🌌"),
                    answerIndex = 1,
                    explanation = "Troposfer membentang setebal 0-12 km di mana kerapatan massa udara menampung semua siklus termodinamika cuaca hidup! 🌦️"
                )
            )
        ),

        // --- 7. GIZI SEHAT (MAPPED FROM GEOGRAFI ATLAS INPUT) ---
        "kesehatan" to mapOf(
            // Age 0: PAUD & TK
            0 to listOf(
                QuizQuestion(
                    question = "Adik-adik, sup supaya badan kita bugar tumbuh tinggi tangguh bebas virus, kita harus lahap makan... 🥦",
                    options = listOf("Es Krim Manis 🍦", "Sayuran Hijau dan Buah Segar 🥦🍎✨", "Permen Lolipop 🍭", "Chiki Asin 🍿"),
                    answerIndex = 1,
                    explanation = "Sayur wortel, bayam, brokoli, dan buah jeruk mengandung vitamin sakti pelindung sel kekebalan tubuh adik! 🥦"
                ),
                QuizQuestion(
                    question = "Minuman sehat berwarna putih kaya vitamin kalsium penguat gigi buatan teman sapi perah adalah... 🥛",
                    options = listOf("Air Bersoda 🥤", "Susu Sapi Lezat 🥛🐮", "Kopi Hitam ☕", "Teh Manis 🍵"),
                    answerIndex = 1,
                    explanation = "Susu mengandung kalsium dan seng yang mempercepat peninggian struktur tulang kaki dan gigi susu adik! 🥛"
                ),
                QuizQuestion(
                    question = "Organ indera mata kesayangan kita wajib dijaga kesehatannya dengan menjauhi kebiasaan buruk... 👁️",
                    options = listOf("Makan Apel 🍎", "Menonton HP Terlalu Dekat dan Gelap 📱❌", "Tidur Tepat Waktu 😴", "Membaca Buku Terang 📚"),
                    answerIndex = 1,
                    explanation = "Layar gadget memancarkan radiasi biru tajam yang merusak kesehatan jaringan retina bola mata jika terlalu dekat! 👁️"
                ),
                QuizQuestion(
                    question = "Sesampainya di rumah habis memegang mainan taman pasir, kita wajib siram tangan menggunakan air dan... 🧼",
                    options = listOf("Minyak Kelapa 🥥", "Sabun Pembersih Kuman 🧼👾", "Tepung Terigu 🍞", "Pasir Bersih 🪨"),
                    answerIndex = 1,
                    explanation = "Sabun melarutkan selubung lemak pelindung virus jahat dan kuman agar luruh hanyut terbuang ke parit! 🧼"
                ),
                QuizQuestion(
                    question = "Supaya nafas kita segar wangi dan tidak berlubang sariawan, kita gosok gigi minimal... 🪥",
                    options = listOf("Seminggu Sekali 📅", "Dua Kali Sehari (Pagi & Malam) 🪥🦷✨", "Satu Kali Sebulan 🗓️", "Kalau Ingat Saja 🤫"),
                    answerIndex = 1,
                    explanation = "Menggosok gigi sehabis sarapan pagi dan sebelum tidur malam membersihkan sisa karamel gula perusak email gigi! 🦷"
                )
            ),
            // Age 1: SD Kelas Rendah
            1 to listOf(
                QuizQuestion(
                    question = "Zat vitamin yang sangat melimpah di dalam perasan jeruk nipis dan buah lemon pencegah sariawan adalah... 🍊",
                    options = listOf("Vitamin A 🥕", "Vitamin C 🍊🌱", "Vitamin D 🥛", "Vitamin K 🥦"),
                    answerIndex = 1,
                    explanation = "Vitamin C (asam askorbat) menyembuhkan sariawan, memperkokoh gusi dan menjaga daya tahan jaringan epitel tubuh! 🍊"
                ),
                QuizQuestion(
                    question = "Makanan pokok sehari-hari seperti nasi putih hangat, roti gandum, dan singkong rebus kaya akan energi penggerak yaitu... 🍚",
                    options = listOf("Lemak Jenuh 🥩", "Karbohidrat Sebagai Energi 🍚🍞✨", "Zat Besi 🩸", "Vitamin B Kompleks 🥖"),
                    answerIndex = 1,
                    explanation = "Karbohidrat dipecah pencernaan usus menjadi molekul glukosa murni, bensin utama otot tubuh berlari aktif! 🍞"
                ),
                QuizQuestion(
                    question = "Berapa jamkah waktu istirahat tidur malam yang ideal bagi tubuh anak usia sekolah dasar agar hormon tumbuh bekerja maksimal? 😴",
                    options = listOf("4 Jam Saja ⏰", "8 hingga 9 Jam Sehari 😴💤", "14 Jam Lamanya 🛌", "Tidak Tidur Sambil Main HP 📱"),
                    answerIndex = 1,
                    explanation = "Tidur malam 8 jam adalah waktu penyegaran sel otak merehabilitasi keletihan fisik sepanjang siang hari! 😴"
                ),
                QuizQuestion(
                    question = "Zat kasar tidak tercerna dalam sayur mayur kangkung dan daun singkong yang berjasa melancarkan buang air besar di toilet adalah... 🥬",
                    options = listOf("Gluten Tepung 🍞", "Serat Makanan (Fiber) 🥬🦖", "Kolesterol Jahat 🥩", "Minyak Atsiri 🍃"),
                    answerIndex = 1,
                    explanation = "Serat sayuran mengikat air di usus besar membuat ampas pencernaan menjadi lunak tanpa memicu sembelit menyiksa! 🥬"
                ),
                QuizQuestion(
                    question = "Mengapa penderita badan meriang, demam tinggi, dan batuk pilek diperintahkan rajin meminum air putih bersih hangat? 🐳",
                    options = listOf("Biar Badan Tambah Gemuk 🐋", "Mencegah Dehidrasi dan Mengencerkan Dahak 🐳💧", "Mengusir Rasa Lapar 🍲", "Membuat Suara Jadi Keras 🗣️"),
                    answerIndex = 1,
                    explanation = "Air putih hangat melancarkan sekresi racun kuman melalui air seni dan menjaga kelembapan tenggorokan kering! 💧"
                )
            ),
            // Age 2: SD Kelas Tinggi
            2 to listOf(
                QuizQuestion(
                    question = "Protein hewani seperti ikan, putih telur kukus, dan dada ayam sangat penting bagi tubuh pertumbuhan anak karena berfungsi sebagai... 🥚",
                    options = listOf(
                        "Pencair Sel Lemak 🧪",
                        "Bahan Pembangun Jaringan Sel dan Otot Otak 🥚🧬✨",
                        "Zat Pelarut Kalsium 🥛",
                        "Penyimpan Cadangan Air Tubuh 💧"
                    ),
                    answerIndex = 1,
                    explanation = "Asam amino protein merakit kembali serat sel otot yang aus serta memicu fungsi pertumbuhan organ biologis tubuh! 🥩"
                ),
                QuizQuestion(
                    question = "Defisiensi atau kekurangan asupan zat iodium/yodium pada bumbu garam harian dapat memicu pembengkakan kelenjar leher parah yang dinamakan... 🧂",
                    options = listOf("Penyakit Diabetes Merah 🩸", "Penyakit Gondok (Struma) 🧂🦠", "Penyakit Rakitis Tulang 🦴", "Sakit Gigi Nyeri 🦷"),
                    answerIndex = 1,
                    explanation = "Iodium diserap kelenjar tiroid leher untuk memproduksi hormon tiroksin pemandu utama metabolik tubuh teratur! 🧂"
                ),
                QuizQuestion(
                    question = "Sel hemoglobin darah merah memerlukan asupan satu mineral kuat dari daun bayam merah pengikat oksigen segar ke otak yaitu... 🩸",
                    options = listOf("Kalsium Tulang 🥛", "Zat Besi (Ferrum) 🩸🥦✨", "Zat Kimia Fosfor 🧪", "Kalium Garam 🧂"),
                    answerIndex = 1,
                    explanation = "Zat besi merakit sel darah merah pembawa molekul oksigen berkeliling mematangkan kesegaran berpikir di sekolah! 🧪"
                ),
                QuizQuestion(
                    question = "Epidemi diabetes melitus tipe 2 usia muda belakangan ini dipicu oleh kegemaran sepele mengonsumsi terlalu berlebihan... 🧃",
                    options = listOf("Air Putih Hangat 🐳", "Gula Pasir dan Minuman Kemasan Manis 🧃❌", "Sayuran Mentah 🥦", "Ikan Pepes 🐟"),
                    answerIndex = 1,
                    explanation = "Sirup fruktosa menguras kinerjanya sel pankreas penghasil insulin, menumpuk toksin lemak hati memicu lonjakan glukosa darah! 🥤"
                ),
                QuizQuestion(
                    question = "Mengapa kita diwajibkan mencuci sayur segar lalapan mengalir sebelum memakannya mentah di piring hidangan? 🧼",
                    options = listOf(
                        "Biarnya Rasa Sayur Menjadi Manis 🍲",
                        "Menghilangkan Sisa Racun Kimia Pestisida dan Telur Cacing Parasit 🚿🥬✨",
                        "Bisa Mengubah Tekstur Sayur Padat 🥦",
                        "Menambah Kandungan Karbohidrat Daun 🌾"
                    ),
                    answerIndex = 1,
                    explanation = "Air mengalir menyapu sisa residu obat pembasmi serangga kebun serta jentik telur parasit tanah berbahaya! 🚿"
                )
            ),
            // Age 3: SMP & Remaja
            3 to listOf(
                QuizQuestion(
                    question = "Kondisi kelemahan eritrosit mengangkut pasokan oksigen akibat runtuhnya jumlah hemoglobin penopang zat besi dalam tubuh remaja disebut... 🧠",
                    options = listOf("Heimofilia Genetik 🩸", "Anemia Defisiensi Besi 🧠💪✨", "Leukemia Sel Putih 🧫", "Hipotensi Jantung 🫀"),
                    answerIndex = 1,
                    explanation = "Anemia meredupkan suplai oksigen ke jaringan saraf parietal otak, menyebabkan gejala 5L: Lemah, Letih, Lesu, Lelah, Lalai! 🧠"
                ),
                QuizQuestion(
                    question = "Mengapa tubuh manusia mutlak mewajibkan kehadiran 9 asam amino esensial lengkap dari asupan protein lauk eksternal sehari-hari? 🥩",
                    options = listOf(
                        "Untuk Mengurangi Kolesterol Lambung",
                        "Sebab Tubuh Sama Sekali Tidak Mampu Mensintesisnya Mandiri 🥩🧠",
                        "Sebagai Alternatif Cadangan Mineral Ginjal",
                        "Membantu Pengendapan Kalsium Tulang"
                    ),
                    answerIndex = 1,
                    explanation = "Protein esensial harus dipasok utuh dari makanan luar (seperti tahu, tempe, telur, ayam) demi memacu sintesis hormon esensial tubuh! 🥩"
                ),
                QuizQuestion(
                    question = "Sejenis hormon peptida yang disintesis sel beta pulau Langerhans pankreas penurun kadar glukosa darah ke dalam sel somatis adalah... 🧪",
                    options = listOf("Hormon Melatonin 😴", "Hormon Insulin 🧪🧬✨", "Hormon Adrenalin 🥵", "Hormon Tiroksin 🧠"),
                    answerIndex = 1,
                    explanation = "Insulin menjadi kunci pembuka gerbang protein GLUT4 di dinding sel agar molekul glukosa larut mengalir terkonversi mengubah energi ATP! 🧬"
                ),
                QuizQuestion(
                    question = "Remaja beraktivitas padat berolahraga memerlukan pasokan air hidrasi harian rata-rata minimal sebanyak... 💧",
                    options = listOf("500 mL sehari 🥤", "2 hingga 2,5 Liter sehari (8-10 Gelas) 💧🥛✨", "5 Liter sehari 🪣", "Hanya minum saat merasa lapar 🍲"),
                    answerIndex = 1,
                    explanation = "Jumlah tersebut meremajakan sel ginjal mengolah residu asam laktat otot dan mengganti cairan tubuh lewat keringat! 💧"
                ),
                QuizQuestion(
                    question = "Kelompok zat gizi mikronutrien vitamin di bawah ini yang berciri larut di dalam matriks lemak tubuh (tidak terbawa air kemih) adalah... 🩺",
                    options = listOf("Vitamin B Kompleks dan Vitamin C 🍊", "Vitamin A, D, E, dan K 🩺🔬🎯", "Vitamin B12 dan Asam Folat 🧪", "Hanya Vitamin D saja 🥛"),
                    answerIndex = 1,
                    explanation = "Vitamin ADEK disimpan lama di dalam parenkim jaringan adiposa hati, kelebihan dosis masifnya dapat memicu toksisitas organ! 🩺"
                )
            )
        ),

        // --- 8. GEOGRAFI (MAPPED FROM GIZI SEHAT INPUT) ---
        "geografi" to mapOf(
            // Age 0: PAUD & TK
            0 to listOf(
                QuizQuestion(
                    question = "Adik-adik, kita tinggal di negeri Indonesia yang indah. Apa warna bendera kebanggaan negara kita? 🇮🇩",
                    options = listOf("Kuning Biru 💛💙", "Merah di Atas, Putih di Bawah 🇮🇩✨", "Hijau Putih 💚🤍", "Hitam Merah 🖤❤️"),
                    answerIndex = 1,
                    explanation = "Bendera Merah Putih berkibar megah di sekolah melambangkan kebersamaan persaudaraan tanah air! 🇮🇩"
                ),
                QuizQuestion(
                    question = "Daratan super tinggi, berhawa dingin sejuk, banyak ditumbuhi pohon teh hijau yang deket awan disebut... 🏞️",
                    options = listOf("Gurun Pasir 🐫", "Gunung Tinggi ⛰️🎒", "Kolam Ikan 🐟", "Lapangan Bola ⚽"),
                    answerIndex = 1,
                    explanation = "Gunung adalah dataran menjulang megah, udaranya segar sangat indah dipandang mata! ⛰️"
                ),
                QuizQuestion(
                    question = "Genangan air asin super luas tak berujung tempat berenang paus besar dan kapal laut berlayar gagah bernama... 🌊",
                    options = listOf("Kolam Renang 🏊", "Lautan Luas 🌊🐳✨", "Sungai Ciliwung 🏞️", "Sawah Padi 🌾"),
                    answerIndex = 1,
                    explanation = "Laut menampung air asin mengalirkan siklus hujan bumi tempat hidup terumbu karang yang ramah! 🌊"
                ),
                QuizQuestion(
                    question = "Kota metropolitan sangat besar dengan gedung tinggi canggih yang menjadi Ibu Kota negara Indonesia saat ini bernama... 🏙️",
                    options = listOf("Surabaya 🦁", "Jakarta 🏙️🏛️", "Bandung ⛰️", "Medan 🍉"),
                    answerIndex = 1,
                    explanation = "Jakarta saat ini adalah lokasi istana negara presiden pusat pemerintahan bersatunya Indonesia! 🏙️"
                ),
                QuizQuestion(
                    question = "Gambar bentuk peta bumi datar penuh benua lautan di kertas atlas besar membantu kita agar tidak... 🗺️",
                    options = listOf("Tidak Lapang 🍲", "Tidak Tersesat di Jalan 🗺️🚀", "Tidak Kedinginan ❄️", "Tidak Mengantuk 💤"),
                    answerIndex = 1,
                    explanation = "Peta atau kompas penunjuk arah menuntun langkah kita mencari lokasi negara lain dengan cepat! 🗺️"
                )
            ),
            // Age 1: SD Kelas Rendah
            1 to listOf(
                QuizQuestion(
                    question = "Negara Indonesia dijuluki sebagai negara kepulauan karena dikelilingi oleh ribuan... 🏝️",
                    options = listOf("Sawah Lebar 🌾", "Pulau Besar dan Kecil 🏝️🗺️✨", "Danau Air Tawar 🏞️", "Gurun Pasir 🐫"),
                    answerIndex = 1,
                    explanation = "Indonesia memiliki lebih dari 17.000 pulau eksotis yang membentang dari Sabang di ujung barat sampai Merauke di timur! 🏝️"
                ),
                QuizQuestion(
                    question = "Selat sempit yang memisahkan jembatan laut penyeberangan Pulau Sumatera dan Pulau Jawa adalah... 🌋",
                    options = listOf("Selat Karimata 🌊", "Selat Sunda 🌋🚢✨", "Selat Madura 橋", "Selat Bali 🚤"),
                    answerIndex = 1,
                    explanation = "Selat Sunda juga merupakan tempat bermukimnya kompleks gunung berapi laut Krakatau yang legendaris! 🌋"
                ),
                QuizQuestion(
                    question = "Apakah nama gunung berapi aktif tertinggi di Jawa Timur yang puncaknya dijuluki tempat petualangan spiritual para pendaki? 🌋",
                    options = listOf("Gunung Tangkuban Perahu 🛶", "Gunung Semeru (Mahameru) 🌋⛰️", "Gunung Merapi 💥", "Gunung Salak 🌲"),
                    answerIndex = 1,
                    explanation = "Gunung Semeru menjulang kokoh setinggi 3.676 meter memiliki kawah aktif Jonggring Saloko yang memukau! 🌋"
                ),
                QuizQuestion(
                    question = "Berapakah jumlah benua utama yang ada di dunia tempat bermukimnya peradaban populasi seluruh manusia? 🌍",
                    options = listOf("3 Benua 🌍", "5 Benua Utama 🌍🏆", "8 Benua 🌍", "10 Benua 🌍"),
                    answerIndex = 1,
                    explanation = "Lima benua dihuni tersebut adalah Benua Asia, Eropa, Afrika, Amerika, dan Australia! 🌏"
                ),
                QuizQuestion(
                    question = "Sungai terpanjang mengalir meliuk eksotis di pedalaman hutan belantara pulau Kalimantan Barat adalah... 🏞️",
                    options = listOf("Sungai Brantas 🌊", "Sungai Kapuas 🏞️🛶✨", "Sungai Musi 🌉", "Sungai Bengawan Solo 🎻"),
                    answerIndex = 1,
                    explanation = "Sungai Kapuas mengalir sepanjang 1.143 kilometer menjadi urat nadi transportasi masyarakat hulu suku Dayak! 🛶"
                )
            ),
            // Age 2: SD Kelas Tinggi
            2 to listOf(
                QuizQuestion(
                    question = "Garis khayal horisontal membagi tebal bumi menjadi belahan bumi utara dan selatan tepat di derajat lintang nol derajat dinamakan... 🌍",
                    options = listOf("Garis Bujur Greenwich ⚓", "Garis Khatulistiwa (Ekuator) 🌍☀️✨", "Garis Lintang Tropik 🌧️", "Garis Meridian Barat 🧭"),
                    answerIndex = 1,
                    explanation = "Garis khatulistiwa melintasi tepat di atas kota Pontianak, Kalimantan Barat, beriklim tropis basah sepanjang tahun! ☀️"
                ),
                QuizQuestion(
                    question = "Pegunungan lipatan tertinggi di permukaan bumi yang puncaknya tertutup gletser es salju abadi di Asia Selatan bernama... 🏔️",
                    options = listOf("Pegunungan Rocky 🗻", "Pegunungan Himalaya 🏔️⛰️✨", "Pegunungan Alpen 🏔️", "Pegunungan Andes 🏔️"),
                    answerIndex = 1,
                    explanation = "Himalaya terbentuk akibat tabrakan tektonik lempeng India ke lempeng Eurasia melahirkan atap dunia Gunung Everest! 🏔️"
                ),
                QuizQuestion(
                    question = "Negara merdeka paling mungil terkecil di belahan bumi yang areanya berada di tengah teritorial Roma, Italia adalah... 🇻🇦",
                    options = listOf("Monako 🇲🇨", "Vatikan 🇻🇦🏫✨", "San Marino 🏛️", "Andorra 🌲"),
                    answerIndex = 1,
                    explanation = "Negara Vatikan hanya memiliki luas sekitar 0,44 kilometer persegi dipimpin tahta suci Paus! 🇻🇦"
                ),
                QuizQuestion(
                    question = "Danau vulkanik kaldera tektonik terbesar di Asia Tenggara yang memiliki pulau Samosir di bagian tengahnya berada di... 🏞️",
                    options = listOf("Danau Singkarak 🌊", "Danau Toba (Sumatera Utara) 🏞️🏆✨", "Danau Maninjau 🏞️", "Danau Ranau 🏞️"),
                    answerIndex = 1,
                    explanation = "Danau Toba meletus dahsyat 74.000 tahun silam menyemburkan abu vulkanik menyelimuti atmosfer bumi global! 🌋"
                ),
                QuizQuestion(
                    question = "Samudra terluas terdalam di bumi kita yang menyelimuti sisa sekat lempeng samudera belahan dunia timur adalah... 🌊",
                    options = listOf("Samudra Hindia 🌊", "Samudra Pasifik 🌊🐳✨", "Samudra Atlantik 🌊", "Samudra Arktik ❄️"),
                    answerIndex = 1,
                    explanation = "Samudra Pasifik dibatasi oleh Cincin Api gunung berapi raksasa yang aktif memicu gempa laut tektonik dunia! 🗺️"
                )
            ),
            // Age 3: SMP & Remaja
            3 to listOf(
                QuizQuestion(
                    question = "Tingkat kerapatan garis elevasi kontur yang sangat padat merapat pada representasi peta topografi menunjukkan bahwa kondisi fisik daerah tersebut... 📐",
                    options = listOf("Lembah Landai Subur 🌾", "Lereng Curam / Jurang Terjal ⛰️📈✨", "Rawa Pasang Air 🌊", "Semenanjung Delta Sungai 🏞️"),
                    answerIndex = 1,
                    explanation = "Semakin dekat jarak antar garis kontur ketinggian menandakan kemiringan sudut lereng daratan curam terjal berbahaya! ⛰️"
                ),
                QuizQuestion(
                    question = "Teori hanyutan apungan lempeng benua (Continental Drift Hypothesis) pertama kali dipublikasikan secara empiris oleh ilmuwan kutub bernama... 🗺️",
                    options = listOf("Charles Darwin 🐵", "Alfred Wegener 🗺️❄️✨", "Isaac Newton 🍎", "Harry Hess 🌋"),
                    answerIndex = 1,
                    explanation = "Wegener merumuskan bahwa benua dahulunya menyatu erat berbentuk daratan super Pangea sebelum retak terbawa gerakan arus konveksi mantel! 🗺️"
                ),
                QuizQuestion(
                    question = "Hembusan sirkulasi angin monsun barat di wilayah kepulauan Indonesia memicu mulainya pergantian musim berupa... 🌦️",
                    options = listOf("Musim Kemarau Kering ☀️", "Musim Penghujan Subur 🌦️⛈️✨", "Musim Gugur Daun 🍂", "Musim Pancaroba Angin 🌪️"),
                    answerIndex = 1,
                    explanation = "Angin monsun barat mengalirkan kelembapan uap air samudra pasifik utara mendinginkannya di atas daratan Indonesia berupa hujan deras! 🌧️"
                ),
                QuizQuestion(
                    question = "Selat pelayaran sempit tersibuk yang memisahkan daratan Malaysia dan pulau Sumatera sebagai gerbang utama transit energi minyak antar benua adalah... 🚢",
                    options = listOf("Selat Sunda 🌋", "Selat Malaka 🚢🗺️✨", "Selat Makassar 🌊", "Selat Lombok 🚤"),
                    answerIndex = 1,
                    explanation = "Selat Malaka adalah jalur strategis (Choke Point) perdagangan global tersibuk dunia yang menghubungkan Samudra Hindia dan Laut Cina Selatan! 🚢"
                ),
                QuizQuestion(
                    question = "Palung samudra (trench) terdalam sejagat bumi yang terbentuk gara-gara subduksi penunjaman lempeng pasifik di bawah lempeng filipina dinamakan... 🌊",
                    options = listOf("Palung Jawa 🇮🇩", "Palung Mariana (Challenger Deep) 🌊🔬✨", "Palung Tonga 🌊", "Palung Puerto Riko 🌊"),
                    answerIndex = 1,
                    explanation = "Palung Mariana kedalamannya menyusut hingga hampir 11.000 meter ke dalam palung kegelapan laut abadi kerak bumi! 🌊"
                )
            )
        )
    )

    fun generateQuiz(context: Context, topic: String, ageGroup: String): List<QuizQuestion> {
        val rand = Random(System.currentTimeMillis())
        val cleanTopic = topic.lowercase().trim()

        // 1. Resolve correct academic category (normal non-reversed mapping)
        val targetCategory = when {
            // Matematika ➗
            cleanTopic.contains("matematika") || cleanTopic.contains("math") || cleanTopic.contains("hitung") -> "matematika"
            
            // Sejarah Kuno 🏛️
            cleanTopic.contains("sejarah") || cleanTopic.contains("history") || cleanTopic.contains("borobudur") || cleanTopic.contains("pahlawan") -> "sejarah"

            // Inggris Kids 🇬🇧
            cleanTopic.contains("inggris") || cleanTopic.contains("english") || cleanTopic.contains("vocab") || cleanTopic.contains("grammar") -> "inggris"
            
            // Luar Angkasa / Astronomi 🪐
            cleanTopic.contains("luar angkasa") || cleanTopic.contains("space") || cleanTopic.contains("astronomi") || cleanTopic.contains("bintang") || cleanTopic.contains("planet") -> "astronomi"

            // Sains & Alam 🧪
            cleanTopic.contains("sains") || cleanTopic.contains("science") || cleanTopic.contains("alam") || cleanTopic.contains("biologi") || cleanTopic.contains("ipa") -> "sains"
            
            // Seni & Musik 🎨
            cleanTopic.contains("seni") || cleanTopic.contains("musik") || cleanTopic.contains("art") || cleanTopic.contains("music") || cleanTopic.contains("lukis") || cleanTopic.contains("gitar") -> "seni"

            // Geografi Atlas 🗺️
            cleanTopic.contains("geografi") || cleanTopic.contains("geography") || cleanTopic.contains("atlas") || cleanTopic.contains("peta") || cleanTopic.contains("benua") -> "geografi"
            
            // Gizi Sehat / Kesehatan 🍎
            cleanTopic.contains("kesehatan") || cleanTopic.contains("gizi") || cleanTopic.contains("sehat") || cleanTopic.contains("vitamin") || cleanTopic.contains("tubuh") -> "kesehatan"

            else -> "sains" // default fallback
        }

        // 2. Identify target age group index (0..3)
        val ageIndex = when {
            ageGroup.contains("PAUD", ignoreCase = true) || ageGroup.contains("TK", ignoreCase = true) || ageGroup.contains("3-6") -> 0
            ageGroup.contains("Rendah", ignoreCase = true) || ageGroup.contains("7-9") -> 1
            ageGroup.contains("Tinggi", ignoreCase = true) || ageGroup.contains("10-12") -> 2
            ageGroup.contains("SMP", ignoreCase = true) || ageGroup.contains("Remaja", ignoreCase = true) || ageGroup.contains("13+") -> 3
            else -> 1 // default to elementary low
        }

        val categoryKey = "general_${targetCategory}_${ageIndex}"

        // Get questions for this corrected combination
        val questionsList = flippedPool[targetCategory]?.get(ageIndex) ?: flippedPool["sains"]?.get(1) ?: emptyList()

        // If there are less than 5 unseen questions left, clear prebuilt category history so we can start over!
        val unseenCount = questionsList.count { !QuestionHistoryTracker.isQuestionSeen(context, it.question, categoryKey) }
        if (unseenCount < 5) {
            QuestionHistoryTracker.clearCategoryHistory(context, categoryKey)
        }

        // Select 5 questions trying to ensure they are unseen
        val shuffledPool = questionsList.shuffled(rand).toMutableList()
        val selected = mutableListOf<QuizQuestion>()
        var attempts = 0

        while (selected.size < 5 && shuffledPool.isNotEmpty() && attempts < 100) {
            attempts++
            val q = shuffledPool.first()
            shuffledPool.removeAt(0)

            val alreadySeen = QuestionHistoryTracker.isQuestionSeen(context, q.question, categoryKey)
            if (!alreadySeen || attempts > 50) {
                selected.add(q)
                QuestionHistoryTracker.markQuestionAsSeen(context, q.question, categoryKey)
            } else {
                shuffledPool.add(q)
            }
        }

        // Fallback or fill to 5 questions in case something happened
        if (selected.size < 5) {
            val remainingNeeded = 5 - selected.size
            val extras = questionsList.filter { extraQ -> !selected.any { it.question == extraQ.question } }.shuffled(rand)
            for (i in 0 until minOf(remainingNeeded, extras.size)) {
                selected.add(extras[i])
                QuestionHistoryTracker.markQuestionAsSeen(context, extras[i].question, categoryKey)
            }
        }

        return selected.take(5)
    }
}
