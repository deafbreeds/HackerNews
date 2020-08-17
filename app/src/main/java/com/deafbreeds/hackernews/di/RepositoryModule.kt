package com.deafbreeds.hackernews.di

import com.deafbreeds.hackernews.HackerNewsApplication
import com.deafbreeds.hackernews.model.network.HackerNewsService
import com.deafbreeds.hackernews.model.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideNewsRepository() = NewsRepository(HackerNewsService(), HackerNewsApplication.database)
}