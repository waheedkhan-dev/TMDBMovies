package com.codecollapse.tmdbmovies.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.codecollapse.tmdbmovies.models.datamodels.TMDBMovies
import com.codecollapse.tmdbmovies.models.datasources.utils.Resource
import com.codecollapse.tmdbmovies.models.repositories.StartUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor(private var startUpRepository: StartUpRepository) :
    ViewModel() {

    fun getTopRatedMovies(): Flow<Resource<List<TMDBMovies.Results>>> =
        startUpRepository.getTopRatedMovies()

    fun getMovieDetails(movieId: Int, movieLanguage: String) =
        startUpRepository.getMovieDetails(movieId, movieLanguage)
}