package com.codecollapse.tmdbmovies.models.datasources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codecollapse.tmdbmovies.models.datamodels.TMDBMovies
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie : TMDBMovies.Results)

    @Query("SELECT *FROM movies where movieType=:movieType order by vote_average desc")
    fun getTMDBMovies(movieType : String) : List<TMDBMovies.Results>

    @Query("DELETE FROM movies")
    suspend fun deleteMovies()
}