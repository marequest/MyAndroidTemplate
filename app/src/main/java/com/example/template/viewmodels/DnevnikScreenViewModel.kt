package com.example.template.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DnevnikUiState(
    val dnevnikId: String? = null,
    val loading: Boolean = false,
)

class DnevnikScreenViewModel : ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(DnevnikUiState(loading = true))
    val uiState: StateFlow<DnevnikUiState> = _uiState.asStateFlow()


    fun setDnevnikId(newId: String?) {
        _uiState.update { it.copy(dnevnikId = newId, loading = true) }
    }
}