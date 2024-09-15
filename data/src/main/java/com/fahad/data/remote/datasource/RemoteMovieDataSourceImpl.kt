package com.fahad.data.remote.datasource

import com.fahad.data.mapper.mapToEntity
import com.fahad.data.mapper.mapToModel
import com.fahad.data.remote.model.MovieResponse
import com.fahad.data.remote.service.MoviesApiService
import com.fahad.domain.models.Movie
import com.fahad.domain.util.Resource

class RemoteMovieDataSourceImpl(
    private val moviesApiService: MoviesApiService
) : RemoteMovieDataSource {
    override suspend fun getMovies(page: Int): Resource<MovieResponse> {
        return try {
            val response = moviesApiService.getMovies(page)
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "")
        }
    }

    override suspend fun getMovieDetails(id: Int): Resource<Movie> {
        return try {
            val response = moviesApiService.getMovieDetails(id)
            if (response.isSuccessful) {
                Resource.Success(response.body()?.mapToModel())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "")
        }
    }
}