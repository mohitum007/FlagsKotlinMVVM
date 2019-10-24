package com.mohitum.flagskotlinmvvm.view

import android.content.pm.ActivityInfo
import android.os.Bundle
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
import androidx.databinding.DataBindingUtil
import com.mohitum.flagskotlinmvvm.databinding.ActivityMainBinding
import kotlinx.coroutines.*

/**
 * Main Activity class to load flags
 */
class MainActivity : AppCompatActivity(), FlagsAdapter.ClickListener {

    /**
     * MainViewModel instance reference
     */
    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.mainViewModel = viewModel

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
        viewModel.isLoading.set(true)

        GlobalScope.launch {
            with(viewModel.getFlagsData(this@MainActivity)) {
                flags?.let {
                    binding.flagsListRvAdapter = FlagsAdapter(
                        context = this@MainActivity,
                        flags = flags,
                        defaultClickIndex = viewModel.selectedIndex,
                        clickListener = this@MainActivity
                    )
                    viewModel.isLoading.set(false)
                } ?: showErrorWithFinishAction(this@MainActivity, DIALOG_TITLE_ERROR, message)
            }
        }
    }

    override fun onClick(index: Int, flag: Flag) {
        val url = flag.url
        if (URLUtil.isValidUrl(url)) {
            Glide.with(this).load(url).placeholder(R.drawable.ic_launcher_background)
                .into(selectedImgVw)
        }
        viewModel.selectedIndex = index
    }

}
