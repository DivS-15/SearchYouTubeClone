package com.example.utubesearchclone.api

import com.example.utubesearchclone.model.VideoSearch
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeService {

    @GET("youtube/v3/search?part=snippet")
    suspend fun getVideoItems(
        @Query("maxResults") pageResults: Int,
        @Query("pageToken") pageToken: String,
        @Query("q") query: String,
        @Query("type") ContentType: String = "video",
        @Query("key") API_KEY: String = "AIzaSyDWN2TgXqkRDsZ5lSMcjae7dfNlUcNmyeo"
    ): VideoSearch

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/"

        private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()


        fun create(): YouTubeService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(YouTubeService::class.java)
        }
    }

}
