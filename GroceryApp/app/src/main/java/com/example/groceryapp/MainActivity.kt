package com.example.groceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.groceryapp.database.NoteDB
import com.example.groceryapp.repository.NoteRepo
import com.example.groceryapp.viewmodels.NoteViewModel
import com.example.groceryapp.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel(){
        val noteRepo = NoteRepo(NoteDB(this))
        val viewModelProviderFactory = ViewModelFactory(application,noteRepo)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[NoteViewModel::class.java]
    }

}