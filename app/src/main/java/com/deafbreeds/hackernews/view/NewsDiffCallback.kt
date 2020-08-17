package com.deafbreeds.hackernews.view

import androidx.recyclerview.widget.DiffUtil
import com.deafbreeds.hackernews.model.domain.News

class NewsDiffCallback(private val oldNewsList: List<News>,
                       private val newNewsList: List<News>) :DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNewsList[oldItemPosition].id == newNewsList[newItemPosition].id
    }

    override fun getOldListSize() = oldNewsList.size

    override fun getNewListSize() = newNewsList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNews = oldNewsList[oldItemPosition]
        val newNews = newNewsList[newItemPosition]

        return oldNews == newNews
    }
}