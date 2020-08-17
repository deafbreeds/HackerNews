package com.deafbreeds.hackernews.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deafbreeds.hackernews.model.domain.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News)

    @Query("SELECT * FROM news_table ORDER BY time DESC")
    fun getNews(): LiveData<List<News>>
}