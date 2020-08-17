package com.deafbreeds.hackernews.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news_table")
data class News(@PrimaryKey val id: Int,
                @SerializedName("by")
                val author: String,
                val time: Long,
                val title: String,
                val url: String,
                val score: Int)