package com.example.utubesearchclone

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.utubesearchclone.api.YouTubeService
import com.example.utubesearchclone.data.YouTubeRepository
import com.example.utubesearchclone.ui.ViewModelFactory

object Injection {
    private fun provideYouTubeRepository(): YouTubeRepository {
        return YouTubeRepository(YouTubeService.create())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideYouTubeRepository())
    }
}