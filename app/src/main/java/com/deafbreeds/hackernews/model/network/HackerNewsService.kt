package com.deafbreeds.hackernews.model.network

import com.deafbreeds.hackernews.model.domain.News
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"

class HackerNewsService : NewsService {

    val api: HackerNewsAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(HackerNewsAPI::class.java)


    override fun getTopNews(): Observable<News?> {
        return api.getTopStoryIds()
            .concatMap { idList ->
                getNewsFromIds(
                    idList
                )
            }
    }

    private fun getNewsFromIds(idList: List<Long?>?): Observable<News?> {
        return Observable.fromIterable(idList)
            .concatMap { id -> api.getStory(id.toString()) }
            .flatMap { news ->
                Observable.just(news)
            }
    }
}