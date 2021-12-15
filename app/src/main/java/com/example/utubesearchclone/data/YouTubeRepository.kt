package com.example.utubesearchclone.data

import android.util.Log
import com.example.utubesearchclone.api.YouTubeService
import com.example.utubesearchclone.model.Item
import com.example.utubesearchclone.model.VideoSearch
import com.example.utubesearchclone.model.VideoSearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException

/**
 * Repository class that works with local and remote data sources.
 */
class YouTubeRepository(private val service: YouTubeService) {

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<Item>()

    // shared flow of results, which allows us to broadcast updates so
    // the subscriber will have the latest data
    private val searchResults = MutableSharedFlow<VideoSearchResult>(replay = 1)

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage: String = " "

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    suspend fun getSearchResultStream(query: String): Flow<VideoSearchResult> {
        Log.d("GithubRepository", "New query: $query")
        //lastRequestedPage = service.getPageKeys(NETWORK_PAGE_SIZE,null,query).nextPageKey
        inMemoryCache.clear()
        requestAndSaveData(query)

        return searchResults
    }

    suspend fun requestMore(query: String) {
        if (isRequestInProgress) return
        val successful = requestAndSaveData(query)
        if (successful) {
            lastRequestedPage =
                service.getVideoItems(
                    NETWORK_PAGE_SIZE,
                    lastRequestedPage,
                    query
                ).nextPageToken.toString()
            Log.d("pageToken", "PageToken $lastRequestedPage")
            //update pageToken
        }
    }

    private suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false


        val apiQuery = query
        try {
            val response = service.getVideoItems(NETWORK_PAGE_SIZE, lastRequestedPage, apiQuery)
            Log.d("GithubRepository", "response $response")
            inMemoryCache.addAll(response.items)
            val reposByName = reposByName(query)
            searchResults.emit(VideoSearchResult.Success(reposByName))
            successful = true
        } catch (exception: IOException) {
            searchResults.emit(VideoSearchResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.emit(VideoSearchResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }

    private fun reposByName(query: String): List<Item> {
        // from the in memory cache select only the repos whose name or description matches
        // the query. Then order the results.
        return inMemoryCache.filter {
            it.snippet.title.contains(query, true) || it.snippet.title.contains(query, true)
        }//.sortedWith(compareByDescending<Video> { it.stars }.thenBy { it.name })
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}
