package com.boedayaid.boedayaapp.ui.translate

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.data.model.Chat
import com.boedayaid.boedayaapp.data.model.ChatAddress
import com.boedayaid.boedayaapp.databinding.ActivityTranslateBinding

class TranslateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTranslateBinding
    private val viewModel: TranslateViewModel by viewModels()

    private lateinit var sukuName: String
    private lateinit var chatAdapter: ChatAdapter
    private var listChat = mutableListOf<Chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        sukuName = intent.getStringExtra("SUKU_NAME").toString()
        binding.tvBahasa.text = "${resources.getString(R.string.translate_bahasa)} $sukuName"

        chatAdapter = ChatAdapter()

        viewModel.listChat.observe(this) { chats ->
            listChat = chats
            chatAdapter.setList(listChat)
            binding.rvChat.smoothScrollToPosition(listChat.lastIndex)
        }

        binding.rvChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@TranslateActivity)
            setHasFixedSize(true)
        }

        chatAdapter.setOnTranslate { position ->
            viewModel.addChat(listChat[position].text, ChatAddress.FROM, true)

        }

        binding.fabSpeech.setOnClickListener {
            viewModel.addChat("Yoyo", ChatAddress.TO, false)
        }
    }
}