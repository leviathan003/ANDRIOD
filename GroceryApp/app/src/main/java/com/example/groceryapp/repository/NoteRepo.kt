package com.example.groceryapp.repository

import androidx.room.Query
import com.example.groceryapp.database.NoteDB
import com.example.groceryapp.models.Notes

class NoteRepo(private val db: NoteDB) {
    suspend fun insertNote(note: Notes) = db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note: Notes) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Notes) = db.getNoteDao().updateNote(note)
    fun getallNotes() = db.getNoteDao().getallNotes()
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)
}