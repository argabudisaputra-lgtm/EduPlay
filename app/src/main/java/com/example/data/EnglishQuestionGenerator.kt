package com.example.data

import android.content.Context
import kotlin.random.Random

object EnglishQuestionGenerator {

    fun generateLocalEnglishQuestions(context: Context, grade: Int, category: String): List<QuizQuestion> {
        val allQuestions = when (category) {
            "Vocabulary" -> getVocabularyPool(grade)
            "Grammar" -> getGrammarPool(grade)
            "Conversation" -> getConversationPool(grade)
            "Reading" -> getReadingPool(grade)
            else -> getVocabularyPool(grade)
        }

        // Enforce 100% unique, non-duplicate questions for the selected level
        val distinctQs = allQuestions.distinctBy { it.question }
        
        // Securely guarantee there are exactly 5 questions
        if (distinctQs.size >= 5) {
            return distinctQs.take(5)
        }

        return allQuestions.take(5)
    }

    private fun getVocabularyPool(grade: Int): List<QuizQuestion> {
        return when (grade) {
            1 -> listOf(
                QuizQuestion("What is the meaning of 'apple'?", listOf("Apel 🍎", "Pisang 🍌", "Jeruk 🍊", "Mangga 🥭"), 0, "Apple artinya Apel. Buah manis yang sehat!"),
                QuizQuestion("What is the meaning of 'cat'?", listOf("Anjing 🐶", "Burung 🐦", "Kucing 🐱", "Kelinci 🐰"), 2, "Cat artinya Kucing, hewan menggemaskan!"),
                QuizQuestion("What color is 'yellow'?", listOf("Biru 🔵", "Merah 🔴", "Kuning 🟡", "Hijau 🟢"), 2, "Yellow artinya Kuning, seperti warna matahari!"),
                QuizQuestion("What is the meaning of 'book'?", listOf("Meja", "Buku 📖", "Tas", "Pensil"), 1, "Book artinya Buku, jendela ilmu dunia!"),
                QuizQuestion("What is the meaning of 'water'?", listOf("Susu 🥛", "Teh 🍵", "Air 💧", "Kopi ☕"), 2, "Water artinya Air, sangat segar dan sehat!")
            )
            2 -> listOf(
                QuizQuestion("What is the meaning of 'dog'?", listOf("Anjing 🐶", "Kucing 🐱", "Ayam 🐓", "Bebek 🦆"), 0, "Dog artinya Anjing, hewan peliharaan yang setia!"),
                QuizQuestion("What is the meaning of 'sun'?", listOf("Awan ☁️", "Matahari ☀️", "Bulan 🌙", "Bintang ⭐️"), 1, "Sun artinya Matahari, pusat tata surya kita!"),
                QuizQuestion("What is the meaning of 'house'?", listOf("Sekolah 🏫", "Kantor 🏢", "Rumah 🏠", "Kebun 🏡"), 2, "House artinya Rumah, tempat berkumpul keluarga!"),
                QuizQuestion("What is the meaning of 'fish'?", listOf("Burung 🐦", "Ikan 🐟", "Kambing 🐐", "Sapi  can"), 1, "Fish artinya Ikan, berenang lincah di air!"),
                QuizQuestion("What is the meaning of 'chair'?", listOf("Meja 🪵", "Kursi 🪑", "Lemari 🗄️", "Pintu 🚪"), 1, "Chair artinya Kursi, tempat duduk yang nyaman!")
            )
            3 -> listOf(
                QuizQuestion("What is the meaning of 'school'?", listOf("Rumah Sakit 🏥", "Sekolah 🏫", "Pasar 🛒", "Taman 🌳"), 1, "School artinya Sekolah, tempat menuntut ilmu!"),
                QuizQuestion("What is the meaning of 'teacher'?", listOf("Dokter 🩺", "Polisi 👮", "Guru 👨‍🏫", "Pilot 👨‍✈️"), 2, "Teacher artinya Guru, pahlawan tanpa tanda jasa!"),
                QuizQuestion("What is the meaning of 'pencil'?", listOf("Penggaris 📏", "Buku 📖", "Pensil ✏️", "Penghapus 🧼"), 2, "Pencil artinya Pensil, alat menulis yang serbaguna!"),
                QuizQuestion("What is the meaning of 'kitchen'?", listOf("Kamar Tidur 🛏️", "Dapur 🍳", "Kamar Mandi 🚿", "Ruang Tamu 🛋️"), 1, "Kitchen artinya Dapur, tempat memasak makanan lezat!"),
                QuizQuestion("What is the meaning of 'family'?", listOf("Teman 👥", "Guru 👨‍🏫", "Keluarga 👨‍👩‍👧‍👦", "Tetangga 🏠"), 2, "Family artinya Keluarga, orang terdekat yang mencintai kita!")
            )
            4 -> listOf(
                QuizQuestion("What is the meaning of 'library'?", listOf("Kantin 🍕", "Perpustakaan 📚", "Kelas 🏫", "Taman 🌳"), 1, "Library artinya Perpustakaan, gudang buku dunia!"),
                QuizQuestion("What is the meaning of 'bedroom'?", listOf("Kamar Mandi 🚿", "Dapur 🍳", "Kamar Tidur 🛏️", "Garasi 🚗"), 2, "Bedroom artinya Kamar Tidur, tempat istirahat malam!"),
                QuizQuestion("What is the meaning of 'dentist'?", listOf("Dokter Gigi 🦷", "Dokter Hewan 🐾", "Apoteker 💊", "Perawat 🩺"), 0, "Dentist artinya Dokter Gigi yang merawat gigi kita!"),
                QuizQuestion("What is the meaning of 'pilot'?", listOf("Masinis 🚂", "Sopir 🚌", "Pilot 👨‍✈️", "Nakhoda 🚢"), 2, "Pilot artinya Pengemudi Pesawat Terbang di udara!"),
                QuizQuestion("What is the meaning of 'carpenter'?", listOf("Tukang Kayu 🪵", "Tukang Besi ⚙️", "Nelayan 🎣", "Petani 🌾"), 0, "Carpenter artinya Tukang Kayu, pembuat mebel indah!")
            )
            5 -> listOf(
                QuizQuestion("What is the meaning of 'delicious'?", listOf("Asin 🧂", "Pahit ☕", "Lezat/Enak 😋", "Asam 🍋"), 2, "Delicious artinya Lezat atau Enak rasanya!"),
                QuizQuestion("What is the meaning of 'healthy'?", listOf("Sakit 🤒", "Sehat 💪", "Lemah 😴", "Kuat 🏋️"), 1, "Healthy artinya Sehat dan bugar badannya!"),
                QuizQuestion("What is the meaning of 'beautiful'?", listOf("Jelek 👺", "Indah/Cantik ✨", "Bersih 🧹", "Rapi 👔"), 1, "Beautiful artinya Indah atau Cantik menawan!"),
                QuizQuestion("What is the meaning of 'dangerous'?", listOf("Aman 🛡️", "Damai 🕊️", "Berbahaya ⚠️", "Seru 🎢"), 2, "Dangerous artinya Berbahaya, harus berhati-hati!"),
                QuizQuestion("What is the meaning of 'important'?", listOf("Penting 📣", "Biasa 📄", "Murah 🏷️", "Mudah 🧩"), 0, "Important artinya Penting, harus didahulukan!")
            )
            6 -> listOf(
                QuizQuestion("What is the meaning of 'hurricane'?", listOf("Gempa Bumi 🌋", "Kekeringan 🍂", "Badai Besar 🌪️", "Banjir 💧"), 2, "Hurricane artinya Badai Besar dengan angin kencang berputar!"),
                QuizQuestion("What is the meaning of 'drought'?", listOf("Kekeringan 🍂", "Tsunami 🌊", "Hujan Es ❄️", "Longsor 🏔️"), 0, "Drought artinya Kekeringan, masa tanpa hujan yang lama!"),
                QuizQuestion("What is the meaning of 'earthquake'?", listOf("Gunung Meletus 🌋", "Gempa Bumi 🪨", "Tornado 🌪️", "Banjir Bandang 🌊"), 1, "Earthquake artinya Gempa Bumi akibat pergeseran lempeng bumi!"),
                QuizQuestion("What is the meaning of 'tsunami'?", listOf("Gelombang Pasang Raksasa 🌊", "Hujan Badai 🌧️", "Pusaran Angin 🌪️", "Kemarau ☀️"), 0, "Tsunami artinya Gelombang Pasang Raksasa akibat gempa dasar laut!"),
                QuizQuestion("What is the meaning of 'volcano'?", listOf("Samudra 🌊", "Gunung Berapi 🌋", "Gurun Pasir 🏜️", "Hujan Salju ❄️"), 1, "Volcano artinya Gunung Berapi yang bisa meletus!")
            )
            7 -> listOf(
                QuizQuestion("What is a synonym of 'huge'?", listOf("Tiny 🐜", "Colossal 🪐", "Messy 🧹", "Polite 🤝"), 1, "Huge bersinonim dengan Colossal (sangat besar)."),
                QuizQuestion("What is the antonym of 'improve'?", listOf("Ameliorate 📈", "Worsen 📉", "Create 🎨", "Maintain 🛡️"), 1, "Lawan kata Improve (membaik) adalah Worsen (memburuk)."),
                QuizQuestion("What is a synonym of 'postpone'?", listOf("Put off 🕰️", "Carry out 🚀", "Set up ⚙️", "Take off ✈️"), 0, "Postpone bersinonim dengan Put off (menunda)."),
                QuizQuestion("What is the meaning of 'messy'?", listOf("Bersih ✨", "Berantakan 🧹", "Sopan 🤝", "Pandai 🧠"), 1, "Messy artinya Berantakan atau kotor."),
                QuizQuestion("What is the meaning of 'polite'?", listOf("Kasar 😡", "Sopan 😊", "Kreatif 🎨", "Berani 🦁"), 1, "Polite artinya Sopan dan ramah dalam tutur kata.")
            )
            8 -> listOf(
                QuizQuestion("What is a synonym of 'small'?", listOf("Gigantic 🪐", "Minute 🔍", "Heavy 🪨", "Bright ☀️"), 1, "Small bersinonim dengan Minute (sangat kecil/kecil sekali)."),
                QuizQuestion("What is the antonym of 'wild'?", listOf("Fierce 🐯", "Tame 🐑", "Savage 🦁", "Bold 🦅"), 1, "Lawan kata Wild (liar) adalah Tame (jinak)."),
                QuizQuestion("What is the meaning of 'generous'?", listOf("Kikir 🪙", "Dermawan 🤝", "Sombong 👑", "Pemalu 🫣"), 1, "Generous artinya Dermawan, suka berbagi."),
                QuizQuestion("What is the meaning of 'ancient'?", listOf("Modern 🚀", "Hubungan", "Kuno/Purbakala 🏛️", "Besar 🪐"), 2, "Ancient artinya Kuno atau Purbakala."),
                QuizQuestion("What is the meaning of 'complex'?", listOf("Sederhana 🟢", "Rumit 🧩", "Cepat ⚡", "Lambat 🐢"), 1, "Complex artinya Rumit atau berbelit-belit.")
            )
            9 -> listOf(
                QuizQuestion("What is the meaning of 'fathom'?", listOf("Mengabaikan 🙈", "Memahami Mendalam 🧠", "Memotong ✂️", "Menunda ⏳"), 1, "Fathom artinya memahami suatu konsep rumit secara mendalam."),
                QuizQuestion("What is the meaning of 'obsolete'?", listOf("Modis 🕶️", "Usang/Kuno 🏛️", "Cepat ⚡", "Kuat 🏋️"), 1, "Obsolete artinya Usang atau sudah tidak digunakan lagi."),
                QuizQuestion("What is the meaning of 'melancholy'?", listOf("Gembira 🥳", "Sedih Mendalam 😢", "Marah 😡", "Takut 😱"), 1, "Melancholy artinya Rasa sedih yang mendalam."),
                QuizQuestion("What is the meaning of 'abundant'?", listOf("Sangat Sedikit 🍂", "Sangat Berlimpah 💎", "Miskin 🪙", "Hilang 🌀"), 1, "Abundant artinya Sangat Berlimpah ruah."),
                QuizQuestion("What is the meaning of 'scarcity'?", listOf("Kelangkaan 🍂", "Kekayaan 💰", "Banjir 🌊", "Keramaian 👥"), 0, "Scarcity artinya Kelangkaan atau kekurangan barang.")
            )
            10 -> listOf(
                QuizQuestion("What is the meaning of 'benevolent'?", listOf("Kejam 👹", "Murah Hati 🤝", "Kikir 🪙", "Pemalas 😴"), 1, "Benevolent artinya Sifat murah hati dan senang menolong orang."),
                QuizQuestion("What is the meaning of 'malicious'?", listOf("Sopan 🤝", "Jahat/Berniat Buruk 😈", "Jujur 😇", "Cerdas 🧠"), 1, "Malicious artinya Jahat atau berniat mencelakai."),
                QuizQuestion("What is the meaning of 'frugal'?", listOf("Boros 💰", "Hemat/Sederhana 🪙", "Kaya 💎", "Miskin 🛖"), 1, "Frugal artinya Hemat atau hidup bersahaja."),
                QuizQuestion("What is the meaning of 'industrious'?", listOf("Pemalas 😴", "Rajin/Giat 🐝", "Lemah 🤒", "Sombong 👑"), 1, "Industrious artinya Rajin atau giat bekerja."),
                QuizQuestion("What is the meaning of 'audacious'?", listOf("Penakut 🫣", "Sangat Berani/Lancang 🦁", "Ramah 😊", "Tenang 🧘"), 1, "Audacious artinya Sangat Berani atau cenderung lancang.")
            )
            11 -> listOf(
                QuizQuestion("What is the meaning of 'resilient'?", listOf("Rapuh 🍂", "Tangguh/Ulet 💪", "Malas 😴", "Penakut 🫣"), 1, "Resilient artinya Tangguh dan cepat bangkit dari kesulitan."),
                QuizQuestion("What is the meaning of 'ambiguous'?", listOf("Jelas ☀️", "Bermakna Ganda/Kabur 🌫️", "Indah 🌸", "Benar ✅"), 1, "Ambiguous artinya Bermakna Ganda atau tidak jelas maksudnya."),
                QuizQuestion("What is the meaning of 'redundant'?", listOf("Berguna 🛠️", "Berlebihan/Mubazir 📦", "Kurang 🍂", "Penting 📣"), 1, "Redundant artinya Berlebihan atau mubazir karena tidak terpakai."),
                QuizQuestion("What is the meaning of 'meticulous'?", listOf("Teledor 🤪", "Sangat Teliti/Cermat 🔍", "Cepat ⚡", "Kasar 😡"), 1, "Meticulous artinya Sangat Teliti atau cermat hingga detail terkecil."),
                QuizQuestion("What is the meaning of 'indifferent'?", listOf("Antusias 🤩", "Acuh Tak Acuh/Skeptis 🥱", "Sayang 🥰", "Peduli 🌟"), 1, "Indifferent artinya Acuh Tak Acuh atau tidak peduli.")
            )
            12 -> listOf(
                QuizQuestion("What is the meaning of 'ephemeral'?", listOf("Abadi ♾️", "Sementara/Singkat ⏳", "Panjang 📏", "Kuat 🏋️"), 1, "Ephemeral artinya Bersifat Sementara atau berlangsung singkat."),
                QuizQuestion("What is the meaning of 'preposterous'?", listOf("Masuk Akal 💡", "Tidak Masuk Akal/Konyol 🤪", "Indah 🌸", "Benar ✅"), 1, "Preposterous artinya Tidak Masuk Akal atau sangat konyol."),
                QuizQuestion("What is the meaning of 'colossal'?", listOf("Sangat Kecil 🔍", "Sangat Besar/Raksasa 🪐", "Bersih ✨", "Rapi 👔"), 1, "Colossal artinya Sangat Besar atau kolosal."),
                QuizQuestion("What is the meaning of 'ameliorate'?", listOf("Memperburuk 📉", "Meningkatkan/Memperbaiki 📈", "Mengabaikan 🙈", "Memotong ✂️"), 1, "Ameliorate artinya Meningkatkan situasi atau memperbaiki kualitas."),
                QuizQuestion("What is the meaning of 'exacerbate'?", listOf("Meredakan 🧘", "Memperburuk Keadaan 📉", "Membantu 🤝", "Membangun 🧱"), 1, "Exacerbate artinya Memperburuk keadaan atau memicu konflik.")
            )
            13 -> listOf(
                QuizQuestion("What is the meaning of 'capricious'?", listOf("Konsisten 🧱", "Berubah-ubah/Plin-plan 🌀", "Tenang 🌊", "Sabar 🧘"), 1, "Capricious artinya Mudah Berubah pikiran secara tiba-tiba."),
                QuizQuestion("What is the meaning of 'ubiquitous'?", listOf("Langka 🍂", "Ada di mana-mana 🌐", "Hilang 🌀", "Baru 🆕"), 1, "Ubiquitous artinya Ada di mana-mana pada waktu bersamaan."),
                QuizQuestion("What is the meaning of 'loquacious'?", listOf("Pendiam 🤫", "Sangat Cerewet/Suka Bicara 🗣️", "Pemalu 🫣", "Sopan 😊"), 1, "Loquacious artinya Sangat Cerewet atau suka mengobrol."),
                QuizQuestion("What is the meaning of 'pragmatic'?", listOf("Teoretis 📚", "Praktis/Pragmatis 🛠️", "Khayalan 🦄", "Malas 😴"), 1, "Pragmatic artinya Berpikir praktis mengutamakan hasil nyata."),
                QuizQuestion("What is the meaning of 'gregarious'?", listOf("Penyendiri 🛖", "Suka Bersosialisasi 👥", "Takut 😱", "Kasar 😡"), 1, "Gregarious artinya Suka Bergaul dan bersosialisasi dengan komunitas.")
            )
            14 -> listOf(
                QuizQuestion("What is the meaning of 'cacophony'?", listOf("Suara Merdu 🎵", "Kebisingan Riuh-rendah 🔊", "Keheningan 🤫", "Bisikan 🍃"), 1, "Cacophony artinya Kebisingan suara sumbang yang riuh-rendah."),
                QuizQuestion("What is the meaning of 'adversity'?", listOf("Kemakmuran 💰", "Kemalangan/Kesulitan 🔥", "Kedamaian 🕊️", "Keberuntungan 🍀"), 1, "Adversity artinya Kemalangan atau kesulitan hidup yang berat."),
                QuizQuestion("What is the meaning of 'venerable'?", listOf("Terhina 👎", "Sangat Dihormati/Mulia 👑", "Muda 🐣", "Biasa 📄"), 1, "Venerable artinya Sangat Dihormati karena usia dan keluhuran budi."),
                QuizQuestion("What is the meaning of 'fastidious'?", listOf("Asal-asalan 😜", "Sangat Rewet/Sangat Pemilih 🔍", "Cepat ⚡", "Sabar 🧘"), 1, "Fastidious artinya Sangat Pemilih atau detail dalam kebersihan/kerapian."),
                QuizQuestion("What is the meaning of 'tenacious'?", listOf("Mudah Menyerah 🏳️", "Gigih/Pemberani 💪", "Lemah 🤒", "Ragu-ragu 🫣"), 1, "Tenacious artinya Sangat Gigih mempertahankan tekad.")
            )
            15 -> listOf(
                QuizQuestion("What is the meaning of 'equilibrium'?", listOf("Ketidakseimbangan 📈", "Keseimbangan Setara ⚖️", "Kekacauan 🌀", "Gerakan 🏃"), 1, "Equilibrium artinya Keseimbangan yang stabil."),
                QuizQuestion("What is the meaning of 'sublime'?", listOf("Rendah 🛖", "Sangat Luhur/Mulia/Indah ✨", "Buruk 👿", "Kasar 😡"), 1, "Sublime artinya Keindahan atau keluhuran yang luar biasa agung."),
                QuizQuestion("What is the meaning of 'eloquent'?", listOf("Kaku Berbicara 🤐", "Piawai Berbicara/Fasih 🗣️", "Diam 🤫", "Kasar 😡"), 1, "Eloquent artinya Piawai dalam bertutur kata atau fasih bicara."),
                QuizQuestion("What is the meaning of 'aesthetic'?", listOf("Kusam 🌫️", "Bernilai Seni/Estetis 🎨", "Murah 🏷️", "Rusak 🔨"), 1, "Aesthetic artinya Bernilai Keindahan atau estetis."),
                QuizQuestion("What is the meaning of 'paradox'?", listOf("Kebenaran Mutlak ✅", "Dua Hal Bertentangan Tapi Benar 🌀", "Kebohongan ❌", "Sains 🔬"), 1, "Paradox artinya Dua hal yang berlawanan namun memuat kebenaran.")
            )
            else -> getVocabularyPool(15)
        }
    }

    private fun getGrammarPool(grade: Int): List<QuizQuestion> {
        return when (grade) {
            1 -> listOf(
                QuizQuestion("Choose the correct pronoun: 'Santi is diligent. ___ studies hard.'", listOf("She", "He", "It", "They"), 0, "She menggantikan satu perempuan (Santi) 👧"),
                QuizQuestion("Choose the correct pronoun: 'Budi is happy. ___ is smiling.'", listOf("She", "He", "It", "They"), 1, "He menggantikan satu laki-laki (Budi) 👦"),
                QuizQuestion("Choose the correct pronoun: 'The cat is sleeping. ___ is cute.'", listOf("She", "He", "It", "They"), 2, "It menggantikan hewan/benda tunggal (cat) 🐱"),
                QuizQuestion("Choose the correct pronoun: 'Budi and I are friends. ___ study together.'", listOf("She", "He", "We", "They"), 2, "We (Kami) menggantikan saya dan Budi 👥"),
                QuizQuestion("Choose the correct pronoun: 'The students are energetic. ___ run fast.'", listOf("She", "He", "We", "They"), 3, "They (Mereka) menggantikan murid-murid jamak 🏃")
            )
            2 -> listOf(
                QuizQuestion("Choose the correct verb: 'I ___ a good student.'", listOf("is", "am", "are", "be"), 1, "I berpasangan dengan 'am' 🌟"),
                QuizQuestion("Choose the correct verb: 'You ___ my best friend.'", listOf("is", "am", "are", "be"), 2, "You berpasangan dengan 'are' 👥"),
                QuizQuestion("Choose the correct verb: 'He ___ playing football.'", listOf("is", "am", "are", "be"), 0, "He berpasangan dengan 'is' 👦"),
                QuizQuestion("Choose the correct verb: 'We ___ happy today.'", listOf("is", "am", "are", "be"), 2, "We berpasangan dengan 'are' 😊"),
                QuizQuestion("Choose the correct verb: 'It ___ a beautiful butterfly.'", listOf("is", "am", "are", "be"), 0, "It berpasangan dengan 'is' 🦋")
            )
            3 -> listOf(
                QuizQuestion("Choose the plural noun: 'There are two ______ on the table.'", listOf("book", "books", "bookes", "booked"), 1, "Jamak dari book adalah books (tambah s) 📚"),
                QuizQuestion("Choose the plural noun: 'We saw five ______ in the park.'", listOf("box", "boxs", "boxes", "boxing"), 2, "Kata berakhiran x bertambah es: boxes 📦"),
                QuizQuestion("Choose the plural noun: 'The ______ are playing happily.'", listOf("child", "children", "childs", "childrens"), 1, "Plural tidak beraturan: children 🧒"),
                QuizQuestion("Choose the plural noun: 'I bought three ______ from the market.'", listOf("mango", "mangoes", "mangos", "manged"), 1, "Akhiran o bertambah es: mangoes 🥭"),
                QuizQuestion("Choose the plural noun: 'Her ______ are clean.'", listOf("foot", "feets", "feet", "footes"), 2, "Plural dari foot adalah feet 👣")
            )
            4 -> listOf(
                QuizQuestion("Choose the preposition: 'The apple is sitting ______ the table.'", listOf("on", "in", "under", "at"), 0, "On untuk posisi menyentuh bidang atas meja 🍎"),
                QuizQuestion("Choose the preposition: 'The pencil is ______ the bag.'", listOf("on", "in", "under", "at"), 1, "In untuk posisi di dalam tas 🎒"),
                QuizQuestion("Choose the preposition: 'The cat slept ______ the chair. (On the floor below)'", listOf("on", "at", "under", "between"), 2, "Under untuk posisi di bawah kursi dan lantai 🐱"),
                QuizQuestion("Choose the preposition: 'The park is located ______ the school and library.'", listOf("on", "under", "between", "among"), 2, "Between di antara dua tempat (sekolah & perpustakaan) 🌳"),
                QuizQuestion("Choose the preposition: 'She wakes up ______ 6 AM.'", listOf("on", "in", "at", "under"), 2, "At digunakan sebelum jam spesifik ⏰")
            )
            5 -> listOf(
                QuizQuestion("Complete the Present Simple: 'Every morning, Budi ______ milk.'", listOf("drink", "drinks", "is drinking", "drank"), 1, "Subjek tunggal (Budi) mendapat akhiran -s: drinks 🥛"),
                QuizQuestion("Complete the Present Simple: 'They ______ English every day.'", listOf("study", "studies", "studying", "studied"), 0, "Subjek jamak (They) menggunakan verb murni: study 📖"),
                QuizQuestion("Complete the Present Simple: 'The sun ______ in the east.'", listOf("rise", "rises", "rising", "rosed"), 1, "Fakta alam menggunakan Present Simple tunggal: rises ☀️"),
                QuizQuestion("Complete the Present Simple: 'I ______ like vegetables.'", listOf("don't", "doesn't", "am not", "not"), 0, "I menggunakan don't untuk kalimat negatif 🥦"),
                QuizQuestion("Complete the Present Simple: '______ she live in Jakarta?'", listOf("Do", "Does", "Is", "Are"), 1, "Tanya untuk subjek tunggal (she) menggunakan Does 🇮🇩")
            )
            6 -> listOf(
                QuizQuestion("Complete the Past Simple: 'Yesterday, Budi ______ to the market.'", listOf("go", "goes", "went", "gone"), 2, "Went adalah bentuk lampau (past form) dari go 🛒"),
                QuizQuestion("Complete the Past Simple: 'We ______ a beautiful movie last night.'", listOf("see", "saw", "seen", "seeing"), 1, "Saw adalah past form dari see 🎥"),
                QuizQuestion("Complete the Past Simple: 'Santi ______ her homework two hours ago.'", listOf("do", "does", "did", "done"), 2, "Did adalah past form dari do ✍️"),
                QuizQuestion("Complete the Past Simple: 'I ______ some delicious cakes this morning.'", listOf("buy", "buys", "bought", "buying"), 2, "Bought adalah past form dari buy 🍰"),
                QuizQuestion("Complete the Past Simple: 'They ______ not play football yesterday.'", listOf("do", "does", "did", "done"), 2, "Negatif lampau menggunakan did + not (did not) ⚽")
            )
            7 -> listOf(
                QuizQuestion("Complete the Present Continuous: 'Look! The baby ______ right now.'", listOf("sleep", "sleeps", "is sleeping", "are sleeping"), 2, "Tunggal baby menggunakan is + verb-ing: is sleeping 👶"),
                QuizQuestion("Complete the Present Continuous: 'Listen! They ______ a song.'", listOf("sing", "sings", "is singing", "are singing"), 3, "Jamak they menggunakan are + verb-ing: are singing 🎵"),
                QuizQuestion("Complete the Present Continuous: 'I ______ my room at this moment.'", listOf("clean", "cleans", "am cleaning", "is cleaning"), 2, "I menggunakan am + verb-ing: am cleaning 🧹"),
                QuizQuestion("Complete the Present Continuous: '______ you doing your homework now?'", listOf("Is", "Are", "Am", "Do"), 1, "Tanya continuous subjek 'you' memakai Are 📖"),
                QuizQuestion("Complete the Present Continuous: 'She is ______ a novel in her bedroom.'", listOf("write", "writes", "writing", "written"), 2, "Continuous menggunakan bentuk verb-ing: writing ✍️")
            )
            8 -> listOf(
                QuizQuestion("Choose the correct article: 'I eat ______ apple every day.'", listOf("a", "an", "the", "some"), 1, "An digunakan sebelum huruf vokal: an apple 🍎"),
                QuizQuestion("Choose the correct article: 'Budi is ______ clever boy.'", listOf("a", "an", "the", "any"), 0, "A digunakan sebelum bunyi konsonan: a clever boy 👦"),
                QuizQuestion("Choose the correct article: 'Could you open ______ main window?'", listOf("a", "an", "the", "any"), 2, "The untuk benda spesifik yang sudah diketahui pendengar 🪟"),
                QuizQuestion("Choose the correct article: 'Let's visit ______ monument downtown.'", listOf("a", "an", "the", "some"), 2, "Benda khusus berpasangan dengan: the monument 🏛️"),
                QuizQuestion("Choose the correct article: 'It takes ______ hour to drive there.'", listOf("a", "an", "the", "many"), 1, "Bunyi awal hour adalah vokal 'aur', gunakan: an hour ⏰")
            )
            9 -> listOf(
                QuizQuestion("Choose the possessive adjective: 'This is my bag. This is ______ bag.'", listOf("my", "your", "his", "her"), 0, "Kepemilikan saya adalah: my bag 🎒"),
                QuizQuestion("Choose the possessive adjective: 'Santi has a pet rabbit. This is ______ rabbit.'", listOf("his", "her", "its", "their"), 1, "Kepemilikan wanita (Santi) adalah: her rabbit 🐰"),
                QuizQuestion("Choose the possessive adjective: 'Budi rides a red bicycle. It is ______ bicycle.'", listOf("my", "her", "his", "our"), 2, "Kepemilikan pria (Budi) adalah: his bicycle 🚲"),
                QuizQuestion("Choose the possessive adjective: 'The dog wagged ______ tail happily.'", listOf("his", "her", "its", "their"), 2, "Kepemilikan hewan/benda tunggal adalah: its tail 🐕"),
                QuizQuestion("Choose the possessive adjective: 'We bought a house. It is ______ warm house.'", listOf("our", "their", "your", "its"), 0, "Kepemilikan kami/kita adalah: our house 🏠")
            )
            10 -> listOf(
                QuizQuestion("Complete the Present Perfect: 'I ______ finished my report.'", listOf("has", "have", "am", "did"), 1, "I berpasangan dengan have + V3: have finished 📄"),
                QuizQuestion("Complete the Present Perfect: 'Santi ______ already gone to school.'", listOf("has", "have", "is", "was"), 0, "Santi (tunggal) berpasangan dengan has + V3: has gone 🏫"),
                QuizQuestion("Complete the Present Perfect: 'They ______ lived here for five years.'", listOf("has", "have", "are", "were"), 1, "They berpasangan dengan have + V3: have lived 🏡"),
                QuizQuestion("Complete the Present Perfect: 'Has Budi ______ his dinner?'", listOf("eat", "eats", "ate", "eaten"), 3, "Bentuk past participle (V3) dari eat adalah eaten 🍽️"),
                QuizQuestion("Complete the Present Perfect: 'We have ______ this movie three times.'", listOf("see", "saw", "seeing", "seen"), 3, "Bentuk V3 dari see adalah seen 🎬")
            )
            11 -> listOf(
                QuizQuestion("Choose the dynamic modal: 'He is very smart. He ______ speak four languages.'", listOf("must", "can", "should", "might"), 1, "Can melambangkan kapasitas kemampuan (ability) 🗣️"),
                QuizQuestion("Choose the dynamic modal: 'It's late. You ______ go home now.'", listOf("can", "should", "might", "would"), 1, "Should melambangkan saran/anjuran (advice) 🏠"),
                QuizQuestion("Choose the dynamic modal: 'This is a red light. You ______ stop your car.'", listOf("must", "can", "should", "might"), 0, "Must melambangkan kewajiban mutlak (obligation) 🚥"),
                QuizQuestion("Choose the dynamic modal: 'Take an umbrella. It ______ rain later.'", listOf("must", "might", "should", "shall"), 1, "Might melambangkan kemungkinan kecil (possibility) 🌧️"),
                QuizQuestion("Choose the dynamic modal: 'Excuse me, ______ you please help me?'", listOf("should", "must", "could", "might"), 2, "Could melambangkan permohonan sopan (polite request) 🤝")
            )
            12 -> listOf(
                QuizQuestion("Choose the superlative form: 'Mount Everest is the ______ mountain on Earth.'", listOf("high", "higher", "highest", "most high"), 2, "Superlatif satu suku kata berakhiran -est: highest 🏔️"),
                QuizQuestion("Choose the superlative form: 'This room is the ______ room in the hotel.'", listOf("clean", "cleaner", "cleanest", "most clean"), 2, "Superlatif dari clean adalah cleanest ✨"),
                QuizQuestion("Choose the superlative form: 'Santi is the ______ girl in our classroom.'", listOf("beautiful", "more beautiful", "most beautiful", "beautifulest"), 2, "Kata sifat panjang superlatif berpasangan dengan: most beautiful 🌸"),
                QuizQuestion("Choose the superlative form: 'This is the ______ book I have ever read.'", listOf("good", "better", "best", "goodest"), 2, "Superlatif tidak beraturan dari good adalah best 📖"),
                QuizQuestion("Choose the superlative form: 'He had the ______ score in Mathematics.'", listOf("bad", "worse", "worst", "baddest"), 2, "Superlatif tidak beraturan dari bad adalah worst 📉")
            )
            13 -> listOf(
                QuizQuestion("Choose the conjunction: 'Budi went to school ______ he was feeling sick.'", listOf("because", "although", "unless", "since"), 1, "Although (meskipun) menunjukkan kontradiksi keadaan 🏫"),
                QuizQuestion("Choose the conjunction: 'We stayed at home ______ it was raining heavily.'", listOf("although", "because", "unless", "besides"), 1, "Because (karena) menunjukkan hubungan sebab-akibat 🌧️"),
                QuizQuestion("Choose the conjunction: 'You cannot enter the exam ______ you have a permit.'", listOf("because", "unless", "although", "whereas"), 1, "Unless (kecuali jika) menyatakan syarat pengecualian 📝"),
                QuizQuestion("Choose the conjunction: 'Santi studies hard ______ she wants to pass.'", listOf("so", "because", "but", "although"), 1, "Because menerangkan alasan kerja kerasnya 🧠"),
                QuizQuestion("Choose the conjunction: 'The sun was warm, ______ the wind was chilly.'", listOf("because", "yet", "since", "unless"), 1, "Yet (namun) menyandingkan pertentangan kecil 💨")
            )
            14 -> listOf(
                QuizQuestion("Complete the Conditional Type 1: 'If it ______ tomorrow, I will stay home.'", listOf("rain", "rains", "will rain", "rained"), 1, "Kondisi masa depan memakai Simple Present: rains 🌧️"),
                QuizQuestion("Complete the Conditional Type 1: 'We will pass the exam if we ______ hard.'", listOf("study", "studies", "will study", "studied"), 0, "Anak kalimat kondisi memakai Simple Present murni: study 📝"),
                QuizQuestion("Complete the Conditional Type 1: 'If she comes to my house, I ______ make coffee.'", listOf("will", "would", "am", "did"), 0, "Induk kalimat kondisi memakai future modal: will coffee ☕"),
                QuizQuestion("Complete the Conditional Type 1: 'If you ______ lazy, you will fail.'", listOf("are", "am", "is", "be"), 0, "Simple present auxiliary untuk 'you' adalah: are 🥱"),
                QuizQuestion("Complete the Conditional Type 1: 'He will earn money if he ______ diligent.'", listOf("is", "are", "am", "be"), 0, "Present auxiliary untuk 'he' tunggal adalah: is 💼")
            )
            15 -> listOf(
                QuizQuestion("Complete the Passive Voice: 'The beautiful letter ______ by Budi yesterday.'", listOf("writes", "is written", "was written", "wrote"), 2, "Pasif lampau tunggal menggunakan was + V3: was written ✉️"),
                QuizQuestion("Complete the Passive Voice: 'This tasty bread is ______ by the chef every morning.'", listOf("baked", "bakes", "baking", "bake"), 0, "Pasif Present menggunakan is + V3: baked 🍞"),
                QuizQuestion("Complete the Passive Voice: 'The active robbers ______ caught by the police.'", listOf("was", "were", "is", "has"), 1, "Pasif lampau jamak (robbers) menggunakan were: were caught 👮"),
                QuizQuestion("Complete the Passive Voice: 'The classroom ______ cleaned by students later.'", listOf("is", "was", "will be", "be"), 2, "Pasif masa depan menggunakan will be + V3: will be cleaned 🏫"),
                QuizQuestion("Complete the Passive Voice: 'The new football rules are ______ by the referee.'", listOf("enforce", "enforces", "enforced", "enforcing"), 2, "Pasif Present jamak menggunakan are + V3: enforced ⚽")
            )
            else -> getGrammarPool(15)
        }
    }

    private fun getConversationPool(grade: Int): List<QuizQuestion> {
        return when (grade) {
            1 -> listOf(
                QuizQuestion("Greeting: 'Hello, how are you?'", listOf("I am Budi", "I am nine years old", "Fine, thank you! 😊", "Nice to meet you"), 2, "Jawaban sopan menanyakan kabar adalah Fine, thank you!"),
                QuizQuestion("Greeting: 'What is your name?'", listOf("Good morning!", "My name is Budi. 👦", "Yes, please", "Thank you"), 1, "Menanyakan nama dibalas dengan mengenalkan nama sendiri."),
                QuizQuestion("Greeting: 'How old are you?'", listOf("I am fine", "I am ten years old. 🎂", "My name is Santi", "Goodbye"), 1, "Menanyakan umur dijawab dengan hitungan tahun."),
                QuizQuestion("Greeting: 'Nice to meet you.'", listOf("You are welcome", "Nice to meet you too! 😊", "Excuse me", "No problem"), 1, "Respon membalas senang berkenalan adalah Nice to meet you too!"),
                QuizQuestion("Greeting: 'Santi: Goodbye Budi!' - Budi: '______ !'", listOf("Hello!", "Goodbye Santi! 👋", "Thank you", "Sorry"), 1, "Balasan dari salam perpisahan adalah salam perpisahan serupa.")
            )
            2 -> listOf(
                QuizQuestion("Response: 'Thank you very much for your help!'", listOf("You are welcome! 😊", "I am sorry", "Yes, of course", "Goodbye"), 0, "Balasan terima kasih yang sopan adalah sama-sama (You are welcome!)"),
                QuizQuestion("Response: 'I am so sorry I broke your pencil.'", listOf("Thank you", "It is okay, no problem! 😊", "Goodbye", "Excuse me"), 1, "Balasan toleransi memaafkan adalah tidak apa-apa (It is okay!)"),
                QuizQuestion("Response: 'Can I borrow your ruler please?'", listOf("No, thanks", "Sure, here it is! 📏", "Happy birthday", "You are welcome"), 1, "Persetujuan meminjamkan barang adalah Tentu saja, ini dia!"),
                QuizQuestion("Response: 'Excuse me, could you open the door please?'", listOf("Goodbye", "No, I am busy", "Certainly! 🚪", "Thank you"), 2, "Respon menyanggupi permohonan tolong secara sopan (Certainly!)"),
                QuizQuestion("Response: 'Happy birthday, Budi!'", listOf("I am sorry", "Thank you so much! 🎂", "Nice to meet you", "Goodbye"), 1, "Balasan ucapan selamat ulang tahun adalah berterima kasih.")
            )
            3 -> listOf(
                QuizQuestion("Asking: 'Where is the school library?'", listOf("It costs ten dollars", "Go straight and turn left. 📚", "It is 9 o'clock", "It is hot today"), 1, "Menanyakan tempat dibalas dengan petunjuk arah jalan."),
                QuizQuestion("Asking: 'How much is this school notebook?'", listOf("It is very heavy", "It is on the table", "It costs two dollars. 💵", "I like to read it"), 2, "Menanyakan harga dibalas dengan nominal uang."),
                QuizQuestion("Asking: 'What time is it now?'", listOf("It is sunny", "I am studying", "It is exactly 10 AM. ⏰", "It is a big clock"), 2, "Menanyakan waktu saat ini dibalas dengan penunjuk jam."),
                QuizQuestion("Asking: 'How do you go to school?'", listOf("By bicycle. 🚲", "With my friends", "At 7 AM", "I study hard"), 0, "Menanyakan akomodasi perjalanan dibalas kendaraan transportasi."),
                QuizQuestion("Asking: 'Why are you crying, Budi?'", listOf("I am so happy", "Because I lost my pen. 😢", "I am going to school", "I am nine"), 1, "Menanyakan alasan dibalas dengan keterangan penyebab (Because...)")
            )
            4 -> listOf(
                QuizQuestion("Invitation: 'Would you like to come to my birthday party?'", listOf("No, I cannot buy it", "Yes, I would love to! 🥳", "Happy birthday to you", "It is very far"), 1, "Respon menerima undangan gembira adalah (Yes, I would love to!)"),
                QuizQuestion("Invitation: 'Let's play football in the yard!'", listOf("That is a good idea! ⚽", "No, I draw a picture", "Football is heavy", "Thank you"), 0, "Respon menyetujui ajakan bermain adalah Ide bagus! (That is a good idea!)"),
                QuizQuestion("Invitation: 'Shall we study math together tomorrow?'", listOf("No, I am not math", "Sure, let's do that! 📚", "Math is hard", "Goodbye"), 1, "Persetujuan ajakan belajar bersama masif, (Sure, let's do that!)"),
                QuizQuestion("Invitation: 'Do you want to join our study group?'", listOf("Yes, that sounds fun! 🏫", "I don't like groups", "Maybe next year", "You are welcome"), 0, "Menerima tawaran gabung belajar (Yes, that sounds fun!)"),
                QuizQuestion("Invitation: 'Would you like to have lunch with me?'", listOf("I am full", "I would be delighted! 🍽️", "Lunch is tasty", "Sorry"), 1, "Menerima tawaran makan siang sopan (I would be delighted!)")
            )
            5 -> listOf(
                QuizQuestion("Dining: 'What would you like to order, sir?'", listOf("I would like a cheeseburger 🍔", "The table is clean", "Here is the bill", "No, thanks"), 0, "Menjawab pertanyaan pelayanan meja makan dengan memesan makanan."),
                QuizQuestion("Dining: 'Would you like a glass of orange juice?'", listOf("Juice is yellow", "Yes, please! That would be nice 🍊", "No, I am eating", "Thank you"), 1, "Menerima tawaran minuman secara sopan (Yes, please!)"),
                QuizQuestion("Dining: 'Excuse me, could we have the bill please?'", listOf("The bill is expensive", "Certainly, I'll bring it right away 🧾", "No, thank you", "Goodbye"), 1, "Tanggapan pelayan kasir membawakan bon rincian pembayaran."),
                QuizQuestion("Dining: 'Is everything alright with your dinner?'", listOf("No, it is night", "Yes, the food is absolutely delicious! 🍛", "The chef is busy", "Thank you"), 1, "Jawaban peninjauan kepuasan menyantap kuliner oleh pelanggan."),
                QuizQuestion("Dining: 'Can I have some more water, please?'", listOf("Water is cold", "Of course, here is another glass 🥛", "No, it is full", "You are welcome"), 1, "Menyahuti permintaan tambah air mineral dalam cangkir.")
            )
            6 -> listOf(
                QuizQuestion("Travel: 'Could you tell me how to reach the central train station?'", listOf("Go straight, it's opposite the bank 🚉", "Train is fast", "I have a ticket", "No, thank you"), 0, "Petunjuk arah jalan spesifik menuju stasiun kereta api."),
                QuizQuestion("Travel: 'Do you have any rooms available for tonight?'", listOf("Yes, we have a double room available 🏨", "I am sleeping", "No, thank you", "It is expensive"), 0, "Reservasi hotel menanyakan kamar kosong dimalam hari."),
                QuizQuestion("Travel: 'Where is the boarding gate for flight GA200?'", listOf("It is Gate 4, on the second floor ✈️", "Flight is long", "I like airplanes", "Goodbye"), 0, "Menanyakan pintu masuk darat pesawat terbang bandara."),
                QuizQuestion("Travel: 'How long does it take to get to the airport?'", listOf("It costs ten dollars", "About 30 minutes by taxi 🚖", "The airport is big", "I will go"), 1, "Menanyakan durasi waktu perjalanan menuju bandar udara."),
                QuizQuestion("Travel: 'Can I see your passport and ticket, please?'", listOf("No, thanks", "Sure, here they are 🛂", "I lost my passport", "Goodbye"), 1, "Pemeriksaan petugas bandara menyahuti visa paspor.")
            )
            7 -> listOf(
                QuizQuestion("Opinion: 'What do you think of our new school uniform?'", listOf("I think it is very stylish! 👔", "The uniform is green", "I have two uniforms", "No, thank you"), 0, "Mengemukakan ekspresi pendapat pribadi atas seragam sekolah baru."),
                QuizQuestion("Opinion: 'Do you agree that mathematics is very important?'", listOf("I completely agree with you! 📊", "Math is hard", "Yes, I study it", "Not at all"), 0, "Menyetujui kesimpulan pentingnya pelajaran ilmu hitung logika."),
                QuizQuestion("Opinion: 'What is your opinion about social media?'", listOf("In my opinion, it can be addictive if not managed 📱", "I have Instagram", "It is blue", "No, thanks"), 0, "Pendapat kritis bijaksana mengenai dampak jejaring sosial."),
                QuizQuestion("Opinion: 'I think reading books is better than watching television.'", listOf("That is exactly what I think! 📚", "TV is colorful", "I don't have a TV", "Yes, sir"), 0, "Menyetujui opini kegemaran membaca buku dibanding pemirsa layar kaca."),
                QuizQuestion("Opinion: 'Would you say studying abroad is a good experience?'", listOf("Absolutely, it broadens our horizons 🌏", "It is far away", "No, it is bad", "Goodbye"), 0, "Menilai keuntungan luas menimba ilmu diluar negeri.")
            )
            8 -> listOf(
                QuizQuestion("Work: 'What do you do for a living?'", listOf("I am a software engineer 💻", "I live in Jakarta", "I am very busy", "Yes, please"), 0, "Menjawab jenis profesi mata pencaharian pencari kerja."),
                QuizQuestion("Work: 'Could we reschedule our meeting to Thursday?'", listOf("Certainly, Thursday at 10 AM works for me 🗓️", "Thursday is hot", "No, I am sleeping", "Thank you"), 0, "Menyetujui penjadwalan ulang rapat kerja kantor."),
                QuizQuestion("Work: 'Who is in charge of this department?'", listOf("Mr. Budi is the department manager 👨‍💼", "It is big", "Yes, I know", "No, thanks"), 0, "Menerangkan penanggung jawab kepala departemen perusahaan."),
                QuizQuestion("Work: 'How do you handle stressful deadlines?'", listOf("I make a priority list and focus on one task 📈", "Deadlines are bad", "I cry", "Goodbye"), 0, "Merespon cara kerja profesional mengurai kendala tugas berat."),
                QuizQuestion("Work: 'Are you available for a job interview tomorrow?'", listOf("Yes, I would be happy to attend 🤝", "Interview is hard", "No, I am lazy", "Thank you"), 0, "Menyanggupi undangan resmi interview kerja.")
            )
            9 -> listOf(
                QuizQuestion("Politeness: 'Would you mind if I opened the window?'", listOf("Not at all, go ahead! 🪟", "The window is glass", "Yes, I mind", "Goodbye"), 0, "Permohonan sopan memberi lampu hijau membuka jendela kamar."),
                QuizQuestion("Politeness: 'May I request a minute of your time?'", listOf("Of course, how can I assist you? 🤝", "I don't have time", "Yes, please", "No, thanks"), 0, "Menanggapi sopan sapaan santun memohon waktu berdialog."),
                QuizQuestion("Politeness: 'I am extremely grateful for your kindness.'", listOf("It was the least I could do, glad to help! 😊", "Thank you too", "I am happy", "No, thanks"), 0, "Balasan rendah hati atas sanjungan kedermawanan bernilai tinggi."),
                QuizQuestion("Politeness: 'Pardon me, I didn't catch what you said.'", listOf("No problem, I will repeat it for you 🗣️", "Speak louder", "I am sleeping", "Yes"), 0, "Meminta maaf kesopanan tidak menangkap ucapan dan dibalas ramah."),
                QuizQuestion("Politeness: 'Could I trouble you for a glass of water?'", listOf("It's no trouble at all, here you are! 🥛", "Water is wet", "No, buy it", "Goodbye"), 0, "Menyediakan air bantuan permohonan santun (It's no trouble!)")
            )
            10 -> listOf(
                QuizQuestion("Debate: 'I am of the opinion that exams should be abolished.'", listOf("I beg to differ, exams measure core knowledge 📝", "Exams are hard", "Yes, please", "No, thanks"), 0, "Mengemukakan sudut pandang tandingan kritis dalam suatu debat akademis."),
                QuizQuestion("Debate: 'Is it true that technology reduces physical interaction?'", listOf("To some extent yes, but it also connects faraway family 🌐", "Yes, it is", "No, thanks", "Goodbye"), 0, "Menimbang seimbang pro-kontra modernisasi pengaruh teknologi."),
                QuizQuestion("Debate: 'What is your counter-argument to this theory?'", listOf("My analysis shows several fatal flaws in assumptions 🧠", "I have no theory", "It is wrong", "Thank you"), 0, "Menyangkal argumen dengan menjabarkan kelemahan asumsi dasar."),
                QuizQuestion("Debate: 'Could you elaborate on your statement?'", listOf("Certainly, let me provide some statistical data 📊", "No, I can't", "Yes, it is big", "Goodbye"), 0, "Menyatakan kesanggupan mengelaborasi opini berbasis data akurat."),
                QuizQuestion("Debate: 'How do we reach a consensus on this conflict?'", listOf("Through mutual compromise and open dialogue 🕊️", "By fighting", "No, thanks", "I am busy"), 0, "Merumuskan solusi damai mufakat konflik kelompok.")
            )
            11 -> listOf(
                QuizQuestion("Negotiation: 'The price you quoted is slightly above our budget.'", listOf("We can offer a 10% discount for bulk orders 🤝", "Price is high", "Ok, buy it", "No, thank you"), 0, "Menawarkan jalan keluar negosiasi potongan harga pesanan kuantitas."),
                QuizQuestion("Negotiation: 'What terms of payment do you offer?'", listOf("We accept bank transfers within 30 days of delivery 🏛️", "Money is nice", "Cash only", "No, thanks"), 0, "Menjelaskan opsi kesepakatan pembayaran tenggat niaga."),
                QuizQuestion("Negotiation: 'Can we agree on a faster delivery date?'", listOf("If we adjust shipping costs, we can expedite it 🚚", "No, it is slow", "Yes, tomorrow", "Goodbye"), 0, "Solusi mempercepat pengiriman barang logistik lewat penyesuaian biaya."),
                QuizQuestion("Negotiation: 'We are ready to sign the contract if you agree.'", listOf("Excellent, let's proceed to finalize the agreement ✍️", "The pen is blue", "No, wait", "Thank you"), 0, "Konfirmasi akhir penandatanganan kesepakatan kontrak dagang."),
                QuizQuestion("Negotiation: 'What if we divide the maintenance costs equally?'", listOf("That seems like a fair and reasonable compromise ⚖️", "Costs are expensive", "No, pay all", "Goodbye"), 0, "Menyetujui pembagian biaya perawatan setara.")
            )
            12 -> listOf(
                QuizQuestion("Interview: 'Why should we hire you for this position?'", listOf("I possess the technical skills and industry experience required 💼", "I need money", "I am Budi", "Yes, please"), 0, "Alasan profesional tangguh pelamar meyakinkan pewawancara kerja."),
                QuizQuestion("Interview: 'How do you handle critical feedback from superiors?'", listOf("I view it as an opportunity to improve my performance 📈", "I get angry", "I ignore it", "Thank you"), 0, "Menilai bijak umpan balik kritis pimpinan sebagai modal evaluasi."),
                QuizQuestion("Interview: 'Where do you see yourself in five years?'", listOf("I aspire to lead a project team and drive innovation 🚀", "On vacation", "I don't know", "Goodbye"), 0, "Visi kontribusi karir jangka menengah pelamar kerja."),
                QuizQuestion("Interview: 'What is your greatest professional success?'", listOf("Streamlining core database processes to boost efficiency by 30% 💾", "Buying a car", "Sleeping 8 hours", "Yes"), 0, "Keberhasilan karir terukur meningkatkan efisiensi operasional."),
                QuizQuestion("Interview: 'Do you have any questions for us?'", listOf("What are the key goals for this team in the coming year? 🗺️", "Can I go home?", "How much is my salary?", "No"), 0, "Menanyakan pertanyaan berbobot balik mengenai target tim perusahaan.")
            )
            13 -> listOf(
                QuizQuestion("Presenting: 'Let's begin by outlining the main objectives.'", listOf("First, we aim to expand our customer base 📈", "Objective is a noun", "No, stop", "Thank you"), 0, "Memulai presentasi bisnis dengan merinci target ekspansi korporat."),
                QuizQuestion("Presenting: 'To illustrate this point, let's look at this chart.'", listOf("As you can see, sales grew exponentially in Q3 📊", "The chart is green", "I have no chart", "Goodbye"), 0, "Menguraikan data grafik visual pertumbuhan penjualan kian melesat."),
                QuizQuestion("Presenting: 'This slide demonstrates our research methodology.'", listOf("We gathered feedback from over a thousand active users 👥", "Methods are hard", "Yes, it does", "No, thanks"), 0, "Menerangkan metodologi penelitian berbasis ribuan koresponden aktif."),
                QuizQuestion("Presenting: 'Now, I'd like to hand over to my teammate Budi.'", listOf("Thank you, Budi will now explain the technical details 🎙️", "Budi is typing", "He is gone", "No"), 0, "Menyerahkan giliran pemaparan presentasi ke rekan kerja kelompok."),
                QuizQuestion("Presenting: 'In conclusion, we highly recommend this strategy.'", listOf("Thank you for your attention, any questions? 📣", "That was long", "No conclusion", "Goodbye"), 0, "Menutup presentasi korporat khidmat membuka sesi tanya jawab.")
            )
            14 -> listOf(
                QuizQuestion("Academic: 'How do you define the term \"globalization\"?'", listOf("The integration of regional economies into a unified global market 🌏", "It's about traveling", "A big word", "No, thanks"), 0, "Mendefinisikan istilah globalisasi ekonomi dunia secara komprehensif."),
                QuizQuestion("Academic: 'What is the primary hypothesis of your Thesis?'", listOf("That carbon pricing significantly motivates emission reductions 🍃", "I have no hypothesis", "Writing is hard", "Yes"), 0, "Hipotesis utama penelitian tesis kelestarian energi terbarukan."),
                QuizQuestion("Academic: 'Which methodology was applied to analyze the sample?'", listOf("We utilized qualitative comparative analysis alongside statistics 📊", "We looked at it", "None", "Goodbye"), 0, "Metodologi ilmiah kombinasi analisis komparatif kualitatif."),
                QuizQuestion("Academic: 'What are the main implications of these findings?'", listOf("They suggest a major shift in public health policies is needed 🩺", "No implications", "It is a secret", "Thanks"), 0, "Implikasi temuan riset mendesak perubahan regulasi kesehatan publik."),
                QuizQuestion("Academic: 'How does your research contribute to the existing literature?'", listOf("It addresses a critical gap regarding developing nations' trade 🗺️", "It is very long", "No contribution", "Goodbye"), 0, "Kontribusi penelitian mengisi kekosongan kajian negara berkembang.")
            )
            15 -> listOf(
                QuizQuestion("Philosophical: 'What is the relationship between ethics and technology?'", listOf("Technology must be bounded by ethical codes to prevent societal harm ⚖️", "Technology is fast", "No relationship", "Yes"), 0, "Ulasan hubungan krusial etika moral membendung bahaya laju teknologi."),
                QuizQuestion("Philosophical: 'Do you believe that art acts as a mirror of society?'", listOf("Indubitably, art reflects both the struggles and virtues of its era 🎨", "I like drawings", "No, not at all", "Goodbye"), 0, "Apresiasi seni luhur sebagai refleksi perjuangan zaman."),
                QuizQuestion("Philosophical: 'How do we define the essence of human happiness?'", listOf("A persistent state of purpose, connection, and spiritual peace 🧘", "Having a lot of money", "Sleeping all day", "No"), 0, "Definisi filosofis hakikat kebahagiaan sejati ketenangan batin."),
                QuizQuestion("Philosophical: 'What is the paradox of tolerance in a society?'", listOf("That absolute tolerance must lead to the rise of intolerance 🌀", "Tolerance is nice", "No paradox", "Thank you"), 0, "Penjabaran paradoks toleransi sosial menurut kacamata filsafat."),
                QuizQuestion("Philosophical: 'Does language construct or merely represent our reality?'", listOf("It structures our thoughts and actively shapes how we perceive the world 🗣️", "It is just words", "I speak Indonesian", "Goodbye"), 0, "Diskursus linguistik bahasa mendominasi konstruksi persepsi realita.")
            )
            else -> getConversationPool(15)
        }
    }

    private fun getReadingPool(grade: Int): List<QuizQuestion> {
        return when (grade) {
            1 -> listOf(
                QuizQuestion("Read: 'The cat is black. It has white paws.' - What color is the cat?", listOf("White", "Black 🐈‍⬛", "Brown", "Grey"), 1, "The cat is black, artinya kucing itu hitam."),
                QuizQuestion("Read: 'The flower is red. It smells sweet.' - What is red?", listOf("The grass", "The sky", "The flower 🌹", "The tree"), 2, "The flower is red, artinya bunga itu merah."),
                QuizQuestion("Read: 'This is a big book. It is blue.' - What is blue?", listOf("The pencil", "The book 📘", "The chair", "The table"), 1, "It is blue merujuk kepada buku (the book)."),
                QuizQuestion("Read: 'Budi is happy. He has a toy car.' - What does Budi have?", listOf("A ball", "A toy car 🚗", "A book", "A cat"), 1, "He has a toy car, artinya dia punya mobil mainan."),
                QuizQuestion("Read: 'Santi plays with her doll. The doll is cute.' - What is cute?", listOf("The doll 🧸", "The dog", "The ball", "The house"), 0, "The doll is cute, bonekamu lucu sekali.")
            )
            2 -> listOf(
                QuizQuestion("Read: 'Budi has a red bicycle. He rides it to school.' - What does Budi ride to school?", listOf("A red car", "A red bicycle 🚲", "A bus", "A train"), 1, "Budi rides a red bicycle, bersepeda ke sekolah."),
                QuizQuestion("Read: 'My mother cooks in the kitchen. She makes soup.' - What is mother making?", listOf("Tea", "Bread", "Soup 🥣", "Rice"), 2, "She makes soup, ibu sedang memasak sup."),
                QuizQuestion("Read: 'The sun is shine bright. The day is very hot.' - How is the day?", listOf("Cold", "Raining", "Very hot ☀️", "Windy"), 2, "The day is very hot, pertanda siang hari yang terik."),
                QuizQuestion("Read: 'Birds have wings. They can fly high.' - What can birds do?", listOf("Swim", "Fly high 🦅", "Run fast", "Climb trees"), 1, "They can fly high, burung terbang tinggi dengan sayapnya."),
                QuizQuestion("Read: 'A big fish lives in the water. It can swim.' - Where does the fish live?", listOf("On trees", "In the water 🐟", "On the ground", "In the forest"), 1, "In the water, habitat ikan berenang bebas di air.")
            )
            3 -> listOf(
                QuizQuestion("Read: 'Santi lives in a big house with a beautiful garden of roses.' - What flower is in Santi's garden?", listOf("Sunflowers", "Jasmine", "Roses 🌹", "Orchids"), 2, "Garden of roses, mawar merah merekah indah di kebunnya."),
                QuizQuestion("Read: 'Computers are smart. We use them to study.' - Why do we use computers?", listOf("To play game only", "To study 💻", "To cook food", "To wash clothes"), 1, "We use them to study, komputer sebagai sarana belajar."),
                QuizQuestion("Read: 'The school library has five hundred interesting books.' - How many books are in the library?", listOf("Five hundred 📚", "Fifty", "Five thousand", "Five"), 0, "Five hundred berarti lima ratus buku menarik."),
                QuizQuestion("Read: 'Rice comes from paddy fields. Farmers work hard there.' - Who works hard in paddy fields?", listOf("Doctors", "Teachers", "Farmers 👨‍🌾", "Pilots"), 2, "Farmers work hard, pahlawan pangan petani kita."),
                QuizQuestion("Read: 'The baby fell asleep on the clean bed.' - Where did the baby fall asleep?", listOf("On the chair", "On the bed 🛏️", "On the floor", "In the garden"), 1, "On the clean bed, tidur nyenyak di ranjang hangat.")
            )
            4 -> listOf(
                QuizQuestion("Read: 'Doctors work in hospitals. They help sick people get better.' - Where do doctors work?", listOf("In schools", "In offices", "In hospitals 🏥", "At restaurants"), 2, "Doctors work in hospitals, menolong pasien yang sakit."),
                QuizQuestion("Read: 'The library is a quiet place where people can borrow books.' - Which word describes the library?", listOf("Noisy", "Quiet 🤫", "Dirty", "Dangerous"), 1, "A quiet place, perpustakaan sebagai wadah membaca khidmat."),
                QuizQuestion("Read: 'Honeybees fly from flower to flower to harvest sweet nectar.' - What do honeybees harvest?", listOf("Water", "Leaves", "Sweet nectar 🍯", "Seeds"), 2, "Sweet nectar, sari madu yang lezat dari kuntum kembang."),
                QuizQuestion("Read: 'Rain provides water for forests. Trees need water to grow.' - What do trees need to grow?", listOf("Stones", "Water 💧", "Sand", "Fire"), 1, "Trees need water, menyerap air tumbuh rimbun di rimba."),
                QuizQuestion("Read: 'Our team won the school cup trophy after studying hard.' - What did our team win?", listOf("A ball", "A trophy cup 🏆", "A bicycle", "A book"), 1, "A trophy cup, piala kemenangan hasil kerja sama tim.")
            )
            5 -> listOf(
                QuizQuestion("Read: 'Bees collect nectar from flowers to make honey in their hive.' - What do bees make?", listOf("Wax", "Honey 🍯", "Milk", "Jam"), 1, "They make honey, lebah memproduksi cairan madu manis."),
                QuizQuestion("Read: 'National parks protect wild animals. Hunting is strictly illegal there.' - What is illegal in national parks?", listOf("Camping", "Hunting 🏹", "Hiking", "Photography"), 1, "Hunting is strictly illegal, larangan keras memburu satwa."),
                QuizQuestion("Read: 'Astronauts travel into outer space in a rocket.' - How do astronauts travel to space?", listOf("By airplane", "In a rocket 🚀", "By submarine", "By train"), 1, "In a rocket, wahana antariksa meluncur menembus awan."),
                QuizQuestion("Read: 'Volcanoes can erupt. They release hot lava and ash.' - What do volcanoes release when they erupt?", listOf("Ice and snow", "Hot lava and ash 🌋", "Clean water", "Gold"), 1, "Hot lava and ash, semburan magma pijar perut bumi."),
                QuizQuestion("Read: 'The English clock tower Big Ben is located in London.' - In which city is Big Ben located?", listOf("Paris", "London 🇬🇧", "New York", "Tokyo"), 1, "Located in London, ikon menara jam legendaris Britania Raya.")
            )
            6 -> listOf(
                QuizQuestion("Read: 'A pilot is a person who flies an airplane.' - What does a pilot do?", listOf("Cures patients", "Flies an airplane ✈️", "Cooks food", "Teaches children"), 1, "Flies an airplane, mengemudikan burung besi lintas benua."),
                QuizQuestion("Read: 'A dentist is a specialized doctor who cares for our teeth.' - What body part does a dentist care for?", listOf("Eyes", "Teeth 🦷", "Heart", "Skin"), 1, "Teeth, merawat kebersihan dan kesehatan gigi geligi."),
                QuizQuestion("Read: 'A carpenter is a highly skilled artisan who makes wooden furniture.' - What material does a carpenter use?", listOf("Metal", "Wood 🪵", "Glass", "Cloth"), 1, "Makes wooden furniture, pahatan bernilai seni perabot kayu."),
                QuizQuestion("Read: 'A chef cooks delicious recipes at fine restaurants.' - Where does a chef work?", listOf("At banks", "At restaurants 👨‍🍳", "At airports", "At schools"), 1, "At restaurants, menyajikan kuliner bercita rasa tinggi."),
                QuizQuestion("Read: 'Meteorologists study weather patterns to predict storms.' - What do meteorologists study?", listOf("Stars and planets", "Weather patterns 🌦️", "Ancient fossils", "Deep oceans"), 1, "Weather patterns, memproyeksikan cuaca penunjang keselamatan penerbangan.")
            )
            7 -> listOf(
                QuizQuestion("Read: 'Mount Everest is the tallest mountain above sea level on Earth.' - What is Mount Everest?", listOf("The deepest trench", "The tallest mountain 🏔️", "The largest desert", "The longest river"), 1, "The tallest mountain, puncak suci atap dunia."),
                QuizQuestion("Read: 'The Sahara is a hot desert covering most of North Africa.' - Where is the Sahara located?", listOf("South America", "North Africa 🏜️", "East Asia", "Europe"), 1, "North Africa, gurun pasir luas membentang gersang."),
                QuizQuestion("Read: 'The Amazon Rainforest produces 20% of Earth's oxygen.' - How much of Earth's oxygen does the Amazon Rainforest produce?", listOf("Ten percent", "Twenty percent 🌳", "Fifty percent", "Five percent"), 1, "Twenty percent, paru-paru bumi rimba belantara basah."),
                QuizQuestion("Read: 'Pacific Ocean is the largest and deepest of Earth's oceans.' - Which ocean is the largest on Earth?", listOf("Indian Ocean", "Pacific Ocean 🌊", "Atlantic Ocean", "Arctic Ocean"), 1, "Pacific Ocean, bentangan air samudera terluas di dunia."),
                QuizQuestion("Read: 'The Nile in Africa is widely considered the longest river on Earth.' - What is the Nile?", listOf("The widest lake", "The longest river 🏞️", "The highest waterfall", "The smallest sea"), 1, "The longest river, berkah aliran air peradaban Afrika.")
            )
            8 -> listOf(
                QuizQuestion("Read: 'The sun is a star at the center of our Solar System.' - What is the sun?", listOf("A planet", "A star ☀️", "A moon", "A comet"), 1, "The sun is a star, bintang induk pemberi kehangatan."),
                QuizQuestion("Read: 'Earth goes around the sun once every 365 days.' - How long does it take for Earth to orbit the sun once?", listOf("Twenty-four hours", "One year/365 days 📅", "Thirty days", "Ten years"), 1, "One year, revolusi bumi menandai pergantian musim."),
                QuizQuestion("Read: 'Gravity is the invisible force that pulls objects toward Earth's center.' - What force holds things on Earth's surface?", listOf("Friction", "Gravity 🪨", "Erosion", "Magnetism"), 1, "Gravity, daya tarik bumi pembentuk tata kosmik."),
                QuizQuestion("Read: 'The moon does not produce its own light; it reflects sunlight.' - Where does the moon's light come from?", listOf("Its hot core", "Reflected sunlight 🌙", "Distress signals", "Neon bulbs"), 1, "Reflected sunlight, pantulan sinar surga dimalam pekat."),
                QuizQuestion("Read: 'Mars is often called the Red Planet due to iron oxide on its surface.' - Why is Mars called the Red Planet?", listOf("It is very hot", "Due to iron oxide on its surface 🪐", "It is made of ruby", "Because of alien plants"), 1, "Due to iron oxide, karat besi tanah merah kering.")
            )
            9 -> listOf(
                QuizQuestion("Read: 'Water boils at 100 degrees Celsius and freezes at 0 degrees.' - At what temperature does water freeze?", listOf("100 degrees", "0 degrees Celsius ❄️", "50 degrees", "Minus 10 degrees"), 1, "Freezes at 0 degrees, batas beku pembentukan kristal es."),
                QuizQuestion("Read: 'Water is made of two hydrogen atoms and one oxygen atom.' - What is the chemical formula of water?", listOf("CO2", "H2O 💧", "NaCl", "O2"), 1, "H2O, dua hidrogen satu oksigen penyusun molekul air."),
                QuizQuestion("Read: 'Sound travels faster through liquids and solids than through air.' - Through which medium does sound travel slowest?", listOf("Steel", "Water", "Air 💨", "Iron"), 2, "Slowest through air, kerapatan molekul gas longgar meredam suara."),
                QuizQuestion("Read: 'Light travels much faster than sound, which is why we see lightning before thunder.' - Why do we see lightning before hearing thunder?", listOf("Thunder is silent", "Light travels faster than sound ⚡", "Lightning is louder", "The sky is dark"), 1, "Light travels faster than sound, laju rambat cahaya mengalahkan gelombang suara."),
                QuizQuestion("Read: 'The human skull acts as a helmet protecting our delicate brain.' - What is the purpose of the skull?", listOf("To assist hearing", "To protect our delicate brain 🧠", "To pump blood", "To absorb food"), 1, "To protect our delicate brain, benteng kokoh pelindung sel nalar.")
            )
            10 -> listOf(
                QuizQuestion("Read: 'Plants use photosynthesis to convert sunlight into chemical energy.' - What is this process called?", listOf("Respiration", "Photosynthesis 🌿", "Evaporation", "Digestion"), 1, "Photosynthesis, proses hijau mengubah cahaya menjadi sari makanan."),
                QuizQuestion("Read: 'Chlorophyll is the green pigment in leaves that absorbs sunlight.' - What is chlorophyll?", listOf("A roots system", "The green pigment in leaves 🍃", "A type of soil", "A plant hormone"), 1, "The green pigment, zat pemurni daun penangkap foton."),
                QuizQuestion("Read: 'Photosynthesis releases oxygen, which supports animal life.' - What gas is released during photosynthesis?", listOf("Carbon dioxide", "Oxygen 💨", "Nitrogen", "Helium"), 1, "Oxygen, gas vitalitas penunjang kehidupan fauna bumi."),
                QuizQuestion("Read: 'During photosynthesis, plants absorb carbon dioxide from the air.' - What gas do plants absorb for photosynthesis?", listOf("Oxygen", "Carbon dioxide 🌫️", "Hydrogen", "Argon"), 1, "Carbon dioxide, menyerap gas buang pembersih udara."),
                QuizQuestion("Read: 'Roots absorb water and nutrients from the soil to support the plant.' - What is the main function of plant roots?", listOf("To make seeds", "To absorb water and nutrients 🪵", "To catch sunlight", "To breathe air"), 1, "To absorb water and nutrients, jangkar kokoh pencari sumber makan.")
            )
            11 -> listOf(
                QuizQuestion("Read: 'A hurricane is a severe tropical storm with high wind speeds.' - What is a hurricane?", listOf("A severe drought", "A severe tropical storm 🌪️", "An active volcano", "An earthquake"), 1, "A severe tropical storm, putaran siklon destruktif cuaca tropis."),
                QuizQuestion("Read: 'The center of a hurricane is called the eye, which is amazingly calm.' - What is the center of a hurricane called?", listOf("The tail", "The eye 👁️", "The core", "The wall"), 1, "The eye, poros sunyi ditengah pusaran maut."),
                QuizQuestion("Read: 'Hurricanes gain their massive energy from warm ocean waters.' - Where do hurricanes gain their energy from?", listOf("Cold mountain ice", "Warm ocean waters 🌊", "Volcanic eruptions", "Forest fires"), 1, "Warm ocean waters, energi uap air laut bergolak hangat."),
                QuizQuestion("Read: 'When a hurricane moves over cold land, it weakens quickly.' - Where does a hurricane lose its strength?", listOf("Over warm seas", "Over cold land 🏔️", "During night time", "Near the equator"), 1, "Over cold land, kehilangan asupan uap panas pendorong siklon."),
                QuizQuestion("Read: 'Meteorologists track hurricanes using advanced satellite systems.' - What technology is used to monitor hurricanes?", listOf("Deep ocean submarines", "Advanced satellite systems 📡", "Geiger counters", "Hot air balloons"), 1, "Advanced satellite systems, pemantauan orbit bumi pencegah bencana dini.")
            )
            12 -> listOf(
                QuizQuestion("Read: 'The Great Wall of China is an ancient wall over 21,000 kilometers long.' - How long is the Great Wall of China?", listOf("2,100 kilometers", "Over 21,000 kilometers 🏛️", "500 miles", "Ten thousand meters"), 1, "Over 21,000 kilometers, mega-struktur legendaris peradaban timur."),
                QuizQuestion("Read: 'The Great Wall was constructed to protect borders from invasions.' - Why was the Great Wall built?", listOf("To serve as a road", "To protect borders from invasions 🛡️", "To control floods", "To guide trade ships"), 1, "To protect borders from invasions, benteng pertahanan dinasti."),
                QuizQuestion("Read: 'It took several centuries and dynasties to construct the wall.' - How long did it take to build the Great Wall?", listOf("Ten years", "Several centuries and dynasties ⏳", "One single month", "A thousand days"), 1, "Several centuries and dynasties, keringat dan darah lintas keturunan."),
                QuizQuestion("Read: 'Millions of workers helped build the wall using local stones.' - What main material was used for building the wall?", listOf("Steel beams", "Local stones and earth 🪨", "Imported glass", "Plastic blocks"), 1, "Local stones, menyusun batu alam sisa sejarah."),
                QuizQuestion("Read: 'Today, the Great Wall is a famous UNESCO World Heritage site.' - What status does the Great Wall hold today?", listOf("A modern military base", "A UNESCO World Heritage site 🗺️", "A restricted science lab", "An active airport"), 1, "A UNESCO World Heritage site, warisan mahakarya arsitektur dunia.")
            )
            13 -> listOf(
                QuizQuestion("Read: 'Artificial Intelligence mimics human cognitive functions in computers.' - What does AI mimic?", listOf("Bird songs", "Human cognitive functions 🧠", "Plant growth", "Ocean currents"), 1, "Human cognitive functions, pemrosesan nalar cerdas buatan."),
                QuizQuestion("Read: 'Machine Learning allows computers to learn from massive datasets without explicit programming.' - What does Machine Learning enable?", listOf("Mechanical cleaning", "Learning from massive datasets 💻", "Writing poetry on paper", "Hardware upgrading"), 1, "Learning from massive datasets, evolusi algoritma mandiri."),
                QuizQuestion("Read: 'Neural Networks are inspired by the structure of the human brain.' - What inspires the design of Neural Networks?", listOf("The solar system", "The human brain structure 🧠", "Ant colonies", "Root systems"), 1, "The human brain structure, permodelan jaringan sinapsis biologis."),
                QuizQuestion("Read: 'AI applications include speech recognition and medical diagnostics.' - Name an active application of AI.", listOf("Building physical bridges", "Speech recognition and medical diagnostics 🩺", "Drilling for crude oil", "Farming"), 1, "Speech recognition, penganalisa suara dan sinyal kesehatan akurat."),
                QuizQuestion("Read: 'Technological ethics are critical to prevent biased AI algorithms.' - Why are tech ethics important in AI?", listOf("To make PCs faster", "To prevent biased AI algorithms ⚖️", "To save electricity", "To manufacture chips"), 1, "To prevent biased AI algorithms, menjaga keadilan keputusan mesin digital.")
            )
            14 -> listOf(
                QuizQuestion("Read: 'The human heart has four chambers that pump blood throughout the body.' - How many chambers does the heart have?", listOf("Two chambers", "Four chambers ❤️", "Six chambers", "Three"), 1, "Four chambers, serambi dan bilik pompa kehidupan."),
                QuizQuestion("Read: 'The left side of the heart pumps oxygen-rich blood to the body.' - What kind of blood does the left heart pump?", listOf("Carbon-rich blood", "Oxygen-rich blood ❤️", "Deoxygenated blood", "No blood"), 1, "Oxygen-rich blood, asupan sari hara kaya oksigen."),
                QuizQuestion("Read: 'The right side of the heart receives blood returning from the body.' - Where does the right heart receive blood from?", listOf("Directly from lungs", "Returning from the body 🦾", "From the brain only", "From liver only"), 1, "Returning from the body, menampung aliran darah kotor."),
                QuizQuestion("Read: 'Heart valves act like one-way doors preventing backflow of blood.' - What is the function of heart valves?", listOf("To generate energy", "To prevent backflow of blood 🛡️", "To filter virus", "To store oxygen"), 1, "To prevent backflow of blood, menjaga satu arah katup sirkulasi."),
                QuizQuestion("Read: 'Regular exercise strengthens the heart muscle and boosts efficiency.' - How does one strengthen the heart?", listOf("By sleeping raw", "By regular exercise 🏃", "By drinking soda", "By eating fat"), 1, "By regular exercise, meningkatkan tonus dan daya tahan organ.")
            )
            15 -> listOf(
                QuizQuestion("Read: 'The release of oxygen by early plants made animal evolution possible.' - What did early plant oxygen release make possible?", listOf("Ice age cooling", "Animal evolution on Earth 🦖", "The formation of the moon", "Oceans dried up"), 1, "Animal evolution on Earth, membuka jalan kehidupan fauna bernafas."),
                QuizQuestion("Read: 'Prior to the oxygen revolution, early life consisted of anaerobic organisms.' - What kind of life existed before the oxygen revolution?", listOf("Complex mammals", "Anaerobic organisms 🦠", "Giant reptiles", "Forest plants"), 1, "Anaerobic organisms, bakteri lambat tanpa kebutuhan oksigen."),
                QuizQuestion("Read: 'Cyanobacteria are believed to have initiated the Great Oxidation Event.' - Which organism initiated the Great Oxidation Event?", listOf("Fern trees", "Cyanobacteria 🧪", "Early birds", "Sea fishes"), 1, "Cyanobacteria, alga biru purba fotosintetis pertama."),
                QuizQuestion("Read: 'This event led to the formation of the ozone layer, masking Earth from UV rays.' - What shield was formed due to the oxygen revolution?", listOf("A magnetic bubble", "The ozone layer 🛡️", "The ocean crust", "A dust cloud"), 1, "The ozone layer, pelindung atmosfer dari sengatan radiasi surya."),
                QuizQuestion("Read: 'Without the ozone layer, land-based multicellular life could not survive.' - What is threatened without the ozone layer?", listOf("Deep-sea thermal vents", "Land-based multicellular life 🌿", "Metallic engines", "Volcanoes"), 1, "Land-based multicellular life, punahnya ekosistem darat bumi.")
            )
            else -> getReadingPool(15)
        }
    }
}
