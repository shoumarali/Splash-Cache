package com.alishoumar.splashcache.domain.usecases

import androidx.paging.PagingData
import com.alishoumar.splashcache.domain.models.UnsplashImage
import com.alishoumar.splashcache.domain.repository.UnsplashImageRepository
import kotlinx.coroutines.flow.Flow

class GetAllImagesUseCase (
    private val repository: UnsplashImageRepository
){
    operator fun invoke(): Flow<PagingData<UnsplashImage>>{
        return repository.getAllImages()
    }
}