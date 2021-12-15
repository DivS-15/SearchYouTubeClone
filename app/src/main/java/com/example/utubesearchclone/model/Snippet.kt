package com.example.utubesearchclone.model

import com.squareup.moshi.Json

data class Snippet(
    @Json(name = "channelTitle")
    val channelTitle: String,

    val thumbnails: Thumbnails,
    val title: String
)