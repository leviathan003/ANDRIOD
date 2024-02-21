package com.example.room_demo.repository

import com.example.room_demo.database.RoomDB
import com.example.room_demo.database.UsersEntity

class RoomRepo(private val db:RoomDB) {

    suspend fun insert(user:UsersEntity) = db.UserDao().insertData(user)

    fun select() = db.UserDao().getData()

    suspend fun delete(user: UsersEntity) = db.UserDao().deleteData(user)

    suspend fun update(user: UsersEntity) = db.UserDao().updateData(user)

}