package com.example.template.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DnevniciListUiState(
    val loading: Boolean = false,
)

class DnevniciListScreenViewModel : ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(DnevniciListUiState(loading = true))
    val uiState: StateFlow<DnevniciListUiState> = _uiState.asStateFlow()


//    fun setDnevnikId(newId: String?) {
//        _uiState.update { it.copy(loading = true) }
//    }
}