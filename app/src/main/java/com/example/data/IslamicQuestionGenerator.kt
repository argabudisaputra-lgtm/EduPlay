package com.example.data

import android.content.Context
import kotlin.random.Random

object IslamicQuestionGenerator {

    fun generateLocalIslamicQuestions(context: Context, grade: Int, category: String): List<QuizQuestion> {
        val allQuestions = when (category) {
            "Aqidah Akhlak" -> getAqidahAkhlakPool(grade)
            "Fiqih Ibadah" -> getFiqihIbadahPool(grade)
            "Al-Quran Tajwid" -> getQuranTajwidPool(grade)
            "Sejarah Islam" -> getSejarahIslamPool(grade)
            else -> getAqidahAkhlakPool(grade)
        }

        // Enforce 100% unique, non-duplicate questions for the selected level
        val distinctQs = allQuestions.distinctBy { it.question }
        
        // Securely guarantee there are exactly 5 questions
        if (distinctQs.size >= 5) {
            return distinctQs.take(5)
        }

        return allQuestions.take(5)
    }

    private fun getAqidahAkhlakPool(grade: Int): List<QuizQuestion> {
        return when (grade) {
            1 -> listOf(
                QuizQuestion("Ada berapa jumlah Rukun Islam?", listOf("3", "4", "5 🕌", "6"), 2, "Rukun Islam ada 5 perkara (Syahadat, Shalat, Zakat, Puasa, Haji)."),
                QuizQuestion("Ada berapa jumlah Rukun Iman?", listOf("5", "6 🌟", "7", "8"), 1, "Rukun Iman ada 6 perkara (Iman kepada Allah, Malaikat, Kitab, Rasul, Hari Akhir, Qada-Qadar)."),
                QuizQuestion("Siapakah Tuhan yang wajib kita sembah?", listOf("Nabi Muhammad", "Malaikat", "Allah SWT 🕋", "Orang Tua"), 2, "Tuhan sekalian alam yang wajib disembah adalah Allah SWT."),
                QuizQuestion("Nabi Muhammad SAW adalah utusan...", listOf("Malaikat", "Raja Makkah", "Allah SWT 🕋", "Manusia Biasa"), 2, "Nabi Muhammad SAW adalah utusan terakhir Allah SWT."),
                QuizQuestion("Apa yang kita ucapkan sebelum makan?", listOf("Alhamdulillah", "Astaghfirullah", "Bismillah... 🥣", "Subhanallah"), 2, "Membaca Bismillah agar makanan berkah dan terhindar setan!")
            )
            2 -> listOf(
                QuizQuestion("Apa arti dari kalimat Syahadat?", listOf("Doa tidur", "Ucapan syukur", "Kesaksian tiada Tuhan selain Allah 🕋", "Salam penutup"), 2, "Syahadat artinya persaksian atau ikrar ketauhidan umat Islam."),
                QuizQuestion("Asmaul Husna 'Ar-Rahman' artinya...", listOf("Maha Penyayang", "Maha Pengasih 💖", "Maha Kuasa", "Maha Adil"), 1, "Ar-Rahman artinya Allah Maha Pengasih bagi seluruh makhluk."),
                QuizQuestion("Asmaul Husna 'Ar-Rahim' artinya...", listOf("Maha Penyayang 💖", "Maha Pencipta", "Maha Bijaksana", "Maha Mengetahui"), 0, "Ar-Rahim artinya Allah Maha Penyayang terutama bagi hamba yang beriman."),
                QuizQuestion("Siapa nama malaikat yang bertugas membawa wahyu?", listOf("Malaikat Mikail", "Malaikat Jibril 🪽", "Malaikat Israfil", "Malaikat Izrail"), 1, "Malaikat Jibril adalah penyampai wahyu kepada para nabi."),
                QuizQuestion("Kepada siapa kita pertama kali berbakti setelah Allah dan Rasul?", listOf("Paman", "Teman", "Ibu & Ayah 👨‍👩‍👧‍👦", "Tetangga"), 2, "Orang tua (terutama Ibu) adalah sosok yang paling berhak kita hormati.")
            )
            3 -> listOf(
                QuizQuestion("Sifat wajib Allah 'Wujud' artinya...", listOf("Ada 🕋", "Terdahulu", "Kekal", "Berbeda"), 0, "Wujud artinya Allah itu Ada, dibuktikan dengan keberadaan alam semesta."),
                QuizQuestion("Sifat wajib Allah 'Qidam' artinya...", listOf("Kekal", "Dahulu/Tanpa Permulaan 🌟", "Berdiri sendiri", "Maha Esa"), 1, "Qidam artinya terdahulu, tidak ada yang mendahului keberadaan Allah."),
                QuizQuestion("Sifat wajib Allah 'Baqa' artinya...", listOf("Berbeda", "Kekal/Abadi ♾️", "Kuat", "Mendengar"), 1, "Baqa artinya Kekal, seluruh makhluk hancur melainkan Allah."),
                QuizQuestion("Sifat wajib Allah 'Mukhalafatuhu lil hawaditsi' artinya...", listOf("Berdiri sendiri", "Berbeda dengan makhluk-Nya 🕋", "Esa", "Berkuasa"), 1, "Allah mustahil sama dengan makhluk ciptaan-Nya."),
                QuizQuestion("Syirik adalah perbuatan menyekutukan Allah. Pelakunya disebut...", listOf("Mukmin", "Musyrik 🚨", "Muttaqin", "Mubaligh"), 1, "Pelaku syirik menyembah selain Allah disebut Musyrik.")
            )
            4 -> listOf(
                QuizQuestion("Malaikat diciptakan oleh Allah dari...", listOf("Tanah", "Cahaya (Nur) 🪽", "Api (Nar)", "Angin"), 1, "Malaikat diciptakan dari Nur (Cahaya), sedangkan Jin dari api."),
                QuizQuestion("Siapa malaikat yang ditugaskan membagikan rezeki dan hujan?", listOf("Malaikat Jibril", "Malaikat Mikail 🌧️", "Malaikat Israfil", "Malaikat Malik"), 1, "Malaikat Mikail mengatur pembagian rezeki, hujan, dan angin."),
                QuizQuestion("Malaikat yang bertugas meniup sangkakala tanda kiamat adalah...", listOf("Malaikat Izrail", "Malaikat Israfil 📯", "Malaikat Munkar", "Malaikat Nakir"), 1, "Malaikat Israfil meniup terompet sangkakala atas perintah Allah."),
                QuizQuestion("Dua malaikat yang mencatat amal baik dan amal buruk adalah...", listOf("Jibril dan Mikail", "Raqib dan Atid 📝", "Munkar dan Nakir", "Malik dan Ridwan"), 1, "Raqib mencatat amal baik, Atid mencatat amal buruk manusia."),
                QuizQuestion("Malaikat penjaga pintu Surga yang ramah bernama...", listOf("Malaikat Malik", "Malaikat Ridwan 🌸", "Malaikat Jibril", "Malaikat Izrail"), 1, "Malaikat Ridwan menyambut hamba beriman di pintu Surga.")
            )
            5 -> listOf(
                QuizQuestion("Kitab suci Zabur diturunkan kepada Nabi...", listOf("Musa AS", "Daud AS 📜", "Isa AS", "Muhammad SAW"), 1, "Kitab Zabur diturunkan kepada Nabi Daud AS dalam bahasa Qibti."),
                QuizQuestion("Kitab suci Taurat diturunkan kepada Nabi...", listOf("Ibrahim AS", "Musa AS 📜", "Daud AS", "Sulaiman AS"), 1, "Kitab Taurat diturunkan kepada Nabi Musa AS dalam bahasa Ibrani."),
                QuizQuestion("Kitab suci Injil diturunkan kepada Nabi...", listOf("Isa AS 📜", "Ismail AS", "Muhammad SAW", "Yusuf AS"), 0, "Kitab Injil diturunkan kepada Nabi Isa AS dalam bahasa Suryani."),
                QuizQuestion("Al-Qur'an diturunkan kepada Nabi...", listOf("Nuh AS", "Ibrahim AS", "Muhammad SAW 🕋", "Musa AS"), 2, "Al-Qur'an diturunkan kepada Nabi Muhammad SAW bahasa Arab."),
                QuizQuestion("Suhuf adalah lembaran wahyu sebelum kitab. Nabi yang menerima suhuf terbanyak adalah...", listOf("Nabi Isa", "Nabi Ibrahim & Nabi Musa AS 📄", "Nabi Daud", "Nabi Nuh"), 1, "Nabi Ibrahim dan Nabi Musa menerima suhuf berupa lembaran petunjuk.")
            )
            6 -> listOf(
                QuizQuestion("Apa arti dari gelar gelar 'Ulul Azmi' bagi para Rasul?", listOf("Rasul paling kaya", "Rasul yang memiliki ketabahan luar biasa 💪", "Rasul berumur panjang", "Rasul berwajah tampan"), 1, "Ulul Azmi adalah kelompok rasul dengan ketabahan luar biasa menghadapi ujian umatnya."),
                QuizQuestion("Manakah dari nabi berikut yang termasuk Rasul Ulul Azmi?", listOf("Nabi Adam AS", "Nabi Nuh AS ⛵", "Nabi Ismail AS", "Nabi Yusuf AS"), 1, "Lima nabi Ulul Azmi disingkat MIMIN (Muhammad, Ibrahim, Musa, Isa, Nuh)."),
                QuizQuestion("Nabi yang diselamatkan Allah dari kobaran api Raja Namrud adalah...", listOf("Nabi Musa AS", "Nabi Ibrahim AS 🔥", "Nabi Isa AS", "Nabi Nuh AS"), 1, "Api tunduk mendingin menyelamatkan Nabi Ibrahim AS."),
                QuizQuestion("Nabi yang memiliki mukjizat tongkat membelah Laut Merah adalah...", listOf("Nabi Muhammad SAW", "Nabi Musa AS 🌊", "Nabi Isa AS", "Nabi Sulaiman AS"), 1, "Musa AS membelah laut membebaskan Bani Israil dari kejaran Firaun."),
                QuizQuestion("Nabi yang lahir tanpa perantara seorang ayah adalah...", listOf("Nabi Adam AS", "Nabi Isa AS 🌸", "Nabi Ibrahim AS", "Nabi Musa AS"), 1, "Nabi Isa AS adalah putra Maryam binti Imran atas kuasa Allah.")
            )
            7 -> listOf(
                QuizQuestion("Sifat wajib Rasul 'Siddiq' artinya...", listOf("Benar/Jujur ✅", "Dapat dipercaya", "Menyampaikan", "Cerdas"), 0, "Siddiq artinya jujur/benar, ucapan rasul adalah wahyu ilahi."),
                QuizQuestion("Sifat wajib Rasul 'Amanah' artinya...", listOf("Benar", "Dapat Dipercaya/Menjaga Mandat 🤝", "Cerdas", "Menyampaikan"), 1, "Amanah artinya dapat dipercaya memegang misi risalah."),
                QuizQuestion("Sifat wajib Rasul 'Tabligh' artinya...", listOf("Cerdas", "Menyampaikan Wahyu 📣", "Jujur", "Bersih"), 1, "Tabligh artinya menyampaikan, rasul mustahil menyembunyikan wahyu."),
                QuizQuestion("Sifat wajib Rasul 'Fathonah' artinya...", listOf("Dapat dipercaya", "Cerdas/Bijaksana 🧠", "Jujur", "Pemberani"), 1, "Fathonah artinya bijaksana/cerdas, mematahkan argumentasi sesat."),
                QuizQuestion("Lawan dari sifat wajib Siddiq pada diri nabi adalah 'Kizib' yang artinya...", listOf("Khianat", "Bohong/Dusta 🚨", "Menyembunyikan", "Bodoh"), 1, "Kizib adalah sifat mustahil bagi rasul yang artinya berdusta.")
            )
            8 -> listOf(
                QuizQuestion("Hormat dan patuh pada kedua orang tua dalam Islam disebut...", listOf("Ukhuwah Islamiyah", "Birrul Walidain 👨‍👩‍👧‍👦", "Hubbud Dunya", "Sifat Riya"), 1, "Birrul Walidain artinya berbuat baik dan berbakti kepada orang tua."),
                QuizQuestion("Bagaimana hukum berbakti (Birrul Walidain) menurut syariat?", listOf("Sunnah", "Fardhu 'Ain (Wajib) 🕋", "Makruh", "Mubah"), 1, "Hukum berbakti kepada orang tua adalah wajib fardhu ain."),
                QuizQuestion("Bagaimana sikap kita jika orang tua meminta bantuan yang tidak maksiat?", listOf("Menolak halus", "Melaksanakan dengan senang hati 😊", "Menunda-nunda", "Menggerutu kesal"), 1, "Membantu orang tua dengan ceria mendatangkan rida Allah."),
                QuizQuestion("Surga berada di bawah telapak kaki ibu, bermakna...", listOf("Ibu tidak pakai sandal", "Rida Allah terletak pada berbakti kepada Ibu 🌸", "Ibu menguasai emas", "Ibu sangat kuat"), 1, "Kiasan mulia pentingnya memuliakan dan berbuat santun pada ibu."),
                QuizQuestion("Perilaku membangkang atau durhaka pada orang tua disebut...", listOf("Siddiq", "Uququl Walidain (Dosa Besar) 🚨", "Syirik", "Takabur"), 1, "Uququl Walidain adalah dosa besar mendurhakai orang tua.")
            )
            9 -> listOf(
                QuizQuestion("Sifat 'Sabar' secara terminologi Islam artinya...", listOf("Pasrah tanpa usaha", "Menahan diri dari keluh kesah dan amarah 🧘", "Menangis", "Diam seribu bahasa"), 1, "Sabar artinya mengendalikan nafsu dan emosi saat tertimpa musibah."),
                QuizQuestion("Sifat 'Ikhlas' bermakna membersihkan amalan dari...", listOf("Kotoran fisik", "Riya/Keinginan dipuji manusia 🕋", "Uang halal", "Air wudhu"), 1, "Ikhlas bernilai murni ibadah semata mencari keridaan Allah."),
                QuizQuestion("Sifat 'Syukur' diaplikasikan dengan memanfaatkan nikmat Allah untuk...", listOf("Berfoya-foya", "Ketaatan dan kebaikan sesama 🌟", "Disombongkan", "Disimpan rapat"), 1, "Syukur mensinergikan harta/jiwa dalam rida sang pencipta."),
                QuizQuestion("Memaafkan kesalahan orang lain sebelum diminta melambangkan akhlak...",
                    listOf("Tercela", "Mulia/Terpuji ✨", "Lemah diri", "Takut konflik"), 1, "Pemaaf adalah akhlak mulia para nabi dan rasul."),
                QuizQuestion("Sifat berbaik sangka terhadap semua ketetapan Allah disebut...", listOf("Suuzhan", "Husnuzhan ☀️", "Tawadhu", "Qanaah"), 1, "Husnuzhan artinya berprasangka baik atas takdir hidup.")
            )
            10 -> listOf(
                QuizQuestion("Sifat tercela 'Riya' adalah beramal dengan tujuan...", listOf("Mendapat pahala Allah", "Dipuji dan dilihat manusia 🚨", "Membantu yatim", "Membersihkan diri"), 1, "Riya menghapus pahala amal shalih karena ingin disanjung."),
                QuizQuestion("Sifat tercela 'Takabur' artinya...", listOf("Kikir", "Sombong/Meremehkan orang lain 🚨", "Menggunjing", "Pemalas"), 1, "Takabur merasa diri lebih hebat dan meremahkan mukmin lain."),
                QuizQuestion("Sifat tercela 'Hasad' artinya merasa tidak senang jika...", listOf("Mendapat musibah", "Melihat orang lain mendapat nikmat kebahagiaan 🚨", "Ibadah bolos", "Teman berpindah"), 1, "Hasad/Dengki menggerogoti amal baik laksana api melalap kayu kering."),
                QuizQuestion("Berbicara bohong menyebarkan aib buruk seseorang yang tidak nyata disebut...", listOf("Ghibah", "Fitnah (Lebih kejam dari pembunuhan) 🚨", "Namimah", "Riya"), 1, "Fitnah memutarbalikkan fakta merusak muruah kehormatan korban."),
                QuizQuestion("Sifat tercela 'Bakhil' artinya...", listOf("Suka mencuri", "Pelit/Kikir enggan berzakat/sedekah 🪙", "Pemarah", "Sombong"), 1, "Kikir menutup pintu keberkahan harta melatih kecurangan.")
            )
            11 -> listOf(
                QuizQuestion("Peristiwa kebangkitan seluruh manusia dari alam kubur disebut...", listOf("Yaumul Mizan", "Yaumul Ba'ats 🕋", "Yaumul Hisab", "Yaumul Jaza"), 1, "Yaumul Ba'ats adalah hari dibangkitkannya kubur pasca tiupan kedua."),
                QuizQuestion("Peristiwa pembagian buku catatan amal dan perhitungan amal disebut...", listOf("Yaumul Barzakh", "Yaumul Hisab 📝", "Yaumul Ba'ats", "Yaumul Mizan"), 1, "Yaumul Hisab adalah hari perhitungan amal tanpa ada dusta."),
                QuizQuestion("Peristiwa penimbangan amal baik dan buruk secara adil dinamakan...", listOf("Yaumul Hisab", "Yaumul Mizan ⚖️", "Yaumul Jaza", "Yaumul Mahsyar"), 1, "Yaumul Mizan menimbang bobot kebajikan dan maksiat hambanya."),
                QuizQuestion("Padang luas tempat dikumpulkannya seluruh manusia setelah dibangkitkan adalah...", listOf("Padang Arafah", "Padang Mahsyar 🌟", "Tanah Haram", "Surga Adn"), 1, "Jutaan umat berkumpul menanti syafaat di bawah terik matahari dekat."),
                QuizQuestion("Jembatan penyeberangan tipis tajam di atas neraka menuju surga bernama...", listOf("Jembatan Sirotol Mustaqim ⚡", "Sajadah", "Jembatan Ampera", "Mizan"), 0, "Sirotol Mustaqim licin nan tajam dilalui secepat kilat atau lambat.")
            )
            12 -> listOf(
                QuizQuestion("Takdir yang sudah pasti tidak dapat diubah oleh manusia disebut takdir...", listOf("Muallaq", "Mubram 🕋", "Syar'i", "Qadariyah"), 1, "Takdir Mubram mutlak, seperti kematian, jenis kelamin, dan hari kiamat."),
                QuizQuestion("Takdir yang dapat diubah melalui ikhtiar usaha serta doa ikhlas adalah...", listOf("Takdir Mubram", "Takdir Muallaq 💪", "Takdir Duniawi", "Takdir Qauli"), 1, "Takdir Muallaq bergantung kesungguhan ikhtiar, seperti kecerdasan."),
                QuizQuestion("Sikap berserah diri sepenuhnya kepada Allah setelah ikhtiar maksimal disebut...", listOf("Sabar", "Tawakal 🧘", "Qanaah", "Ikhlas"), 1, "Tawakal memasrahkan hasil akhir di tangan hakim yang adil."),
                QuizQuestion("Sikap merasa cukup dan rela terhadap apa yang diberikan Allah dinamakan...", listOf("Zuhud", "Qanaah 😊", "Raja'", "Sabar"), 1, "Qanaah menjauhkan sifat tamak rakus memburu keduniawian."),
                QuizQuestion("Keyakinan kuat bahwa segala kebaikan dan keburukan ada dalam takdir Allah adalah...", listOf("Rukun Islam ke-3", "Rukun Iman ke-6 🌟", "Sifat Wajib Allah", "Rahmatan lil 'Alamin"), 1, "Rukun iman keenam melengkapi kedamaian tauhid seorang mukmin.")
            )
            13 -> listOf(
                QuizQuestion("Apa sifat Jaiz yang wajib diyakini ada pada diri Allah SWT?", listOf("Wajib menciptakan alam", "Bebas berbuat/menciptakan atau tidak menciptakan alam 🕋", "Mustahil ada makhluk", "Makan dan minum"), 1, "Sifat Jaiz bagi Allah adalah kebebasan berbuat sekehendak-Nya."),
                QuizQuestion("Apa sifat Jaiz yang ada pada diri para Rasul Allah?", listOf("Lalai dari dakwah", "Sifat kemanusiaan biasa yang tidak mengurangi derajat kerasulan 👨‍💼", "Tidak pernah lapar", "Terbuat dari nur"), 1, "A'radhul Basyariyah (sifat manusiawi) seperti tidur, sakit ringan, lapar dll."),
                QuizQuestion("Sifat yang mustahil bagi rasul 'Al-Baladah' artinya...", listOf("Khianat", "Bodoh 🚨", "Dusta", "Menyembunyikan"), 1, "Baladah adalah sifat mustahil bodoh, rasul dianugerahi fathonah."),
                QuizQuestion("Sifat mustahil bagi rasul 'Al-Kitman' artinya...", listOf("Bohong", "Menyembunyikan wahyu 🚨", "Bodoh", "Khianat"), 1, "Kitman menyembunyikan kebenaran, rasul wajib bertabligh."),
                QuizQuestion("Sifat mustahil bagi rasul 'Al-Khianah' artinya...", listOf("Bodoh", "Khianat/tidak jujur 🚨", "Pemberani", "Sakit"), 1, "Khianat menyalahi amanah, rasul selalu terjaga dari dosa besar (maksum).")
            )
            14 -> listOf(
                QuizQuestion("Bagaimana pandangan Islam terhadap kelestarian alam lingkungan hidup?", listOf("Lingkungan boleh dirusak", "Wajib dirawat sebagai amanah khalifah fil ardh 🌲", "Boleh dibakar habis", "Tidak penting"), 1, "Manusia diutus menjaga bumi lestari dari polusi kerukusan."),
                QuizQuestion("Hukum menyiksa hewan kelaparan tanpa alasan dibenarkan adalah...", listOf("Mubah", "Haram dan berdosa besar masuk neraka 🚨", "Makruh", "Sunnah"), 1, "Siksaan menzalimi hewan dilaknat syariat Allah ta'ala."),
                QuizQuestion("Nabi mencontohkan menanam sebatang pohon bernilai pahala...", listOf("Syirik", "Sedekah jariah (terus mengalir) 🍃", "Riya", "Wajib syar'i"), 1, "Menanam pohon memberi naungan oksigen berbuah pahala sedekah jarian."),
                QuizQuestion("Bagaimana adab menyembelih hewan qurban yang diajarkan Islam?", listOf("Membuat hewan stres", "Menajamkan pisau agar hewan tidak tersiksa lama 🔪", "Menyiksa perlahan", "Menghalangi air"), 1, "Menajamkan pisau ihsan menyembelih menjauhkan kebiadaban."),
                QuizQuestion("Larangan membuang sampah sembarangan dan mengotori sungai selaras dengan hadits...", listOf("Menuntut ilmu", "Kebersihan sebagian dari Iman ✨", "Berbakti orang tua", "Sifat jujur"), 1, "At-Thaharah minal iman menjaga kesucian ekosistem.")
            )
            15 -> listOf(
                QuizQuestion("Apakah ukuran diterimanya amal shaleh di sisi Allah SWT?", listOf("Banyaknya harta penunjang", "Niat ikhlas mutaba'ah (sesuai tuntunan syariat) 🕋", "Kemegahan seremonial", "Disorot kamera"), 1, "Amal diterima bila ikhlas karena Allah dan benar mengikuti sunnah nabi."),
                QuizQuestion("Arti dari ridha (rida) terhadap ketentuan takdir Allah adalah...", listOf("Mengeluh sembunyi", "Kelapangan hati menerima keputusan takdir pahit manis 🧘", "Putus asa", "Marah tertahan"), 1, "Rida mendatangkan sakinah ketenangan jiwa spiritual puncak."),
                QuizQuestion("Bagaimana tingkatan tertinggi ibadah seorang mukmin menurut hadits Jibril?", listOf("Islam", "Ihsan (Beribadah seolah melihat Allah) 🕋", "Iman", "Taqwa"), 1, "Ihsan merasakan pengawasan Allah yang Maha Menatap sedalam kalbu."),
                QuizQuestion("Ikhlas dalam beramal menghalangi hati dirasuki penyakit riya dan...", listOf("Tawakal", "Sum'ah (Senang didengar amalannya) 🚨", "Sabar", "Iffah"), 1, "Sum'ah ingin popularitas didengar, merusak ketulusan hati."),
                QuizQuestion("Puncak tauhid ditiupkan ke dada mukmin melahirkan ketetapan...", listOf("Kekhawatiran nasb", "Menyembah hanyalah kepada Allah (Kalimat Thayyibah) 🕋", "Mengandalkan dukun", "Ragu kiamat"), 1, "La ilaha illallah mengesakan zat, asma, dan ketuhanan-Nya.")
            )
            else -> getAqidahAkhlakPool(15)
        }
    }

    private fun getFiqihIbadahPool(grade: Int): List<QuizQuestion> {
        return when (grade) {
            1 -> listOf(
                QuizQuestion("Thaharah memiliki arti...", listOf("Belajar", "Bersuci (dari najis dan hadas) ✨", "Membersihkan baju", "Mandi malam"), 1, "Thaharah adalah bersuci lahir batin agar sah beribadah."),
                QuizQuestion("Manakah air yang suci lagi mensucikan?", listOf("Air teh manis", "Air kelapa muda", "Air hujan/sumur alami 🌧️", "Air cucian piring"), 2, "Air mutlak (hujan, laut, sumur, sungai) suci mensucikan."),
                QuizQuestion("Najis dibersihkan agar sah melaksanakan ibadah...", listOf("Puasa", "Shalat 🕋", "Zakat", "Haji"), 1, "Syarat sah shalat harus suci badan, pakaian, dan tempat dari najis."),
                QuizQuestion("Bersuci dari hadas kecil dilakukan dengan cara...", listOf("Mandi wajib", "Berwudhu atau tayammum 🚿", "Membasuh kaki", "Cuci tangan jabat"), 1, "Hadas kecil batal wudhu dihilangkan lewat basuhan wudhu."),
                QuizQuestion("Berapa kali kita shalat fardhu dalam sehari semalam?", listOf("3 kali", "4 kali", "5 kali 🕋", "6 kali"), 2, "Kewajiban shalat fardhu Subuh, Zhuhur, Ashar, Maghrib, Isya.")
            )
            2 -> listOf(
                QuizQuestion("Membasuh wajah saat berwudhu hukumnya adalah...", listOf("Sunnah", "Rukun/Wajib wudhu 🕋", "Makruh", "Mubah"), 1, "Niat, membasuh wajah, tangan, kepala, kaki, dan tertib adalah rukun."),
                QuizQuestion("Berkumur-kumur dan membasuh telinga saat wudhu hukumnya adalah...", listOf("Rukun", "Sunnah wudhu ✨", "Makruh", "Membatalkan"), 1, "Berkumur dan membersihkan telinga adalah amalan sunnah wudhu."),
                QuizQuestion("Manakah yang termasuk pembatal wudhu?", listOf("Makan nasi goreng", "Keluar angin dari dubur (kentut) 🚨", "Berbicara keras", "Minum air putih"), 1, "Mengeluarkan angin menetapkan wudhu batal wajib bersuci kembali."),
                QuizQuestion("Sebelum membasuh tangan sampai siku, wudhu diawali membasuh...", listOf("Kaki", "Kedua telapak tangan dan berkumur ✨", "Telinga", "Rambut"), 1, "Sunnah mendahulukan telapak tangan menyela jari wudhu."),
                QuizQuestion("Tayamum adalah bersuci menggunakan...", listOf("Air mineral", "Debu yang suci (saat tidak ada air/sakit) 🪵", "Tisu basah", "Daun pisang"), 1, "Tayamum solusi rukhshah bersuci darurat debu steril.")
            )
            3 -> listOf(
                QuizQuestion("Berapa jumlah rakaat Shalat Ashar?", listOf("2 rakaat", "3 rakaat", "4 rakaat 🕋", "17 rakaat"), 2, "Shalat Ashar wajib dikerjakan 4 rakaat."),
                QuizQuestion("Shalat wajib yang jumlah rakaatnya 3 adalah...", listOf("Shalat Isya", "Shalat Subuh", "Shalat Maghrib 🕋", "Shalat Zhuhur"), 2, "Shalat Maghrib dikerjakan 3 rakaat selepas matahari senja terbenam."),
                QuizQuestion("Berapa jumlah keseluruhan rakaat shalat fardhu 5 waktu dalam sehari?", listOf("10 rakaat", "15 rakaat", "17 rakaat 🕋", "20 rakaat"), 2, "Jumlahnya 17 rakaat (Subuh 2, Zhuhur 4, Ashar 4, Maghrib 3, Isya 4)."),
                QuizQuestion("Gerakan membungkukkan badan meletakkan kedua telapak tangan di lutut disebut...", listOf("Sujud", "Ruku' 🕋", "I'tidal", "Takbiratul Ihram"), 1, "Ruku' wajib tumakninah meluruskan punggung ikhlas."),
                QuizQuestion("Gerakan meletakkan dahi, hidung, kedua lutut, dan jemari kaki di lantai disebut...", listOf("I'tidal", "Sujud 🕋", "Ruku'", "Duduk tahiyat"), 1, "Sujud merendahkan hamba sehina debu di hadapan kebesaran sang rabb.")
            )
            4 -> listOf(
                QuizQuestion("Suara panggilan mulia penanda masuknya waktu shalat disebut...", listOf("Iqamah", "Adzan 🔊", "Shalawat", "Khotbah"), 1, "Adzan disuarakan muazin memanggil jemaah berkumpul masjid."),
                QuizQuestion("Bagaimana hukum menjawab lafadz adzan saat berkumandang?", listOf("Haram", "Sunnah Muakkad (Sangat dianjurkan) ✨", "Wajib fardhu", "Makruh"), 1, "Menjawab adzan mendapat pahala jaminan syafaat nabi."),
                QuizQuestion("Suara seruan tanda shalat berjamaah akan segera dimulai disebut...", listOf("Kultum", "Iqamah 🕋", "Adzan", "Tahlil"), 1, "Iqamah disuarakan jemaah meluruskan shaf shalat."),
                QuizQuestion("Orang yang mengumandangkan adzan disebut...", listOf("Imam", "Muadzin 🗣️", "Makmum", "Khatib"), 1, "Muadzin mendapat kemuliaan leher terpanjang di hari kiamat."),
                QuizQuestion("Doa setelah mendengar adzan berisi permohonan syafaat Nabi...", listOf("Musa AS", "Muhammad SAW 🕋", "Ibrahim AS", "Isa AS"), 1, "Berdoa wasilah maqam terpuji nabi tercinta.")
            )
            5 -> listOf(
                QuizQuestion("Puasa Ramadhan hukum mulianya adalah...", listOf("Sunnah", "Wajib bagi setiap muslim baligh berakal 🕋", "Makruh", "Mubah"), 1, "Puasa sebulan Ramadhan rukun Islam ke-4 hukumnya fardhu ain."),
                QuizQuestion("Menahan diri dari makan, minum, syahwat puasa dimulai sejak...", listOf("Matahari terbit", "Terbit fajar shadiq (waktu subuh) 🌅", "Jam 8 pagi", "Tengah malam"), 1, "Membatasi nafsu dari fajar subuh hingga maghrib tiba."),
                QuizQuestion("Waktu berakhir puasa (berbuka puasa) adalah pada saat...", listOf("Adzan ashar", "Terbenamnya matahari (masuk waktu maghrib) 🌇", "Malam hari jam 9", "Matahari di atas kepala"), 1, "Berbuka maghrib disunnahkan menyegerakan kurma air."),
                QuizQuestion("Amalan sunnah sahur sebelum fajar bertujuan mendapat...", listOf("Merasa kenyang berlebih", "Keberkahan (Barakah) makan sahur ✨", "Pujian tetangga", "Menambah lemak"), 1, "Sahur barakah penopang fisik berpuasa siang hari."),
                QuizQuestion("Manakah di bawah ini yang membatalkan ibadah puasa?", listOf("Mandi siang hari", "Makan atau minum dengan sengaja 🚨", "Berkumur wajar", "Lupa makan sebiji kismis"), 1, "Sengaja menelan benda cair/padat membatalkan rukun puasa.")
            )
            6 -> listOf(
                QuizQuestion("Berapa kelipatan derajat keutamaan shalat berjamaah dibanding shalat sendirian?", listOf("5 derajat", "10 derajat", "27 derajat 🕋", "40 derajat"), 2, "Shalat jamaah melipatgandakan pahala hingga 27 derajat."),
                QuizQuestion("Orang yang memimpin jalannya shalat berjamaah di depan disebut...", listOf("Makmum", "Imam 🕋", "Muadzin", "Khatib"), 1, "Imam berdiri terdepan memimpin shaf jamaah shalat."),
                QuizQuestion("Makmum yang terlambat datang rakaat shalat imam dinamakan makmum...", listOf("Muafik", "Masbuq 🏃", "Munfarid", "Ghaib"), 1, "Masbuq menyempurnakan rakaat sisa setelah imam salam."),
                QuizQuestion("Hukum merapatkan dan meluruskan shaf (barisan) shalat berjamaah adalah...", listOf("Sunnah penyempurna shalat ✨", "Wajib mutlak jemaah", "Haram", "Pembatal shalat"), 0, "Shaf lurus pembendung celah masuknya setan perusak khusyuk."),
                QuizQuestion("Bolehkan makmum mendahului gerakan shalat imam?", listOf("Boleh kapan saja", "Haram (Dilarang keras) 🚨", "Dianjurkan", "Makruh wajar"), 1, "Makmum wajib mengikuti imam bukan mendahului gerakan imam.")
            )
            7 -> listOf(
                QuizQuestion("Air kencing bayi laki-laki kurang 2 tahun yang hanya minum ASI termasuk najis...", listOf("Mutawassithah (sedang)", "Mukhaffafah (ringan) ✨", "Mughalladhah (berat)", "Najis dimaafkan"), 1, "Cukup dipercikkan air murni pada bagian terkena najis."),
                QuizQuestion("Kotoran sapi, air kencing dewasa, darah, darah haid termasuk najis...", listOf("Mukhaffafah", "Mutawassithah (sedang) 🛑", "Mughalladhah", "Najis batin"), 1, "Mutawassithah wajib dicuci hingga hilang warna, bau, dan rasa."),
                QuizQuestion("Najis air liur anjing atau babi termasuk golongan najis...", listOf("Mukhaffafah", "Mutawassithah", "Mughalladhah (berat) 🚨", "Najis ringan"), 2, "Wajib disucikan dengan membasuh air 7 kali, salah satunya dicampur debu tanah suci."),
                QuizQuestion("Darah bangkai hewan darat hukum kenajisannya adalah...", listOf("Suci", "Najis mutlak 🛑", "Halal dimakan", "Sunnah"), 1, "Darah mengalir bangkai haram hukumnya najis sedang."),
                QuizQuestion("Bagian bangkai laut (ikan dan belalang) hukumnya adalah...", listOf("Najis berat", "Suci lagi halal dimakan 🐟", "Najis ringan", "Boleh dibuang"), 1, "Dua bangkai halal dimaafkan dalam Islam adalah ikan dan belalang.")
            )
            8 -> listOf(
                QuizQuestion("Zakat yang wajib dikeluarkan tiap jiwa muslim di akhir Ramadhan adalah zakat...", listOf("Zakat Mal (Harta)", "Zakat Fitrah 🌾", "Zakat Profesi", "Sedekah sunnah"), 1, "Zakat fitrah mensucikan jiwa berupa makanan pokok setara 2.5 kg."),
                QuizQuestion("Berapakah kadar zakat fitrah per jiwa yang wajib ditunaikan?", listOf("1 kg nasi murni", "2.5 kg atau 3.5 liter beras makanan pokok 🍚", "5 kg biji gandum", "10 kg beras"), 1, "Ditetapkan syariat seberat 1 sha beras/gandum."),
                QuizQuestion("Zakat atas kepemilikan harta (emas, perak, perdagangan) setelah mencapai nishab setahun disebut...", listOf("Zakat Fitrah", "Zakat Mal (Harta) 💰", "Infak sukarela", "Wakaf tanah"), 1, "Zakat Mal membersihkan harta dari hak kaum fakir dhuafa."),
                QuizQuestion("Batas minimal kepemilikan harta wajib dikeluarkan zakatnya disebut...", listOf("Kadar harta", "Nisab 🕋", "Haul", "Mustahik"), 1, "Nisab adalah batas minimum kekayaan terkena kewajiban zakat."),
                QuizQuestion("Orang yang berhak menerima penyaluran zakat disebut...", listOf("Muzakki", "Mustahik (Ada 8 golongan) 🤝", "Mubaligh", "Amil saja"), 1, "Mustahik zakat diatur surah At-Taubah ayat 60.")
            )
            9 -> listOf(
                QuizQuestion("Shalat sunnah yang dikerjakan pada pagi hari saat matahari naik adalah shalat...", listOf("Shalat Tahajjud", "Shalat Dhuha ☀️", "Shalat Tarawih", "Shalat Istisqa"), 1, "Dhuha pembuka wasilah keberkahan rezeki siang hari."),
                QuizQuestion("Shalat sunnah di sepertiga malam hari terakhir setelah terjaga tidur disebut shalat...", listOf("Shalat Istikharah", "Shalat Tahajjud 🌙", "Shalat Rawatib", "Shalat Hajat"), 1, "Tahajjud ibadah malam mulia sarana doa mustajab dikabulkan."),
                QuizQuestion("Shalat sunnah pengiring shalat fardhu (sebelum dan sesudahnya) disebut shalat...", listOf("Shalat Witir", "Shalat Rawatib (Qabliyah & Ba'diyyah) 🕋", "Shalat Dhuha", "Shalat Khusuf"), 1, "Rawatib menambal kekurangan cacat shalat wajib fardhu."),
                QuizQuestion("Shalat sunnah ganjil menutup rangkaian ibadah malam hari dinamakan shalat...", listOf("Shalat Witir 🌙", "Shalat Idul Fitri", "Shalat Tarawih", "Shalat Gerhana"), 0, "Witir minimal satu rakaat ganjil penutup shalat lail."),
                QuizQuestion("Shalat sunnah untuk memohon petunjuk pilihan keputusan terbaik adalah shalat...", listOf("Shalat Hajat", "Shalat Istikharah 🧭", "Shalat Witir", "Shalat Tahajjud"), 1, "Istikharah memohon ketetapan hati rida Allah.")
            )
            10 -> listOf(
                QuizQuestion("Ibadah mengunjungi Ka'bah Baitullah di bulan Dzulhijjah melakukan amalan khusus adalah...", listOf("Umrah", "Haji (Rukun Islam ke-5) 🕋", "Ziarah kubur", "Safar dakwah"), 1, "Haji wajib bagi muslim yang mampu secara finansial, fisik, dan aman."),
                QuizQuestion("Wukuf sebagai rukun haji agung dilaksanakan di padang...", listOf("Mina", "Arafah 🕋", "Muzdalifah", "Makkah"), 1, "Al-Hajju Arafah, wukuf esensi puncak zikir pengampunan dosa."),
                QuizQuestion("Mengelilingi Ka'bah sebanyak 7 putaran berlawanan arah jarum jam disebut...", listOf("Sa'i", "Thawaf 🕋", "Wukuf", "Tahallul"), 1, "Thawaf rukun haji dan umrah mengitari baitullah khusyuk."),
                QuizQuestion("Berlari-lari kecil antara bukit Shafa dan Marwah dinamakan...", listOf("Thawaf", "Sa'i ⛰️", "Tahallul", "Ihram"), 1, "Sa'i perjuangan ibunda Ismail Siti Hajar mencari air zam-zam."),
                QuizQuestion("Memotong atau mencukur rambut pertanda bebas dari larangan ihram dinamakan...", listOf("Thawaf", "Tahallul ✂️", "Mabit", "Miqat"), 1, "Tahallul menyempurnakan ibadah haji umrah luruh dosa.")
            )
            11 -> listOf(
                QuizQuestion("Hukum melaksanakan Shalat Jumat berjamaah bagi laki-laki muslim mukim adalah...", listOf("Sunnah muakkad", "Fardhu 'Ain (Wajib) 🕋", "Fardhu Kifayah", "Makruh"), 1, "Wajib bagi pria dewasa berkumpul khotbah jumat."),
                QuizQuestion("Khutbah jumat merupakan rukun shalat jumat, dibacakan sebanyak...", listOf("Satu kali khutbah", "Dua kali khutbah (dipisahkan duduk sebentar) 🗣️", "Tiga kali khutbah", "Tidak wajib"), 1, "Khatib berdiri membaca rukun khutbah memegang tongkat."),
                QuizQuestion("Bagaimana sikap yang benar saat khatib sedang membacakan khutbah Jumat?", listOf("Berbincang santai", "Mendengarkan dengan tenang dan diam (tidak bersuara) 🤫", "Bermain HP", "Berdzikir keras"), 1, "Bicara saat khutbah melenyapkan pahala shalat jumat sia-sia."),
                QuizQuestion("Berapa jumlah rakaat shalat Jumat fardhu?", listOf("4 rakaat", "2 rakaat 🕋", "3 rakaat", "1 rakaat"), 1, "Shalat Jumat dikerjakan 2 rakaat setelah khutbah selesai."),
                QuizQuestion("Amalan sunnah hari jumat agung adalah membaca surat...", listOf("Ar-Rahman", "Al-Kahfi 📖", "Al-Baqarah", "Yasin"), 1, "Membaca Al-Kahfi menerangi mukmin cahaya antar dua jumat.")
            )
            12 -> listOf(
                QuizQuestion("Shalat sunnah meminta turunnya air hujan saat kekeringan panjang disebut shalat...", listOf("Shalat Khusuf", "Shalat Istisqa 🌧️", "Shalat Kusuf", "Shalat Istikharah"), 1, "Shalat Istisqa dikerjakan lapangan memohon tobat diturunkan hujan berkah."),
                QuizQuestion("Shalat sunnah berjamaah saat terjadinya peristiwa Gerhana Bulan disebut shalat...", listOf("Shalat Kusuf", "Shalat Khusuf 🌙", "Shalat Istisqa", "Shalat Rawatib"), 1, "Khusuf untuk gerhana rembulan, Kusuf untuk gerhana matahari."),
                QuizQuestion("Shalat sunnah berjamaah saat terjadinya peristiwa Gerhana Matahari disebut shalat...", listOf("Shalat Khusuf", "Shalat Kusuf ☀️", "Shalat Hajat", "Shalat Tahiyatul"), 1, "Kusuf mengiringi gerhana mentari bersujud mengagumi kebesaran ciptaan."),
                QuizQuestion("Berapa jumlah ruku' dalam tiap rakaat shalat gerhana (Kusuf/Khusuf)?", listOf("Satu ruku' biasa", "Dua kali ruku' dalam satu rakaat 🕋", "Tiga ruku'", "Tanpa ruku'"), 1, "Keistimewaan shalat gerhana memiliki dua berdiri dan dua ruku dalam satu rakaat."),
                QuizQuestion("Bagaimanakah lafadz takbiran Shalat Idul Fitri disunnahkan keras dibaca?", listOf("Sejak malam hingga Imam naik mimbar shalat Id 📢", "Hanya selesai ashar", "Setiap waktu subuh saja", "Tidak disunnahkan"), 0, "Gema takbir fitri menyebarkan syiar syukur kemenangan.")
            )
            13 -> listOf(
                QuizQuestion("Bagaimana hukum dasar mengonsumsi makanan yang baik dan bersih (Thayyib)?", listOf("Haram", "Halal dan bernutrisi sehat 🍎", "Makruh", "Sunnah"), 1, "Kriteria makanan mukmin adalah halal zatnya dan thayyib olahannya."),
                QuizQuestion("Manakah dari binatang berikut yang haram dikonsumsi?", listOf("Ayam potong", "Babi, bangkai, darah, hewan disembelih tanpa asma Allah 🚨", "Kambing etawa", "Sapi perah"), 1, "Haram mutlak sesuai teks Al-Baqarah ayat 173."),
                QuizQuestion("Hewan darat buas bertaring atau burung bercakar tajam memiliki hukum...", listOf("Halid", "Haram dikonsumsi 🛑", "Makruh", "Halal darurat"), 1, "Dilarang nabi mengonsumsi binatang buas pemangsa."),
                QuizQuestion("Apakah khamr (minuman memabukkan) halal diminum?", listOf("Halal jika sedikit", "Haram mutlak karena merusak akal (Khamr ummul khabaits) 🚨", "Makruh", "Mubah"), 1, "Khamr induk kejahatan merusak kesadaran nalar akal."),
                QuizQuestion("Bangkai laut dan belalang dikecualikan dari haram, hukumnya adalah...", listOf("Najis", "Halal dikonsumsi 🐟", "Makruh", "Haram mutlak"), 1, "Ikan dan belalang halal dimakan tanpa perlu disembelih syar'i.")
            )
            14 -> listOf(
                QuizQuestion("Hukum mengurus jenazah muslim (memandikan, mengkafani, menshalatkan, mengubur) adalah...", listOf("Fardhu 'Ain", "Fardhu Kifayah 🕋", "Sunnah muakkad", "Mubah"), 1, "Wajib jemaah daerah, gugur dosa lingkungan jika ada sebagian yang mengurus."),
                QuizQuestion("Berapakah jumlah takbir dalam Shalat Jenazah?", listOf("2 kali takbir", "4 kali takbir (tanpa ruku' dan sujud) 🕋", "5 kali takbir", "Sama dengan shalat biasa"), 1, "Shalat jenazah berdiri tegak 4 kali takbir diakhiri salam."),
                QuizQuestion("Doa memohon ampunan mayit pada shalat jenazah dibaca setelah takbir ke...", listOf("Takbir pertama", "Takbir ketiga (Allahummaghfir lahu...) 🕋", "Takbir kedua", "Takbir keempat"), 1, "Takbir 1 Al-Fatihah, takbir 2 Shalawat, takbir 3 Doa mayit, takbir 4 Doa keluarga."),
                QuizQuestion("Berapakah jumlah lembaran kain kafan disunnahkan untuk jenazah laki-laki?", listOf("2 lembar", "3 lembar kain putih bersih ✉️", "5 lembar", "7 lembar"), 1, "Jenazah pria disunting 3 lapis kain kafan putih ganjil."),
                QuizQuestion("Berapakah jumlah lembaran kain kafan disunnahkan untuk jenazah perempuan?", listOf("3 lembar", "5 lembar kain kafan putih ✉️", "2 lembar", "1 lembar"), 1, "Jenazah wanita 5 lembar penutup aurat sempurna.")
            )
            15 -> listOf(
                QuizQuestion("Riba secara bahasa artinya kelebihan/tambahan. Bagaimana hukum riba dalam transaksi?", listOf("Halal saling rida", "Haram mutlak berpotensi mematikan keadilan ekonomi 🚨", "Makruh", "Sunnah"), 1, "Riba diperangi Allah dan Rasul karena menindas kesulitan hutang sesama."),
                QuizQuestion("Syarat utama keabsahan proses jual beli menurut syariat adalah...", listOf("Adanya bunga tinggi", "Kerelaan kedua belah pihak (An-Taradhin) dan objek halal 🤝", "Tanpa ijab kabul", "Barang fiktif"), 1, "Saling rida dan objek transaksi jelas spesifikasi dan kepemilikan."),
                QuizQuestion("Transaksi menjual barang yang tidak ada fisik/kepemilikannya (Gharar) hukumnya...", listOf("Halal", "Haram/Dilarang karena unsur penipuan 🛑", "Boleh meluas", "Sunnah"), 1, "Gharar ketidakjelasan objek memicu sengketa kebencian pembeli."),
                QuizQuestion("Sikap jujur timbangan dan tidak mengurangi hak takaran dagang diatur surah...", listOf("At-Tin", "Al-Muthaffifin (Kecelakaan bagi yang curang) ⚖️", "Al-Kautsar", "An-Nas"), 1, "Mengurangi timbangan takaran mengundang azab kehancuran niaga negeri."),
                QuizQuestion("Pinjaman modal usaha tolong menolong tanpa bunga tambahan disebut...", listOf("Riba nasiah", "Qardhul Hasan (Pinjaman Kebajikan) 🤝", "Pegadaian", "Gharar"), 1, "Qardhul hasan sedekah mulia meringankan beban keuangan saudara.")
            )
            else -> getFiqihIbadahPool(15)
        }
    }

    private fun getQuranTajwidPool(grade: Int): List<QuizQuestion> {
        return when (grade) {
            1 -> listOf(
                QuizQuestion("Surat pertama pembuka dalam mushaf Al-Qur'an adalah...", listOf("Surat Al-Baqarah", "Surat Al-Fatihah 📖", "Surat An-Nas", "Surat Al-Ikhlas"), 1, "Al-Fatihah artinya Pembuka, wajib dibaca disetiap rakaat shalat."),
                QuizQuestion("Surat terakhir penutup dalam susunan Al-Qur'an adalah...", listOf("Surat Al-Falaq", "Surat An-Nas 📖", "Surat Al-Kafirun", "Surat Al-Ikhlas"), 1, "Surat An-Nas penutup surat ke-114 mushaf Al-Qur'an."),
                QuizQuestion("Surat Al-Falaq dan An-Nas disebut 'Al-Mu'awwidzatain' artinya...", listOf("Surat pembuka", "Dua surat perlindungan dari kejahatan 🛡️", "Surat sejarah", "Surat hukum"), 1, "Dua surat ampuh membentengi diri dari sihir setan dan bisikan jahat."),
                QuizQuestion("Kitab suci Al-Qur'an diturunkan menggunakan bahasa...", listOf("Inggris", "Arab 🕋", "Ibrani", "Suryani"), 1, "Al-Qur'an diturunkan berbahasa Arab yang fashih dan indah."),
                QuizQuestion("Membaca Al-Qur'an dipahala per huruf. Huruf pertama kata 'Al-hamdu' bermula...", listOf("Ba", "Alif/Hamzah ✨", "Ta", "Mim"), 1, "Membaca kitabullah bernilai sepuluh kebaikan per huruf.")
            )
            2 -> listOf(
                QuizQuestion("Ada berapa juz keseluruhan dalam kitab suci Al-Qur'an?", listOf("10 Juz", "20 Juz", "30 Juz 📖", "114 Juz"), 2, "Al-Qur'an terdiri dari 30 Juz lengkap pelbagai isi."),
                QuizQuestion("Berapakah jumlah surat keseluruhan yang ada di Al-Qur'an?", listOf("100 Surat", "110 Surat", "114 Surat 📖", "30 Surat"), 2, "Jumlah surat dari Al-Fatihah (1) hingga An-Nas (114) ada 114 surat."),
                QuizQuestion("Surat terpanjang dalam Al-Qur'an terletak pada juz 1-3, yaitu...", listOf("Surat Ali 'Imran", "Surat Al-Baqarah 📖", "Surat Al-Ma'idah", "Surat Al-A'raf"), 1, "Al-Baqarah artinya Sapi Betina terdiri dari 286 ayat terpanjang."),
                QuizQuestion("Nama tempat turunnya wahyu pertama kepada Nabi Muhammad SAW adalah...", listOf("Gua Tsur", "Gua Hira ⛰️", "Masjidil Haram", "Kota Madinah"), 1, "Nabi bertahannuts di Gua Hira menerima malaikat Jibril."),
                QuizQuestion("Apa wahyu pertama Al-Qur'an yang diturunkan kepada Nabi Muhammad SAW?", listOf("Surat Al-Fatihah", "Surat Al-'Alaq ayat 1-5 📖", "Surat Al-Ikhlas", "Surat Yasin"), 1, "Iqra' bismi rabbikalladzi khalaq, perintah wajib membaca belajar.")
            )
            3 -> listOf(
                QuizQuestion("Surat Al-Qur'an yang diturunkan sebelum Nabi hijrah ke Madinah disebut kelompok surat...", listOf("Madaniyah", "Makkiyah 🕋", "Syamiyah", "Qudsiyah"), 1, "Makkiyah umumnya ayatnya pendek berisi aqidah akhlak diturunkan di Makkah."),
                QuizQuestion("Surat Al-Qur'an yang diturunkan setelah Nabi hijrah ke kota Madinah disebut...", listOf("Makkiyah", "Madaniyah 🏛️", "Mesiriyah", "Irakiyah"), 1, "Madaniyah umumnya ayatnya panjang berisi hukum kemasyarakatan pranata sosial."),
                QuizQuestion("Surat Al-Mulk terletak di juz berapa?", listOf("Juz 28", "Juz 29 📖", "Juz 30", "Juz 1"), 1, "Surat Al-Mulk pembuka juz 29 penolong siksa kubur."),
                QuizQuestion("Juz ke-30 dalam Al-Qur'an sering juga disebut dengan nama...", listOf("Juz Lail", "Juz 'Amma 📖", "Juz Tabarak", "Juz Mulk"), 1, "Juz 'Amma diawali pembacaan surat An-Naba' (Amma yatasa'alun)."),
                QuizQuestion("Surat apakah yang bernilai sepertiga Al-Qur'an karena keagungan tauhidnya?", listOf("Surat Al-Fatihah", "Surat Al-Ikhlas 🕋", "Surat Al-Falaq", "Surat An-Nas"), 1, "Al-Ikhlas memurnikan ketauhidan tiada melahirkan kembaran sewujud.")
            )
            4 -> listOf(
                QuizQuestion("Tajwid secara bahasa berarti 'Membaguskan'. Hukum Nun mati bertemu huruf Alif (ء) disebut...", listOf("Ikhfa", "Izhhar Halqi (Membaca Jelas) 🗣️", "Idgham", "Iqlab"), 1, "Izhhar artinya dibaca jelas terarah tanpa dengung mendalam."),
                QuizQuestion("Hukum nun mati/tanwin bertemu huruf Ba (ب) dibaca mendengung membalik suara mim disebut...", listOf("Izhhar", "Iqlab 🌀", "Ikhfa", "Idgham bighunnah"), 1, "Iqlab membalik Nun mati ke suara Mim samar mendengung."),
                QuizQuestion("Hukum nun mati bertemu huruf Yar-ma-lu-na meleburkan suara disebut...", listOf("Izhhar", "Idgham 🌀", "Ikhfa", "Syafawi"), 1, "Idgham artinya memasukkan/meleburkan harakat nun mati huruf berikutnya."),
                QuizQuestion("Idgham bighunnah dibaca melebur disertai dengung. Hurufnya ada 4 disingkat...", listOf("Baju di toko", "Yan-mu (ي, ن, م, و) 🗣️", "Alif lam", "Halqi"), 1, "Yanmu melebur berdengung sunnah tertahan 2 harakat."),
                QuizQuestion("Idgham bilaghunnah dibaca melebur TANPA dengung sekejap. Hurufnya adalah...", listOf("Ba dan Ta", "Lam dan Ra (ل , ر) ✨", "Alif dan Hamzah", "Qaf dan Kaf"), 1, "Lam dan Ra murni melebur langsung tanpa tahanan dengung tenggorok.")
            )
            5 -> listOf(
                QuizQuestion("Hukum Mim Mati bertemu huruf Mim (م) dinamakan hukum...", listOf("Ikhfa Syafawi", "Idgham Mimi (Mutamatsilain) 🌀", "Izhhar Syafawi", "Iqlab"), 1, "Idgham Mimi mendengungkan pertemuan dua huruf mim murni."),
                QuizQuestion("Hukum Mim Mati bertemu huruf Ba (ب) dibaca samar mendengung bibir disebut...", listOf("Izhhar Syafawi", "Ikhfa Syafawi 🗣️", "Idgham Mimi", "Iqlab"), 1, "Ikhfa Syafawi menyamarkan mim mati menyentuh huruf ba."),
                QuizQuestion("Hukum Mim Mati bertemu huruf selain Mim dan Ba dibaca JELAS mutlak bibir disebut...", listOf("Ikhfa Syafawi", "Izhhar Syafawi ✨", "Idgham Mimi", "Iqlab"), 1, "Izhhar Syafawi dibaca jelas membunyikan mim mati."),
                QuizQuestion("Huruf hijaiyah yang keluar dari rongga mulut (Al-Jauf) adalah huruf...", listOf("Kaf dan Jim", "Huruf Mad (Alif, Wawu, Ya mati) 🎙️", "Ha dan Kha", "Hamzah"), 1, "Al-Jauf melahirkan desah vokal panjang ketukan mad."),
                QuizQuestion("Mempelajari kaidah hukum tajwid Al-Qur'an secara ilmiah hukumnya adalah...", listOf("Fardhu 'Ain", "Fardhu Kifayah 🕋", "Haram", "Makruh"), 1, "Mempelajari ilmunya fardhu kifayah, membacanya tartil wujud fardhu ain.")
            )
            6 -> listOf(
                QuizQuestion("Qalqalah secara bahasa artinya 'Memantul'. Huruf qalqalah ada 5 dikumpulkan kalimat...", listOf("Yanmu", "Baju Di Toko (ب , ج , د , ط , ق) 🔨", "Yarmalun", "Halqi"), 1, "Lima huruf pantul yang memicu desis hentakan bersuara."),
                QuizQuestion("Qalqalah yang terjadi ditengah kalimat dibaca memantul tipis ringan disebut Qalqalah...", listOf("Kubra", "Sughra 🔨", "Mutlak", "Akbar"), 1, "Sughra pantulan kecil, berada di tengah kata matinya asli."),
                QuizQuestion("Qalqalah yang terjadi karena waqaf (berhenti) di akhir ayat dibaca mantap tebal disebut Qalqalah...", listOf("Sughra", "Kubra 🔨", "Mad", "Syafawi"), 1, "Kubra pantulan kuat, dipicu berhenti paksa penutupan nafas."),
                QuizQuestion("Contoh Qalqalah Kubra dalam juz 'Amma dapat disimak pada akhir surat...", listOf("An-Nas", "Al-Ikhlas (Ahad, Shamad) 🔨", "Al-Fatihah", "Al-Kautsar"), 1, "Huruf dal mati waqaf berdentang tebal melambung."),
                QuizQuestion("Hukum memantulkan huruf qalqalah yang bertanda tasydid di akhir ayat disebut...", listOf("Sughra", "Qalqalah Akbar (Sangat tebal) 🔨", "Izhhar", "Ikhfa"), 1, "Pantulan akbar tertahan tumpuan ganda tasydid baru memantul.")
            )
            7 -> listOf(
                QuizQuestion("Hukum membaca Alifs Lam dibaca JELAS huruf lamnya disebut Alif Lam...", listOf("Syamsiyah", "Qamariyah ✨", "Mad", "Syafawi"), 1, "Qamariyah laksana rembulan nampak bersinar lam matinya."),
                QuizQuestion("Hukum membaca Alif Lam melebur lebur langsung ke huruf berikutnya disebut Alif Lam...", listOf("Qamariyah", "Syamsiyah ☀️", "Gunnah", "Iqlab"), 1, "Syamsiyah laksana mentari, menyamarkan lam langsung tajdid berikutnya."),
                QuizQuestion("Manakah contoh pembacaan Alif Lam Qamariyah di bawah ini?", listOf("As-Syamsu", "Al-Qamaru ✨", "Ar-Rahman", "An-Nas"), 1, "Al-Qamaru dibaca jelas lam matinya terarah."),
                QuizQuestion("Manakah contoh pembacaan Alif Lam Syamsiyah di bawah ini?", listOf("Al-Ardh", "An-Naju (pelesapan lam langsung na) ☀️", "Al-Kitab", "Al-Hamdu"), 1, "An-Najmu melesapkan lam lurus mendengung n."),
                QuizQuestion("Huruf Alif Lam Syamsiyah berjumlah...", listOf("5 huruf", "14 huruf 🕋", "28 huruf", "10 huruf"), 1, "Setengah hijaiyah milik Syamsiyah, setengah milik Qamariyah.")
            )
            8 -> listOf(
                QuizQuestion("Mad Thabi'i (Mad Asli) dibaca panjang ketukan harakat sebanyak...", listOf("1 harakat", "2 harakat (Satu alif) ✨", "5 harakat", "6 harakat"), 1, "Mad Asli dibaca panjang dua ketukan mengalir santai."),
                QuizQuestion("Huruf mad yang memicu pemanjangan vokal ada 3 yaitu...", listOf("Alif, Ba, Ta", "Alif (ا), Wawu (و), Ya (ي) 🎙️", "Hamzah, Ha, Ain", "Mim, Nun, Lam"), 1, "Tiga pilar huruf pemanjang vokal tilawah tajwid."),
                QuizQuestion("Apabila ada fathah diikuti Alif mati (ا) maka melahirkan mad...", listOf("Mad Arid", "Mad Thabi'i (Asli) ✨", "Mad Iwad", "Mad Lazim"), 1, "Fathah Alif, Kasrah Ya sukun, Dhammah Wawu sukun hukumnya Mad Thabi'i."),
                QuizQuestion("Apabila ada dhammah diikuti Wawu sukun (و) maka dibaca panjang...", listOf("1 harakat", "2 harakat ✨", "4 harakat", "6 harakat"), 1, "Mad Thabi'i standar ketukan stabil pertengahan."),
                QuizQuestion("Kata 'Nu-hi-ha' memuat ketiga huruf mad thabi'i secara...", listOf("Terpisah jauh", "Berurutan lengkap ✨", "Satu mad saja", "Batal dibaca"), 1, "Nu (dhammah wawu), hi (kasrah ya), ha (fathah alif) contoh terlengkap asli.")
            )
            9 -> listOf(
                QuizQuestion("Surat Al-Ikhlas menegaskan sifat keesaan Allah. Apa makna 'Al-Ikhlas'?", listOf("Pertolongan", "Memurnikan keesaan Allah SWT 🕋", "Kemenangan", "Pembukaan"), 1, "Memurnikan tauhid menepis persekutuan menyembah patung objek."),
                QuizQuestion("Lafadz 'Allahush-Shamad' dalam surah Al-Ikhlas artinya...", listOf("Allah Maha Pengasih", "Allah tempat meminta segala sesuatu 🕋", "Allah Maha Agung", "Allah tidak tidur"), 1, "Shamad tumpuan akhir doa seluruh hajat makhluk hidup."),
                QuizQuestion("Surat Al-Ikhlas menegaskan bahwa Allah tidak beranak dan...", listOf("Tidak berkaki", "Tidak diperanakkan (tiada beribu/bapak) 🕋", "Tidak mendengar", "Tidak melihat"), 1, "Lam yalid wa lam yuulad, Allah berdiri sendiri azali."),
                QuizQuestion("Lafadz 'Walam yakul-lahu kufuwan ahad' artinya...", listOf("Satu kali bersujud", "Dan tidak ada sesuatu yang setara dengan Dia 🕋", "Allah Maha kaya", "Raja sejagat"), 1, "Kufuwan ahad setara/sebanding, tiada yang sebanding secuilpun bagi Allah."),
                QuizQuestion("Surat Al-Ikhlas diturunkan di kota Makkah merespon pertanyaan kaum...", listOf("Mukmin Madinah", "Musyrikin Makkah tentang nasab Tuhan mereka 🕋", "Nasrani Najran", "Yahudi Khaibar"), 1, "Musyrik bertanya nasab pencipta dijawab surat mulia pemurni tauhid.")
            )
            10 -> listOf(
                QuizQuestion("Apabila Mad Thabi'i bertemu Hamzah (ء) dalam satu kata/kalimat bersambung disebut Mad...", listOf("Mad Jaiz Munfasil", "Mad Wajib Muttasil (Panjang 4-5 harakat) 🕋", "Mad Arid Lisukun", "Mad Badal"), 1, "Muttasil artinya bersambung satu kata wajib dibaca panjang."),
                QuizQuestion("Apabila Mad Thabi'i bertemu Hamzah di lain kata terpisah disebut Mad...", listOf("Mad Wajib Muttasil", "Mad Jaiz Munfasil (Panjang 2, 4 atau 5 harakat) ✨", "Mad Arid Lisukun", "Mad Lazim"), 1, "Munfasil terpisah kata, kelenturan harakat membaca tilawah."),
                QuizQuestion("Apabila ada Mad Thabi'i di akhir ayat bertemu huruf hidup yang diwaqafkan (berhenti) dinamakan...", listOf("Mad Iwad", "Mad 'Arid Lisukun (Panjang 2, 4, atau 6 harakat) 🕋", "Mad Badal", "Mad Shilah"), 1, "Arid lisukun mad penutup ayat tilawah Al-Qur'an bersenandung indah."),
                QuizQuestion("Mad yang terjadi karena berkumpulnya dua Hamzah dalam satu kata ditandai pemanjangan hamzah kedua disebut...", listOf("Mad Lazim", "Mad Badal ✨", "Mad Iwad", "Mad Farq"), 1, "Badal artinya pengganti, merubah hamzah sukun menjadi mad huruf vokal panjang."),
                QuizQuestion("Hukum membaca mad karena fathatain di akhir ayat diwaqafkan dibaca fathah panjang pengganti tanwin disebut...", listOf("Mad 'Arid", "Mad 'Iwad (Panjang 2 harakat) ✨", "Mad Badal", "Mad Shilah"), 1, "Iwad mengganti bunyi tanwin an menjadi vokal panjang biasa.")
            )
            11 -> listOf(
                QuizQuestion("Tanda waqaf berbentuk huruf Mim kecil (م) di Al-Qur'an menandakan wajib...", listOf("Boleh terus", "Wajib Berhenti (Waqaf Lazim) 🛑", "Wajib membaca cepat", "Dilarang memandang"), 1, "Lazim berhenti di sana, jika dilanggar merubah total makna kalimat."),
                QuizQuestion("Tanda waqaf berbentuk huruf La (لا) di Al-Qur'an menandakan dilarang...", listOf("Wajib berhenti", "Dilarang Berhenti (Harus terus membaca) ☀️", "Boleh sesuka hati", "Wajib sujud"), 1, "La waqaf dilarang berhenti di sana lanjut sambung kalimat."),
                QuizQuestion("Tanda waqaf berupa titik tiga ganda (؀... ؀) disebut Waqaf Mu'anaqah artinya...", listOf("Wajib wasal terus", "Boleh berhenti di salah satu tanda saja 🛑", "Wajib berhenti keduanya", "Dilarang melihat"), 1, "Mu'anaqah memilih rehat disatu sisi titik menguraikan hikmah."),
                QuizQuestion("Tanda waqaf berbentuk huruf Jim (ج) melambangkan hukum...", listOf("Wajib berhenti di sana", "Boleh Berhenti atau Boleh Terus (Setara) ✨", "Dilarang berhenti", "Wajib bersujud"), 1, "Waqaf Jaiz memiliki tingkat kebebasan setara wasal/waqaf."),
                QuizQuestion("Tanda waqaf berupa tulisan 'Qola' (قلى) menandakan bahwa...", listOf("Terus lebih baik", "Berhenti lebih utama (Al-Waqfu Aula) 🛑", "Takbir dulu", "Wajib sujud sahwi"), 1, "Qola mengutamakan perhentian nafas meresapi intisari kalam.")
            )
            12 -> listOf(
                QuizQuestion("Berapa keutamaan luar biasa membaca Surat Al-Kahfi di hari Jumat?", listOf("Mendapat emas", "Diberi naungan cahaya iman di antara dua jumat 🌟", "Kebal penyakit", "Menjadi pintar instan"), 1, "Penerang rohani kalbu terlindung fitnah dajjal akhir zaman."),
                QuizQuestion("Sepuluh ayat pertama surat Al-Kahfi jika dihafalkan bermanfaat membentengi diri dari...", listOf("Sengatan ular", "Fitnah besar Dajjal akhir zaman 🚨", "Kebakaran rumah", "Hutang bank"), 1, "Keutamaan pelindung fitnah akhir zaman pilar keimanan."),
                QuizQuestion("Kisah agung Ashabul Kahfi menceritakan sekumpulan pemuda beriman tertidur dalam goa selama...", listOf("10 tahun", "309 tahun 🕋", "100 tahun", "50 tahun"), 1, "Membuktikan kebesaran Allah membangkitkan jasad mati melampaui masa abad."),
                QuizQuestion("Surat Al-Kahfi memuat dialog pelajaran mencari hikmah ilmu antara nabi Musa AS dan nabi...", listOf("Nabi Daud AS", "Nabi Khidhir AS ⛵", "Nabi Ibrahim AS", "Nabi Sulaiman AS"), 1, "Musa berguru kerendahan hati menyusuri samudra hikmah."),
                QuizQuestion("Tokoh raja adil pengembara barat dan timur pembangun dinding besi penahan Yakjuj Makjuj adalah...", listOf("Firaun", "Raja Dzulqarnain 👑", "Raja Namrud", "Raja Jalut"), 1, "Dzulqarnain membangun benteng tembaga panas melindungi kaum tertindas.")
            )
            13 -> listOf(
                QuizQuestion("Hukum membaca tajwid dengung menahan suara kuat pada huruf Nun tasydid (نّ) atau Mim tasydid (مّ) disebut...", listOf("Ikhfa", "Gunnah (Membaca Dengung Murni) 🗣️", "Izhhar", "Iqlab"), 1, "Gunnah wajib mendengung tertahan kokoh pada pangkal hidung."),
                QuizQuestion("Berapakah ketukan harakat standar menyuarakan dengung Gunnah Musyaddadah?", listOf("1 harakat", "2-3 harakat (Dengung mantap) ✨", "5 harakat", "6 harakat"), 1, "Dengung penuh (Kamilah) tertahan dinamis memberi kelembutan tilawah."),
                QuizQuestion("Kata 'Am-ma' (عمّ) memuat hukum bacaan...", listOf("Izhhar", "Gunnah Mimi ✨", "Iqlab", "Qalqalah"), 1, "Mim tasydid dibaca mendengung ditahan g."),
                QuizQuestion("Kata 'Min-na' (منّ) memuat hukum bacaan...", listOf("Idgham bilaghunnah", "Gunnah Nun 🎙️", "Izhhar Syafawi", "Mad Iwad"), 1, "Nun tasydid dibaca mendengung menahan nafas nasal sempuran n."),
                QuizQuestion("Pangkal hidung sebagai tempat keluarnya desis dengung tajwid dinamakan organ...", listOf("Al-Halq", "Al-Khaisyum (Rongga Hidung) 🎙️", "Al-Lisan", "As-Syafatain"), 1, "Khaisyum poros dengung seluruh rumpun ikhfa gunnah.")
            )
            14 -> listOf(
                QuizQuestion("Hukum membaca huruf Ra (ر) tebal menggetarkan pangkal lidah ke atas langit-langit dinamakan...", listOf("Tarqiq", "Tafkhim (Tebal) 🗣️", "Gunnah", "Iqlab"), 1, "Tafkhim menebalkan gaungan vokal Ra saat berharakat fathah/dhammaah."),
                QuizQuestion("Hukum membaca huruf Ra (ر) tipis merendahkan lidah ke dasar mulut dinamakan...", listOf("Tafkhim", "Tarqiq (Tipis) ✨", "Qalqalah", "Idgham"), 1, "Tarqiq menipiskan desah huruf Ra saat berharakat kasrah."),
                QuizQuestion("Ra berharakat fathah (رَ) atau dhammah (رُ) wajib dibaca...", listOf("Tarqiq (tipis)", "Tafkhim (tebal) 📝", "Gunnah", "Senyap"), 1, "Ra fathah/dhammah berbunyi tebal menggelegar contoh: Rabbuna."),
                QuizQuestion("Ra berharakat kasrah (رِ) contoh kata 'Rijlun' wajib dibaca...", listOf("Tafkhim", "Tarqiq (tipis) ✨", "Melingkar", "Syafawi"), 1, "Ra kasrah disuarakan tipis lembut mengalir."),
                QuizQuestion("Ra sukun didahului huruf berbaris kasrah murni contoh 'Fir'auna' dibaca...", listOf("Tafkhim", "Tarqiq (tipis) ✨", "Qalqalah", "Idgham"), 1, "Membunyikan Ra sukun tipis riang karena pengaruh kasrah sebelumnya.")
            )
            15 -> listOf(
                QuizQuestion("Makhraj (tempat keluar huruf) yang bersumber dari lima area utama tenggorokan disebut...", listOf("Al-Lisan", "Al-Halq (Tenggorokan) 🎙️", "Al-Khaisyum", "Al-Jauf"), 1, "Al-Halq meloloskan huruf Hamzah, Ha, Ain, Ha besar, Ghain, Kha."),
                QuizQuestion("Huruf hijaiyah yang keluar dari bibir (As-Syafatain) adalah huruf...", listOf("Kaf dan Qaf", "Fa, Wawu, Ba, Mim (ف, و, ب, م) 🎙️", "Jim dan Syin", "Alif"), 1, "Syafatain mempertemukan katup bibir melahirkan letupan bibir mutlak."),
                QuizQuestion("Area lidah (Al-Lisan) melahirkan makhraj huruf hijaiyah terbanyak berjumlah...", listOf("5 huruf", "18 huruf 🕋", "10 huruf", "28 huruf"), 1, "Lidah area artikulasi tersibuk mengontrol 18 huruf hijaiyah."),
                QuizQuestion("Huruf hijaiyah 'Dhod' (ض) keluar makhraj menekan sisi lidah ke...", listOf("Bibir bawah", "Gigi geraham atas kiri/kanan 🎙️", "Langit depan", "Tenggorokan bawah"), 1, "Dhod huruf spesifik tersulit pembeda jati diri lisan arab (Lughatul Dhod)."),
                QuizQuestion("Tujuan praktis mempelajari makhraj dan tertil Al-Qur'an agar...", listOf("Dapat hadiah", "Menghindari kesalahan lafadz yang merubah arti/makna ayat 🕋", "Membaca cepat selesai", "Menjadi artis qori"), 1, "Menjaga keaslian pelafalan mukjizat kalamullah dari kepunahan arti.")
            )
            else -> getQuranTajwidPool(15)
        }
    }

    private fun getSejarahIslamPool(grade: Int): List<QuizQuestion> {
        return when (grade) {
            1 -> listOf(
                QuizQuestion("Nabi Muhammad SAW dilahirkan di kota suci...", listOf("Madinah", "Makkah 🕋", "Mesir", "Yerusalem"), 1, "Nabi Muhammad SAW lahir di kota Makkah Al-Mukarramah."),
                QuizQuestion("Peristiwa penyerbuan pasukan gajah Abrahah bertepatan lahirnya Nabi memicu penamaan tahun...", listOf("Tahun Air", "Tahun Gajah 🕋", "Tahun Hijrah", "Tahun Kuda"), 1, "Allah menghancurkan pasukan gajah Abrahah melalui kiriman burung Ababil."),
                QuizQuestion("Siapa nama kakek yang mengasuh Nabi Muhammad SAW pasca wafatnya sang ibu?", listOf("Abu Thalib", "Abdul Muthalib 👨", "Abu Lahab", "Hamzah"), 1, "Abdul Muthalib, tokoh sesepuh terpandang Quraisy penguasa sumur Zam-zam."),
                QuizQuestion("Siapa wanita mulia yang menyusui Nabi Muhammad SAW saat bayi di pedesaan?", listOf("Khadijah", "Halimah As-Sa'diyyah 👩", "Fatima", "Aminah"), 1, "Halimah menyusui nabi mendatangkan keberkahan melimpah ternak pedesaannya."),
                QuizQuestion("Nama ayah kandung Nabi Muhammad SAW yang wafat sebelum nabi lahir adalah...", listOf("Abu Thalib", "Abdullah 👨", "Abdul Muthalib", "Hamzah"), 1, "Abdullah wafat dalam perjalanan niaga saat nabi masih dalam kandungan aminah.")
            )
            2 -> listOf(
                QuizQuestion("Siapa nama ibu kandung Nabi Muhammad SAW yang wafat saat nabi berumur 6 tahun?", listOf("Halimah", "Aminah binti Wahab 👩", "Khadijah", "Fatimah"), 1, "Aminah wafat dimakamkan di Abwa sewaktu pulang perjalanan ziarah."),
                QuizQuestion("Paman Nabi yang sangat menyayangi dan melindungi dakwah nabi di Makkah adalah...", listOf("Abu Lahab", "Abu Thalib 👨", "Abu Jahal", "Abbas"), 1, "Abu Thalib mengasuh dan membela nabi dari boikot kejam Quraisy."),
                QuizQuestion("Kota kelahiran bapak para nabi Nabi Ibrahim AS berada di peradaban...", listOf("Makkah", "Babilonia (Irak Kuno) 🕌", "Mesir", "Yatsrib"), 1, "Ibrahim lahir di Babilonia daerah kekuasaan melimpahnya berhala Raja Namrud."),
                QuizQuestion("Binatang mukjizat tunggangan dakwah Nabi shalih AS yang dibunuh kaum Tsamud adalah...", listOf("Kuda terbang", "Unta Betina raksasa keluar dari celah batu 🐫", "Gajah putih", "Keledai cerdik"), 1, "Mukjizat unta betina keluar dari batu sebagai ujian keimanan kaum Tsamud."),
                QuizQuestion("Siapa nabi penyabar yang diuji sakit kulit menahun dan kehilangan keluarganya namun tetap bersyukur?", listOf("Nabi Isa AS", "Nabi Ayyub AS 🧘", "Nabi Musa AS", "Nabi Yusuf AS"), 1, "Ayyub AS lulus ujian ketabahan mutlak panutan kesabaran.")
            )
            3 -> listOf(
                QuizQuestion("Nabi Muhammad SAW menerima wahyu pertama kali di...", listOf("Masjidil Haram", "Gua Hira (Gunung Jabal Nur) ⛰️", "Masjid Nabawi", "Gua Tsur"), 1, "Nabi mengasingkan diri tafakkur di Gua Hira dihampiri Jibril."),
                QuizQuestion("Berapakah usia Nabi Muhammad SAW saat diangkat resmi menjadi Rasul?", listOf("25 tahun", "40 tahun 🕋", "30 tahun", "63 tahun"), 1, "Malaikat Jibril memeluk erat nabi menyuruh membaca Iqra'."),
                QuizQuestion("Surat pertama kali diturunkan di gua hira memerintahkan untuk membaca adalah...", listOf("Surat Al-Fatihah", "Surat Al-'Alaq ayat 1-5 📖", "Surat Al-Ikhlas", "Surat Al-Muddassir"), 1, "Mengawali fajar ilmu pengetahuan kewajiban pemberantasan buta huruf rohani."),
                QuizQuestion("Siapakah kawan setia saudagar kaya wanita pertama yang membenarkan wahyu nabi?", listOf("Aisyah", "Khadijah binti Khuwailid 👑", "Fatimah", "Asma binti Abu Bakar"), 1, "Khadijah menyelimuti kepanikan nabi mendermakan seluruh kekayaan perkasa miliknya."),
                QuizQuestion("Siapa paman nabi yang memusuhi menentang dakwah nabi sampai diabadikan surat Al-Lahab?", listOf("Abu Thalib", "Abu Lahab 🚨", "Abbas", "Hamzah"), 1, "Abu Lahab menyebarkan duri fitnah mencaci maki nabi keponakannya.")
            )
            4 -> listOf(
                QuizQuestion("Gelar 'Al-Amin' disematkan penduduk Quraisy kepada Nabi Muhammad SAW artinya...", listOf("Maha kaya", "Terpercaya/Dapat dipercaya 🤝", "Pemberani besar", "Pemilik ilmu sakti"), 1, "Nabi dikenal jujur tidak pernah berdusta sekalipun sejak belia kanak-kanak."),
                QuizQuestion("Peristiwa nabi menengahi peletakan batu Hajar Aswad di Ka'bah menghindarkan...", listOf("Kebakaran hebat", "Perang saudara antar kabilah Arab 🤝", "Kemusyrikan baru", "Banjir bandang"), 1, "Nabi membentangkan kain membagi pegangan kabilah secara adil bijaksana."),
                QuizQuestion("Siapa pemeluk Islam golongan anak-anak pertama kali?", listOf("Zaid bin Haritsah", "Ali bin Abi Thalib 👦", "Abu Bakar", "Umar bin Khattab"), 1, "Ali sepupu nabi bersyahadat penuh keberanian di usia kanak-kanak."),
                QuizQuestion("Sahabat nabi golongan dewasa pertama kali bersyahadat menemani hijrah nabi adalah...", listOf("Umar bin Khattab", "Abu Bakar As-Siddiq 🕋", "Utsman bin Affan", "Ali bin Abi Thalib"), 1, "Abu Bakar membenarkan dakwah nabi seketika tanpa keraguan riset."),
                QuizQuestion("Sahabat nabi dari golongan budak pertama dibebaskan yang disiksa kejam bertauhid Ahad-Ahad...", listOf("Zaid bin Haritsah", "Bilal bin Rabah (Muadzin pertama) 🗣️", "Yasir", "Amar bin Yasir"), 1, "Bilal teguh bertauhid ditindih batu membara padang pasir makkah.")
            )
            5 -> listOf(
                QuizQuestion("Peristiwa perjalanan malam luar biasa Nabi Muhammad dari Masjidil Haram ke Masjidil Aqsa disebut...", listOf("Mi'raj", "Isra' 🕋", "Hijrah", "Fathu Makkah"), 1, "Isra' merupakan perjalanan horizontal kilat di malam hari."),
                QuizQuestion("Peristiwa naiknya Nabi Muhammad SAW dari Masjidil Aqsa ke langit ketujuh Sidratul Muntaha disebut...", listOf("Isra'", "Mi'raj 🕋", "Nuzulul Quran", "Safar"), 1, "Mi'raj kenaikan vertikal melampaui batas dimensi alam ciptaan."),
                QuizQuestion("Kewajiban utama ibadah umat Islam hasil perintah langsung dari peristiwa Isra' Mi'raj adalah...", listOf("Zakat fitrah", "Shalat fardhu 5 waktu 🕋", "Puasa sebulan penuh", "Ibadah haji"), 1, "Awal perintah 50 rakaat diringankan menjadi 5 waktu rahmat bagi mukmin."),
                QuizQuestion("Binatang tunggangan kilat cahaya sarana Isra' Mi'raj pendamping Jibril adalah...", listOf("Keledai biasa", "Buraq 🪽", "Unta merah", "Kuda bersayap"), 1, "Buraq melesat secepat kedipan mata menempuh jarak kosmik."),
                QuizQuestion("Sebelum berangkat Isra' Mi'raj dada Nabi Muhammad dibelah jibril untuk...", listOf("Cek kesehatan fisik", "Disucikan hati dengan air zam-zam diisi iman hikmah 💖", "Mencari luka perang", "Menghapus memori jahat"), 1, "Penyucian qolbu nabi menjaga kemurnian menerima mukjizat langit.")
            )
            6 -> listOf(
                QuizQuestion("Siapa golongan pertama dari sahabat nabi pembela syiar Islam fase awal yang disebut 'Assabiqunal Awwalun'?", listOf("Orang munafik Madinah", "Kelompok yang paling awal masuk Islam 🕋", "Musuh Quraisy", "Pasukan berkuda"), 1, "Pioneer dakwah tauhid bertaruh nyawa di bawah ancaman pembunuhan."),
                QuizQuestion("Tempat pusat dakwah rahasia pembinaan tauhid nabi fase makkah bertempat di rumah...", listOf("Rumah Abu Bakar", "Rumah Arqam bin Abil Arqam 🏠", "Gua Hira", "Darun Nadwah"), 1, "Darul Arqam madrasah pengkaderan pertama melahirkan ksatria Islam."),
                QuizQuestion("Hijrah pertama umat Islam mencari suaka keamanan atas saran nabi menuju negeri Kristen...", listOf("Madinah", "Habsyah (Abisinia/Etiopia) dipimpin Raja Najasyi 🌍", "Persia", "Yaman"), 1, "Raja Najasyi adil melindungi pelarian jemaah mukmin dari kejaran utusan Quraisy."),
                QuizQuestion("Sahabat nabi juru bicara fasih didepan Raja Najasyi Habsyah membela ajaran kesucian Isa dan Maryam adalah...", listOf("Abu Bakar", "Ja'far bin Abi Thalib 🗣️", "Utsman bin Affan", "Bilal bin Rabah"), 1, "Ja'far membaca surah Maryam menggetarkan tangisan haru raja kristen."),
                QuizQuestion("Warga kota Yatsrib yang menyambut kedatangan nabi menawarkan pertolongan disebut kaum...", listOf("Kaum Muhajirin", "Kaum Anshar (Penolong) 🤝", "Kaum Aus saja", "Kaum Khajraj saja"), 1, "Anshar berbagi rumah, harta, dan ladang demi saudara muslim seiman.")
            )
            7 -> listOf(
                QuizQuestion("Peristiwa perpindahan besar-besaran Nabi dan sahabat dari Makkah ke kota Yatsrib (Madinah) disebut...", listOf("Isra'", "Hijrah 🗺️", "Mi'raj", "Fathu Makkah"), 1, "Hijrah menandai titik balik kekuatan Islam ke arah kemerdekaan syiar."),
                QuizQuestion("Mengapa kota tujuan hijrah nabi berganti nama menjadi 'Madinatun Nabi' (Madinah)?", listOf("Keinginan raja asing", "Menghormati nabi sebagai pusat peradaban baru (Madinah) 🏛️", "Nama kuno sumur", "Saran musuh"), 1, "Madinah artinya kota cahaya peradaban berlandaskan hukum syariah adil."),
                QuizQuestion("Siapa nama sahabat setia menemani nabi bersembunyi di Gua Tsur dari kejaran pembunuh makkah?", listOf("Ali bin Abi Thalib", "Abu Bakar As-Siddiq 🪨", "Umar bin Khattab", "Zaid bin Haritsah"), 1, "Abu Bakar merelakan kakinya disengat kalajengking demi menjaga tidur nabi."),
                QuizQuestion("Siapa ksatria muda pemberani yang tidur di ranjang nabi mengecoh para algojo pembunuh Quraisy?", listOf("Abu Bakar", "Ali bin Abi Thalib ⚔️", "Hamzah", "Umar bin Khattab"), 1, "Ali rela bertaruh nyawa demi mengamankan lolosnya nabi berhijrah."),
                QuizQuestion("Nama gua sempit tempat nabi dan Abu Bakar bersembunyi yang diselamatkan sarang laba-laba dan burung merpati adalah...", listOf("Gua Hira", "Gua Tsur 🪨", "Gua Kahfi", "Gua Uhud"), 1, "Gua Tsur mengubur pelacakan musuh lewat mukjizat sarang laba-laba ringkih.")
            )
            8 -> listOf(
                QuizQuestion("Perang besar pertama dalam sejarah perjuangan membela agama Islam adalah perang...", listOf("Perang Uhud", "Perang Badar Al-Kubra ⚔️", "Perang Khandaq", "Perang Hunain"), 1, "Badar terjadi 17 Ramadhan mempertemukan 313 mukmin melawan 1000 bersenjata lengkap Quraisy."),
                QuizQuestion("Berapakah kekuatan jumlah pasukan muslim yang bertarung di lembah Badar?", listOf("1000 pasukan", "313 pasukan mukmin bersenjata seadanya ⚔️", "100 pasukan", "10.000 pasukan"), 1, "Sedikit jemaah mengalahkan gerombolan besar atas pertolongan malaikat."),
                QuizQuestion("Siapa tokoh gembong musuh Islam pemimpin Quraisy yang tewas mengenaskan di perang Badar?", listOf("Abu Sufyan", "Abu Jahal (Amr bin Hisyam) 🚨", "Abu Lahab", "Umayah bin Khalaf"), 1, "Abu Jahal (Firaun umat ini) tewas menandai kehancuran kesombongan syirik."),
                QuizQuestion("Lembah lokasi terjadinya perang kedua dimana kaum muslimin kalah akibat joki pemanah melanggar perintah nabi adalah...", listOf("Lembah Badar", "Bukit Uhud ⛰️", "Parit Salman", "Gurun Tabuk"), 1, "Pemanah Uhud tergiur harta rampasan perang (Ghanimah) berujung kepungan maut."),
                QuizQuestion("Siapa paman nabi berjuluk 'Asadullah' (Singa Allah) yang syahid gugur dimutilasi di perang Uhud?", listOf("Abbas", "Hamzah bin Abdul Muthalib 🦁", "Abu Thalib", "Ja'far bin Abi Thalib"), 1, "Syuhada agung Hamzah dibunuh tombak Wahsyi atas hasutan Hindun.")
            )
            9 -> listOf(
                QuizQuestion("Sahabat nabi penegak keadilan pengganti Abu Bakar memimpin kekhalifahan Islam pertama adalah...", listOf("Utsman bin Affan", "Umar bin Khattab (Al-Faruq) 👑", "Ali bin Abi Thalib", "Muawiyah"), 1, "Umar pembuka kejayaan Islam meluas hingga Persia Romawi."),
                QuizQuestion("Gelar mulia khalifah Umar bin Khattab 'Al-Faruq' memiliki arti...", listOf("Pemilik pedang sakti", "Pembeda antara kebenaran (hak) dan kebatilan ⚖️", "Dermawan santun", "Penulis wahyu"), 1, "Al-Faruq ketegasan memilah hitam putih kebenaran syariat."),
                QuizQuestion("Sahabat nabi terkaya yang mendermakan seluruh sumur air dan pembiayaan pasukan perang adalah...", listOf("Umar bin Khattab", "Utsman bin Affan (Dzun Nurain) 💰", "Ali bin Abi Thalib", "Aburrahman bin Auf"), 1, "Utsman membeli sumur Raumah dari Yahudi digratiskan untuk seluruh rakyat madinah."),
                QuizQuestion("Gelar ustadz mulia sahabat Utsman bin Affan 'Dzun Nurain' artinya...", listOf("Singa padang pasir", "Pemilik dua cahaya (Menikahi dua putri Nabi) 🌸", "Penakluk persia", "Penjaga harta"), 1, "Dzun Nurain menikahi Ruqayyah dan Ummu Kultum berturut-turut."),
                QuizQuestion("Sahabat nabi gerbangnya ilmu, ahli hukum syariat cerdas khulafaur rasyidin ke-4 adalah...", listOf("Abu Bakar", "Ali bin Abi Thalib (Babul 'Ilmi) 🧠", "Umar bin Khattab", "Utsman bin Affan"), 1, "Ali bin Abi Thalib dijuluki nabi kota ilmu sedangkan Ali gerbang masuknya.")
            )
            10 -> listOf(
                QuizQuestion("Peristiwa pembebasan damai kota suci Makkah dari cengkeraman berhala Quraisy tahun 8 Hijrah disebut...", listOf("Fathu Makkah 🕋", "Perang Badar", "Perjanjian Hudaibiyah", "Haji Wada"), 0, "Fathu Makkah pembebasan teladan tanpa pertumpahan darah setetes pun."),
                QuizQuestion("Bagaimana perlakuan nabi terhadap para tawanan musuh Quraisy pasca kemenangan Fathu Makkah?", listOf("Disiksa kejam", "Dimaafkan massal dibebaskan merdeka tanpa dendam 🤝", "Dijadikan budak berat", "Diusir gersang"), 1, "Nabi membacakan amnesti umum mencontohkan keluhuran pemaaf tiada tanding."),
                QuizQuestion("Berapa jumlah berhala bergelantungan di Ka'bah yang dihancurkan nabi saat Fathu Makkah?", listOf("10 berhala", "Sekitar 360 berhala kayu batu 🕋", "1000 berhala", "50 berhala"), 1, "Sambil memukul patung nabi membaca: kebenaran datang, batil lenyap."),
                QuizQuestion("Perjanjian genjatan senjata 10 tahun sebelum Fathu Makkah yang dimanfaatkan dakwah nabi bernama...", listOf("Piagam Madinah", "Perjanjian Hudaibiyah 📜", "Baiat Aqabah", "Boikot Quraisy"), 1, "Perjanjian membawa hikmah pelonjakan penganut Islam pesat."),
                QuizQuestion("Siapa panglima penunggang kuda Quraisy terkuat yang masuk Islam pasca perjanjian Hudaibiyah?", listOf("Abu Jahal", "Khalid bin Walid (Pedang Allah yang Terhunus) ⚔️", "Abu Sufyan", "Ikrimah"), 1, "Khalid bin Walid mualaf menaruh nyawa memimpin kemenangan tempur Islam.")
            )
            11 -> listOf(
                QuizQuestion("Dokumen piagam perjanjian tertulis pertama dunia mengatur toleransi suku agama di madinah bernama...", listOf("Deklarasi HAM", "Piagam Madinah (Constitution of Medina) 📜", "Perjanjian Aqabah", "Hudaibiyah"), 1, "Piagam Madinah menjunjung kebebasan beragama mendirikan pertahanan kota bersama."),
                QuizQuestion("Semboyan kerukunan toleransi beragama dalam Al-Qur'an terpatri mulia bunyinya...", listOf("Semua agama sama", "Untukmu agamamu, dan untukku agamaku (Surat Al-Kafirun: 6) 🕋", "Bersekutu kuat", "Bebas berzina"), 1, "Lakum diinukum waliyadiin batas tegas ketauhidan menghargai tetangga."),
                QuizQuestion("Bagaimana sikap kaum muslimin terhadap tetangga non-muslim yang damai tidak memerangi?", listOf("Mengusir paksa", "Berbuat baik adil dan menyambung hubungan sosial santun 🤝", "Memusuhi tertutup", "Mencuri hartanya"), 1, "Islam memerintahkan tetangga dihargai aman jiwanya."),
                QuizQuestion("Nabi memandu jemaah berdiri menghormati kereta usungan mayit yang lewat, sekalipun mayit...", listOf("Musuh perang", "Seorang penganut Yahudi sosisal 👤", "Orang gila", "Sahabat dekat"), 1, "Nabi berkata: bukankah ia juga sesama jiwa ciptaan Allah? Nilai kemanusiaan tinggi."),
                QuizQuestion("Suku Yahudi Madinah yang melanggar kesepakatan piagam berkhianat memihak musuh perang ahzab adalah...", listOf("Suku Aus", "Suku Bani Quraizhah, Qainuqa dan Nadhir 🚨", "Kaum Anshar", "Umat Nasrani"), 1, "Diusir mufakat setelah bersekutu menusuk dari belakang pertahanan parit.")
            )
            12 -> listOf(
                QuizQuestion("Masa keemasan kejayaan ilmu pengetahuan Islam (Golden Age) berakar kuat dinasti...", listOf("Dinasti Umayyah", "Dinasti Abbasiyah (Pusat Baghdad) 🏛️", "Ottoman Turki", "Mamluk Mesir"), 1, "Lembaga riset Baitul Hikmah Baghdad melahirkan kedokteran astronomi modern."),
                QuizQuestion("Perpustakaan pusat penerjemahan sains intelektual megah di kota Baghdad dinamakan...", listOf("Al-Azhar", "Baitul Hikmah (House of Wisdom) 📚", "Universitas Cordoba", "Taj Mahal"), 1, "Wadah ilmuwan muslim nasrani persia bersinergi menerjemahkan naskah Yunani."),
                QuizQuestion("Ilmuwan muslim penemu sistem angka Aljabar modern dan algoritma adalah...", listOf("Ibnu Sina", "Al-Khawarizmi 📊", "Al-Biruni", "Ibnu Khaldun"), 1, "Kitab Al-Jabr wa-al-Muqabala mendasari pemrograman digital android masa kini."),
                QuizQuestion("Bapak kedokteran dunia muslim penulis ensiklopedia 'The Canon of Medicine' adalah...", listOf("Ibnu Rusyd", "Ibnu Sina (Avicenna) 🩺", "Ar-Razi", "Ibnu Nafis"), 1, "Karya rujukan kedokteran Eropa selama berabad-abad."),
                QuizQuestion("Sosiolog sejarawan muslim pelopor ilmu filsafat sejarah penemu teori 'Muqaddimah' adalah...", listOf("Ibnu Khaldun 📜", "Al-Ghazali", "Ibnu Batutah", "Al-Kindi"), 0, "Analisis siklus dinasti peradaban menjadi rujukan sosiologi modern.")
            )
            13 -> listOf(
                QuizQuestion("Dua suku asli kota Yatsrib yang bertikai ratusan tahun sebelum dipersaudarakan nabi adalah...", listOf("Quraisy dan Hasyim", "Aus dan Khazraj 🤝", "Nadhir dan Quraizhah", "Muzainah dan Juhainah"), 1, "Nabi menyatukan hati permusuhan Aus Khazraj di bawah ikatan ukhuwah Islamiyah."),
                QuizQuestion("Siapakah tokoh ulama legendaris penyebar Islam di Pulau Jawa Indonesia?", listOf("Umat Buddha", "Wali Songo (Sembilan Wali) 🕌", "Saudagar Gujarat saja", "Ksatria Majapahit"), 1, "Wali Songo menyebarkan Islam ramah akulturasi budaya damai."),
                QuizQuestion("Sembilan wali pertama pilar dakwah Islamiyah di Nusantara bermula kepemimpinan...", listOf("Sunan Kalijaga", "Sunan Gresik (Maulana Malik Ibrahim) 🌟", "Sunan Bonang", "Sunan Ampel"), 1, "Sunan Gresik merintis dakwah pertanian pengobatan murah rakyat jelata."),
                QuizQuestion("Wali Jawa penemu wayang kultural gamelan tembang pengingat dhuha ilir-ilir adalah...", listOf("Sunan Kudus", "Sunan Kalijaga 🎭", "Sunan Giri", "Sunan Drajat"), 1, "Kalijaga memadukan kesenian daerah mengenalkan tatanan moral tauhid tanpa memicu konflik."),
                QuizQuestion("Wali penemu bangunan menara masjid unik khas akulturasi candi hindu di Jawa adalah...", listOf("Sunan Muria", "Sunan Kudus (Masjid Menara Kudus) 🏛️", "Sunan Ampel", "Sunan Gunung Jati"), 1, "Kudus menjunjung tinggi toleransi melarang menyembelih sapi dakwah menghormati tetangga hindu.")
            )
            14 -> listOf(
                QuizQuestion("Tahun kesedihan mendalam Nabi Muhammad SAW ditinggal wafat pelindungnya dinamakan...", listOf("Amul Fiil", "Amul Huzni 🥀", "Hijrah", "Amul Jama'ah"), 1, "Wafatnya istri tercinta Khadijah dan paman Abu Thalib pelindung fisik dakwah."),
                QuizQuestion("Berapa usia perkawinan bahagia nabi dengan Ibunda Khadijah binti Khuwailid?", listOf("10 tahun", "25 tahun (Hingga Khadijah wafat) 👑", "5 tahun", "40 tahun"), 1, "Pernikahan sakral monogami sejati nabi dengan Khadijah tanpa memadunya."),
                QuizQuestion("Fase dakwah nabi penuh siksaan berdarah terpaksa dilempari batu oleh penduduk kota pegunungan...", listOf("Kota Madinah", "Kota Thaif ⛰️", "Kota Yaman", "Kota Habsyah"), 1, "Nabi terluka dilempari batu namun menolak tawaran malaikat membalik gunung kepada mereka."),
                QuizQuestion("Bagaimana doa pemaaf nabi menyikapi kebiadaban penduduk Thaif yang melempari batu?", listOf("Memohon azab hancur", "Ya Allah berilah petunjuk kaumku, sesungguhnya mereka tidak mengetahui 🕋", "Mengutuk keras", "Menantang perang"), 1, "Kelembutan dakwah agung mengharapkan keturunan beriman lahir dari kota Thaif."),
                QuizQuestion("Persekutuan besar kaum musyrikin mengepung madinah dengan parit dalam perang...", listOf("Perang Badar", "Perang Ahzab (Khandaq) ⚔️", "Perang Uhud", "Perang Tabuk"), 1, "Khandaq parit ide jenius sahabat Salman Al-Farisi menghalau 10.000 koalisi pengepung.")
            )
            15 -> listOf(
                QuizQuestion("Ibadah haji bersejarah terakhir kalinya dikerjakan Nabi Muhammad di akhir hayatnya disebut...", listOf("Haji Pertama", "Haji Wada' (Haji Perpisahan) 🕋", "Umrah Qadha", "Haji Ifrad"), 1, "Haji wada tahun 10 hijrah menyiratkan pesan amanat terakhir melepas umat."),
                QuizQuestion("Pemberitaan khutbah nabi dihadapan ratusan ribu jemaah haji wada padang arafah berisi...", listOf("Deklarasi perang baru", "Kesetaraan manusia, penghapusan riba, penghormatan wanita ⚖️", "Memburu harta", "Ramalan takdir bintang"), 1, "Deklarasi piagam kemanusiaan agung merobohkan sekat rasisme."),
                QuizQuestion("Ayat Al-Qur'an terakhir pertanda telah disempurnakannya ajaran Islam diturunkan di Arafah...", listOf("Surah Al-Fatihah", "Surah Al-Ma'idah ayat 3 📖", "Surat An-Nas", "Surat Al-Baqarah 282"), 1, "Al-yauma akmaltu lakum diinakum rida Islam sebagai agama."),
                QuizQuestion("Berapakah usia Nabi Muhammad SAW saat dipanggil menghadap Allah (wafat)?", listOf("40 tahun", "63 tahun 🕋", "70 tahun", "50 tahun"), 1, "Mangkat di Madinah setelah menyelesaikan tugas dakwah risalah sempurna."),
                QuizQuestion("Amanat terakhir warisan nabi yang jika dipegang teguh umat tidak akan sesat selamanya adalah...", listOf("Kumpulan emas", "Al-Qur'an dan Sunnah nabi-Nya 🕋", "Sebuah pedang", "Benteng pertahanan"), 1, "Dua warisan abadi lentera jalan mukmin mengarungi kebenaran.")
            )
            else -> getSejarahIslamPool(15)
        }
    }
}
