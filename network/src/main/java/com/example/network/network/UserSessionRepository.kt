package com.example.network.network

import android.content.Context

interface UserSessionRepository {
    fun storeTokenAndExpiryTime(accessToken: String, ttl: Long)

    fun storeLoginDetails(userUUID: String, accessToken: String, ttl: Long)

    fun storeTokenZero(tokenZero: String)

    fun getLoginDetails(): Triple<String?, String?, Long?>

    fun getTokenZero(): String?


}