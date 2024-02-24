package com.example.network.network.dump

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


fun storeTokenAndExpiryTime(context: Context, accessToken: String, ttl: Long) {
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
        putString("accessToken", accessToken)
        putLong("expiryTime", expiryTime)
        apply()
    }
}
fun storeTokenZero(context: Context, tokenZero: String) {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "EncryptedPreferencesFile",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    // Store tokenZero securely
    with(sharedPreferences.edit()) {
        putString("tokenZero", tokenZero)
        apply()
    }
}
fun getTokenZero(context: Context): String? {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "EncryptedPreferencesFile",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    return sharedPreferences.getString("tokenZero", null)
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
//        putLong("ttl", ttl) TODO Vidi ttl ili expiryTime
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

