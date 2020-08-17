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
    private val newsListAdapter = NewsListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsListViewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        setContentView(R.layout.activity_main)
        initUI(newsListViewModel)

        if(savedInstanceState == null){
            newsListViewModel.refresh()
            runRecyclerViewAnimation()
        }
        observeViewModel(newsListViewModel)
    }

    private fun initUI(viewModel: NewsListViewModel){
        newsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsListAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
    }

    private fun runRecyclerViewAnimation() {
        val controller =
            AnimationUtils.loadLayoutAnimation(newsList.context, R.anim.layout_animation_fall_down)
        newsList.layoutAnimation = controller
        newsList.adapter!!.notifyDataSetChanged()
        newsList.scheduleLayoutAnimation()
    }

    private fun observeViewModel(viewModel: NewsListViewModel) {
        viewModel.news.observe(this, Observer {
            it?.let {
                newsList.visibility = View.VISIBLE
                newsListAdapter.updateNews(it)
            }
        })

        viewModel.loadError.observe(this, Observer { isError ->
            isError?.let {
                newsList_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
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
