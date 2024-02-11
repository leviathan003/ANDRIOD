package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.models.Article
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.viewmodels.NewsViewModel
import com.example.newsprojectpractice.R
import com.example.newsprojectpractice.databinding.FragmentNewsReadBinding
import com.google.android.material.snackbar.Snackbar

class NewsReadFragment : Fragment(R.layout.fragment_news_read) {

    lateinit var newsViewModel: NewsViewModel
    lateinit var binding: FragmentNewsReadBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsReadBinding.bind(view)

        newsViewModel = (activity as MainActivity).newsViewModel

        var article:Article
        val bundle = arguments

        if (bundle != null) {
            article = bundle.getSerializable("article") as Article
            binding.webView.apply {
                webViewClient = WebViewClient()
                article.url.let {
                    loadUrl(it)
                    binding.webProgress.visibility = View.GONE
                }
            }

            binding.fab.setOnClickListener{
                newsViewModel.addToFavs(article)
                Snackbar.make(view,"Saved to favourites",Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}