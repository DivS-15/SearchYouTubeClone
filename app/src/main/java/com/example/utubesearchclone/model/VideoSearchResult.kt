package com.example.utubesearchclone.model

import java.lang.Exception

sealed class VideoSearchResult {
    data class Success(val data: List<Item>) : VideoSearchResult()
    data class Error(val error: Exception) : VideoSearchResult()
}
