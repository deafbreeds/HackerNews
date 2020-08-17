package com.deafbreeds.hackernews.view

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.deafbreeds.hackernews.R
import com.deafbreeds.hackernews.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModelNews: NewsListViewModel
    private val newsListAdapter = NewsListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()

        viewModelNews = ViewModelProvider(this).get(NewsListViewModel::class.java)
        if(savedInstanceState == null){
            viewModelNews.refresh()
            runRecyclerViewAnimation()
        }
        observeViewModel()
    }

    private fun initUI(){
        newsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsListAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModelNews.refresh()
        }
    }

    private fun runRecyclerViewAnimation() {
        val controller =
            AnimationUtils.loadLayoutAnimation(newsList.context, R.anim.layout_animation_fall_down)
        newsList.layoutAnimation = controller
        newsList.adapter!!.notifyDataSetChanged()
        newsList.scheduleLayoutAnimation()
    }

    private fun observeViewModel() {
        viewModelNews.news.observe(this, Observer {
            it?.let {
                newsList.visibility = View.VISIBLE
                newsListAdapter.updateNews(it)
            }
        })

        viewModelNews.loadError.observe(this, Observer { isError ->
            isError?.let {
                newsList_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModelNews.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if(it){
                    newsList_error.visibility = View.GONE
                    newsList.visibility = View.GONE
                }
            }
        })
    }
}
