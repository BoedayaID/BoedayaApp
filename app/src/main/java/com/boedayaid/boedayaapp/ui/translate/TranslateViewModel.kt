package com.boedayaid.boedayaapp.ui.translate

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boedayaid.boedayaapp.data.api.ApiConfig
import com.boedayaid.boedayaapp.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class TranslateViewModel : ViewModel() {

    companion object {
        const val TRANSLATE_LOADING = 1
        const val TRANSLATE_DONE = 2
        const val TRANSLATE_ERROR = 3
        const val PREDICT_LOADING = 4
        const val PREDICT_DONE = 5
        const val PREDICT_ERROR = 6
    }

    private val translateServices = ApiConfig.provideTranslateService()
    private val aiServices = ApiConfig.provideAiService()

    private val _listChat = MutableLiveData<MutableList<Chat>>(
        mutableListOf(
            ChatFrom(0, "Hallo, ada yang bisa saya bantu", ChatType.Regular),
        )
    )
    val listChat get() = _listChat

    val stateTranslate = MutableLiveData(0)


    fun addChat(text: String, type: ChatAddress, convertAksara: Boolean) {
        val chat = if (!convertAksara) {
            when (type) {
                ChatAddress.TO -> {
                    ChatTo(1, text)
                }
                ChatAddress.FROM -> {
                    ChatFrom(1, text, ChatType.Regular)
                }
            }
        } else {
            val result = convertAksara(text)
            ChatFrom(1, result, ChatType.Aksara)
        }
        _listChat.value?.add(chat)
        _listChat.notifyObserver()
    }

    fun predictAudio(file: File) {
        stateTranslate.postValue(PREDICT_LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("FILE NAME", file.name)
                val requestBody = file.asRequestBody("audio/wav".toMediaTypeOrNull())
                val result = aiServices.predictAudio(
                    requestBody
                )
                withContext(Dispatchers.Main) {
                    addChat(result.keyword, ChatAddress.TO, false)
                }
                translate(result.keyword)
                stateTranslate.postValue(PREDICT_DONE)
            } catch (e: Exception) {
                Log.d("Retrofit Exception", e.message.toString())
                stateTranslate.postValue(PREDICT_ERROR)
            }
        }
    }

    fun translate(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stateTranslate.postValue(TRANSLATE_LOADING)
            try {
                val result = translateServices.translate(text, "id", "su")
                withContext(Dispatchers.Main) {
                    addChat(result.result, ChatAddress.FROM, false)
                }
                stateTranslate.postValue(TRANSLATE_DONE)
            } catch (e: Exception) {
                Log.e("Network Exception", e.message.toString())
                stateTranslate.postValue(TRANSLATE_ERROR)
            }
        }
    }

    private fun convertAksara(text: String): String {
        val unisunda = mutableMapOf<String, String>()

        // panungtung +
        unisunda["+ng"] = "\u1B80"
        unisunda["+r"] = "\u1B81"
        unisunda["+h"] = "\u1B82"
        unisunda["+O"] = "\u1BAA" // pamaeh

        // vokal mandiri
        unisunda["A"] = "\u1B83"
        unisunda["I"] = "\u1B84"
        unisunda["U"] = "\u1B85"
        unisunda["\u00C9"] = "\u1B86" // "e curek"
        unisunda["O"] = "\u1B87"
        unisunda["E"] = "\u1B88"
        unisunda["EU"] = "\u1B89"

        // konsonan ngalagena
        unisunda["k"] = "\u1B8A"
        unisunda["q"] = "\u1B8B"
        unisunda["g"] = "\u1B8C"
        unisunda["ng"] = "\u1B8D"
        unisunda["c"] = "\u1B8E"
        unisunda["j"] = "\u1B8F"
        unisunda["z"] = "\u1B90"
        unisunda["ny"] = "\u1B91"
        unisunda["t"] = "\u1B92"
        unisunda["d"] = "\u1B93"
        unisunda["n"] = "\u1B94"
        unisunda["p"] = "\u1B95"
        unisunda["f"] = "\u1B96"
        unisunda["v"] = "\u1B97"
        unisunda["b"] = "\u1B98"
        unisunda["m"] = "\u1B99"
        unisunda["y"] = "\u1B9A"
        unisunda["r"] = "\u1B9B"
        unisunda["l"] = "\u1B9C"
        unisunda["w"] = "\u1B9D"
        unisunda["s"] = "\u1B9E"
        unisunda["x"] = "\u1B9F"
        unisunda["h"] = "\u1BA0"
        unisunda["kh"] = "\u1BAE"
        unisunda["sy"] = "\u1BAF"

        // konsonan sisip
        unisunda["+ya"] = "\u1BA1"
        unisunda["+ra"] = "\u1BA2"
        unisunda["+la"] = "\u1BA3"

        // pangubah suara vokal
        unisunda["a"] = ""
        unisunda["i"] = "\u1BA4"
        unisunda["u"] = "\u1BA5"
        unisunda["\u00E9"] = "\u1BA6"
        unisunda["o"] = "\u1BA7"
        unisunda["e"] = "\u1BA8"
        unisunda["eu"] = "\u1BA9"

        // angka
        unisunda["0"] = "\u1BB0"
        unisunda["1"] = "\u1BB1"
        unisunda["2"] = "\u1BB2"
        unisunda["3"] = "\u1BB3"
        unisunda["4"] = "\u1BB4"
        unisunda["5"] = "\u1BB5"
        unisunda["6"] = "\u1BB6"
        unisunda["7"] = "\u1BB7"
        unisunda["8"] = "\u1BB8"
        unisunda["9"] = "\u1BB9"


        val patV = 1
        val patVK = 2
        val patKV = 3
        val patKVK = 4
        val patKRV = 5
        val patKRVK = 6
        val patSILABA = 7
        val patLAIN = 0

        fun hurufSundaAkhir(huruf: String): String {
            var retval = ""
            retval = if (huruf == "h" || huruf == "r" || huruf == "ng") {
                unisunda["+$huruf"].toString()
            } else {
                unisunda[huruf] + unisunda["+O"]
            }
            return retval
        }

        var inputString = text.toLowerCase()

        val inputLength = inputString.length;
        var index = 0
        var jump = 0

        var tStr = ""
        var outputString = ""
        var r: Any? = null
        lateinit var silaba2: Any
        lateinit var suku: Any
        var polasuku = patLAIN

        val kons = "kh|sy|[b-df-hj-mp-tv-z]|ng|ny|n".toRegex()
        val vok = "[aiuoé]|eu|e".toRegex()
        val rep = "[yrl]".toRegex()
        var silaba = "^"
        silaba += "(${kons})?"
        silaba += "(${rep})?"
        silaba += "(${vok})"
        silaba += "(${kons})?"
        silaba += "(${vok}|${rep})?"

        val konsonan = "^($kons)"
        val digit = "^([0-9]+)"

        while (index < inputLength) {
            suku = "";
            r = silaba.toRegex().find(inputString)?.groupValues
            if (r !== null) {
                // cari pola:
                if (r[1] != "") {
                    if (r[4] != "") {
                        if (r[2] != "") {
                            if (r[5] != "") {
                                polasuku = patKRV
                            } else {
                                polasuku = patKRVK
                            }
                        } else {
                            if (r[5] != "") {
                                polasuku = patKV
                            } else {
                                polasuku = patKVK
                            }
                        }
                    } else {
                        if (r[2] != "") {
                            polasuku = patKRV
                        } else {
                            polasuku = patKV
                        }
                    }
                } else {
                    if (r[4] != "") {
                        if (r[5] != "") {
                            polasuku = patV
                        } else {
                            polasuku = patVK
                        }
                    } else {
                        polasuku = patV
                    }
                }

                // bentuk:
                if (polasuku == patKRVK) {
                    // kyuh, kruh
                    suku = r[1] + r[2] + r[3] + r[4]
                    silaba2 = unisunda[r[1]].toString()
                    silaba2 += unisunda["+" + r[2] + "a"]
                    silaba2 += unisunda[r[3]]
                    silaba2 += hurufSundaAkhir(r[4])
                } else if (polasuku == patKRV) {
                    // kyu, kru
                    suku = r[1] + r[2] + r[3]
                    silaba2 = unisunda[r[1]].toString()
                    silaba2 += unisunda["+" + r[2] + "a"]
                    silaba2 += unisunda[r[3]]
                } else if (polasuku == patKVK) {
                    // kih, kuh
                    suku = r[1] + r[3] + r[4]
                    silaba2 = unisunda[r[1]].toString()
                    silaba2 += unisunda[r[3]]
                    silaba2 += hurufSundaAkhir(r[4])
                } else if (polasuku == patKV) {
                    // ki ku ke ko keu
                    suku = r[1] + r[3];
                    silaba2 = unisunda[r[1]].toString()
                    silaba2 += unisunda[r[3]]
                } else if (polasuku == patVK) {
                    // ah, ih, uh
                    suku = r[3] + r[4]
                    silaba2 = unisunda[r[3].toUpperCase()].toString()
                    silaba2 += hurufSundaAkhir(r[4])
                } else {
                    // a, i, u
                    suku = r[3]
                    silaba2 = unisunda[suku.toUpperCase()].toString()
                }

                outputString += silaba2
                tStr += "$suku($polasuku):"
                polasuku = patSILABA
            } else {
                r = konsonan.toRegex().find(inputString)?.groupValues
                if (r != null) {
                    suku = r[1]
                    if (polasuku == patSILABA) {
                        silaba2 = hurufSundaAkhir(suku)
                    } else {
                        silaba2 = unisunda[suku] + unisunda["+O"]
                    }
                    outputString += silaba2
                    tStr += "$suku;"
                } else {
                    r = digit.toRegex().find(inputString)?.groupValues
                    if (r != null) {
                        silaba2 = "|"
                        suku = r[1]
                        val l = suku.length
                        var i = 0
                        while (i < l) {
                            silaba2 += unisunda["${suku[i]}"]
                            i += 1
                        }
                        silaba2 += "|"
                        outputString += silaba2
                    } else {
                        suku = "${inputString[0]}"
                        silaba2 = suku
                        outputString += suku
                    }
                    tStr += "$suku(?)"
                }
                polasuku = patLAIN
            }

            inputString = inputString.substring(suku.length)
            index += suku.length
        }
        return outputString

    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}


