package com.example.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.data.Email
import com.example.template.data.EmailsRepository
import com.example.template.data.EmailsRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TemplateHomeViewModel(private val emailsRepository: EmailsRepository = EmailsRepositoryImpl()): ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUIState(loading = true))
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        observeEmails()
    }

    private fun observeEmails() {
        viewModelScope.launch {
            emailsRepository.getAllEmails()
                .catch { ex ->
                    _uiState.value = HomeUIState(error = ex.message)
                }
                .collect { emails ->
                    _uiState.value = HomeUIState(emails = emails)
                }
        }
    }
}

data class HomeUIState(
    val emails : List<Email> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)