package com.deafbreeds.hackernews.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.deafbreeds.hackernews.R
import com.deafbreeds.hackernews.model.domain.News
import com.deafbreeds.hackernews.util.TimeUtil
import kotlinx.android.synthetic.main.item_news.view.*
import org.joda.time.DateTime
import java.util.*

class NewsListAdapter(var newsList: ArrayList<News>) :
    RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    fun updateNews(newsList: List<News>) {
        val diffCallback = NewsDiffCallback(this.newsList, newsList)
        val result = DiffUtil.calculateDiff(diffCallback)

        this.newsList.clear()
        this.newsList.addAll(newsList)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
    )

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.title
        private val subtitle = view.subtitle

        fun bind(news: News) {
            title.text = news.title
            subtitle.text = "${news.score} points by ${news.author} ${TimeUtil.getPostTime(view.context,DateTime(news.time * 1000))}"

            view.setOnClickListener {
                val intent = Intent(view.context, NewsActivity::class.java).apply {
                    putExtra(NewsActivity.EXTRA_URL, news.url)
                }
                view.context.startActivity(intent)
            }
        }
    }
}