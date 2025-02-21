package com.alishoumar.splashcache.data.remote

import com.alishoumar.splashcache.BuildConfig
import com.alishoumar.splashcache.data.models.UnSplashImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnSplashApi {

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/photos")
    suspend fun getAllImages(
        @Query("page") pageNumber: Int,
        @Query("per_page") perPage: Int
    ):List<UnSplashImage>

    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("page") pageNumber: Int,
        @Query("per_page") perPage: Int
    ):List<UnSplashImage>
}