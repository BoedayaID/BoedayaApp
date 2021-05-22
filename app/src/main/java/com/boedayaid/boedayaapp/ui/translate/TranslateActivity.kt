package com.boedayaid.boedayaapp.ui.translate

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.data.model.Chat
import com.boedayaid.boedayaapp.data.model.ChatAddress
import com.boedayaid.boedayaapp.databinding.ActivityTranslateBinding

class TranslateActivity : AppCompatActivity() {

    private val openMicAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.open_mic)
    }
    private val closeMicAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.close_mic)
    }

    private var isMicOpen = false
    private var handlerAnimation = Handler()

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
            startAnim()
            Handler().postDelayed({
                stopAnim()
            }, 3000)

            isMicOpen = !isMicOpen
        }
    }

    private fun startAnim() {
        binding.fabSpeech.isEnabled = false

        binding.fabSpeechAnimation.visibility = View.INVISIBLE
        binding.rippleAnim.visibility = View.INVISIBLE
        binding.fabSpeechAnimation.startAnimation(openMicAnim)
        binding.rippleAnim.startAnimation(openMicAnim)

        runnable.run()
    }


    private fun stopAnim() {
        binding.fabSpeech.isEnabled = true

        binding.fabSpeechAnimation.visibility = View.VISIBLE
        binding.rippleAnim.visibility = View.VISIBLE
        binding.fabSpeechAnimation.startAnimation(closeMicAnim)
        binding.rippleAnim.startAnimation(closeMicAnim)

        handlerAnimation.removeCallbacks(runnable)

        viewModel.addChat("Bahasa Indonesia", ChatAddress.TO, false)
        viewModel.translate("Bahasa Indonesia")
    }

    private var runnable = object : Runnable {
        override fun run() {
            binding.rippleAnim.animate().scaleX(3f).scaleY(3f).alpha(0f)
                .setDuration(700)
                .withEndAction {
                    binding.rippleAnim.scaleX = 1f
                    binding.rippleAnim.scaleY = 1f
                    binding.rippleAnim.alpha = 1f
                }

            handlerAnimation.postDelayed(this, 800)
        }

    }
}