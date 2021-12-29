package com.codecollapse.tmdbmovies.di.modules

import android.content.Context
import androidx.room.Room
import com.codecollapse.tmdbmovies.models.datasources.local.dao.MovieDao
import com.codecollapse.tmdbmovies.models.datasources.local.dao.MovieDetailDao
import com.codecollapse.tmdbmovies.models.datasources.local.database.TMDBDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): TMDBDatabase {
        return Room.databaseBuilder(
            context, TMDBDatabase::class.java,
            TMDBDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: TMDBDatabase): MovieDao {
        return appDatabase.moviesDao()
    }

    @Singleton
    @Provides
    fun provideMovieDetailDao(appDatabase: TMDBDatabase): MovieDetailDao {
        return appDatabase.movieDetailDao()
    }
}