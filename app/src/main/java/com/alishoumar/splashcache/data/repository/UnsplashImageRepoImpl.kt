package com.alishoumar.splashcache.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.alishoumar.splashcache.data.local.database.UnsplashImageDatabase
import com.alishoumar.splashcache.data.mappers.toUnsplashImagePagingData
import com.alishoumar.splashcache.data.models.UnsplashImageDto
import com.alishoumar.splashcache.data.paging.UnsplashRemoteMediator
import com.alishoumar.splashcache.data.remote.UnsplashApi
import com.alishoumar.splashcache.data.utils.Constants.ITEMS_PER_PAGE
import com.alishoumar.splashcache.domain.models.UnsplashImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UnsplashImageRepoImpl @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashImageDatabase: UnsplashImageDatabase
)  {

     @OptIn(ExperimentalPagingApi::class)
     fun getAllImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = {
            unsplashImageDatabase.unsplashImageDao().getAllImages()
        }
        return Pager(
            config = PagingConfig(pageSize =  ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi,
                unsplashImageDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map {it.toUnsplashImagePagingData()}
    }
}