package com.example.network.network

import android.content.Context
import android.util.Log
import com.example.network.network.data.ErrorResponse
import com.example.network.network.data.LoginRequest
import com.example.network.network.data.LoginResponse
import com.example.network.network.data.TokenZeroRequest
import com.example.network.network.data.TokenZeroResponse
import com.example.network.network.helpers.generateSalt
import com.example.network.network.helpers.getLoginDetails
import com.example.network.network.helpers.getTokenZero
import com.example.network.network.helpers.handleErrorResponse
import com.example.network.network.helpers.hashPassword
import com.example.network.network.helpers.isTokenValid
import com.example.network.network.helpers.storeLoginDetails
import com.example.network.network.helpers.storeTokenAndExpiryTime
import com.example.network.network.helpers.storeTokenZero
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import java.security.MessageDigest
import java.security.SecureRandom


interface AuthService {
    @POST("/api/v1/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
    @PUT("/api/v1/login/token")
    fun renewAccessToken(@Body tokenZero: TokenZeroRequest): Call<TokenZeroResponse>

//    @POST("/api/v1/register")
//    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}

fun loginUser(context: Context, username: String, password: String) {
    val salt = generateSalt()
    val tokenZero = hashPassword(password, salt)
    storeTokenZero(context, tokenZero)

    val authService = RetrofitClient.getRetrofitInstance().create(AuthService::class.java)
    val loginRequest = LoginRequest(username, password, salt)

    authService.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    storeLoginDetails(context, loginResponse.userUUID, loginResponse.accessToken, loginResponse.ttl)
                } else {
                    // What
                }
            } else {
                handleErrorResponse(response)
            }
        }
        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            // Handle failure, such as network errors
        }
    })
}

fun renewAccessToken(context: Context) {
    val authService = RetrofitClient.getRetrofitInstance().create(AuthService::class.java)
    val tokenZero = getTokenZero(context)

    if (tokenZero.isNullOrEmpty()) {
        // Handle the case where tokenZero is not available, possibly navigate the user to the login screen
        return
    }

    val tokenZeroRequest = TokenZeroRequest(tokenZero)
    authService.renewAccessToken(tokenZeroRequest).enqueue(object : Callback<TokenZeroResponse> {
        override fun onResponse(call: Call<TokenZeroResponse>, response: Response<TokenZeroResponse>) {
            if (response.isSuccessful) {
                val newAccessToken = response.body()?.accessToken
                val newTTL = response.body()?.ttl
                if (newAccessToken != null && newTTL != null) {
                    storeTokenAndExpiryTime(context, newAccessToken, newTTL)
                }
            } else {
                handleErrorResponse(response)
            }
        }
        override fun onFailure(call: Call<TokenZeroResponse>, t: Throwable) {
            // Handle network errors or other failures
        }
    })
}

fun renewTokenIfNeeded(context: Context) {
    if (!isTokenValid(context)) {
        // Token is expired or about to expire, renew it
        renewToken(context)
    }
}
fun renewToken(context: Context) {
    // Implement the token renewal logic here
    renewAccessToken(context)
    // This typically involves making a PUT request to the /api/v1/login/token endpoint
    // Don't forget to update the stored token and expiry time after successfully renewing the token
}


// Before any API call that requires authentication, call renewTokenIfNeeded to ensure the accessToken is valid.
fun makeAuthenticatedApiCall(context: Context) {
    renewTokenIfNeeded(context)
    // Proceed with the API call using the valid accessToken
}


//fun registerUser(context: Context, username: String, password: String, email: String) {
//    val authService = RetrofitClient.getRetrofitInstance().create(AuthService::class.java)
//    val registerRequest = RegisterRequest(username, password, email)
//
//    authService.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
//        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
//            if (response.isSuccessful) {
//                // Handle successful registration
//                val registerResponse = response.body()
//                // Use registerResponse.userUUID and other data as needed
//            } else {
//                // Handle unsuccessful registration, such as username already taken
//                handleErrorResponse(response)
//            }
//        }
//
//        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
//            // Handle failure, such as network errors
//        }
//    })
//}