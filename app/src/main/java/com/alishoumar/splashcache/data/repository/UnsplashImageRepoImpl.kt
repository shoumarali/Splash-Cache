package com.alishoumar.splashcache.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alishoumar.splashcache.data.local.database.UnsplashImageDatabase
import com.alishoumar.splashcache.data.mappers.toUnsplashImagePagingData
import com.alishoumar.splashcache.data.paging.UnsplashRemoteMediator
import com.alishoumar.splashcache.data.remote.UnsplashApi
import com.alishoumar.splashcache.data.utils.Constants.ITEMS_PER_PAGE
import com.alishoumar.splashcache.domain.models.UnsplashImage
import com.alishoumar.splashcache.domain.repository.UnsplashImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UnsplashImageRepoImpl @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashImageDatabase: UnsplashImageDatabase
)  : UnsplashImageRepository {

     @OptIn(ExperimentalPagingApi::class)
     override fun getAllImages(): Flow<PagingData<UnsplashImage>> {
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