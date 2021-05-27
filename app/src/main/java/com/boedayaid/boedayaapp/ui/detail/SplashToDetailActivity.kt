package com.boedayaid.boedayaapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.boedayaid.boedayaapp.databinding.ActivitySplashToDetailBinding
import com.bumptech.glide.Glide

class SplashToDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashToDetailBinding

    private var sukuId: Int? = null
    private var sukuName: String? = null
    private var sukuImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashToDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sukuId = intent.getIntExtra("SUKU_ID", 0)
        sukuName = intent.getStringExtra("SUKU_NAME")
        sukuImage = intent.getStringExtra("SUKU_IMAGE")

        binding.tvSukuName.text = sukuName
        Glide.with(this)
            .load(sukuImage)
            .into(binding.imgSplash)

        Handler().postDelayed({
            Intent(this@SplashToDetailActivity, DetailActivity::class.java).also {
                it.putExtra("SUKU_ID", sukuId)
                startActivity(it)
                finish()
            }
        }, 700)

    }
}