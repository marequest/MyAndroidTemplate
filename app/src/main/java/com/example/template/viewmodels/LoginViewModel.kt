package com.example.template.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.network.network.AuthService
import com.example.network.network.RetrofitClient
import com.example.network.network.UserSessionRepository
import com.example.network.network.data.LoginRequest
import com.example.network.network.data.TokenZeroRequest
import com.example.network.network.helpers.generateSalt
import com.example.network.network.helpers.getTokenZero
import com.example.network.network.helpers.hashPassword
import com.example.network.network.helpers.storeLoginDetails
import com.example.network.network.helpers.storeTokenAndExpiryTime
import com.example.network.network.helpers.storeTokenZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val IS_TEST = true

sealed interface LoginState {
    object Idle: LoginState
    object Loading : LoginState
    object Success : LoginState
    data class Error(val message: String?) : LoginState
}
sealed interface TokenRenewalState {
    object Idle : TokenRenewalState
    object Loading : TokenRenewalState
    object Success : TokenRenewalState
    data class Error(val message: String) : TokenRenewalState
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _tokenRenewalState = MutableStateFlow<TokenRenewalState>(TokenRenewalState.Idle)
    val tokenRenewalState: StateFlow<TokenRenewalState> = _tokenRenewalState

    fun login(username: String, password: String) = viewModelScope.launch {
        if(IS_TEST) {
            _loginState.value = LoginState.Loading

            delay(2000)

            _loginState.value = LoginState.Success

        } else {
            try {
                val salt = generateSalt()
                val tokenZero = hashPassword(password, salt)
                userSessionRepository.storeTokenZero(tokenZero)

                val authService = RetrofitClient.getRetrofitInstance().create(AuthService::class.java)
                val loginRequest = LoginRequest(username, password, salt)

                val response = authService.loginUser(loginRequest)

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    userSessionRepository.storeLoginDetails(loginResponse.userUUID, loginResponse.accessToken, loginResponse.ttl)
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Login failed: ${response.errorBody()?.string() ?: "Unknown error"}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun renewAccessToken() = viewModelScope.launch {
        if(IS_TEST) {
            _tokenRenewalState.value = TokenRenewalState.Loading

            delay(3000)

            _tokenRenewalState.value = TokenRenewalState.Success

        } else {
            _tokenRenewalState.value = TokenRenewalState.Loading

            try {
                val tokenZero = userSessionRepository.getTokenZero()

                if (tokenZero.isNullOrEmpty()) {
                    _tokenRenewalState.value = TokenRenewalState.Error("Token zero is not available")
                    return@launch
                }

                val authService = RetrofitClient.getRetrofitInstance().create(AuthService::class.java)
                val tokenZeroRequest = TokenZeroRequest(tokenZero)

                val response = authService.renewAccessToken(tokenZeroRequest)

                if (response.isSuccessful && response.body() != null) {
                    val newAccessToken = response.body()!!.accessToken
                    val newTTL = response.body()!!.ttl
                    userSessionRepository.storeTokenAndExpiryTime(newAccessToken, newTTL)
                    _tokenRenewalState.value = TokenRenewalState.Success
                } else {
                    _tokenRenewalState.value = TokenRenewalState.Error("Failed to renew access token")
                }
            } catch (e: Exception) {
                _tokenRenewalState.value = TokenRenewalState.Error("Exception occurred: ${e.message}")
            }
        }
    }

}
