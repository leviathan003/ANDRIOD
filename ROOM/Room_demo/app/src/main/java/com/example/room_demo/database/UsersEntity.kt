package com.example.room_demo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    var name:String,
    var email:String
)
