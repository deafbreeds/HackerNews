package com.deafbreeds.hackernews.model.network

import com.deafbreeds.hackernews.model.domain.News
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsAPI {
    @GET("topstories.json")
    fun getTopStoryIds(): Observable<List<Long>>

    @GET("item/{itemId}.json")
    fun getStory(@Path("itemId") itemId: String) : Observable<News>
}
