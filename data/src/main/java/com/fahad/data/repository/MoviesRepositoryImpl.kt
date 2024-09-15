package com.fahad.data.repository

import com.fahad.data.local.datasoruce.LocalMovieDataSource
import com.fahad.data.mapper.mapEntityToModel
import com.fahad.data.mapper.mapToEntity
import com.fahad.data.remote.datasource.RemoteMovieDataSource
import com.fahad.domain.models.Movie
import com.fahad.domain.models.MovieResponse
import com.fahad.domain.repositories.MoviesRepository
import com.fahad.domain.util.Resource
import com.fahad.data.utils.handleSuccess
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteMovieDataSource,
    private val localDataSource: LocalMovieDataSource
) : MoviesRepository {

    override suspend fun getMovies(page: Int): Flow<Resource<MovieResponse>> {
        val movieResponses = remoteDataSource.getMovies(page)
        val movies = movieResponses.data?.movies?.map {
            it.mapToEntity()
        }
        movies?.forEach {
            localDataSource.saveMovies(it)
        }
        val localMovies = localDataSource.getMovies().map { it.mapEntityToModel() }
        return handleSuccess(
            MovieResponse(
                totalPages = movieResponses.data?.totalPages ?: 0,
                movies = localMovies
            )
        )
    }

    override suspend fun getMovieDetails(id: Int): Flow<Resource<Movie>> {
        val movie = localDataSource.getMovieDetails(id)
        return if (movie != null) {
            handleSuccess(movie.mapEntityToModel())
        } else {
            val movieResponses = remoteDataSource.getMovieDetails(id)
            val movies = movieResponses.data
            handleSuccess(movies)
        }
    }
}