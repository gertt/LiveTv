package com.gprifti.livetv.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 3, exportSchema = true)
abstract class LiveTvDatabase: RoomDatabase() { abstract fun getTaskDao(): FavoriteDao }