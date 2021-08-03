package com.boedayaid.boedayaapp.data.model

data class Recipe(
    val nama: String,
    val bahan: List<String>,
    val bumbu: List<String>,
    val cara: List<String>,
)


val DUMMY_RECIPE = listOf(
    Recipe(
        "Karedok",
        listOf(
            "¼ buah kol, iris tipis ",
            "5 buah kacang panjang, iris tipis",
            "100 gram taoge",
            "1 buah mentimun, iris tipis",
            "10 lembar daun kemangi",
            "2 buah terung gelatik, iris tipis"
        ),
        listOf(
            "2 buah cabai merah keriting",
            "3 buah cabai rawit hijau",
            "½ siung bawang putih",
            "1 ruas jari kencur",
            "1 sdt garam",
            "200 gram kacang tanah goreng, haluskan",
            "2 sdm gula merah",
            "1 sdm ubi rebus",
            "1 sdm air asam jawa"
        ),
        listOf(
            "Siapkan semua sayuran yang sudah dicuci bersih. Sisihkan.",
            "Haluskan cabai merah, cabai rawit, bawang putih, kencur, dan garam. Masukkan kacang tanah, gula merah, dan ubi rebus. Ulek hingga rata dan halus. Tambahkan air asam jawa, aduk rata.",
            "Masukkan irisan kacang panjang, ulek hingga rata. Masukkan sayuran lain, aduk rata. Sajikan karedok segera dengan kerupuk"
        )
    ),
    Recipe(
        "Cimol",
        listOf(
            "200 gr tepung tapioca",
            "100 gr tepung terigu",
            "1 batang  daun bawang dan seledri",
            "Perasa buatan"
        ),
        listOf(
            "2 siung bawang putih",
            "Merica bubuk",
            "Garam, reyko ayam"
        ),
        listOf(
            "Pertama campurkan tepung dan beri taburan lada bubuk, setelah itu haluskan seledri, daun bawang dan 2 siung bawang putih, beri garam dan reyko",
            "Masukan kedalam wadah setelah dihaluskan, siapkan air panas yang mendidih dan campurkan sedikit demi sedikit, jangan kebanyakan. Setelah itu bentuk bulat”, dan setelah itu digoreng",
            "Goreng cimol sampai mengembang, dan setelah Cimol sudah matang, tambahkan perasa buatan"
        ),
    ),
    Recipe(
        "Nasi Timbel",
        listOf(
            "1/2 cup beras",
            "cup air",
            "batang sereh, memarkan, ikat simpul",
            "lembar daun pandan, sobek, ikat simpul",
            "lembar daun jeruk",
            "genggam kemangi (optional, bila suka)",
            "secukupnya daun pisang",
            "1 buah labu siam kecil, kupas, potong dadu",
            "1 buah jagung manis, potong 3 cm",
            "3 batang kacang panjang, potong 5 cm",
            "1 buah terong kecil, potong tebal 1 cm",
            "1 genggam daun melinjo",
            "1 genggam buah melinjo",
            "2 ruas asam jawa",
            "1/2 sdt terasi",
            "lengkuas potong tebal 1 cm",
            "2 lembar daun salam",
            "1 sdt gula merah",
            "secukupnya garam",
        ),
        listOf(
            "8 buah cabe rawit merah",
            "8 buah cabe merah keriting",
            "1 siung bawang putih",
            "1 buah tomat mengkal",
            "2 lembar daun jeruk",
            "1/4 sdt kaldu bubuk instant, Royco",
            "1/2 sdt gula merah sisir halus",
            "secukupnya garam"
        ),
        listOf(
            "Membuat nasi : ambil panci kecil, masukkan beras, sereh, pandan, daun jeruk dan air. Masak kurang lebih 10-15 menit sampai air mengering. Angkat. Ambil daun pisang, letakkan nasi, kemangi (jika suka), bungkus rapi sesuka, kukus selama 30 menit. Angkat. Sisihkan",
            "Membuat sayur asam : ulek bawang putih, bawang merah, cabe, garam, gula dan terasi. Didihkan air, masukkan bumbu, daun salam dan asam jawa. Tambahkan sayuran berturut-turut melinjo, labu, jagung, kacang panjang, terong, daun melinjo. Masak sampai matang. Angkat, sisihkan",
            "Membuat sambal : ulek kasar semua bahan. Sisihkan.",
            "Sajikan nasi, sayur asam, sambal dengan makanan pelengkap : ayam, ikan jambal asin goreng, tempe dan tahu goreng serta lalapan.",
        ),
    ),
    Recipe(
        "Lotek",
        listOf(
            "Kangkung - 1 ikat",
            "Bayam - 1 ikat",
            "Tauge - 100 gram",
            "Kacang panjang - 100 gram",
            "Kol - 100 gram",
            "Labu siam - 100 gram"
        ),
        listOf(
            "Kacang tanah, goreng - 100 gram",
            "Bawang putih - 1 siung",
            "Kencur - 3 cm",
            "Cabai rawit - 3 buah",
            "Terasi, goreng - 1/2 sdt",
            "Garam - 1 sdt",
            "Gula merah - 1 sdt",
            "Air asam - 1 sdm",
            "Jeruk limau, ambil airnya - 1 buah",
            "Air matang – secukupnya"
        ),
        listOf(
            "Siapkan semua sayuran. Petik dan potong-potong kangkung serta  daun bayam. Cuci bersih dan tiriskan. Cuci bersih tauge, kacang panjang, kol, dan labu siam. Potong-potong kacang panjang dan kol. Iris kecil labu siam.",
            "Rebus masing-masing sayuran. Angkat dan tiriskan.",
            "Haluskan bawang putih, kencur, cabai rawit, dan garam.",
            "Tambahkan kacang tanah goreng dan gula merah. Haluskan kembali.",
            "Tuang air asam, air perasan jeruk limau, dan air matang secukupnya hingga saus berubah jadi pasta",
            "Masukan sayur rebus. Aduk rata hingga sayur terlumuri oleh saus seluruhnya.",
            "Tata di piring dan siap disajikan bersama bahan pelengkap.",
        ),
    ),
    Recipe(
        "Cimol",
        listOf(
            "1 kg mie kuning",
            "250 g kikil sapi rebus",
            "250 g daging tetelan sapi rebus",
            "20 butir bakso sapi",
            "200 g tauge",
            "Kuah:",
            "2 liter kaldu sapi",
            "3 lembar daun salam",
            "4 cm lengkuas, memarkan",
            "1 batang daun bawang, iris kasar",
        ),
        listOf(
            "8 siung bawang putihh (haluskan)",
            "5 butir bawang merah (haluskan)",
            "3 butir kemiri (haluskan)",
            "1 sdt merica butiran (haluskan)",
            "1 cm jahe (haluskan)",
            "1 sdm garam (haluskan)",
        ),
        listOf(
            "Kuah: Giling semua bahan bumbu hingga halus benar.",
            "Panaskan 4 sdm minyak dalam wajan. Tumis bumbu halus bersama daun salam dan lengkuas hingga harum dan matang.",
            "Masukkan bumbu ke dalam panci, tambahkan kaldu.",
            "Masak dengan api sedang hingga mendidih. Masukkan bakso, tetelan, kikil sapi dan daun bawang.",
            "Siram mie kuning dengan air panas lalu tiriskan.",
            "Seduh tauge sebentar dengan air mendidih dan tiriskan.",
            "Penyajian: Susum mie kuning, tauge, dan siram kaldu berikut bakso, kikil dan tetelan.",
            "Sajikan panas dengan Pelengkapnya."
        ),
    )
)