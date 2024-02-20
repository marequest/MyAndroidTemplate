package com.example.network.network.dump


interface NetworkCallback<T> {
    fun onSuccess(response: T)
    fun onError(errorMessage: String?)
}