package com.example.chattingapplication.models

import java.io.Serializable

data class User(val uid:String, val name:String,val isOnline:Boolean):Serializable
