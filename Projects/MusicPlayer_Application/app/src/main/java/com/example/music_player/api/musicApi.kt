package com.example.music_player.api

import com.example.music_player.datamodels.Music
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface musicApi {
    @Headers("X-RapidAPI-Key: 29e07924aamsh7da56ace7580208p121dbfjsn9888b77848c3",
            "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
    suspend fun getData(
        @Query("q") query: String,
        @Query("page") page: Int=1
    ):Response<Music>
}