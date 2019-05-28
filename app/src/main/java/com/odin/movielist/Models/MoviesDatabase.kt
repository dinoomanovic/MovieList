package com.odin.movielist.models

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by Dino Omanovic on May 27, 2019
 */
@Database(entities = [Search::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesResponseDao() : MoviesResponseDao

    companion object {
        @Volatile private var instance: MoviesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                MoviesDatabase::class.java, "movies-list.db")
                .build()
    }
}