package com.example.network.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("example-endpoint")
    fun getExampleData(
        @Query("part") part: String,
        @Query("id") id: String
    ): Call<YourResponseModel?>?
}

class YourResponseModel {

}
