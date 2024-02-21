package com.example.room_demo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(user: UsersEntity):Long

    @Query("select * from users")
    fun getData():LiveData<List<UsersEntity>>

    @Delete
    suspend fun deleteData(user:UsersEntity)

    @Update
    suspend fun updateData(user:UsersEntity)
}