package com.boedayaid.boedayaapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.databinding.ActivityDetailBinding
import com.boedayaid.boedayaapp.ui.detail.adapter.KesenianAdapter
import com.boedayaid.boedayaapp.ui.detail.adapter.MakananAdapter
import com.boedayaid.boedayaapp.ui.detail.adapter.RumahAdatAdapter
import com.boedayaid.boedayaapp.ui.detail.adapter.TempatWisataAdapter
import com.boedayaid.boedayaapp.ui.detail.infodialog.InfoDialogFragment
import com.boedayaid.boedayaapp.ui.translate.TranslateActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    private var sukuId: Int? = null

    private lateinit var rumahAdatAdapter: RumahAdatAdapter
    private lateinit var kesenianAdapter: KesenianAdapter
    private lateinit var makananAdapter: MakananAdapter
    private lateinit var tempatWisataAdapter: TempatWisataAdapter

    private var rumahAdat = emptyList<DetailSuku.RumahAdat>()
    private var kesenianDaerah = emptyList<DetailSuku.Kesenian>()
    private var makananDaerah = emptyList<DetailSuku.Makanan>()
    private var tempatWisataDaerah = emptyList<DetailSuku.TempatWisata>()

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
        viewModel.getDetailSuku(sukuId!!)
        showLoading(true)

        // config rumah adat
        rumahAdatAdapter = RumahAdatAdapter()
        rumahAdatAdapter.setOnClick { position ->
            val rumah = rumahAdat[position]
            val infoDialog = InfoDialogFragment.newInstance(
                rumah.gambar!!,
                rumah.namaRumahAdat!!,
                rumah.deskripsi!!
            )
            infoDialog.show(supportFragmentManager, "Info Dialog")
        }
        binding.rvRumahAdat.apply {
            adapter = rumahAdatAdapter
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        // config kesenian daerah
        kesenianAdapter = KesenianAdapter()
        kesenianAdapter.setOnClick { position ->
            val seni = kesenianDaerah[position]
            val infoDialog = InfoDialogFragment.newInstance(
                seni.gambar!!,
                seni.namaKesenian!!,
                seni.deskripsi!!
            )
            infoDialog.show(supportFragmentManager, "Info Dialog")
        }
        binding.rvKesenianDaerah.apply {
            adapter = kesenianAdapter
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        // config makanan daerah
        makananAdapter = MakananAdapter()
        makananAdapter.setOnClick { position ->
            val makan = makananDaerah[position]
            val infoDialog = InfoDialogFragment.newInstance(
                makan.gambar!!,
                makan.namaMakanan!!,
                makan.deskripsi!!
            )
            infoDialog.show(supportFragmentManager, "Info Dialog")
        }
        binding.rvMakananDaerah.apply {
            adapter = makananAdapter
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        // config tempat wisata
        tempatWisataAdapter = TempatWisataAdapter()
        tempatWisataAdapter.setOnClick { position ->
            val wisata = tempatWisataDaerah[position]
            val infoDialog = InfoDialogFragment.newInstance(
                wisata.gambar!!,
                wisata.namaTempat!!,
                wisata.deskripsi!!
            )
            infoDialog.show(supportFragmentManager, "Info Dialog")
        }
        binding.rvTempatWisata.apply {
            adapter = tempatWisataAdapter
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        viewModel.detailSuku.observe(this) { detailSuku ->

            rumahAdat = detailSuku.rumahAdat as List<DetailSuku.RumahAdat>
            rumahAdatAdapter.setList(rumahAdat)
            kesenianDaerah = detailSuku.kesenian as List<DetailSuku.Kesenian>
            kesenianAdapter.setList(kesenianDaerah)
            makananDaerah = detailSuku.makanan as List<DetailSuku.Makanan>
            makananAdapter.setList(makananDaerah)
            tempatWisataDaerah = detailSuku.tempatWisata as List<DetailSuku.TempatWisata>
            tempatWisataAdapter.setList(tempatWisataDaerah)

            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(detailSuku.gambar)
                    .into(imgSuku)

                tvSukuName.text = detailSuku.namaSuku
                tvSejarah.text = detailSuku.sejarah
            }

            showLoading(false)
            if (detailSuku.aksaraTranslate == 0) {
                binding.fabTranslate.visibility = View.GONE
            } else {
                binding.fabTranslate.visibility = View.VISIBLE
            }
        }

        binding.fabTranslate.setOnClickListener {
            Intent(this, TranslateActivity::class.java).also {
                it.putExtra("SUKU_NAME", "Sunda")
                startActivity(it)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                loading.visibility = View.VISIBLE
                imgSuku.visibility = View.GONE
                fabTranslate.visibility = View.GONE
                tvSukuName.visibility = View.GONE
                sejarah.visibility = View.GONE
                tvSejarah.visibility = View.GONE
                rumah.visibility = View.GONE
                rvRumahAdat.visibility = View.GONE
                kesenian.visibility = View.GONE
                rvKesenianDaerah.visibility = View.GONE
                makanan.visibility = View.GONE
                rvMakananDaerah.visibility = View.GONE
                tempatWisata.visibility = View.GONE
                rvTempatWisata.visibility = View.GONE
            }
        } else {
            binding.apply {
                loading.visibility = View.GONE
                imgSuku.visibility = View.VISIBLE
                fabTranslate.visibility = View.VISIBLE
                tvSukuName.visibility = View.VISIBLE
                sejarah.visibility = View.VISIBLE
                tvSejarah.visibility = View.VISIBLE
                rumah.visibility = View.VISIBLE
                rvRumahAdat.visibility = View.VISIBLE
                kesenian.visibility = View.VISIBLE
                rvKesenianDaerah.visibility = View.VISIBLE
                makanan.visibility = View.VISIBLE
                rvMakananDaerah.visibility = View.VISIBLE
                tempatWisata.visibility = View.VISIBLE
                rvTempatWisata.visibility = View.VISIBLE
            }
        }
    }
}