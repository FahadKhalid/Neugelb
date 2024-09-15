package com.fahad.domain.usecases

import com.fahad.domain.repositories.MoviesRepository

class GetMovies(private val repository: MoviesRepository) {
    suspend fun getMovies(page: Int) = repository.getMovies(page)
    suspend fun getMovieDetails(id: Int) = repository.getMovieDetails(id)
}