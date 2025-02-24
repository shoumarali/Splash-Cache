package com.alishoumar.splashcache.di

import com.alishoumar.splashcache.data.repository.UnsplashImageRepoImpl
import com.alishoumar.splashcache.domain.repository.UnsplashImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUnsplashImageRepository(
        unsplashImageRepoImpl: UnsplashImageRepoImpl
    ):UnsplashImageRepository

}