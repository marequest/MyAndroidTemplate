package com.example.network.network.data

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
