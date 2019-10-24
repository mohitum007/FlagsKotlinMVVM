package com.mohitum.flagskotlinmvvm.models

import androidx.annotation.StringDef
import com.mohitum.flagskotlinmvvm.models.Message.Companion.INVALID_URL
import com.mohitum.flagskotlinmvvm.models.Message.Companion.NO_DATA
import com.mohitum.flagskotlinmvvm.models.Message.Companion.NO_INTERNET
import com.mohitum.flagskotlinmvvm.models.Message.Companion.SUCCESS_MESSAGE

/**
* Denotes Message for the app users.
*/
@StringDef(SUCCESS_MESSAGE, NO_INTERNET, NO_DATA, INVALID_URL)
annotation class Message {
    companion object {
        const val SUCCESS_MESSAGE = "Success"
        const val NO_INTERNET = "No Internet Connection"
        const val NO_DATA = "No Data Found"
        const val INVALID_URL = "Invalid Url"
        const val GENERIC_ERROR = "Something went wrong. Please try later"
    }
}
