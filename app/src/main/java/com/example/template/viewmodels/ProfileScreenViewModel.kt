package com.example.template.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileUiState(
    val loading: Boolean = false,
)

class ProfileScreenViewModel : ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(ProfileUiState(loading = true))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()


    fun setDnevnikId(newId: String?) {
        _uiState.update { it.copy(loading = true) }
    }
}