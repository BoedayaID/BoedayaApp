package com.boedayaid.boedayaapp.ui.buy_ticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.databinding.ActivityBuyTicketBinding
import com.bumptech.glide.Glide

class BuyTicketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyTicketBinding
    private lateinit var tempatWisata: DetailSuku.TempatWisata
    private var jumlah = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        tempatWisata =
            intent.getParcelableExtra<DetailSuku.TempatWisata>("EXTRA_DATA") as DetailSuku.TempatWisata


        binding.apply {
            tvNamaTempat.text = tempatWisata.namaTempat.toString()
            Glide.with(applicationContext)
                .load(tempatWisata.gambar)
                .into(imgTempat)

            tvAlamatTempat.text = tempatWisata.alamat
            tvDescTempat.text = tempatWisata.deskripsi
        }

        binding.btnTambah.setOnClickListener {
            jumlah++
            binding.tvJumlah.text = jumlah.toString()
        }

        binding.btnKurang.setOnClickListener {
            if(jumlah !=1){
                jumlah--
                binding.tvJumlah.text = jumlah.toString()
            }
        }
    }
}