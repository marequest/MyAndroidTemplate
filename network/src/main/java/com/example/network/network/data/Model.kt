package com.example.network.network.data

import com.google.gson.annotations.SerializedName

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

data class TokenZeroRequest(
    val tokenZero: String
)
data class TokenZeroResponse(
    val accessToken: String,
    val ttl: Long
)

data class ApiErrorResponse(
    @SerializedName("error") val error: String?,
    @SerializedName("message") val message: String?
)
data class ErrorResponse(
    val error: String,
    val message: String
)

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String // Add more fields as needed
)
data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val userUUID: String? // Include any other relevant fields
)
