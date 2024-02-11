package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.database.ArticleDB
import com.example.newsapp.models.Article

class NewsRepository(val db:ArticleDB) {

    suspend fun getHeadlines(countryCode: String,pageNumber:Int)=
        RetrofitInstance.api.getHeadlines(countryCode,pageNumber)

    suspend fun searchNews(query:String,pageNumber: Int)=
        RetrofitInstance.api.searchForNews(query,pageNumber)

    suspend fun insert(article: Article) = db.getArticleDAO().insertFav(article)

    fun getArticles() = db.getArticleDAO().getAllArticles()

    suspend fun delete(article: Article) = db.getArticleDAO().deleteFav(article)

}