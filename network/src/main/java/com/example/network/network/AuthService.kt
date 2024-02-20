package com.example.network.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.network.network.data.ErrorResponse
import com.example.network.network.data.LoginRequest
import com.example.network.network.data.LoginResponse
import com.example.network.network.data.RegisterRequest
import com.example.network.network.data.RegisterResponse
import com.example.network.network.data.TokenZeroRequest
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

    @POST("/api/v1/register")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @PUT("/api/v1/login/token")
    fun renewAccessToken(@Body tokenZero: TokenZeroRequest): Call<LoginResponse>

}

fun loginUser(context: Context, username: String, password: String) {
    val salt = generateSalt()
    val tokenZero = hashPassword(password, salt)
    // TODO Save tokenZero on device

    val authService = RetrofitClient.getRetrofitInstance().create(AuthService::class.java)
    val loginRequest = LoginRequest(username, password, salt)


    authService.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                // Handle successful login
                val loginResponse = response.body()
                // Use loginResponse.userUUID and loginResponse.accessToken as needed
                if (loginResponse != null) {
                    storeLoginDetails(context, loginResponse.userUUID, loginResponse.accessToken, loginResponse.ttl)
                } else {
                    // What
                }
            } else {
                // Handle unsuccessful login, such as invalid credentials
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
    val sharedPreferences = context.getSharedPreferences("AuthPreferences", Context.MODE_PRIVATE)
    val tokenZero = sharedPreferences.getString("tokenZero", null)

    if (tokenZero != null) {
        val tokenZeroRequest = TokenZeroRequest(tokenZero)

        authService.renewAccessToken(tokenZeroRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Update stored accessToken and expiry time
                    val newAccessToken = response.body()?.accessToken
                    val newTTL = response.body()?.ttl
                    if (newAccessToken != null && newTTL != null) {
//                        storeTokenAndExpiryTime(context, newAccessToken, newTTL)
                    }
                } else {
                    // Handle error, prompt user to login again or take other actions
                    handleErrorResponse(response)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle network errors or other failures
            }
        })
    }
}

fun isTokenValid(context: Context): Boolean {
    val triple = getLoginDetails(context)

    val expiryTime = triple.third
    return System.currentTimeMillis() < expiryTime!!
}

fun renewTokenIfNeeded(context: Context) {
    if (!isTokenValid(context)) {
        // Token is expired or about to expire, renew it
        renewToken(context)
    }
}
fun renewToken(context: Context) {
    // Implement the token renewal logic here
    // This typically involves making a PUT request to the /api/v1/login/token endpoint
    // Don't forget to update the stored token and expiry time after successfully renewing the token
}


// Before any API call that requires authentication, call renewTokenIfNeeded to ensure the accessToken is valid.
fun makeAuthenticatedApiCall(context: Context) {
    renewTokenIfNeeded(context)
    // Proceed with the API call using the valid accessToken
}

fun storeLoginDetails(context: Context, userUUID: String, accessToken: String, ttl: Long) {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "EncryptedPreferencesFile",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    val expiryTime = System.currentTimeMillis() + ttl


    with(sharedPreferences.edit()) {
        putString("userUUID", userUUID)
        putString("accessToken", accessToken)
        putLong("expiryTime", expiryTime)
//        putLong("ttl", ttl)
        apply()
    }
}

fun getLoginDetails(context: Context): Triple<String?, String?, Long?> {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "EncryptedPreferencesFile",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    val userUUID = sharedPreferences.getString("userUUID", null)
    val accessToken = sharedPreferences.getString("accessToken", null)
    val ttl = sharedPreferences.getLong("expiryTime", -1)

    return Triple(userUUID, accessToken, ttl)
}

fun registerUser(context: Context, username: String, password: String, email: String) {
    val authService = RetrofitClient.getRetrofitInstance().create(AuthService::class.java)
    val registerRequest = RegisterRequest(username, password, email)

    authService.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
            if (response.isSuccessful) {
                // Handle successful registration
                val registerResponse = response.body()
                // Use registerResponse.userUUID and other data as needed
            } else {
                // Handle unsuccessful registration, such as username already taken
                handleErrorResponse(response)
            }
        }

        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
            // Handle failure, such as network errors
        }
    })
}

fun handleErrorResponse(response: Response<*>) {
    val errorBody = response.errorBody()?.string()
    val gson = Gson()

    try {
        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
        // Use errorResponse to display error messages or take actions
        Log.e("Error", "Error: ${errorResponse.error}, Message: ${errorResponse.message}")
    } catch (e: Exception) {
        Log.e("Error", "An error occurred: ${e.message}")
    }
}

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
