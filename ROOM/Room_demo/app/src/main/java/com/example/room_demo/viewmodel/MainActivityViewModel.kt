package com.example.room_demo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    fun getUsers(): LiveData<List<UsersEntity>> {
        return userRepo.select()
    }

    fun updateUsers(user: UsersEntity) = viewModelScope.launch {
        userRepo.update(user)
    }
}