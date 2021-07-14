package com.boedayaid.boedayaapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.boedayaid.boedayaapp.data.firebase.auth.AuthState
import com.boedayaid.boedayaapp.databinding.ActivityLoginBinding
import com.boedayaid.boedayaapp.ui.profile.ProfileActivity
import com.boedayaid.boedayaapp.ui.register.RegisterActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        viewModel.authState.observe(this) {
            when (it.state) {
                AuthState.LOADING -> {
                    binding.btnRegister.isEnabled = false
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
                AuthState.ERROR -> {
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
                AuthState.DONE -> {
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    Intent(applicationContext, ProfileActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            when {
                email.isEmpty() -> {
                    Toast.makeText(
                        applicationContext,
                        "Email tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    Toast.makeText(
                        applicationContext,
                        "Password tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                else -> {
                    viewModel.login(email, password)
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            Intent(applicationContext, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = Firebase.auth.currentUser
        if (user != null) {
            Intent(applicationContext, ProfileActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}