package com.mohitum.flagskotlinmvvm.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohitum.flagskotlinmvvm.databinding.ItemFlagBinding
import com.mohitum.flagskotlinmvvm.models.Flag

/**
 * Recycler view adapter class for showing flags list
 *
 * @param context activity context
 * @param flags list of Flags
 * @param defaultClickIndex default item select on load
 * @param clickListener item click listener reference
 */
open class FlagsAdapter(
    private val context: Context, private val flags: List<Flag>,
    private val defaultClickIndex: Int, private val clickListener: ClickListener
) : RecyclerView.Adapter<FlagsAdapter.FlagViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagViewHolder {
        val itemBinding = ItemFlagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlagViewHolder(itemBinding)
    }

    override fun getItemCount() = flags.size

    override fun onBindViewHolder(holder: FlagViewHolder, position: Int) {
        val flag = flags[position]
        holder.bind(flag)

        holder.itemView.setOnClickListener {
            clickListener.onClick(position, flag)
        }
        if (position == defaultClickIndex) {
            holder.itemView.performClick()
        }
    }

    /**
     * View holder item to represent a single view
     *
     * @param itemFlagBinding default item view binding
     */
    inner class FlagViewHolder(private val itemFlagBinding: ItemFlagBinding) : RecyclerView.ViewHolder(itemFlagBinding.root) {

        /**
         * Bind the flag with view holder
         *
         * @param flagData item flag data
         */
        fun bind(flagData: Flag) {
            itemFlagBinding.apply {
                flag = flagData
                executePendingBindings()
            }
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