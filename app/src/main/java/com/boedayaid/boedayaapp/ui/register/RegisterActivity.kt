package com.boedayaid.boedayaapp.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.boedayaid.boedayaapp.data.firebase.auth.AuthState
import com.boedayaid.boedayaapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnLogin.setOnClickListener {
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
                    finish()
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            when {
                username.isEmpty() -> {
                    Toast.makeText(
                        applicationContext,
                        "Username tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
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
                    viewModel.register(username, email, password)
                }
            }
        }
    }
}