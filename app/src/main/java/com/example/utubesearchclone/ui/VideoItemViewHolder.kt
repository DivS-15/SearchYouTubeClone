package com.example.utubesearchclone.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.utubesearchclone.R
import com.example.utubesearchclone.model.Item
import com.example.utubesearchclone.model.VideoSearch

class VideoItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val videoTitle: TextView = view.findViewById(R.id.Video_Title)
    private val channel: TextView = view.findViewById(R.id.Channel_Title)
    private val videoImage: ImageView = view.findViewById(R.id.video_image)

    private var video: Item? = null


    fun bind(video: Item?) {
        if (video == null) {
            val resources = itemView.resources
            videoTitle.text = resources.getString(R.string.loading)
            channel.visibility = View.GONE

        } else {
            showVideoData(video)
        }
    }

    private fun showVideoData(video: Item) {
        this.video = video
        videoTitle.text = video.snippet.title
        channel.text = video.snippet.channelTitle

        val imgUrl = video.snippet.thumbnails.high.url
        imgUrl.let {
            val imgUri = it.toUri().buildUpon().scheme("https").build()

            videoImage.load(imgUri)
        }
    }

    companion object {
        fun create(parent: ViewGroup): VideoItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.video_view_item, parent, false)
            return VideoItemViewHolder(view)
        }
    }
}
