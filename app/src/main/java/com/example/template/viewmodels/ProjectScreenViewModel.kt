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

data class ProjectUiState(
    val loading: Boolean = false,
)

@HiltViewModel
class ProjectScreenViewModel @Inject constructor() : ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(ProjectUiState(loading = true))
    val uiState: StateFlow<ProjectUiState> = _uiState.asStateFlow()


    fun setDnevnikId(newId: String?) {
        _uiState.update { it.copy(loading = true) }
    }
}