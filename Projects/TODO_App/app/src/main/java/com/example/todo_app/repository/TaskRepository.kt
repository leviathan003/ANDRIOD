package com.example.todo_app.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.todo_app.dao.TaskDao
import com.example.todo_app.database.TaskDatabase
import com.example.todo_app.models.Task
import com.example.todo_app.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TaskRepository(application: Application) {

    private val taskDao:TaskDao = TaskDatabase.getInstance(application).taskDao

    fun getTaskList() = flow {
        emit(Resource.Loading())
        try{
            val result = taskDao.getTaskList()
            emit(Resource.Success(result))
        }catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
    fun insertTask(task: Task)=MutableLiveData<Resource<Long>>().apply {
        postValue(Resource.Loading())
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.insertTask(task)
                postValue(Resource.Success(result))
            }
        }catch (e:Exception){
            postValue(Resource.Error(e.message.toString()))
        }
    }

    fun deleteTask(task: Task)=MutableLiveData<Resource<Int>>().apply {
        postValue(Resource.Loading())
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.deleteTask(task)
                postValue(Resource.Success(result))
            }
        }catch (e:Exception){
            postValue(Resource.Error(e.message.toString()))
        }
    }

    fun updateTask(task:Task)=MutableLiveData<Resource<Int>>().apply {
        postValue(Resource.Loading())
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val result = taskDao.updateTask(task)
                postValue(Resource.Success(result))
            }
        }catch (e:Exception){
            postValue(Resource.Error(e.message.toString()))
        }
    }

    fun searchTask(query: String) = flow {
        try{
            val result = taskDao.searchTasks("%${query}%")
            emit(Resource.Success(result))
        }catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}