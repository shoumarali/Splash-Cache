package com.alishoumar.splashcache.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alishoumar.splashcache.data.models.UnsplashRemoteKeys

@Dao
interface UnsplashRemoteKeyDao {

    @Query("SELECT * FROM unsplash_remote_keys_table WHERE id = :id")
     fun getRemoteKeys(id: String): UnsplashRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addAllRemoteKeys(remoteKeys: List<UnsplashRemoteKeys>)

    @Query("DELETE FROM unsplash_remote_keys_table")
     fun deleteAllRemoteKeys()
}