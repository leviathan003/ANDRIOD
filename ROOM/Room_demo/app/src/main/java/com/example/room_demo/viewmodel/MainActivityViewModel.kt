package com.example.room_demo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.room_demo.database.UsersEntity
import com.example.room_demo.repository.RoomRepo
import kotlinx.coroutines.launch

class MainActivityViewModel(app:Application, val userRepo:RoomRepo):AndroidViewModel(app) {

    fun addUser(user: UsersEntity) = viewModelScope.launch{
        userRepo.insert(user)
    }

    fun deleteUser(user: UsersEntity) = viewModelScope.launch {
        userRepo.delete(user)
    }

    fun getUsers() = viewModelScope.launch {
        userRepo.select()
    }

    fun updateUsers() = viewModelScope.launch {
        userRepo.update()
    }
}