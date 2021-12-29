package com.codecollapse.tmdbmovies.models.repositories

import android.util.Log
import com.codecollapse.tmdbmovies.common.AppConstants
import com.codecollapse.tmdbmovies.models.datamodels.MovieDetail
import com.codecollapse.tmdbmovies.models.datamodels.TMDBMovies
import com.codecollapse.tmdbmovies.models.datasources.api.TMDBApi
import com.codecollapse.tmdbmovies.models.datasources.local.dao.MovieDao
import com.codecollapse.tmdbmovies.models.datasources.local.dao.MovieDetailDao
import com.codecollapse.tmdbmovies.models.datasources.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StartUpRepository @Inject constructor(
    private var movieDao: MovieDao,
    private var tmdbApi: TMDBApi,
    private var movieDetailDao: MovieDetailDao
) {
    companion object {
        private const val TAG = "StartUpRepository"
    }

    fun getTopRatedMovies(): Flow<Resource<List<TMDBMovies.Results>>> {
        return flow {
            try {
                emit(Resource.success(movieDao.getTMDBMovies()))
                tmdbApi.getTopRatedMovies(AppConstants.API_KEY).let {
                    if (it.isSuccessful) {
                        Log.d(TAG, "getTrendingMoviesList: ${it.body()!!.results}")
                        it.body()!!.results.forEach { movie->
                            movieDao.insertMovies(movie)
                        }
                        emit(Resource.success(movieDao.getTMDBMovies()))
                    } else {
                        emit(Resource.error(it.body()!!.status_message!!, data = null))
                    }
                }
            }
            catch (ex : Exception){
                Log.d(TAG, "getMovieDetails: ${ex.message}")
                emit(Resource.error("something went wrong", data = null))
            }

        }.flowOn(IO)
    }

    fun getMovieDetails(movieId: Int, movieLanguage: String): Flow<Resource<MovieDetail>> {
        return flow {
            try {
                emit(Resource.success(movieDetailDao.getTMDBMovieById(movieId)))
                tmdbApi.getMovieDetails(movieId,AppConstants.API_KEY, movieLanguage).let {
                    if (it.isSuccessful) {
                        Log.d(TAG, "getMovieDetails: ${it.body()!!}")
                        movieDetailDao.insertMovie(it.body()!!)
                        emit(Resource.success(movieDetailDao.getTMDBMovieById(movieId)))
                    } else {
                        emit(Resource.error("something went wrong", data = null))
                    }
                }
            }catch (ex : Exception){
                Log.d(TAG, "getMovieDetails: ${ex.message}")
                emit(Resource.error("something went wrong", data = null))
            }

        }.flowOn(IO)
    }
}