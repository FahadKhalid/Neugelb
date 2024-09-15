package com.fahad.moviesapp.di

import com.fahad.data.local.dao.MoviesDao
import com.fahad.data.local.datasoruce.LocalMovieDataSource
import com.fahad.data.local.datasoruce.LocalMovieDataSourceImpl
import com.fahad.data.remote.datasource.RemoteMovieDataSource
import com.fahad.data.remote.datasource.RemoteMovieDataSourceImpl
import com.fahad.data.remote.service.MoviesApiService
import com.fahad.data.repository.MovieRepositoryImpl
import com.fahad.domain.repositories.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    fun provideLocalDataSource(dao: MoviesDao): LocalMovieDataSource {
        return LocalMovieDataSourceImpl(dao)
    }

    @Provides
    fun provideRemoteDataSource(apiService: MoviesApiService): RemoteMovieDataSource {
        return RemoteMovieDataSourceImpl(apiService)
    }

    @Provides
    fun provideRepository(
        localMovieDataSource: LocalMovieDataSource,
        remoteMovieDataSource: RemoteMovieDataSource
    ): MoviesRepository {
        return MovieRepositoryImpl(remoteMovieDataSource, localMovieDataSource)
    }
}