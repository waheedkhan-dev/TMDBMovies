package com.codecollapse.tmdbmovies.di.modules

import com.codecollapse.tmdbmovies.models.datasources.api.TMDBApi
import com.codecollapse.tmdbmovies.models.datasources.local.dao.MovieCastDao
import com.codecollapse.tmdbmovies.models.datasources.local.dao.MovieDao
import com.codecollapse.tmdbmovies.models.datasources.local.dao.MovieDetailDao
import com.codecollapse.tmdbmovies.models.repositories.StartUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideStartUpRepository(movieDao: MovieDao,tmdbApi: TMDBApi,movieDetailDao: MovieDetailDao,movieCastDao: MovieCastDao) : StartUpRepository{
        return StartUpRepository(movieDao = movieDao,tmdbApi = tmdbApi, movieDetailDao = movieDetailDao,movieCastDao)
    }
}