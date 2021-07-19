package com.boedayaid.boedayaapp.ui.draw_aksara

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.boedayaid.boedayaapp.data.model.Aksara
import com.boedayaid.boedayaapp.databinding.ActivityDrawBinding
import com.bumptech.glide.Glide

class DrawActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_STORAGE = 1337
    }

    private lateinit var binding: ActivityDrawBinding
    private val viewModel: DrawViewModel by viewModels()
    private lateinit var aksara: Aksara

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        aksara = intent.getParcelableExtra<Aksara>("EXTRA_DATA") as Aksara
        binding.apply {
            Glide.with(applicationContext)
                .load(aksara.symbol)
                .into(imgAksara)
            tvAksara.text = "Aksara ${aksara.nama}"
        }

        binding.btnDelete.setOnClickListener {
            binding.drawView.delete()
        }

        binding.btnCheck.setOnClickListener {
            val bitmap = getBitmapFromView(binding.drawView)
            viewModel.predictAksara(bitmap!!, aksara.nama)
        }

        viewModel.aksaraState.observe(this) { state ->
            when (state["state"]) {
                DrawViewModel.RESULT_LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.tvResult.visibility = View.GONE
                }
                DrawViewModel.RESULT_WRONG -> {
                    binding.loading.visibility = View.GONE
                    binding.tvResult.visibility = View.VISIBLE
                    binding.tvResult.setTextColor(Color.RED)
                    binding.tvResult.text =
                        "Maaf, aksara yang anda gambar terprediksi ${state["actual_result"]}, silahkan coba lagi"
                }
                DrawViewModel.RESULT_CORRECT -> {
                    binding.loading.visibility = View.GONE
                    binding.tvResult.visibility = View.VISIBLE
                    binding.tvResult.setTextColor(Color.BLACK)
                    binding.tvResult.text =
                        "Benar, anda sudah bisa menggambar aksara ${state["actual_result"]}"
                }
                else -> {
                    binding.loading.visibility = View.GONE
                    binding.tvResult.visibility = View.VISIBLE
                    binding.tvResult.text = "Error, silahkan cek koneksi dan coba lagi"
                }
            }
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