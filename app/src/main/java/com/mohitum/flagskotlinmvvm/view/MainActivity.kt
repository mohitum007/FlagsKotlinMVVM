package com.mohitum.flagskotlinmvvm.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.URLUtil
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mohitum.flagskotlinmvvm.R
import com.mohitum.flagskotlinmvvm.adapter.FlagsAdapter
import com.mohitum.flagskotlinmvvm.models.Flag
import com.mohitum.flagskotlinmvvm.utils.*
import com.mohitum.flagskotlinmvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Activity class to load flags
 */
class MainActivity : AppCompatActivity(), FlagsAdapter.ClickListener,
    GetFlagsTask.JsonTaskListener {

    /**
     * MainViewModel instance reference
     */
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initUI()
    }

    /**
     * Initialise User interface with default data
     */
    private fun initUI() {
        if (!isTablet(this)) { //restrict to portrait only
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        flagsListRv.layoutManager = LinearLayoutManager(this)
        viewModel.getFlagsData(this, this)
    }

    override fun onClick(index: Int, flag: Flag) {
        val url = flag.url
        if (URLUtil.isValidUrl(url)) {
            Glide.with(this).load(url).placeholder(R.drawable.ic_launcher_background)
                .into(selectedImgVw)
        }
        viewModel.selectedIndex = index
    }

    override fun onTaskStarted(url: String) {
        contentLayout.visibility = GONE
        progressLayout.visibility = VISIBLE
    }

    override fun onTaskCompleted(flags: List<Flag>?) {
        flags?.let {
            flagsListRv.adapter = FlagsAdapter(
                context = this,
                flags = flags,
                defaultclickIndex = viewModel.selectedIndex,
                clickListener = this
            )
            contentLayout.visibility = VISIBLE
            progressLayout.visibility = GONE
            viewModel.flagsList = flags
        } ?: onTaskError(NO_DATA_ERROR)
    }

    override fun onTaskError(errorString: String) {
        showErrorWithFinishAction(this, DIALOG_TITLE_ERROR, errorString)
    }
}
