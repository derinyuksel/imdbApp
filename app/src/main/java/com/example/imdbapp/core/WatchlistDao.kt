package com.example.imdbapp.core

import androidx.room.*
import com.example.imdbapp.model.WatchlistMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {

    @Query("SELECT * FROM watchlist")
    fun getWatchlist(): Flow<List<WatchlistMovie>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchlist(movie: WatchlistMovie)

    @Delete
    suspend fun removeFromWatchlist(movie: WatchlistMovie)

    @Query("SELECT EXISTS(SELECT * FROM watchlist WHERE id = :id)")
    fun isMovieInWatchlist(id: Int): Flow<Boolean>
}