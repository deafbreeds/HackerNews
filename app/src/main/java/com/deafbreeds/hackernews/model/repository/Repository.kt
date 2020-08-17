package com.deafbreeds.hackernews.model.repository

import androidx.lifecycle.LiveData
import com.deafbreeds.hackernews.model.domain.News

interface Repository {
    fun fetchNews(callBack: FetchCallBack?)
    fun cancel()
    fun getNews(): LiveData<List<News>>
}