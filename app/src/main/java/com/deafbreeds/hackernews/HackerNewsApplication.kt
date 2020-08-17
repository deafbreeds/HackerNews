package com.deafbreeds.hackernews

import android.app.Application
import androidx.room.Room
import com.deafbreeds.hackernews.model.database.NewsDatabase

class HackerNewsApplication: Application() {
    companion object{
        lateinit var database: NewsDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, NewsDatabase::class.java, "news_database").build()
    }
}