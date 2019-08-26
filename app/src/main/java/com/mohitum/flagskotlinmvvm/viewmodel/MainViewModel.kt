package com.mohitum.flagskotlinmvvm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mohitum.flagskotlinmvvm.models.Flag
import com.mohitum.flagskotlinmvvm.utils.GetFlagsTask
import com.mohitum.flagskotlinmvvm.utils.JSON_URL
import com.mohitum.flagskotlinmvvm.utils.NO_INTERNET_ERROR
import com.mohitum.flagskotlinmvvm.utils.isInternetAvailable

/**
 * View model class to load and maintain data
 */
class MainViewModel : ViewModel() {

    /**
     *  default selection index to maintain when activity rotates Default = 0
     */
    var selectedIndex = 0

    /**
     *  holding reference for the flags list. once it is fetched from the url
     */
    var flagsList: List<Flag>? = null

    /**
     * Get the list of flags from the JSON Url if it is not cached previously.
     *
     * @param jsonTaskListener task listener
     *
     * @return flagsList if already downloaded else fetch the list of Flag items from JSON_URL
     */
    fun getFlagsData(context: Context, jsonTaskListener: GetFlagsTask.JsonTaskListener) {
        if (flagsList.isNullOrEmpty()) {
            if (isInternetAvailable(context)) {
                GetFlagsTask(JSON_URL, jsonTaskListener).execute()
            } else {
                jsonTaskListener.onTaskError(NO_INTERNET_ERROR)
            }
        } else {
            jsonTaskListener.onTaskCompleted(flagsList)
        }
    }
}