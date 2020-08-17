package com.deafbreeds.hackernews.di

import com.deafbreeds.hackernews.viewmodel.NewsListViewModel
import dagger.Component

@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {
    fun inject(viewModel: NewsListViewModel)
}