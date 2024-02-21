package com.example.demo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class ChannelsDemo {

    val channel = Channel<Int>()
    fun producer(){
        CoroutineScope(Dispatchers.Main).launch {
            channel.send(1)
            channel.send(2)
        }
    }
    fun consumer(){
        CoroutineScope(Dispatchers.Main).launch {
            println(channel.receive().toString())
            println(channel.receive().toString())
        }
    }
}