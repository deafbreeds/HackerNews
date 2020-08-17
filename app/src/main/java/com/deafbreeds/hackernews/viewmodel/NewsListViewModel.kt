package com.deafbreeds.hackernews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deafbreeds.hackernews.di.DaggerRepositoryComponent
import com.deafbreeds.hackernews.model.domain.News
import com.deafbreeds.hackernews.model.repository.FetchCallBack
import com.deafbreeds.hackernews.model.repository.NewsRepository
import javax.inject.Inject

class NewsListViewModel : ViewModel() {
    @Inject
    lateinit var repository: NewsRepository

    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    var news : LiveData<List<News>>

    init {
        DaggerRepositoryComponent.create().inject(this)
        news = repository.getNews()
    }

    fun refresh() {
        fetchNews()
    }

    private fun fetchNews() {
        loading.value = true
        repository.fetchNews(object : FetchCallBack() {
            override fun onError() {
                loading.postValue(false)
                if (news.value?.size == 0) {
                    loadError.postValue(true)
                }
            }

            override fun onSuccess() {
                loading.postValue(false)
                loadError.postValue(false)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        repository.cancel()
    }
}