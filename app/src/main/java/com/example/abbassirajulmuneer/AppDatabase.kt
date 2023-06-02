package com.example.abbassirajulmuneer

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Details::class], version = 2, exportSchema = false)

abstract class AppDatabase: RoomDatabase() {
    abstract fun DetailsDaoDao(): DetailsDao
}