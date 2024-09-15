package com.fahad.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fahad.data.local.dao.MoviesDao
import com.fahad.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val dao : MoviesDao
}