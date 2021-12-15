package com.example.utubesearchclone.ui

import androidx.lifecycle.*
import com.example.utubesearchclone.data.YouTubeRepository
import com.example.utubesearchclone.model.VideoSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchVideosViewModel(
    private val repository: YouTubeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * Stream of immutable states representative of the UI.
     */
    val state: LiveData<UiState>

    /**
     * Processor of side effects from the UI which in turn feedback into [state]
     */
    val accept: (UiAction) -> Unit

    init {
        val queryLiveData =
            MutableLiveData(savedStateHandle.get(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY)

        state = queryLiveData
            .distinctUntilChanged()
            .switchMap { queryString ->
                liveData {
                    if (queryString == DEFAULT_QUERY) {

                        val uiState = repository.getSearchResultStream(DEFAULT_QUERY)
                            .map {
                                UiState(
                                    query = queryString,
                                    searchResult = it
                                )
                            }
                            .asLiveData(Dispatchers.Main)
                        emitSource(uiState)
                    }

                    else{
                        val uiState = repository.getSearchResultStream(queryString)
                            .map {
                                UiState(
                                    query = queryString,
                                    searchResult = it
                                )
                            }
                            .asLiveData(Dispatchers.Main)
                        emitSource(uiState)
                    }

                }
            }

        accept = { action ->
            when (action) {
                is UiAction.Search -> queryLiveData.postValue(action.query)
                is UiAction.Scroll -> if (action.shouldFetchMore) {
                    val immutableQuery = queryLiveData.value
                    if (immutableQuery != null) {
                        viewModelScope.launch {
                            repository.requestMore(immutableQuery)
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value?.query
        super.onCleared()
    }
}

private val UiAction.Scroll.shouldFetchMore
    get() = visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount

sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(
        val visibleItemCount: Int,
        val lastVisibleItemPosition: Int,
        val totalItemCount: Int
    ) : UiAction()
}

data class UiState(
    val query: String,
    val searchResult: VideoSearchResult
)

private const val VISIBLE_THRESHOLD = 5
private const val LAST_SEARCH_QUERY: String = "last_search_query"
private const val DEFAULT_QUERY = "Google Developers"