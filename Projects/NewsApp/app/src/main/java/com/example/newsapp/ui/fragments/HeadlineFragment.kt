package com.example.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapters.NewsRVAdapter
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodels.NewsViewModel
import com.example.newsprojectpractice.R
import com.example.newsprojectpractice.databinding.FragmentHeadlineBinding
import com.google.android.material.snackbar.Snackbar

class HeadlineFragment : Fragment(R.layout.fragment_headline) {
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsRVAdapter: NewsRVAdapter
    lateinit var retryBtn:Button
    lateinit var errorTxt:TextView
    lateinit var itemHeadlineError:CardView
    lateinit var binding: FragmentHeadlineBinding

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlineBinding.bind(view)

        itemHeadlineError = view.findViewById(R.id.itemHeadlinesError)
        val inflator = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view:View = inflator.inflate(R.layout.item_error,null)

        retryBtn = view.findViewById(R.id.retryButton)
        errorTxt = view.findViewById(R.id.errorText)

        newsViewModel = (activity as MainActivity).newsViewModel
        setupHeadlineRV()

        newsRVAdapter.setOnItemClickListener {article->
            if(article!=null) {
                val bundle = Bundle().apply {
                    putSerializable("article", article)
                }
                findNavController().navigate(
                    R.id.action_headlinesFragment_to_articleFragment,
                    bundle
                )
            }else{
                Snackbar.make(view,"404: Article Not Found",Snackbar.LENGTH_SHORT).show()
            }
        }

        newsViewModel.headlines.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success<*>->{
                    hidePGBar()
                    hideErrorMessage()
                    response.data?.let {newsResponse->
                        newsRVAdapter.differ.submitList(newsResponse.articles.toList())
                        val totPages = newsResponse.totalResults/Constants.QUERY_PAGE_SIZE+2
                        isLastPage = newsViewModel.headlinesPage == totPages
                        if(isLastPage){
                            binding.recyclerHeadlines.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error<*>->{
                    hidePGBar()
                    response.message?.let {
                        message->
                        Toast.makeText(activity,"Error: $message",Toast.LENGTH_LONG).show()
                        showErrorMessage(message)
                    }
                }
                is Resource.Loading<*>->{
                    showPGBar()
                    hideErrorMessage()
                }
            }
        })

        retryBtn.setOnClickListener {
            newsViewModel.getHeadines("in")
        }

    }

    private fun hidePGBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showPGBar(){
        binding.paginationProgressBar.visibility  =View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage(){
        itemHeadlineError.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message:String){
        itemHeadlineError.visibility = View.VISIBLE
        errorTxt.text = message
        isError =  true
    }

    val scrollListener = object :RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPos+visibleItemCount >= totalItemCount
            val isNotAtBeg = firstVisibleItemPos >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem &&
                    isNotAtBeg && isTotalMoreThanVisible && isScrolling

            if(shouldPaginate){
                newsViewModel.getHeadines("in")
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }

    private fun setupHeadlineRV(){
        newsRVAdapter = NewsRVAdapter()
        binding.recyclerHeadlines.apply {
            adapter = newsRVAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HeadlineFragment.scrollListener)
        }
    }

}