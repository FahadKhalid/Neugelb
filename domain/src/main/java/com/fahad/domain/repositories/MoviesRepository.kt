package com.fahad.domain.repositories

import com.fahad.domain.models.Movie
import com.fahad.domain.models.MovieResponse
import com.fahad.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getMovies(page: Int): Flow<Resource<MovieResponse>>
    suspend fun getMovieDetails(id: Int): Flow<Resource<Movie>>
}