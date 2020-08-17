package com.deafbreeds.hackernews.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deafbreeds.hackernews.model.domain.News

@Database(entities = [(News::class)], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}