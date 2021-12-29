package com.codecollapse.tmdbmovies.models.datasources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codecollapse.tmdbmovies.models.datamodels.MovieDetail

@Dao
interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie : MovieDetail)

    @Query("SELECT *FROM movie_detail where id=:movieId")
    fun getTMDBMovieById(movieId : Int) : MovieDetail

}