package com.boedayaid.boedayaapp.ui.translate

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.data.model.Chat
import com.boedayaid.boedayaapp.data.model.ChatAddress
import com.boedayaid.boedayaapp.databinding.ActivityTranslateBinding
import com.github.squti.androidwaverecorder.WaveRecorder
import java.io.File


class TranslateActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_RECORD_AUDIO = 1337
    }

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

    private val openAnimPredictingStatus: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.open_anim)
    }
    private val closeAnimPredictingStatus: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.close_anim)
    }

    private var isMicOpen = false
    private var handlerAnimation = Handler()

    private lateinit var binding: ActivityTranslateBinding
    private val viewModel: TranslateViewModel by viewModels()

    private lateinit var sukuName: String
    private lateinit var chatAdapter: ChatAdapter
    private var listChat = mutableListOf<Chat>()

    private var filePath: String = ""
    private lateinit var wavRecorder: WaveRecorder

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
            }, 2000)

            isMicOpen = !isMicOpen
        }

        viewModel.stateTranslate.observe(this) { state ->
            when (state) {
                TranslateViewModel.PREDICT_LOADING -> isPredicting(true)
                TranslateViewModel.PREDICT_DONE -> isPredicting(false)
                TranslateViewModel.PREDICT_ERROR -> {
                    isPredicting(false)
                    predictingFailed()
                }
                TranslateViewModel.TRANSLATE_LOADING -> isTranslating(true)
                TranslateViewModel.TRANSLATE_DONE -> isTranslating(false)
                TranslateViewModel.TRANSLATE_ERROR -> {
                    isTranslating(false)
                    translateFailed()
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestMicrophonePermission()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("AudioRequest", "Audio permission granted :)")
            } else {
                Log.e("AudioRequest", "Audio permission not granted :(")
            }
        }
    }

    private fun requestMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("AudioRequest", "Audio permission granted :)")
        } else {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO)
        }
    }

    private fun startRecordAudio() {
        startAnim()

        val path = this.getExternalFilesDir("/")?.absolutePath
        val fileName = "Recording_${System.currentTimeMillis()}.wav"
        filePath = "$path/$fileName"

        try {
            wavRecorder = WaveRecorder(filePath)
            wavRecorder.noiseSuppressorActive = true
            wavRecorder.waveConfig.sampleRate = 44100
            wavRecorder.startRecording()

        } catch (e: Exception) {
            Log.e("ErrorRecording", e.message.toString())
        }
    }

    private fun stopRecordAudio() {
        stopAnim()

        wavRecorder.stopRecording()
        viewModel.predictAudio(File(filePath))
    }

    private var runnableAnimationRipple = object : Runnable {
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

    private fun startAnim() {
        binding.fabSpeech.isEnabled = false

        binding.fabSpeechAnimation.visibility = View.VISIBLE
        binding.rippleAnim.visibility = View.VISIBLE
        binding.fabSpeechAnimation.startAnimation(openAnim)
        binding.rippleAnim.startAnimation(openAnim)

        runnableAnimationRipple.run()
    }

    private fun stopAnim() {
        binding.fabSpeech.isEnabled = true

        binding.fabSpeechAnimation.startAnimation(closeAnim)
        binding.rippleAnim.startAnimation(closeAnim)
        binding.fabSpeechAnimation.visibility = View.GONE
        binding.rippleAnim.visibility = View.GONE
        handlerAnimation.removeCallbacks(runnableAnimationRipple)
    }

    private fun isTranslating(state: Boolean) {
        binding.loadingStatus.visibility = View.VISIBLE
        binding.tvLoadingStatus.text = "Sedang Menerjemahkan"
        if (state) {
            binding.translateStatus.visibility = View.VISIBLE
            binding.translateStatus.startAnimation(openAnimTranslateStatus)
        } else {
            binding.translateStatus.startAnimation(closeAnimTranslateStatus)
            binding.translateStatus.visibility = View.GONE
        }
    }

    private fun translateFailed() {
        binding.loadingStatus.visibility = View.GONE
        binding.tvLoadingStatus.text = "Gagal menerjemahkan"
        binding.translateStatus.visibility = View.VISIBLE
        binding.translateStatus.startAnimation(openAnimTranslateStatus)
        Handler().postDelayed({
            binding.translateStatus.startAnimation(closeAnimTranslateStatus)
            binding.translateStatus.visibility = View.GONE
        }, 700)
    }

    private fun isPredicting(state: Boolean) {
        binding.loadingStatus.visibility = View.VISIBLE
        binding.tvLoadingStatus.text = "Sedang memprediksi suara"
        if (state) {
            binding.translateStatus.visibility = View.VISIBLE
            binding.translateStatus.startAnimation(openAnimPredictingStatus)
        } else {
            binding.translateStatus.startAnimation(closeAnimPredictingStatus)
            binding.translateStatus.visibility = View.GONE
        }
    }

    private fun predictingFailed() {
        binding.loadingStatus.visibility = View.GONE
        binding.tvLoadingStatus.text = "Gagal memprediksi suara"
        binding.translateStatus.visibility = View.VISIBLE
        binding.translateStatus.startAnimation(openAnimPredictingStatus)
        Handler().postDelayed({
            binding.translateStatus.startAnimation(closeAnimPredictingStatus)
            binding.translateStatus.visibility = View.GONE
        }, 700)
    }
}