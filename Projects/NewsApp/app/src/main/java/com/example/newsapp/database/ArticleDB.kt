package com.example.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.models.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDB:RoomDatabase() {

   abstract fun getArticleDAO():ArticleDAO

   companion object{
       @Volatile
       private var instance:ArticleDB?=null
       private val LOCK=Any()

        operator fun invoke(context: Context)= instance?:
        synchronized(LOCK){
            instance?:createDB(context).also{
                instance = it
            }
        }

       private fun createDB(context: Context) = Room.databaseBuilder(
           context.applicationContext,
           ArticleDB::class.java,
           name = "article_db.db"
       ).build()
   }

}