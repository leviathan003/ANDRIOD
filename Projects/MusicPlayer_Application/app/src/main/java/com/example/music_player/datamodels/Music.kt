package com.example.music_player.datamodels

data class Music(
    val `data`: List<Data>,
    val next: String,
    val total: Int
)