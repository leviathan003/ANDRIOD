package com.example.chattingapplication.models

data class Message(val senderUID:String,val recieverUID:String,val chatID:String ,val message:String, val timestamp: Long = 0){
    constructor() : this("", "", "","")
}