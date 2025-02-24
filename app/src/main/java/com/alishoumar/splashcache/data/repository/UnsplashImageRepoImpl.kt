package com.alishoumar.splashcache.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSourceFactory
import com.alishoumar.splashcache.data.local.database.UnsplashImageDatabase
import com.alishoumar.splashcache.data.models.UnsplashImageDto
import com.alishoumar.splashcache.data.paging.UnsplashRemoteMediator
import com.alishoumar.splashcache.data.remote.UnsplashApi
import com.alishoumar.splashcache.data.utils.Constants.ITEMS_PER_PAGE
import com.alishoumar.splashcache.domain.models.UnsplashImage
import com.alishoumar.splashcache.domain.repository.UnsplashImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnsplashImageRepoImpl @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashImageDatabase: UnsplashImageDatabase
)  {


     @OptIn(ExperimentalPagingApi::class)
     fun getAllImages(): Flow<PagingData<UnsplashImageDto>> {
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
    }
}