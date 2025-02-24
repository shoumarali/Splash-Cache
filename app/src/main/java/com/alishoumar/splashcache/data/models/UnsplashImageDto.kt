package com.alishoumar.splashcache.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alishoumar.splashcache.data.utils.Constants.UNSPLASH_IMAGE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = UNSPLASH_IMAGE_TABLE)
data class UnsplashImageDto(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @Embedded
    val urls: UrlsDto,
    val likes: Int,
    @Embedded
    val user: UserDto
)