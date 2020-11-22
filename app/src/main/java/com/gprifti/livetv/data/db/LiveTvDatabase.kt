package com.gprifti.livetv.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavoriteEntity::class], version = 2, exportSchema = true)
abstract class LiveTvDatabase : RoomDatabase() {

    abstract fun getTaskDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: LiveTvDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

            instance ?: createDatabase(context).also { instance = it }

        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            LiveTvDatabase::class.java,
            "liveTv.db"
        ).fallbackToDestructiveMigration().build()
    }
}