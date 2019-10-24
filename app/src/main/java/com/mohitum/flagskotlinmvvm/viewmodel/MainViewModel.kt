package com.mohitum.flagskotlinmvvm.viewmodel

import android.content.Context
import android.webkit.URLUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohitum.flagskotlinmvvm.models.Flag
import com.mohitum.flagskotlinmvvm.models.FlagsWrapper
import com.mohitum.flagskotlinmvvm.models.Message.Companion.GENERIC_ERROR
import com.mohitum.flagskotlinmvvm.models.Message.Companion.INVALID_URL
import com.mohitum.flagskotlinmvvm.models.Message.Companion.NO_INTERNET
import com.mohitum.flagskotlinmvvm.models.Message.Companion.SUCCESS_MESSAGE
import com.mohitum.flagskotlinmvvm.utils.*
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * View model class to load and maintain data
 */
class MainViewModel : ViewModel() {

    /**
     *  default selection index to maintain when activity rotates Default = 0
     */
    var selectedIndex = 0

    var isLoading: ObservableBoolean = ObservableBoolean()

    /**
     *  holding reference for the flags list. once it is fetched from the url
     */
    var flagsList: List<Flag>? = null

    /**
     * Get the list of flags from the JSON Url if it is not cached previously.
     *
     * @return FlagsWrapper with flagsList if already downloaded else fetch the list of Flag items from JSON_URL
     */
    suspend fun getFlagsData(context: Context): FlagsWrapper {
        var message: String = GENERIC_ERROR
        if (flagsList.isNullOrEmpty()) {
            if (isInternetAvailable(context)) {
                if (URLUtil.isValidUrl(JSON_URL)) {
                    flagsList = GetFlagsTask(JSON_URL).execute().get()
                    message = SUCCESS_MESSAGE
                } else {
                    message = INVALID_URL
                }
            } else {
                message = NO_INTERNET
            }
        }
        return FlagsWrapper(message, flagsList)
    }

    fun fetchFromServer(urlString: String): List<Flag>? {
        try {
            (URL(urlString).openConnection() as HttpsURLConnection).apply {
                connect()
                if (responseCode == 200) {
                    val jsonString =
                        inputStream.use { it.reader().use { reader -> reader.readText() } }
                    return Gson().fromJson(jsonString, object : TypeToken<List<Flag>>() {}.type)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}