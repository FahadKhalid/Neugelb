package com.fahad.data.mapper

import com.fahad.data.local.entity.MovieEntity
import com.fahad.data.remote.model.MovieModel
import com.fahad.domain.models.Movie


fun MovieModel.mapToEntity(): MovieEntity {
    return MovieEntity(
        this.id,
        this.overview,
        this.image,
        this.releaseDate,
        this.title
    )
}
fun MovieModel.mapToModel(): Movie {
    return Movie(
        this.id,
        this.overview,
        this.image,
        this.releaseDate,
        this.title
    )
}

fun MovieEntity.mapEntityToModel(): Movie {
    return Movie(
        this.id,
        this.overview,
        this.posterImage,
        this.releaseDate,
        this.title
    )
}