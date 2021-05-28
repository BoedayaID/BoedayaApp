package com.boedayaid.boedayaapp.ui.translate

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioRecord
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.HandlerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.data.model.Chat
import com.boedayaid.boedayaapp.data.model.ChatAddress
import com.boedayaid.boedayaapp.databinding.ActivityTranslateBinding
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.audio.classifier.AudioClassifier

class TranslateActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_RECORD_AUDIO = 1337
        private const val MODEL_FILE = "model-metadata-new.tflite"
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

    private var isMicOpen = false
    private var handlerAnimation = Handler()

    private lateinit var binding: ActivityTranslateBinding
    private val viewModel: TranslateViewModel by viewModels()

    private lateinit var sukuName: String
    private lateinit var chatAdapter: ChatAdapter
    private var listChat = mutableListOf<Chat>()


    private var audioClassifier: AudioClassifier? = null
    private var audioRecord: AudioRecord? = null
    private lateinit var handler: Handler  // background thread handler to run classification
    private var resultRecognition = emptyList<Category>()

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
                TranslateViewModel.TRANSLATE_ERROR -> {
                    isTranslating(false)
                    translateFailed()
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestMicrophonePermission()
        }

        // Create a handler to run classification in a background thread
        val handlerThread = HandlerThread("backgroundThread")
        handlerThread.start()
        handler = HandlerCompat.createAsync(handlerThread.looper)
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
        },700)
    }

    private fun startRecordAudio() {
        binding.fabSpeech.isEnabled = false

        binding.fabSpeechAnimation.visibility = View.VISIBLE
        binding.rippleAnim.visibility = View.VISIBLE
        binding.fabSpeechAnimation.startAnimation(openAnim)
        binding.rippleAnim.startAnimation(openAnim)

        runnableAnimationRipple.run()

        startRecognition()
    }


    private fun stopRecordAudio() {
        binding.fabSpeech.isEnabled = true

        binding.fabSpeechAnimation.startAnimation(closeAnim)
        binding.rippleAnim.startAnimation(closeAnim)
        binding.fabSpeechAnimation.visibility = View.GONE
        binding.rippleAnim.visibility = View.GONE

        handlerAnimation.removeCallbacks(runnableAnimationRipple)

        stopRecognition()
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

    private fun startRecognition() {
        // If the audio classifier is initialized and running, do nothing.
        if (audioClassifier != null) return

        // Initialize the audio classifier
        val classifier = AudioClassifier.createFromFile(this, MODEL_FILE)
        val audioTensor = classifier.createInputTensorAudio()

        // Initialize the audio recorder and start recording
        val record = classifier.createAudioRecord()
        record.startRecording()

        val format = classifier.requiredTensorAudioFormat
        val recorderSpecs = "Number Of Channels: ${format.channels}\n" +
                "Sample Rate: ${format.sampleRate}"
        Log.d("Recognition", recorderSpecs)

        val startRecordTime = System.currentTimeMillis()
        Log.d("Recognition", "start record $startRecordTime")
        val runnableVoice = Runnable {
            val startTime = System.currentTimeMillis()
            Log.d("Recognition", "start classify $startTime")

            // Load the latest audio sample
            audioTensor.load(record)
            val result = classifier.classify(audioTensor)

            resultRecognition = result[0].categories

            val finishTime = System.currentTimeMillis()
            Log.d("Recognition", "end classify $finishTime")

        }

        // Start the classification after 2s taking record, and extra 1s to classify
        handler.postDelayed(runnableVoice, 2000L)

        // Save the instances we just created for use later
        audioClassifier = classifier
        audioRecord = record
    }

    private fun stopRecognition() {
        handler.removeCallbacksAndMessages(null)
        // stop recording
        audioRecord?.stop()
        audioRecord = null
        audioClassifier = null

        val endRecordTime = System.currentTimeMillis()
        Log.d("Recognition", "end record $endRecordTime")

        // sent highest prediction to view model
        val resultHighest = resultRecognition.sortedByDescending { it.score }[0]
        viewModel.addChat(resultHighest.label, ChatAddress.TO, false)
        viewModel.translate(resultHighest.label)

        Log.d("Recognition", "result : $resultRecognition")
        Log.d("Recognition", "highest result = $resultHighest")
    }
}