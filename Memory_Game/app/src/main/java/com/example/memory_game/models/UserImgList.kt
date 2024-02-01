package com.example.memory_game.models

import com.google.firebase.firestore.PropertyName

data class UserImgList (
    @PropertyName("images") val images: List<String>? = null
)
