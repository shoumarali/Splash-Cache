package com.alishoumar.splashcache.di

import com.alishoumar.splashcache.domain.repository.UnsplashImageRepository
import com.alishoumar.splashcache.domain.usecases.GetAllImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllImagesUseCase(
        unsplashImageRepository: UnsplashImageRepository
    ): GetAllImagesUseCase {
        return GetAllImagesUseCase(unsplashImageRepository)
    }
}