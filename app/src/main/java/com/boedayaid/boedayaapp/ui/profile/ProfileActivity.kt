package com.boedayaid.boedayaapp.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.databinding.ActivityProfileBinding
import com.boedayaid.boedayaapp.ui.login.LoginActivity
import com.boedayaid.boedayaapp.ui.profile.tab_layout.TabAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    private var user: FirebaseUser? = null

    private lateinit var imgProfile: ImageView
    private lateinit var btnEditImgProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        imgProfile = findViewById(R.id.img_profile)
        btnEditImgProfile = findViewById(R.id.btn_edit_image)

        user = Firebase.auth.currentUser

        if (user != null) {
            viewModel.getProfile(user?.uid!!)
        }

        viewModel.userProfile.observe(this) {
            binding.tvUsername.text = it.username
            val reference = Firebase.storage.reference.child(it.imageProfile)
            Glide.with(applicationContext)
                .load(reference)
                .into(binding.imgProfile)
        }

        btnEditImgProfile.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                resultLauncher.launch(it)
            }
        }

        val tabAdapter = TabAdapter(this)
        binding.pager.adapter = tabAdapter

        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = "Bucket"
                1 -> tab.text = "History"
            }
        }.attach()
    }

    override fun onStart() {
        super.onStart()
        if (user == null) {
            Intent(applicationContext, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_logout -> {
                viewModel.logout()
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
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

            imgProfile.setImageBitmap(bitmap)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            viewModel.changeImageProfile(data, user?.uid!!)
        }
    }
}