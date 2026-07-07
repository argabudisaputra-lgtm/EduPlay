package com.example.data

import kotlin.random.Random

object OfflineEduBot {

    private val GREETINGS = listOf(
        "Halo juga, sobat pintar! 🌟 Ada yang ingin kamu diskusikan atau tanyakan padaku hari ini? Aku siap menemanimu belajar!",
        "Hai! 👋 Senang sekali bisa mengobrol denganmu lagi. Hari ini kita mau belajar topik apa nih? Matematika, Sains, atau Bahasa Inggris?",
        "Halo! Semangat belajarmu luar biasa sekali hari ini! 💪 Yuk, katakan apa yang ingin kamu pelajari hari ini bersama EduBot!",
        "Selamat datang kembali di EduBot! 🧠 Ingat, tidak ada pertanyaan yang konyol. Semua pertanyaanmu adalah langkah awal menuju kejeniusan!"
    )

    private val MATH_TIPS = listOf(
        "Matematika itu bukan cuma menghafal rumus, tapi memahami polanya! 📐 Misalnya, perkalian 9 punya pola unik: jumlah angka hasilnya selalu 9 (18 -> 1+8=9, 27 -> 2+7=9, dan seterusnya). Menarik, kan?",
        "Ingin jago matematika? Cobalah petualangan **Math Land 🏰** di menu utama! Di sana kamu bisa bertualang mengalahkan monster batu dan naga api menggunakan kekuatan hitung cepatmu! Seru banget!",
        "Tips berhitung cepat: Untuk mengalikan angka dengan 5, kamu bisa membagi angka tersebut dengan 2 lalu kalikan dengan 10. Contoh: 16 × 5 = (16 ÷ 2) × 10 = 80! Mudah, kan? 💡",
        "Pecahan sering membingungkan? Bayangkan pecahan seperti memotong loyang pizza lezat! 🍕 1/2 pizza tentu lebih besar daripada 1/4 bagian pizza. Jangan lupa samakan penyebutnya saat menjumlahkan ya!"
    )

    private val SCIENCE_FACTS = listOf(
        "Tahukah kamu? 🌌 Di luar angkasa sangat sunyi karena tidak ada udara untuk merambatkan suara. Jadi, ledakan bintang raksasa sekalipun tidak akan bersuara di sana!",
        "Fakta sains unik: Jantung paus biru sangat besar sehingga manusia ukuran dewasa bisa berenang di dalam aorta (pembuluh darah utama) mereka! 🐳 Luar biasa besarnya alam semesta ini!",
        "Pohon adalah paru-paru bumi kita! 🌳 Melalui proses **Fotosintesis**, sebatang pohon dewasa mampu menghasilkan oksigen bersih untuk bernapas hingga 4 orang manusia setiap harinya.",
        "Mengapa langit berwarna biru? 💙 Itu karena cahaya biru dari matahari memiliki gelombang yang pendek dan tersebar ke segala arah oleh gas-gas di atmosfer bumi kita!"
    )

    private val HISTORY_TALES = listOf(
        "Wah, membahas sejarah selalu mengasyikkan! 🏛️ Tahukah kamu bahwa Candi Borobudur dibangun sekitar abad ke-8 masehi tanpa menggunakan semen sama sekali? Batu-batunya dikunci menggunakan sistem interlock kuno yang sangat presisi!",
        "Kisah Nusantara: Patih Gajah Mada dari Kerajaan Majapahit mengucapkan **Sumpah Palapa** yang terkenal, berjanji tidak akan menikmati kelapa/kesenangan duniawi sebelum berhasil mempersatukan seluruh pulau di wilayah nusantara!",
        "Tahukah kamu? Manusia purba mendiami bumi ratusan ribu tahun yang lalu. Di Indonesia,考古学家 menemukan fosil terkenal seperti *Pithecantropus Erectus* di daerah Trinil, Jawa Timur! 🔍"
    )

    private val ENGLISH_TIPS = listOf(
        "Learning English is fun! 🇬🇧 Tips hari ini: Untuk melatih bahasa Inggris, cobalah menonton kartun favoritmu dengan subtitle bahasa Inggris. Ini akan mempercepat penambahan kosakatamu secara alami!",
        "Mari belajar perbedaan penggunaan kata plural: Kebanyakan kata hanya ditambah akhiran '-s' (contoh: *book* menjadi *books*). Tapi ada kata istimewa yang berubah total (contoh: *child* menjadi *children*, *tooth* menjadi *teeth*)!",
        "Don't be afraid to make mistakes! 😉 Kesalahan adalah bukti bahwa kamu sedang mencoba. Cobalah menguji kemampuan kosakata dan tata bahasamu di fitur Kuis Bahasa Inggris kami!"
    )

    private val ISLAMIC_NOTES = listOf(
        "Belajar dan menuntut ilmu adalah kewajiban mulia bagi setiap Muslim sejak buaian hingga akhir hayat! 🕌 Nabi Muhammad SAW bersabda bahwa siapa saja yang menempuh jalan untuk mencari ilmu, maka Allah akan memudahkan baginya jalan menuju surga.",
        "Tahukah kamu? Rukun Islam ada 5 perkara, dimulai dari Syahadat, menegakkan Shalat 5 waktu, menunaikan Zakat, berpuasa di bulan Ramadhan, dan berhaji bagi yang mampu secara finansial dan fisik. 🕋",
        "Kisah inspiratif: Di masa keemasan Islam, banyak ilmuwan muslim penemu hal hebat! Sebut saja Al-Khawarizmi, sang penemu angka Aljabar dan algoritma yang menjadi dasar teknologi komputer yang kamu gunakan saat ini!"
    )

    private val JOKES_PUNS = listOf(
        "Kenapa buku matematika selalu terlihat murung dan sedih? 😢 Karena dia terlalu banyak memiliki masalah hidup! Hahaha... 😹",
        "Buah apa yang paling pintar di sekolah? 🤔 Jawabannya: Apel! Karena dia selalu jadi buah bibir para guru bahasa Inggris (*An Apple a day...*)!",
        "Kenapa matahari selalu panas setiap hari? Karena dia malas mandi sore, sukanya berjemur terus! ☀️ Hahaha...",
        "Pantun EduBot: \n*Pergi ke pasar beli belanak,* \n*Pulangnya mampir makan ketupat.* \n*Ayo belajar anak-anak,* \n*Biar jadi anak yang hebat!* 🏆"
    )

    private val GENERAL_ENCOURAGEMENTS = listOf(
        "Hebat sekali! Pikiranmu kritis dan penuh rasa ingin tahu yang tinggi. 🚀 Pertanyaanmu membuatku semakin bersemangat menemanimu. Cobalah bermain Tebak Kata untuk menyegarkan pikiranmu!",
        "Mengagumkan! Pembelajaran terbaik berawal dari pertanyaan seperti ini. Ingatlah bahwa otak kita seperti otot, semakin sering digunakan belajar, ia akan semakin kuat dan cerdas! 💪",
        "Menarik sekali! Belajar secara offline di EP Knowledge Land membuatmu bebas gangguan iklan internet dan bisa fokus penuh mengasah kecerdasanmu kapan saja dan di mana saja! 😉",
        "Teruslah bermimpi besar dan rajin belajar! Setiap materi pelajaran yang kamu kuasai hari ini adalah batu pijakan menuju cita-citamu yang gemilang di masa depan! 🌠"
    )

    fun getResponse(inputText: String): String {
        val rand = Random(System.currentTimeMillis())
        val clean = inputText.trim().lowercase()

        // 1. Dynamic Math Equation Solver
        val digits = Regex("\\d+").findAll(clean).map { it.value.toLong() }.toList()
        if (digits.size >= 2) {
            val a = digits[0]
            val b = digits[1]
            val op = when {
                clean.contains("+") || clean.contains("tambah") || clean.contains("plus") || clean.contains("jumlah") -> "addition"
                clean.contains("-") || clean.contains("kurang") || clean.contains("minus") || clean.contains("selisih") -> "subtraction"
                clean.contains("x") || clean.contains("*") || clean.contains("kali") || clean.contains("dikali") -> "multiplication"
                clean.contains("/") || clean.contains("bagi") || clean.contains("dibagi") -> "division"
                else -> null
            }

            if (op != null) {
                return when (op) {
                    "addition" -> {
                        val result = a + b
                        "Wah, asyik sekali! Kamu bertanya tentang perhitungan matematika! 🧮 Hasil dari **$a ditambah $b** adalah **$result**! Hebat sekali kamu sedang giat berlatih berhitung! 💪\n\n💡 **3 Fakta Kilat Matematika:**\n1. Simbol tambah (+) dan kurang (-) pertama kali diperkenalkan oleh matematikawan Jerman abad ke-15 bernama Johannes Widmann.\n2. Matematika melatih logika berpikirmu untuk menemukan solusi kreatif pemecahan masalah!\n3. Cobalah asah kekuatan perhitungan hebatmu di menu utama **Math Land 🏰** melawan monster batu!"
                    }
                    "subtraction" -> {
                        val result = a - b
                        "Wah, asyik sekali! Kamu bertanya tentang perhitungan matematika! 🧮 Hasil dari **$a dikurangi $b** adalah **$result**! Hebat sekali kamu sedang giat berlatih berhitung! 💪\n\n💡 **3 Fakta Kilat Matematika:**\n1. Pengurangan adalah salah satu dari empat operasi aritmetika dasar yang dipelajari manusia sejak zaman prasejarah.\n2. Konsep angka nol (0) sebagai bilangan asli dikembangkan di India kuno.\n3. Berlatih matematika setiap hari membuat jaringan neuron di otakmu makin tebal dan cerdas!"
                    }
                    "multiplication" -> {
                        val result = a * b
                        "Wah, asyik sekali! Kamu bertanya tentang perhitungan matematika! 🧮 Hasil dari **$a dikalikan $b** adalah **$result**! Hebat sekali kamu sedang giat berlatih berhitung! 💪\n\n💡 **3 Fakta Kilat Perkalian:**\n1. Perkalian sebenarnya adalah penjumlahan berulang yang disederhanakan agar lebih cepat!\n2. Tabel perkalian pertama kali ditemukan oleh matematikawan Yunani terkenal bernama Pythagoras.\n3. Tahukah kamu bahwa semua angka yang dikalikan dengan nol (0) hasilnya akan selalu nol!"
                    }
                    "division" -> {
                        if (b == 0L) {
                            "Aduh! Dalam matematika kita tidak bisa membagi angka apapun dengan angka nol (0) ya, sobat pintar! 🧮 Coba gunakan angka pembagi lainnya!"
                        } else {
                            if (a % b == 0L) {
                                val result = a / b
                                "Wah, asyik sekali! Kamu bertanya tentang perhitungan matematika! 🧮 Hasil dari **$a dibagi $b** adalah **$result**! Hebat sekali kamu sedang giat berlatih berhitung! 💪\n\n💡 **3 Fakta Kilat Pembagian:**\n1. Pembagian melatih kemampuanmu untuk membagikan sesuatu secara adil sama rata kepada teman-temanmu!\n2. Pembagian adalah operasi kebalikan (invers) dari perkalian.\n3. Cobalah membagi loyang pizza untuk dibagikan secara merata, itu disebut dasar dari pecahan matematika!"
                            } else {
                                val floatResult = a.toDouble() / b.toDouble()
                                val formatted = String.format("%.2f", floatResult)
                                "Wah, asyik sekali! Kamu bertanya tentang perhitungan matematika! 🧮 Hasil dari **$a dibagi $b** adalah sekitar **$formatted** (berbentuk desimal)! Hebat sekali kamu sedang giat berlatih berhitung! 💪\n\n💡 **3 Fakta Kilat Pecahan:**\n1. Pecahan desimal yang menggunakan tanda koma pertama kali dipopulerkan oleh Simon Stevin pada tahun 1585.\n2. Angka di belakang koma menunjukkan pembagian yang lebih kecil dari satu bagian utuh.\n3. Pecahan desimal sangat berguna saat kita mengukur panjang atau menimbang berat benda agar sangat akurat!"
                            }
                        }
                    }
                    else -> "Pertanyaan berhitung yang keren! 🧮 Terus semangat berlatih ya!"
                }
            }
        }

        // 2. Direct Answers for Specific Scientific, Historical, Profile, and General Questions
        return when {
            // -- App & Bot Profile --
            clean.contains("kamu siapa") || clean.contains("siapa kamu") || clean.contains("nama kamu") || 
            (clean.contains("kamu") && clean.contains("robot")) || clean.contains("siapa edubot") -> {
                "Aku adalah **EduBot** 🤖, robot asisten belajar cerdas pribadimu! Aku dirancang untuk membantumu belajar matematika, kuis, kosakata, agama, dan sejarah dengan cara yang asyik dan penuh keceriaan!\n\n💡 **3 Fakta Kilat EduBot:**\n1. EduBot bisa menemanimu belajar 100% offline tanpa perlu koneksi internet atau kuota berkelimpahan!\n2. Aku sangat menyukai pertanyaan kritis dari anak-anak pintar sepertimu.\n3. Cobalah tanyakan hal-hal menakjubkan seputar sains, sejarah, bumi, atau agama kepadaku!"
            }
            clean.contains("buat kamu") || clean.contains("pencipta") || clean.contains("dibuat oleh") || 
            clean.contains("siapa penciptamu") || clean.contains("siapa pembuatmu") -> {
                "Aku diciptakan khusus oleh tim pengembang **EP Knowledge Land** 🚀 berbakat untuk menjadi sahabat karib belajarmu di mana saja dan kapan saja!\n\n💡 **3 Fakta Kilat EP Knowledge Land:**\n1. EP Knowledge Land dikembangkan sebagai aplikasi edukasi ramah anak dengan desain visual interaktif.\n2. Aplikasi ini menyatukan kuis cerdas cermat, petualangan Math Land, tebak kata acak, dan obrolan EduBot.\n3. Kami berkomitmen menyebarkan keceriaan belajar penuh manfaat bagi anak-anak Indonesia!"
            }

            // -- Science Questions --
            clean.contains("awan putih") || (clean.contains("kenapa") && clean.contains("awan") && clean.contains("putih")) -> {
                "Awan terlihat berwarna putih karena butiran air dan kristal es di dalamnya saling berdempetan sangat rapat! Ketika cahaya matahari (yang terdiri dari semua warna pelangi) bersinar masuk ke awan, semua warna cahaya tersebut dipantulkan kembali secara merata ke mata kita. Pembiasan sempurna ini membuat awan tampak putih bersih di langit! ☁️\n\n💡 **3 Fakta Kilat Awan:**\n1. Awan sebenarnya adalah kumpulan dari triliunan butiran air kecil yang sangat ringan sehingga bisa mengapung di udara.\n2. Awan kumulus yang berbentuk bulat mengembang mirip kapas raksasa bisa memiliki berat ratusan ton, setara rombongan puluh gajah!\n3. Jika suhu awan menjadi sangat dingin di bawah nol derajat, butiran airnya akan membeku menjadi butiran es padat atau salju."
            }
            clean.contains("langit biru") || (clean.contains("kenapa") && clean.contains("langit") && clean.contains("biru")) -> {
                "Langit di siang hari terlihat berwarna biru karena fenomena ilmiah yang disebut **Hamburan Rayleigh**! 💙 Cahaya matahari sebenarnya memiliki semua warna pelangi, tetapi cahaya berwarna biru memiliki gelombang yang paling pendek dan cepat. Ketika memasuki atmosfer bumi yang penuh dengan gas, cahaya biru ini disebarkan ke segala arah jauh lebih kuat dibanding warna lainnya, sehingga langit tampak membiru indah!\n\n💡 **3 Fakta Kilat Langit Biru:**\n1. Di luar angkasa tempat tidak ada atmosfer (lapisan udara), langit selalu terlihat berwarna hitam pekat dan sunyi!\n2. Pada sore hari saat matahari terbenam, langit berubah warna oranye-merah karena cahaya matahari harus melewati lapisan udara yang jauh lebih tebal.\n3. Planet lain memiliki langit berwarna berbeda, misalnya langit planet Mars berwarna oranye kecokelatan di siang hari!"
            }
            clean.contains("pelangi") || (clean.contains("bagaimana") && clean.contains("pelangi")) -> {
                "Pelangi terbentuk ketika sinar matahari bersinar menembus sisa-sisa butiran air hujan yang melayang di udara! 🌈 Butiran air bertindak seperti jutaan prisma kaca kecil yang membelokkan, memantulkan, dan memilah cahaya putih matahari menjadi tujuh komponen warna aslinya secara berurutan: Merah, Jingga, Kuning, Hijau, Biru, Nila, dan Ungu (Me-Ji-Ku-Hi-Bi-Ni-U).\n\n💡 **3 Fakta Kilat Pelangi:**\n1. Pelangi sebenarnya berbentuk lingkaran lingkaran penuh yang utuh, tetapi kita hanya melihat setengah lingkaran saja dari tanah.\n2. Kamu selalu melihat pelangi dengan posisi matahari berada tepat di belakang punggungmu.\n3. Terkadang ada pelangi ganda (double rainbow) di mana pelangi kedua di atasnya memiliki urutan warna yang terbalik!"
            }
            clean.contains("hujan") || (clean.contains("proses") && clean.contains("hujan")) || (clean.contains("bagaimana") && clean.contains("hujan")) -> {
                "Hujan terjadi melalui siklus air hidrologi di bumi kita yang terus berputar tiada henti! 🌧️\n1. **Penguapan (Evaporasi)**: Matahari memanaskan air laut, sungai, dan danau sehingga air berubah menjadi uap air tidak terlihat lalu naik ke angkasa.\n2. **Pengembunan (Kondensasi)**: Di langit yang tinggi dan dingin, uap air mendingin dan berkumpul membentuk awan padat.\n3. **Curahan (Presipitasi)**: Awan semakin tebal, berat, dan dingin hingga tak mampu lagi menampung air, lalu butiran air jatuh ke bumi sebagai hujan!\n\n💡 **3 Fakta Kilat Hujan:**\n1. Air yang kita rasakan sebagai air hujan saat ini bisa jadi adalah air sama yang diminum oleh dinosaurus jutaan tahun yang lalu!\n2. Bau tanah yang harum dan khas setelah hujan turun disebut dengan istilah ilmiah **Petrikor**.\n3. Kecepatan jatuh butiran air hujan ke bumi berkisar antara 8 sampai 32 kilometer per jam."
            }
            clean.contains("daun hijau") || (clean.contains("kenapa") && clean.contains("daun") && clean.contains("hijau")) || clean.contains("klorofil") -> {
                "Sebagian besar daun tanaman berwarna hijau karena memiliki zat pigmen alami yang menakjubkan bernama **Klorofil** (zat hijau daun)! 🍃 Klorofil sangat penting karena berfungsi menangkap sinar matahari sebagai bahan utama memasak makanan bagi tanaman melalui proses **Fotosintesis**.\n\n💡 **3 Fakta Kilat Daun:**\n1. Klorofil menyerap semua warna cahaya matahari kecuali warna hijau yang dipantulkan kembali ke mata kita.\n2. Melalui daunnya yang hijau, tumbuhan menghasilkan oksigen segar yang dihirup oleh semua manusia dan hewan untuk hidup.\n3. Pada musim gugur di luar negeri, klorofil berkurang drastis sehingga daun tanaman berubah warna menjadi kuning emas, oranye, atau merah hangat!"
            }
            clean.contains("laut asin") || (clean.contains("kenapa") && clean.contains("laut") && clean.contains("asin")) -> {
                "Air laut terasa asin karena proses alamiah selama miliaran tahun! 🌊 Air hujan yang turun mengikis mineral garam dari bebatuan di daratan. Air sungai kemudian membawa garam terlarut ini mengalir menuju lautan luas. Di laut, panas matahari menguapkan air kembali ke langit membentuk awan, tetapi kandungan garamnya tetap tertinggal dan menumpuk terus-menerus!\n\n💡 **3 Fakta Kilat Laut Asin:**\n1. Laut mati (Dead Sea) di Yordania memiliki kandungan garam yang sangat tinggi sehingga manusia bisa mengapung di atas air tanpa perlu berenang.\n2. Jika seluruh garam di lautan bumi dikeringkan, ketebalannya bisa menyelimuti permukaan daratan bumi setinggi gedung 40 lantai!\n3. Rasa asin laut sangat penting untuk menjaga kelangsungan hidup ekosistem hewan laut dan terumbu karang yang megah."
            }
            clean.contains("bumi bulat") || (clean.contains("kenapa") && clean.contains("bumi") && clean.contains("bulat")) || clean.contains("bentuk bumi") -> {
                "Bumi berbentuk bulat karena adanya **Gaya Gravitasi** bumi yang sangat besar! 🌍 Gravitasi menarik seluruh materi bumi dari segala sisi menuju tepat ke pusatnya dengan kekuatan yang seimbang dan rata. Tarikan mutlak ini secara alami membentuk bola bundar yang padat dan kokoh.\n\n💡 **3 Fakta Kilat Bentuk Bumi:**\n1. Bumi tidak bulat sempurna mirip bola biliar, melainkan sedikit pepat di bagian kutub utara-selatan dan sedikit menggelembung di khatulistiwa akibat rotasinya.\n2. Gravitasi adalah alasan utama mengapa air laut, gedung, dan manusia tidak jatuh melayang ke angkasa luar meskipun bumi terus berputar.\n3. Astronaut pertama yang memotret bumi dari luar angkasa menggambarkan planet kita mirip dengan 'Kelereng Biru Raksasa yang Indah'."
            }
            clean.contains("bulan bersinar") || (clean.contains("kenapa") && clean.contains("bulan") && clean.contains("terang")) || (clean.contains("cahaya") && clean.contains("bulan")) -> {
                "Bulan tampak bersinar terang di malam hari bukan karena menghasilkan cahayanya sendiri, sobat pintar! 🌙 Bulan sebenarnya adalah benda langit berbentuk batu padat yang dingin dan gelap gulita. Ia tampak bersinar indah karena permukaannya yang berdebu memantulkan kembali cahaya dari matahari langsung ke arah bumi seperti cermin raksasa!\n\n💡 **3 Fakta Kilat Bulan:**\n1. Bulan adalah satu-satunya satelit alami yang setia menemani dan mengorbit planet Bumi kita.\n2. Tarikan gaya gravitasi bulan sangat mempengaruhi pasang-surut permukaan air laut di bumi.\n3. Karena tidak memiliki atmosfer (lapisan udara), kawah-kawah bekas hantaman meteorit di bulan akan terus bertahan selamanya tanpa terkikis angin."
            }
            clean.contains("matahari") || clean.contains("pusat tata surya") -> {
                "Matahari adalah bintang raksasa yang sangat menyala membara tepat di pusat tata surya kita! ☀️ Ia berupa bola gas hidrogen dan helium yang sangat panas. Pancaran sinar dan energi hangat matahari sangat krusial sebagai penunjang utama roda kehidupan bagi seluruh makhluk hidup di planet bumi kita!\n\n💡 **3 Fakta Kilat Matahari:**\n1. Ukuran matahari sangat besar! Sekitar 1,3 juta planet bumi bisa muat masuk ke dalam perut matahari.\n2. Cahaya matahari bergerak super cepat, hanya membutuhkan waktu sekitar 8 menit 20 detik untuk sampai menyinari bumi.\n3. Suhu permukaan matahari mencapai 5.500 derajat Celsius, sedangkan suhu bagian intinya mencapai 15 juta derajat Celsius!"
            }
            clean.contains("gravitasi") || clean.contains("gaya tarik bumi") -> {
                "Gaya gravitasi adalah gaya tarik-menarik tidak terlihat yang dimiliki oleh seluruh benda yang memiliki berat/massa. 🍎 Bumi kita sangat besar dan berat, sehingga ia memiliki gaya gravitasi kuat yang menarik tubuh kita, rumah, mobil, dan atmosfer agar tetap menempel aman di permukaan bumi tanpa melayang-layang ke ruang hampa angkasa!\n\n💡 **3 Fakta Kilat Gravitasi:**\n1. Ilmuwan terkenal Sir Isaac Newton menemukan teori gravitasi setelah melihat buah apel matang jatuh lurus ke bawah dari pohonnya.\n2. Di bulan, gaya gravitasi hanya 1/6 dari bumi, sehingga jika berada di sana kamu bisa melompat 6 kali lebih tinggi layaknya pahlawan super!\n3. Tanpa adanya gravitasi bumi, air laut, udara, dan semua makhluk hidup akan terlempar melayang pergi ke hampa udara yang dingin."
            }
            clean.contains("fotosintesis") || clean.contains("tumbuhan masak") -> {
                "Fotosintesis adalah cara ajaib tanaman hijau memasak makanannya menggunakan bahan alami: air dari tanah, karbon dioksida dari udara, klorofil di daun, dan energi cahaya matahari! 🌿 Proses ini menghasilkan glukosa (makanan tumbuhan untuk tumbuh kian subur) serta melepaskan oksigen bersih ke udara untuk kita hirup.\n\n💡 **3 Fakta Kilat Fotosintesis:**\n1. Kata 'Fotosintesis' berasal dari bahasa Yunani kuno: 'Foto' (cahaya) dan 'Sintesis' (penggabungan).\n2. Selain tumbuhan hijau, alga dan beberapa bakteri di dasar laut juga bisa berfotosintesis.\n3. Hutan hujan tropis di bumi sering disebut 'Paru-paru Dunia' karena fotosintesis pohonnnya menghasilkan oksigen dalam jumlah skala global."
            }

            // -- History Questions --
            clean.contains("indonesia merdeka") || clean.contains("kapan merdeka") || clean.contains("hari kemerdekaan") -> {
                "Indonesia merdeka setelah menyatakan proklamasi kemerdekaannya pada hari Jumat, tanggal **17 Agustus 1945**! 🇮🇩 Peristiwa pembacaan proklamasi ini bertempat di kediaman Bung Karno, Jalan Pegangsaan Timur No. 56, Jakarta Pusat, membawa kemerdekaan bagi seluruh rakyat.\n\n💡 **3 Fakta Kilat Proklamasi:**\n1. Bendera merah putih yang pertama kali dikibarkan dijahit langsung dengan tangan penuh cinta oleh **Ibu Fatmawati Soekarno**.\n2. Naskah asli teks proklamasi sempat terbuang ke tong sampah sebelum diselamatkan oleh seorang jurnalis bernama B.M. Diah!\n3. Lagu kebangsaan *Indonesia Raya* pertama kali dikumandangkan secara umum pada peristiwa Kongres Pemuda II tahun 1928."
            }
            clean.contains("presiden pertama") || clean.contains("soekarno") || clean.contains("bung karno") -> {
                "Presiden pertama Republik Indonesia adalah sang proklamator agung, **Ir. Soekarno** (lebih akrab dipanggil Bung Karno), didampingi oleh wakilnya yang cerdas **Drs. Mohammad Hatta** (Bung Hatta)! 🇮🇩 Beliau berdua memimpin perjuangan kemerdekaan kita pada tahun 1945.\n\n💡 **3 Fakta Kilat Bung Karno:**\n1. Bung Karno dikenal menguasai banyak bahasa asing termasuk bahasa Belanda, Inggris, Jerman, dan Prancis secara otodidak!\n2. Beliau adalah salah satu perumus utama dasar negara kita yang kokoh yaitu **Pancasila**.\n3. Pidato Bung Karno yang berapi-api selalu membakar semangat juang dan sangat terkenal hingga diakui di Forum PBB di tingkat dunia."
            }
            clean.contains("borobudur") || clean.contains("candi buddha terbesar") -> {
                "Candi Borobudur amat tersohor sebagai candi Buddha terbesar di dunia! 🏯 Letaknya berada di kota Magelang, Jawa Tengah. Candi ini dibangun megah pada masa dinasti Syailendra sekitar abad ke-8 Masehi. Yang mengagumkan, candi setinggi puluhan meter ini disusun dari jutaan balok batu andesit yang presisi saling mengunci tanpa perekat semen sama sekali!\n\n💡 **3 Fakta Kilat Borobudur:**\n1. Di dinding Candi Borobudur terukir lebih dari 2.600 panel relief indah yang bercerita tentang nilai-nilai kehidupan Buddha.\n2. Candi ini sempat tersembunyi tertimbun abu vulkanik letusan gunung berapi dan hutan lebat selama ratusan tahun sebelum ditemukan kembali tahun 1814.\n3. Borobudur diakui sebagai salah satu situs Warisan Dunia resmi oleh lembaga internasional UNESCO."
            }

            // -- Islamic Questions --
            clean.contains("rukun islam") || clean.contains("rukun islam ada") -> {
                "Rukun Islam adalah lima pondasi utama yang wajib dijalankan oleh setiap umat Muslim! 🕋\n1. **Syahadat**: Mengikrarkan kesaksian tauhid kepada Allah dan rasul-Nya.\n2. **Shalat**: Menegakkan ibadah shalat 5 waktu sehari.\n3. **Zakat**: Membagikan kepedulian harta bagi fakir miskin yang membutuhkan.\n4. **Puasa**: Berpuasa menahan diri di bulan suci Ramadhan.\n5. **Haji**: Berziarah suci ke Baitullah di Makkah bagi yang mampu secara fisik dan finansial.\n\n💡 **3 Fakta Kilat Rukun Islam:**\n1. Rukun Islam mencakup hubungan ibadah langsung kepada Allah dan hubungan sosial peduli sesama manusia.\n2. Ibadah shalat diibaratkan sebagai tiang penopang utama bangunan agama Islam.\n3. Puasa mendidik rasa empati kita untuk ikut merasakan beratnya rasa lapar yang dialami oleh saudara kita yang sedang kesusahan."
            }
            clean.contains("rukun iman") || clean.contains("rukun iman ada") -> {
                "Rukun Iman adalah enam pilar kepercayaan hati yang mendalam bagi seorang Muslim! ✨\n1. Keyakinan kepada **Allah SWT**.\n2. Keyakinan kepada **Malaikat-Malaikat Allah**.\n3. Keyakinan kepada **Kitab-Kitab Suci Allah** (Taurat, Zabur, Injil, Al-Qur'an).\n4. Keyakinan kepada **Rasul / Utusan Allah**.\n5. Keyakinan kepada **Hari Akhir / Kiamat**.\n6. Keyakinan kepada **Qada dan Qadar** (takdir ketetapan dari Allah SWT).\n\n💡 **3 Fakta Kilat Rukun Iman:**\n1. Kata 'Iman' berarti mempercayai dengan hati, mengucapkannya dengan lisan, dan membuktikannya lewat amal perbuatan.\n2. Kitab suci yang wajib kita ketahui ada 4: Taurat diturunkan kepada Nabi Musa, Zabur kepada Nabi Daud, Injil kepada Nabi Isa, dan Al-Qur'an kepada Nabi Muhammad SAW.\n3. Mengimani takdir membuat hati seorang muslim selalu tenang, berprasangka baik (*husnudzon*) dan bersabar dalam menghadapi ujian hidup."
            }
            clean.contains("nabi muhammad") || clean.contains("muhammad saw") -> {
                "Nabi Muhammad SAW adalah nabi pembawa ajaran Islam dan utusan Allah yang terakhir untuk seluruh umat manusia. ❤️ Beliau lahir di kota Makkah pada tanggal 12 Rabiul Awal tahun Gajah. Beliau dikenal memiliki sifat akhlak yang sangat luhur dan mulia sejak kecil, penyayang kepada seluruh makhluk, bersikap jujur (*Al-Amin*), serta selalu mengajarkan perdamaian dan kasih sayang di bumi.\n\n💡 **3 Fakta Kilat Nabi Muhammad:**\n1. Menjelang menerima wahyu pertama, beliau sering merenung memohon petunjuk di Gua Hira yang berada di puncak Jabal Nur.\n2. Wahyu pertama yang diturunkan kepada beliau adalah Surah Al-'Alaq ayat 1-5 yang diawali dengan perintah 'Iqra!' (Bacalah!).\n3. Mukjizat terbesar beliau adalah kitab suci Al-Qur'an dan peristiwa perjalanan suci Isra Mi'raj dalam kurun waktu satu malam."
            }

            // -- English Vocabulary --
            clean.contains("terima kasih") || clean.contains("makasih") || clean.contains("thank you") -> {
                "Sama-sama, sobat pintar! 😊 Kesenangan bagiku untuk bisa menemanimu belajar. Terus pertahankan rasa ingin tahumu yang luar biasa ya! Kamu sangat hebat!\n\n💡 **3 Fakta Kilat Belajar:**\n1. Kata *Thank you* adalah ungkapan bahasa Inggris yang paling sering diucapkan di dunia.\n2. Membiasakan diri berterima kasih mempererat ikatan pertemanan dan persaudaraan.\n3. Orang yang rajin berterima kasih terbukti secara ilmiah lebih bahagia dalam hidupnya!"
            }
            clean.contains("kucing") && clean.contains("inggris") -> {
                "Bahasa Inggrisnya kucing adalah **Cat** (dibaca: ket)! 🐱\n\n💡 **3 Fakta Kilat Kucing:**\n1. Kucing memiliki pendengaran super tajam, bahkan bisa mendengar suara ultrasonik yang tidak bisa didengar manusia.\n2. Seekor kucing menghabiskan sekitar 70% waktu hidupnya hanya untuk tidur!\n3. Kucing berkomunikasi mengeong hanya kepada manusia, bukan kepada sesama kucing dewasa."
            }
            clean.contains("anjing") && clean.contains("inggris") -> {
                "Bahasa Inggrisnya anjing adalah **Dog** (dibaca: dog)! 🐶\n\n💡 **3 Fakta Kilat Anjing:**\n1. Indra penciuman anjing sangat luar biasa, sekitar 40 kali lipat lebih tajam daripada penciuman manusia!\n2. Anjing memiliki kecerdasan setara dengan anak manusia berusia 2 tahun.\n3. Setiap hidung anjing memiliki pola guratan yang unik, sama seperti sidik jari manusia."
            }
            clean.contains("burung") && clean.contains("inggris") -> {
                "Bahasa Inggrisnya burung adalah **Bird** (dibaca: berd)! 🐦\n\n💡 **3 Fakta Kilat Burung:**\n1. Burung kolibri adalah satu-satunya burung yang bisa terbang mundur secara stabil.\n2. Burung memiliki tulang yang berongga dan sangat ringan untuk membantunya terbang melesat di langit.\n3. Burung merpati memiliki ingatan arah yang sangat hebat, bahkan bisa mengenali jalan pulang dari jarak ribuan kilometer!"
            }
            clean.contains("apel") && clean.contains("inggris") -> {
                "Bahasa Inggrisnya apel adalah **Apple** (dibaca: epel)! 🍎\n\n💡 **3 Fakta Kilat Apel:**\n1. Jika kamu mencelupkan apel ke dalam air, ia akan mengapung karena 25% bagian tubuhnya terisi oleh udara!\n2. Sebatang pohon apel memerlukan waktu sekitar 4 sampai 5 tahun setelah ditanam untuk bisa menghasilkan buah apel pertamanya.\n3. Ada lebih dari 7.500 jenis varietas buah apel yang tumbuh di seluruh penjuru bumi."
            }
            clean.contains("buku") && clean.contains("inggris") -> {
                "Bahasa Inggrisnya buku adalah **Book** (dibaca: buk)! 📖\n\n💡 **3 Fakta Kilat Buku:**\n1. Buku tertua di dunia yang dicetak menggunakan blok kayu berasal dari China sekitar tahun 868 Masehi.\n2. Membaca buku selama 6 menit saja terbukti bisa menurunkan tingkat stres hingga 68%!\n3. Istilah *Bibliofil* digunakan untuk menjuluki orang-orang yang sangat gemar membaca atau mengoleksi buku."
            }

            // -- General Categories (Fallback Grouping) --
            clean.contains("matematika") || clean.contains("hitung") || clean.contains("angka") -> MATH_TIPS.random(rand)
            clean.contains("sains") || clean.contains("ipa") || clean.contains("biologi") || clean.contains("fisika") -> SCIENCE_FACTS.random(rand)
            clean.contains("sejarah") || clean.contains("majapahit") || clean.contains("kuno") -> HISTORY_TALES.random(rand)
            clean.contains("inggris") || clean.contains("english") || clean.contains("vocab") -> ENGLISH_TIPS.random(rand)
            clean.contains("islam") || clean.contains("agama") || clean.contains("shalat") || clean.contains("puasa") -> ISLAMIC_NOTES.random(rand)
            clean.contains("lucu") || clean.contains("canda") || clean.contains("pantun") || clean.contains("joke") -> JOKES_PUNS.random(rand)

            // -- Greetings Fallback --
            clean.contains("halo") || clean.contains("hai") || clean.contains("pagi") || 
            clean.contains("siang") || clean.contains("sore") || clean.contains("malam") || 
            clean.contains("assalamualaikum") -> GREETINGS.random(rand)

            // -- Complete General Fallback encouraging educational play --
            else -> {
                "Sobat pintar, itu pertanyaan yang sangat menarik! 💡 Karena saat ini aku sedang beroperasi dalam mode offline (bebas kuota), aku belum menyimpan penjelasan spesifik untuk itu di memori lokalku. \n\nNamun, jangan khawatir! Kamu tetap bisa mempelajari banyak ilmu luar biasa lewat fitur **Kuis Interaktif 🧠** di menu utama, bertualang hitung cepat di **Math Land 🏰**, bermain teka-teki kata di **Tebak Kata 🧪**, atau menjelajahi **Tantangan Harian 🌟**! Mari asah kecerdasanmu bersama EP Knowledge Land!"
            }
        }
    }
}
