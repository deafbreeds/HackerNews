package com.deafbreeds.hackernews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deafbreeds.hackernews.model.database.NewsDao
import com.deafbreeds.hackernews.model.database.NewsDatabase
import com.deafbreeds.hackernews.model.domain.News
import com.deafbreeds.hackernews.model.network.NewsService
import com.deafbreeds.hackernews.model.repository.NewsRepository
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var database: NewsDatabase

    @Mock
    lateinit var newsDao: NewsDao

    @Mock
    lateinit var service: NewsService

    lateinit var repository: NewsRepository


    @Before
    fun setup() {
        doReturn(newsDao)
            .whenever(database)
            .newsDao()
        repository = NewsRepository(service, database)

        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @After
    fun closeDB() {
        database.close()
    }


    @Test
    fun fetchNewsSuccess() {
        val news = News(3, "Naiyi", 1000, "Test", "url", 100)

        doReturn(Observable.just(news))
            .whenever(service)
            .getTopNews()
        repository.fetchNews(null)

        //then
        verify(newsDao).insert(news)
    }

    @Test
    fun fetchNewsFailed() {
        val news = News(3, "Naiyi", 1000, "", "url", 100)

        doReturn(Observable.just(news))
            .whenever(service)
            .getTopNews()
        repository.fetchNews(null)

        //then
        verify(newsDao, never()).insert(news)
    }
}