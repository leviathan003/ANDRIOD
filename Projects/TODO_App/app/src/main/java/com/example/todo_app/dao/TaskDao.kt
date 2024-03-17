package com.example.todo_app.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todo_app.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
   @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task):Long

    @Query("select * from task order by date desc")
    fun getTaskList(): Flow<List<Task>>

    @Delete
    suspend fun deleteTask(task:Task):Int

    @Update
    suspend fun updateTask(task:Task):Int

    @Query("select * from task where taskTitle like :query order by date desc")
    fun searchTasks(query:String):Flow<List<Task>>
}