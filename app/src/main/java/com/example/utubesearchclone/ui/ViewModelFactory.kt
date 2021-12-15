package com.example.utubesearchclone.ui

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.utubesearchclone.data.YouTubeRepository

class ViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: YouTubeRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(SearchVideosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchVideosViewModel(repository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
