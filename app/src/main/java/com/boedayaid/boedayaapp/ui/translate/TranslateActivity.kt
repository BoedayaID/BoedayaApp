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

    private val openAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.open_anim)
    }
    private val closeAnim: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.close_anim)
    }

    private val openAnimTranslateStatus: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.open_anim)
    }
    private val closeAnimTranslateStatus: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.close_anim)
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
            startRecordAudio()
            Handler().postDelayed({
                stopRecordAudio()
            }, 3000)

            isMicOpen = !isMicOpen
        }

        viewModel.stateTranslate.observe(this) { state ->
            when (state) {
                TranslateViewModel.TRANSLATE_LOADING -> isTranslating(true)
                TranslateViewModel.TRANSLATE_DONE -> isTranslating(false)
            }
        }
    }

    private fun isTranslating(state: Boolean) {
        if (state) {
            binding.translateStatus.visibility = View.VISIBLE
            binding.translateStatus.startAnimation(openAnimTranslateStatus)
        } else {
            binding.translateStatus.startAnimation(closeAnimTranslateStatus)
            binding.translateStatus.visibility = View.GONE
        }
    }

    private fun startRecordAudio() {
        binding.fabSpeech.isEnabled = false

        binding.fabSpeechAnimation.visibility = View.VISIBLE
        binding.rippleAnim.visibility = View.VISIBLE
        binding.fabSpeechAnimation.startAnimation(openAnim)
        binding.rippleAnim.startAnimation(openAnim)

        runnable.run()
    }


    private fun stopRecordAudio() {
        binding.fabSpeech.isEnabled = true

        binding.fabSpeechAnimation.startAnimation(closeAnim)
        binding.rippleAnim.startAnimation(closeAnim)
        binding.fabSpeechAnimation.visibility = View.GONE
        binding.rippleAnim.visibility = View.GONE

        handlerAnimation.removeCallbacks(runnable)

        // dummy result
        val result = "selamat makan"

        viewModel.addChat(result, ChatAddress.TO, false)
        viewModel.translate(result)
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