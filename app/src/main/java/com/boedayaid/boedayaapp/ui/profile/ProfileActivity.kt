package com.boedayaid.boedayaapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.databinding.ActivityProfileBinding
import com.boedayaid.boedayaapp.ui.login.LoginActivity
import com.boedayaid.boedayaapp.ui.profile.tab_layout.TabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    private var user: FirebaseUser? = null

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

        user = Firebase.auth.currentUser

        if (user != null) {
            viewModel.getProfile(user?.uid!!)
        }

        viewModel.userProfile.observe(this) {
            binding.tvUsername.text = it.username
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
}