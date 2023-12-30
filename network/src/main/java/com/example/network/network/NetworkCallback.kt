package com.example.network.network


interface NetworkCallback<T> {
    fun onSuccess(response: T)
    fun onError(errorMessage: String?)
}