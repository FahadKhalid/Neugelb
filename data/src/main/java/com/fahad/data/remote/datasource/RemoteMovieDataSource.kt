package com.fahad.data.remote.datasource

import com.fahad.data.remote.model.MovieResponse
import com.fahad.domain.models.Movie
import com.fahad.domain.util.Resource

interface RemoteMovieDataSource {
    suspend fun getMovies(page: Int): Resource<MovieResponse>
    suspend fun getMovieDetails(id: Int): Resource<Movie>
}