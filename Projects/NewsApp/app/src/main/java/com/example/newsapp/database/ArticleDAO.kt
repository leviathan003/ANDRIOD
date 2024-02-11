package com.example.newsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.models.Article


@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(article: Article): Long

    @Query("select * from articles")
    fun getAllArticles():LiveData<List<Article>>

    @Delete
    suspend fun deleteFav(article: Article)
}