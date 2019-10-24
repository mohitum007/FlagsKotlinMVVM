package com.mohitum.flagskotlinmvvm.utils

import android.os.AsyncTask
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
 */
class GetFlagsTask(private val urlString: String) :
    AsyncTask<String, Unit, List<Flag>?>() {

    override fun doInBackground(vararg params: String?): List<Flag>? {
        try {
            (URL(urlString).openConnection() as HttpsURLConnection).apply {
                Thread.sleep(5000)
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