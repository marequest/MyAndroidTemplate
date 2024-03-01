package com.example.network.network.helpers

import android.content.Context
import android.util.Log
import com.example.network.network.data.ApiErrorResponse
import com.example.network.network.data.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response
import java.security.MessageDigest
import java.security.SecureRandom


class AuthServiceHelper {
    fun isTokenValid(context: Context, expiryTime: Long): Boolean {
        return System.currentTimeMillis() < expiryTime
    }

//fun isTokenValid(context: Context): Boolean {
//    val triple = getLoginDetails(context)
//    val expiryTime = triple.third
//    return System.currentTimeMillis() < expiryTime!!
//}


    fun generateSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }

    // Ovo je tokenZero
    fun hashPassword(password: String, salt: String): String {
        val md = MessageDigest.getInstance("SHA-512")
        md.update((salt + password).toByteArray())
        val digest = md.digest()
        return digest.joinToString("") { "%02x".format(it) }
    }

    fun handleErrorResponse(response: Response<*>) {
        val errorBody = response.errorBody()?.string()
        val gson = Gson()

        try {
            val errorResponse = gson.fromJson(errorBody, ApiErrorResponse::class.java)
            Log.e("Api Error", "Error: ${errorResponse.error}, Message: ${errorResponse.message}")

            when (response.code()) {
                400, 401 -> {
                    // Bad Request, Unauthorized
                    // Maybe navigate to login screen or show login dialog
                }
                404 -> {
                    // Not Found
                }
                500 -> {
                    // Internal Server Error
                }
                else -> {
                    // Other errors
                }
            }
        } catch (e: Exception) {
            Log.e("Api Error", "Error parsing error body: ${e.message}")
            ApiErrorResponse("ParseError", "An error occurred. Please try again.")
        }
    }
}