package com.fahad.data.local.datasoruce

import com.fahad.data.local.entity.MovieEntity
import com.fahad.domain.models.Movie

interface LocalMovieDataSource {
    suspend fun saveMovies(movies: MovieEntity)
    suspend fun getMovies(): List<MovieEntity>
    suspend fun getMovieDetails(id: Int): MovieEntity?
}