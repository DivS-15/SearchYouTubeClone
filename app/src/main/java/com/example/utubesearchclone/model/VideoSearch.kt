package com.example.utubesearchclone.model

data class VideoSearch(
    val items: List<Item> = emptyList(),
    val prevPageToken: String?,
    val nextPageToken: String?,
    val pageInfo: PageInfo,
    val regionCode: String
)