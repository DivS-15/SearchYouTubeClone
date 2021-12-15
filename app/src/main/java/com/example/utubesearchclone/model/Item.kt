package com.example.utubesearchclone.model

import com.squareup.moshi.Json

data class Item(
    @Json(name = "snippet")
    val snippet: Snippet
)