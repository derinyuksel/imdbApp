package com.example.imdbapp.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imdbapp.model.WatchlistMovie
import com.example.imdbapp.model.User

@Database(entities = [WatchlistMovie::class, User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
    abstract fun userDao(): UserDao
}