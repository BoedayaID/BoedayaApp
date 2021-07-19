package com.boedayaid.boedayaapp.ui.detail_aksara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.data.model.Aksara
import com.boedayaid.boedayaapp.databinding.ActivityDetailAksaraBinding
import com.boedayaid.boedayaapp.ui.draw_aksara.DrawActivity

class DetailAksaraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAksaraBinding

    private lateinit var aksaraAdapter: AksaraAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAksaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        aksaraAdapter = AksaraAdapter()
        aksaraAdapter.setList(listAksaraJawa)
        aksaraAdapter.setOnClick { aksara ->
            Intent(applicationContext, DrawActivity::class.java).also {
                it.putExtra("EXTRA_DATA", aksara)
                startActivity(it)
            }
        }

        binding.listAksara.apply {
            adapter = aksaraAdapter
            layoutManager = GridLayoutManager(applicationContext, 3)
            setHasFixedSize(true)
        }

    }

    val listAksaraJawa = listOf(
        Aksara("Ha", R.drawable.img_aksara_ha),
        Aksara("Na", R.drawable.img_aksara_na),
        Aksara("Ca", R.drawable.img_aksara_ca),
        Aksara("Ra", R.drawable.img_aksara_ra),
        Aksara("Ka", R.drawable.img_aksara_ka),
        Aksara("Da", R.drawable.img_aksara_da),
        Aksara("Ta", R.drawable.img_aksara_ta),
        Aksara("Sa", R.drawable.img_aksara_sa),
        Aksara("Wa", R.drawable.img_aksara_wa),
        Aksara("La", R.drawable.img_aksara_la),
        Aksara("Pa", R.drawable.img_aksara_pa),
        Aksara("Dha", R.drawable.img_aksara_dha),
        Aksara("Ja", R.drawable.img_aksara_ja),
        Aksara("Ya", R.drawable.img_aksara_ya),
        Aksara("Nya", R.drawable.img_aksara_nya),
        Aksara("Ma", R.drawable.img_aksara_ma),
        Aksara("Ga", R.drawable.img_aksara_ga),
        Aksara("Ba", R.drawable.img_aksara_ba),
        Aksara("Tha", R.drawable.img_aksara_tha),
        Aksara("Nga", R.drawable.img_aksara_nga),
    )
}