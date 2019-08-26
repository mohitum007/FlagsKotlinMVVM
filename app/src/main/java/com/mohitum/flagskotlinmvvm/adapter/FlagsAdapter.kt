package com.mohitum.flagskotlinmvvm.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mohitum.flagskotlinmvvm.R
import com.mohitum.flagskotlinmvvm.models.Flag
import kotlinx.android.synthetic.main.item_flag.view.*

/**
 * Recycler view adapter class for showing flags list
 *
 * @param context activity context
 * @param flags list of Flags
 * @param defaultclickIndex default item select on load
 * @param clickListener item click listener reference
 */
open class FlagsAdapter(
    private val context: Context, private val flags: List<Flag>,
    private val defaultclickIndex: Int, private val clickListener: ClickListener
) : RecyclerView.Adapter<FlagsAdapter.FlagViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FlagViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_flag, null
        )
    )

    override fun getItemCount() = flags.size

    override fun onBindViewHolder(holder: FlagViewHolder, position: Int) {
        val flag = flags[position]
        holder.loadImage(context, flag.url)
        holder.setTitle(flag.title)

        holder.itemView.setOnClickListener {
            clickListener.onClick(position, flag)
        }
        if (position == defaultclickIndex) {
            holder.itemView.performClick()
        }
    }

    /**
     * View holder item to represent a single view
     *
     * @param view default item view
     */
    inner class FlagViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        /**
         * Load image from the Url into item imageview
         *
         * @param context activity context
         * @param url image Url
         */
        fun loadImage(context: Context, url: String) {
            if (URLUtil.isValidUrl(url)) {
                Glide.with(context).load(url).placeholder(R.drawable.ic_launcher_background)
                    .into(view.flagImgVw)
            }
        }

        /**
         * Set Item title
         *
         * @param title text to set as item title
         */
        fun setTitle(title: String) {
            view.flagsTitleTxtVw.text = title
        }
    }


    /**
     * Custom click listener for listening the click event with data
     */
    interface ClickListener {

        /**
         * Called when item of recycler view is clicked
         *
         * @param index clicked item index
         * @param flag the flag data
         */
        fun onClick(index: Int, flag: Flag)
    }
}