package com.example.room_demo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsersEntity::class], version = 1)
abstract class RoomDB: RoomDatabase() {
    abstract fun UserDao(): RoomDao
    companion object{
        @Volatile
        private var instance:RoomDB?=null
        private val LOCK=Any()

        operator fun invoke(context: Context)= instance?:
        synchronized(LOCK){
            instance?:createDB(context).also{
                instance = it
            }
        }

        private fun createDB(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            name="users_db.db"
        ). build()
    }
}