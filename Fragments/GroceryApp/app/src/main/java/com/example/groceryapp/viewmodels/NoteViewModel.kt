package com.example.groceryapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.groceryapp.models.Notes
import com.example.groceryapp.repository.NoteRepo
import kotlinx.coroutines.launch

class NoteViewModel(app:Application,private val noteRepo:NoteRepo):AndroidViewModel(app) {

    fun addNote(note: Notes)=
        viewModelScope.launch {
            noteRepo.insertNote(note)
        }

    fun delNote(note: Notes)=
        viewModelScope.launch {
            noteRepo.deleteNote(note)
        }

    fun updtNote(note: Notes) =
        viewModelScope.launch {
            noteRepo.updateNote(note)
        }

    fun searchNote(query:String?) = noteRepo.searchNote(query)

    fun getAllNotes() = noteRepo.getallNotes()

}