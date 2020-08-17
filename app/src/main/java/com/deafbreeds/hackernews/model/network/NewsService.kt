package com.deafbreeds.hackernews.model.network

import com.deafbreeds.hackernews.model.domain.News
import io.reactivex.Observable

interface NewsService {
    fun getTopNews(): Observable<News?>
}