package com.example.groceryapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.groceryapp.models.Notes

@Database(entities = [Notes::class], version = 1)
abstract class NoteDB :RoomDatabase(){

    abstract fun getNoteDao():NotesDao

    companion object{
        @Volatile
        private var instance:NoteDB?=null
        private var lock=Any()

        operator fun invoke(context:Context)= instance ?:
        synchronized(lock){
            instance ?:
            createDB(context).also {
                instance = it
            }
        }

        private fun createDB(context:Context)=
            Room.databaseBuilder(
                context.applicationContext,
                NoteDB::class.java,
                name="notes_db"
            ).build()
    }
}