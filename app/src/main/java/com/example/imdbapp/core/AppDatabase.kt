package com.example.imdbapp.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imdbapp.model.WatchlistMovie

@Database(entities = [WatchlistMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
}