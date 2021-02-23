package com.gprifti.livetv.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query("SELECT * from FavoriteEntity")
    suspend fun readFavorite(): List<FavoriteEntity>

    @Query("DELETE FROM FavoriteEntity WHERE id = :id")
    suspend fun deleteById(id: Long) :Int
}