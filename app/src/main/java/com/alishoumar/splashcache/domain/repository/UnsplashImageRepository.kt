package com.alishoumar.splashcache.domain.repository

import androidx.paging.PagingData
import com.alishoumar.splashcache.domain.models.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface UnsplashImageRepository {

    fun getAllImages(): Flow<PagingData<UnsplashImage>>
}