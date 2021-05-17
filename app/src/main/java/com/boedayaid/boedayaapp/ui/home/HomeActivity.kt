package com.boedayaid.boedayaapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.data.model.Island
import com.boedayaid.boedayaapp.databinding.ActivityHomeBinding
import com.google.android.material.chip.Chip
import java.util.*
import kotlin.math.abs

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var provinceAdapter: ProvinceAdapter
    private var islands = listOf<Island>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                val temp = r * 0.12f
                Log.d("TRANS", temp.toString())
                page.scaleY = 0.90f + r * 0.05f
            }
        }
        provinceAdapter = ProvinceAdapter()
        binding.carouselProvince.apply {
            adapter = provinceAdapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
        }


        viewModel.listIsland.observe(this) { lands ->
            islands = lands

            for (index in lands.indices) {
                val chipIsland = layoutInflater.inflate(
                    R.layout.chip_island,
                    binding.chipGroupIndoIslands,
                    false
                ) as Chip
                chipIsland.id = index
                chipIsland.text = lands[index].name
                binding.chipGroupIndoIslands.addView(chipIsland)
            }
        }

        viewModel.listProvince.observe(this) { listProvince ->
            provinceAdapter.setList(listProvince)
        }

        binding.chipGroupIndoIslands.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.all_islands -> {
                    viewModel.selectedIsland.value = "all"
                }
                else -> {
                    viewModel.selectedIsland.value =
                        islands[checkedId].name.lowercase(Locale.getDefault())
                }
            }
        }
    }

}