package com.example.room_demo.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.room_demo.repository.RoomRepo

class MAViewModelFactory(val app:Application,val userRepo: RoomRepo):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(app,userRepo) as T
    }

}