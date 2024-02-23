package com.example.network.network

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class EncryptedUserSessionRepository(private val context: Context) : UserSessionRepository {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            "EncryptedPreferencesFile",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun storeTokenAndExpiryTime(accessToken: String, ttl: Long) {
        val expiryTime = System.currentTimeMillis() + ttl
        with(sharedPreferences.edit()) {
            putString("accessToken", accessToken)
            putLong("expiryTime", expiryTime)
            apply()
        }
    }

    override fun storeLoginDetails(userUUID: String, accessToken: String, ttl: Long) {
        val expiryTime = System.currentTimeMillis() + ttl
        with(sharedPreferences.edit()) {
            putString("userUUID", userUUID)
            putString("accessToken", accessToken)
            putLong("expiryTime", expiryTime)
            apply()
        }
    }

    override fun storeTokenZero(tokenZero: String) {
        with(sharedPreferences.edit()) {
            putString("tokenZero", tokenZero)
            apply()
        }
    }

    override fun getLoginDetails(): Triple<String?, String?, Long?> {
        val userUUID = sharedPreferences.getString("userUUID", null)
        val accessToken = sharedPreferences.getString("accessToken", null)
        val ttl = sharedPreferences.getLong("expiryTime", -1)

        return Triple(userUUID, accessToken, ttl)
    }

    override fun getTokenZero(): String? {
        return sharedPreferences.getString("tokenZero", null)
    }
}
