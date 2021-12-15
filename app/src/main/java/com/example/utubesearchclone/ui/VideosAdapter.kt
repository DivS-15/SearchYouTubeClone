package com.example.utubesearchclone.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.utubesearchclone.model.Item
import com.example.utubesearchclone.model.VideoSearch

class VideosAdapter : ListAdapter<Item, VideoItemViewHolder>(VIDEO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {
        return VideoItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {
        val videoItem = getItem(position)

        if (videoItem != null) {
            holder.bind(videoItem)
        }
    }

    companion object {
        private val VIDEO_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.snippet.title == newItem.snippet.title
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

        }
    }
}
