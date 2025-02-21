package com.alishoumar.splashcache.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alishoumar.splashcache.data.utils.Constants.UNSPLASH_REMOTE_KEYS_TABLE

@Entity(tableName = UNSPLASH_REMOTE_KEYS_TABLE)
data class UnsplashRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPageKey: Int?,
    val nextPageKey: Int?
)