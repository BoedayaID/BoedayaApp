package com.boedayaid.boedayaapp.ui.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.boedayaid.boedayaapp.data.model.DUMMY_RECIPE
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.databinding.ActivityRecipeBinding
import com.bumptech.glide.Glide
import java.util.*

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        val makanan = intent.getParcelableExtra<DetailSuku.Makanan>("DATA")
        val resep = DUMMY_RECIPE.find {
            it.nama.lowercase(Locale.getDefault()) == makanan?.namaMakanan?.lowercase(
                Locale.getDefault()
            )
        }

        binding.apply {
            tvMakanan.text = makanan?.namaMakanan
            Glide.with(applicationContext)
                .load(makanan?.gambar)
                .into(imgMakanan)
            tvDescMakanan.text = makanan?.deskripsi
            tvBahan.text = resep?.bahan?.joinToString("\n-", "-")
            tvBumbu.text = resep?.bumbu?.joinToString("\n-", "-")
            tvCara.text = resep?.cara?.joinToString("\n\n-", "-")
        }

    }
}