package com.boedayaid.boedayaapp.ui.draw_aksara

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.api.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class DrawViewModel : ViewModel() {

    companion object {
        const val RESULT_LOADING = 0
        const val RESULT_CORRECT = 1
        const val RESULT_WRONG = 2
        const val RESULT_ERROR = 3
    }

    private val aksaraServices = ApiConfig.provideAksaraService()

    private val _aksaraState = MutableLiveData<Map<String, Any>>()
    val aksaraState get() = _aksaraState

    fun predictAksara(bitmap: Bitmap, wantedResult: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _aksaraState.postValue(mapOf("state" to RESULT_LOADING, "actual_result" to ""))
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream)
                val byte = stream.toByteArray()

                val reqBody = byte.toRequestBody("image/jpeg".toMediaTypeOrNull())
                val result = aksaraServices.predictImage(reqBody)

                if (result.prediction.lowercase(Locale.getDefault())
                    == wantedResult.lowercase(Locale.getDefault())
                ) {
                    _aksaraState.postValue(
                        mapOf(
                            "state" to RESULT_CORRECT,
                            "actual_result" to result.prediction
                        )
                    )

                } else {
                    _aksaraState.postValue(
                        mapOf(
                            "state" to RESULT_WRONG,
                            "actual_result" to result.prediction
                        )
                    )
                }

            } catch (e: Exception) {
                Log.d("Retrofit Exception", e.message.toString())
                _aksaraState.postValue(mapOf("state" to RESULT_ERROR, "actual_result" to ""))
            }
        }
    }
}