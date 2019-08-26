package com.mohitum.flagskotlinmvvm.utils

import android.os.AsyncTask
import android.webkit.URLUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohitum.flagskotlinmvvm.models.Flag
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * The Asynchronous call to get flags list from the provided url
 *
 * @param urlString flags json url
 * @param jsonTaskListener the callback to handle UI operations
 */
class GetFlagsTask(private val urlString: String, private val jsonTaskListener: JsonTaskListener) :
    AsyncTask<String, Void, List<Flag>?>() {

    override fun onPreExecute() {
        super.onPreExecute()
        if (!URLUtil.isValidUrl(urlString)) {
            cancel(true)
            jsonTaskListener.onTaskError(INVALID_URL_ERROR)
        } else {
            jsonTaskListener.onTaskStarted(urlString)
        }
    }

    override fun doInBackground(vararg params: String?): List<Flag>? {
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

    override fun onPostExecute(result: List<Flag>?) {
        super.onPostExecute(result)
        jsonTaskListener.onTaskCompleted(result)
    }

    /**
     * Interface listener for handling UI callbacks for the async task
     */
    interface JsonTaskListener {
        fun onTaskStarted(url: String)
        fun onTaskCompleted(flags: List<Flag>?)
        fun onTaskError(errorString: String)
    }
}