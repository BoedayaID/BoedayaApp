package com.boedayaid.boedayaapp.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.boedayaid.boedayaapp.databinding.ActivityDetailBinding
import com.boedayaid.boedayaapp.ui.translate.TranslateActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var sukuId: Int? = null
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        sukuId = intent.getIntExtra("SUKU_ID", 0)

        binding.fabTranslate.setOnClickListener {
            Intent(this, TranslateActivity::class.java).also {
                it.putExtra("SUKU_NAME", "Sunda")
                startActivity(it)
            }
        }
    }
}