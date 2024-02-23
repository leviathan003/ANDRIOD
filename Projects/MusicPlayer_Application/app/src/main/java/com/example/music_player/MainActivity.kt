package com.example.music_player

import android.os.Bundle
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.music_player.adapters.MusicRVAdapter
import com.example.music_player.databinding.ActivityMainBinding
import com.example.music_player.viewmodel.MusicViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MusicViewModel
    private lateinit var adapter: MusicRVAdapter
    var isScrolling = false
    var isLastPage = false
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        setupRecyclerView()
        observeData()

        binding.searchBtn.setOnClickListener {
            val name = binding.searchName.text.toString().trim()
            if (name.isNotEmpty()) {
                viewModel.searchMusic(name)
            }
        }
    }

    val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPos + visibleItemCount >= totalItemCount
            val isNotAtBeg = firstVisibleItemPos >= 0
            val isTotalMoreThanVisible = totalItemCount >= 20
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem &&
                    isNotAtBeg && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.searchMusic(binding.searchName.text.toString().trim())
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MusicRVAdapter(this)
        binding.musicRV.layoutManager = LinearLayoutManager(this)
        binding.musicRV.adapter = adapter
        binding.musicRV.addOnScrollListener(scrollListener)
        binding.musicRV.setHasFixedSize(true)
    }

    private fun observeData() {
        viewModel.musicList.observe(this, Observer { musicList ->
            adapter.updateData(musicList)
        })
    }
}
