package com.boedayaid.boedayaapp.ui.detail_wisata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.databinding.ActivityDetailBinding
import com.boedayaid.boedayaapp.databinding.ActivityDetailWisataBinding
import com.bumptech.glide.Glide

class DetailWisataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailWisataBinding
    private lateinit var wisata: DetailSuku.TempatWisata
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWisataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        wisata = intent.getParcelableExtra("extra_data")!!

        binding.apply {
            tvNamaTempat.text = wisata.namaTempat.toString()
            Glide.with(applicationContext)
                .load(wisata.gambar)
                .into(imgTempat)

            tvAlamatTempat.text = wisata.alamat
            tvDescTempat.text = wisata.deskripsi
        }

    }
}