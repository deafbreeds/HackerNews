package com.deafbreeds.hackernews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.deafbreeds.hackernews.model.database.NewsDao
import com.deafbreeds.hackernews.model.database.NewsDatabase
import com.deafbreeds.hackernews.model.domain.News
import com.nhaarman.mockitokotlin2.mock
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var databse: NewsDatabase
    private lateinit var newsDao: NewsDao


    @Before
    fun setup() {
        databse = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            NewsDatabase::class.java
        ).build()
        newsDao = databse.newsDao()
    }

    @After
    fun closeDB() {
        databse.close()
    }

    @Test
    fun getNewsReturnsEmptyList() {
        val testObserver: Observer<List<News>> = mock()
        newsDao.getNews().observeForever(testObserver)
        verify(testObserver).onChanged(emptyList())
    }

    @Test
    fun addNews() {
        val news = News(id = 1, title = "Test", author = "Naiyi", url = "url", time = 100000, score = 100)
        newsDao.insert(news)

        val testObserver: Observer<List<News>> = mock()
        newsDao.getNews().observeForever(testObserver)

        val listClass =
            ArrayList::class.java as Class<ArrayList<News>>

        val argumentCaptor = ArgumentCaptor.forClass(listClass)
        verify(testObserver).onChanged(argumentCaptor.capture())

        assertTrue(argumentCaptor.value.size > 0)
    }

    @Test
    fun getNewsRetrievesData() {
        val news = News(id = 1, title = "Test", author = "Naiyi", url = "url", time = 100000, score = 100)
        newsDao.insert(news)

        val testObserver: Observer<List<News>> = mock()
        newsDao.getNews().observeForever(testObserver)

        val listClass =
            ArrayList::class.java as Class<ArrayList<News>>
        val argumentCaptor = ArgumentCaptor.forClass(listClass)
        verify(testObserver).onChanged(argumentCaptor.capture())
        val capturedArgument = argumentCaptor.value
        assertTrue(
            capturedArgument
                .containsAll(listOf(news))
        )

    }
}