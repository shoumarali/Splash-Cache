package com.alishoumar.splashcache.data.mappers

import androidx.paging.PagingSource
import com.alishoumar.splashcache.data.models.UnsplashImageDto
import com.alishoumar.splashcache.domain.models.UnsplashImage
import com.alishoumar.splashcache.domain.models.Urls
import com.alishoumar.splashcache.domain.models.User
import com.alishoumar.splashcache.domain.models.UserLinks

fun List<UnsplashImageDto>.toUnsplashImage() : List<UnsplashImage>{
    return this.map {
        UnsplashImage(
            id = it.id,
            urls = Urls(it.urls.regular),
            likes = it.likes,
            user = User(UserLinks(it.user.userLinks.html), it.user.username)
        )
    }
}
