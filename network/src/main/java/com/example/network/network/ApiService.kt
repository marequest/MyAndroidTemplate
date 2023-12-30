package com.example.network.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

//    @GET("/facts/random?animal_type=cat&amount=5")
//    fun getCatData(): Call<CatFact>

    @GET("/facts/random")
    fun getRandomCatFacts(
        @Query("animal_type") animalType: String = "cat",
        @Query("amount") amount: Int = 6
    ): Call<List<CatFact>>
}

data class CatFact(
    val _id: String,
    val __v: Int,
    val text: String,
    val updatedAt: String,
    val deleted: Boolean,
    val source: String,
    val sentCount: Int
)
