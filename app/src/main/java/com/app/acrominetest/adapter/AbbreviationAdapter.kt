package com.app.acrominetest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.acrominetest.databinding.LayoutItemAbbreviationBinding
import com.app.acrominetest.models.AcromineResponseLf

class AbbreviationAdapter :
    ListAdapter<AcromineResponseLf, AbbreviationAdapter.ViewHolder>(AcromineDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: LayoutItemAbbreviationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AcromineResponseLf) {
            binding.abbreviation = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemAbbreviationBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class AcromineDiffCallback : DiffUtil.ItemCallback<AcromineResponseLf>() {

    override fun areItemsTheSame(oldItem: AcromineResponseLf, newItem: AcromineResponseLf): Boolean {
        return oldItem.freq == newItem.freq
    }


    override fun areContentsTheSame(oldItem: AcromineResponseLf, newItem: AcromineResponseLf): Boolean {
        return oldItem == newItem
    }


}