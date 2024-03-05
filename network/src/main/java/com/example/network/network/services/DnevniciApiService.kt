package com.example.network.network.services

import retrofit2.http.GET

interface DnevniciApiService {

    @GET("dnevnici")
    suspend fun getMyDnevnici(): String


}