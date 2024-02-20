package com.example.network.network.data

data class LoginRequest(
    val username: String,
    val password: String,
    val salt: String
)

data class LoginResponse(
    val username: String,
    val userUUID: String,
    val accessToken: String,
    val ttl: Long
)

data class TokenZeroRequest(val tokenZero: String)
