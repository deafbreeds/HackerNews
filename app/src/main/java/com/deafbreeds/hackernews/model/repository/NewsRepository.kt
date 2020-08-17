package com.deafbreeds.hackernews.model.repository

import androidx.lifecycle.LiveData
import com.deafbreeds.hackernews.model.database.NewsDatabase
import com.deafbreeds.hackernews.model.domain.News
import com.deafbreeds.hackernews.model.network.NewsService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class NewsRepository(private val remoteService: NewsService, database: NewsDatabase) :
    Repository {
    private val disposable = CompositeDisposable()
    private val newsDao = database.newsDao()
    private val newsList: LiveData<List<News>>

    init {
        newsList = newsDao.getNews()
    }

    override fun fetchNews(callBack: FetchCallBack?) {
        disposable.add(
            remoteService.getTopNews()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<News>() {
                    override fun onComplete() {
                    }

                    override fun onNext(news: News?) {
                        if (news != null &&
                            !news.title.isNullOrEmpty() &&
                            !news.url.isNullOrEmpty() &&
                            !news.author.isNullOrEmpty()
                        ) {
                            addNews(news)
                            callBack?.onSuccess()
                        }
                    }

                    override fun onError(e: Throwable?) {
                        callBack?.onError()
                    }

                })
        )
    }

    override fun cancel() {
        disposable.clear()
    }

    private fun addNews(news: News) {
        newsDao.insert(news)
    }

    override fun getNews() = newsList
}

abstract class FetchCallBack {
    abstract fun onError()
    abstract fun onSuccess()
}