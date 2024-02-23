package com.example.music_player.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.music_player.api.RetrofitInstance
import com.example.music_player.datamodels.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicViewModel(app:Application) : AndroidViewModel(app) {

    private val _musicList = MutableLiveData<List<Data>>()
    val musicList: LiveData<List<Data>> = _musicList

    fun searchMusic(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            println(name)
            val response = RetrofitInstance.api.getData(name,20)
            if (response.isSuccessful) {
                val music = response.body()?.data ?: emptyList()
                _musicList.postValue(music)
            }
        }
    }
}
