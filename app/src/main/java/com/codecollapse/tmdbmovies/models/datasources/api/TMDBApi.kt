package com.codecollapse.tmdbmovies.models.datasources.api

import com.codecollapse.tmdbmovies.models.datamodels.MovieDetail
import com.codecollapse.tmdbmovies.models.datamodels.TMDBMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("trending/all/day")
    suspend fun getTrendingMovies(@Query("api_key") apiKey: String): Response<TMDBMovies>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): Response<TMDBMovies>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query("api_key") apiKey: String) : Response<TMDBMovies>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId : Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<MovieDetail>

    /*@GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ) : Response<MovieCredits>*/
}