package com.example.template.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val loading: Boolean = false,
)

@HiltViewModel
class ProfileScreenViewModel @Inject constructor() : ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(ProfileUiState(loading = true))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun setDnevnikId(newId: String?) {
        _uiState.update { it.copy(loading = true) }
    }
}