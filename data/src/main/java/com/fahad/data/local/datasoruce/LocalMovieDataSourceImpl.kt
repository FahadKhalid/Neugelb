package com.fahad.data.local.datasoruce

import com.fahad.data.local.dao.MoviesDao
import com.fahad.data.local.entity.MovieEntity
import com.fahad.data.mapper.mapToEntity
import com.fahad.domain.models.Movie

class LocalMovieDataSourceImpl(private val movieDao: MoviesDao) : LocalMovieDataSource {
    override suspend fun saveMovies(movies: MovieEntity) = movieDao.saveMovies(movies)

    override suspend fun getMovies(): List<MovieEntity> = movieDao.getMovies()

    override suspend fun getMovieDetails(id: Int) = movieDao.getMovie(id)

}