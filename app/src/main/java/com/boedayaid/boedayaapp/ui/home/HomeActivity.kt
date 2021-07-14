package com.boedayaid.boedayaapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.data.model.Island
import com.boedayaid.boedayaapp.data.model.Province
import com.boedayaid.boedayaapp.databinding.ActivityHomeBinding
import com.boedayaid.boedayaapp.ui.home.bottomsheet.BottomSheetFragment
import com.boedayaid.boedayaapp.ui.profile.ProfileActivity
import com.google.android.material.chip.Chip
import kotlin.math.abs

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var provinceAdapter: ProvinceAdapter
    private var bottomSheet: BottomSheetFragment = BottomSheetFragment()
    private var islands = listOf<Island>()
    private var province = listOf<Province>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BoedayaID)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoadingCarousel(true)

        val transformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(20))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.90f + r * 0.05f
            }
        }

        provinceAdapter = ProvinceAdapter()
        provinceAdapter.setOnClick { position ->
            bottomSheet = BottomSheetFragment.newInstance(
                province[position].id,
                province[position].name
            )
            bottomSheet.show(supportFragmentManager, "Bottom Sheet")
        }

        binding.carouselProvince.apply {
            adapter = provinceAdapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
        }

        viewModel.listIsland.observe(this) { lands ->
            showChipIsland()
            islands = lands

            for (index in lands.indices) {
                val chipIsland = layoutInflater.inflate(
                    R.layout.item_chip_island,
                    binding.chipGroupIndoIslands,
                    false
                ) as Chip
                chipIsland.id = index
                chipIsland.text = lands[index].name
                binding.chipGroupIndoIslands.addView(chipIsland)
            }
        }

        viewModel.selectedIsland.observe(this) { islandId ->
            showLoadingCarousel(true)
            viewModel.getListProvince(islandId)
        }

        viewModel.listProvince.observe(this) { listProvince ->
            showLoadingCarousel(false)
            province = listProvince
            provinceAdapter.setList(listProvince)
        }

        binding.chipGroupIndoIslands.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.all_islands -> {
                    viewModel.selectedIsland.value = 0
                }
                else -> {
                    viewModel.selectedIsland.value = islands[checkedId].id
                }
            }
        }

        binding.btnProfile.setOnClickListener {
            Intent(applicationContext, ProfileActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (bottomSheet.isVisible) {
            bottomSheet.dismiss()
        }
    }

    private fun showLoadingCarousel(state: Boolean) {
        if (state) {
            binding.loading.visibility = View.VISIBLE
            binding.carouselProvince.visibility = View.GONE
        } else {
            binding.loading.visibility = View.GONE
            binding.carouselProvince.visibility = View.VISIBLE
        }
    }

    private fun showChipIsland() {
        binding.chipGroupIndoIslands.visibility = View.VISIBLE
    }
}