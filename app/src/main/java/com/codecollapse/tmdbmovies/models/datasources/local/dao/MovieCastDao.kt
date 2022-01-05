package com.codecollapse.tmdbmovies.models.datasources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codecollapse.tmdbmovies.models.datamodels.MovieCredits

@Dao
interface MovieCastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCast(movieCast : MovieCredits.MovieCast)

    @Query("SELECT *FROM movie_cast where movieId=:movieId order by cast_id asc limit 8")
    fun getMovieCast(movieId : Int) : List<MovieCredits.MovieCast>
}