package com.example.groceryapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.groceryapp.models.Notes

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Notes)

    @Update
    suspend fun updateNote(note:Notes)

    @Delete
    suspend fun deleteNote(notes: Notes)

    @Query("select * from notes order by id desc")
    fun getallNotes():LiveData<List<Notes>>

    @Query("select * from notes where title like :query or description like :query")
    fun searchNote(query:String?):LiveData<List<Notes>>

}