package com.alishoumar.splashcache.domain.models

data class UnsplashImage(
    val id: String,
    val urls: Urls,
    val likes: Int,
    val user: User
)