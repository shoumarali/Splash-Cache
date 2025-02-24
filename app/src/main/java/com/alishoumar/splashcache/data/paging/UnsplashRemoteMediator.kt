package com.alishoumar.splashcache.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.alishoumar.splashcache.data.local.database.UnsplashImageDatabase
import com.alishoumar.splashcache.data.models.UnsplashImageDto
import com.alishoumar.splashcache.data.models.UnsplashRemoteKeys
import com.alishoumar.splashcache.data.remote.UnsplashApi
import com.alishoumar.splashcache.data.utils.Constants.ITEMS_PER_PAGE
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class UnsplashRemoteMediator @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashImageDatabase: UnsplashImageDatabase
): RemoteMediator<Int, UnsplashImageDto>(){

    private val unsplashImageDao = unsplashImageDatabase.unsplashImageDao()
    private val unsplashRemoteKeyDao = unsplashImageDatabase.unsplashRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImageDto>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyCloseForTheCurrentElement(state)
                    remoteKeys?.nextPageKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForTheFirstElement(state)
                    val prevPages = remoteKeys?.prevPageKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPages
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForTheLastElement(state)
                    val nextPage = remoteKeys?.nextPageKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = unsplashApi.getAllImages(
                pageNumber = currentPage, perPage = ITEMS_PER_PAGE
            )
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            unsplashImageDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    unsplashImageDao.deleteAllImages()
                    unsplashRemoteKeyDao.deleteAllRemoteKeys()
                }

                val keys = response.map { unsplashImage ->
                    UnsplashRemoteKeys(
                        id = unsplashImage.id,
                        prevPageKey = prevPage,
                        nextPageKey = nextPage
                    )
                }
                unsplashRemoteKeyDao.addAllRemoteKeys(keys)
                unsplashImageDao.addImages(images = response)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: Exception){
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyCloseForTheCurrentElement(
        state: PagingState<Int, UnsplashImageDto>
    ): UnsplashRemoteKeys?{
        return state.anchorPosition?.let {position ->
            state.closestItemToPosition(position)?.id?.let {id ->
                unsplashRemoteKeyDao.getRemoteKeys(id)
            }
        }
    }


    private suspend fun getRemoteKeyForTheFirstElement(
        state: PagingState<Int, UnsplashImageDto>
    ): UnsplashRemoteKeys?{
        return state.pages.firstOrNull{ it.data.isNotEmpty() }?.data?.firstOrNull()?.let {unSplashImage ->
            unsplashRemoteKeyDao.getRemoteKeys(unSplashImage.id)
        }
    }

    private suspend fun getRemoteKeyForTheLastElement(
        state: PagingState<Int, UnsplashImageDto>
    ): UnsplashRemoteKeys?{

        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {unSplashImage ->
            unsplashRemoteKeyDao.getRemoteKeys(unSplashImage.id)
        }
    }
}