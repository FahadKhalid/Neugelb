package com.fahad.moviesapp.di

import android.content.Context
import androidx.room.Room
import com.fahad.data.local.dao.MoviesDao
import com.fahad.data.local.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): MoviesDatabase = Room.databaseBuilder(
        context.applicationContext,
        MoviesDatabase::class.java,
        "movies-db"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: MoviesDatabase): MoviesDao = db.dao

}