package com.example.template.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class DnevnikUiState(
    val dnevnikId: String? = null,
    val loading: Boolean = false,
)

@HiltViewModel
class DnevnikScreenViewModel @Inject constructor() : ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(DnevnikUiState(loading = true))
    val uiState: StateFlow<DnevnikUiState> = _uiState.asStateFlow()


    fun setDnevnikId(newId: String?) {
        _uiState.update { it.copy(dnevnikId = newId, loading = true) }
    }
}