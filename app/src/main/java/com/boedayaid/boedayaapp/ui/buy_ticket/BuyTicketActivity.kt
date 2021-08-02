package com.boedayaid.boedayaapp.ui.buy_ticket

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.databinding.ActivityBuyTicketBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream

class BuyTicketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyTicketBinding
    private lateinit var tempatWisata: DetailSuku.TempatWisata
    private var jumlah = 1;
    private var total = 25000;
    private val viewModel: BuyTicketViewModel by viewModels()
    private var hasImage = false
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
            total = jumlah * 25000
            binding.tvTotal.text = "Rp.${total}.00-"
        }

        binding.btnKurang.setOnClickListener {
            if (jumlah != 1) {
                jumlah--
                binding.tvJumlah.text = jumlah.toString()
                total = jumlah * 25000
                binding.tvTotal.text = "Rp.${total}.00-"
            }
        }

        binding.imgProve.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                resultLauncher.launch(it)
            }
        }

        binding.btnBeli.setOnClickListener {
            if (hasImage) {
                val uid = Firebase.auth.currentUser?.uid
                if (uid != null) {
                    val bitmap = getBitmapFromView(binding.imgProve)
                    val baos = ByteArrayOutputStream()
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()
                    viewModel.buyTicket(uid, tempatWisata, jumlah, data)
                    Toast.makeText(
                        applicationContext,
                        "Berhasil membeli tiket !!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Upload bukti pembayaran terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intentData = result.data
            val imageUri = intentData?.data
            val imageStream = imageUri?.let { contentResolver.openInputStream(it) }
            val bitmap = BitmapFactory.decodeStream(imageStream)

            binding.imgProve.setImageBitmap(bitmap)
            hasImage = true
        }
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}