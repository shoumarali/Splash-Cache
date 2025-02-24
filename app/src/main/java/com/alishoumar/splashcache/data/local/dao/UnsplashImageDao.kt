package com.alishoumar.splashcache.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alishoumar.splashcache.data.models.UnsplashImageDto


@Dao
interface UnsplashImageDao {

    @Query("SELECT * FROM unsplash_image_table")
     fun getAllImages():PagingSource<Int, UnsplashImageDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addImages(images: List<UnsplashImageDto>)

    @Query("DELETE FROM unsplash_image_table")
     fun deleteAllImages()
}