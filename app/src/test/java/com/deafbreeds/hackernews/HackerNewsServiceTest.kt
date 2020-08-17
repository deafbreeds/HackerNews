package com.deafbreeds.hackernews

import com.deafbreeds.hackernews.model.network.HackerNewsService
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HackerNewsServiceTest {
    lateinit var service: HackerNewsService

    @Before
    fun setup(){
        service = HackerNewsService()
    }

    @Test
    fun getTopStoryIds(){
        val ids = service.api.getTopStoryIds().blockingFirst()


        Assert.assertTrue(ids.isNotEmpty())
    }

    @Test
    fun getStory(){
        val news = service.api.getStory("24181783").blockingFirst()

        Assert.assertNotNull(news)
    }

    @Test
    fun getNewsSuccess(){
        val news = service.getTopNews().blockingFirst()

        Assert.assertNotNull(news)
        Assert.assertNotNull(news?.title)
        Assert.assertNotNull(news?.author)
        Assert.assertNotNull(news?.url)
    }
}