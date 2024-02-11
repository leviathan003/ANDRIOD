package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.database.ArticleDB
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.viewmodels.NewsVMFactory
import com.example.newsapp.viewmodels.NewsViewModel
import com.example.newsprojectpractice.R
import com.example.newsprojectpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDB(this))
        val viewModelProviderFactory = NewsVMFactory(application,newsRepository)
        newsViewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)

        val navHost = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHost.navController
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}