@file:Suppress("DEPRECATION")

package com.mohitum.flagskotlinmvvm.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import com.mohitum.flagskotlinmvvm.R
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohitum.flagskotlinmvvm.adapter.FlagsAdapter
import kotlinx.android.synthetic.main.item_flag.view.*

/**
 * Top level method to check if the device is a tablet or not
 *
 * @param context activity context
 *
 * @return boolean true for tablet
 */
fun isTablet(context: Context) = context.resources.getBoolean(R.bool.isTablet)

/**
 * Check for if internet connection is available or not
 *
 * @param context activity context
 *
 * @return true if connection is available
 */
fun isInternetAvailable(context: Context): Boolean {
    val connectivity = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivity.allNetworkInfo
    for (anInfo in info) {
        if (anInfo.state == NetworkInfo.State.CONNECTED) {
            return true
        }
    }
    return false
}

/**
 * create and show alert dialog for error messages and finish on positivie button click action
 *
 * @param activity activity reference for finishing
 * @param title dialog title to be
 * @param message message to be displayed
 */
fun showErrorWithFinishAction(activity: Activity, title: String, message: String) {
    val builder = AlertDialog.Builder(activity)

    builder.setTitle(title)
    builder.setMessage(message)

    builder.setPositiveButton(R.string.ok) { _, _ ->
        activity.finish()
    }
    builder.create().show()
}


@BindingAdapter("app:adapter")
fun bindRvAdapter(rcView: RecyclerView, adapter: FlagsAdapter?) {
    adapter?.let { rcView.adapter = it }
}

@BindingAdapter("app:srcUrl")
fun bindImage(imgView: ImageView, url: String?) {
    if (URLUtil.isValidUrl(url)) {
        Glide.with(imgView.context).load(url).placeholder(R.drawable.ic_launcher_background)
            .into(imgView)
    }
}