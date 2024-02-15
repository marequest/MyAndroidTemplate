package com.example.template.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.network.ApiService
import com.example.network.network.RetrofitClient
import com.example.network.network.CatFact
import com.example.template.emaildata.Email
import com.example.template.emaildata.EmailsRepository
import com.example.template.emaildata.EmailsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class TemplateHomeViewModel(private val emailsRepository: EmailsRepository = EmailsRepositoryImpl()): ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUIState(loading = true))
    val uiState: StateFlow<HomeUIState> = _uiState

    private val apiService = RetrofitClient.getRetrofitInstance().create(ApiService::class.java)
    private val _catState = MutableStateFlow(CatsState())
    val catState: StateFlow<CatsState> = _catState


    init {
        observeEmails()
//        fetchCatFacts()
    }

    fun fetchCatFacts() {
        viewModelScope.launch {
            try {
                _catState.value = CatsState(isLoading = true)

                withContext(Dispatchers.IO) {

                    val call: Call<List<CatFact>> = apiService.getRandomCatFacts(amount = 21)
                    call.enqueue(object : Callback<List<CatFact>> {
                        override fun onResponse(call: Call<List<CatFact>>, response: Response<List<CatFact>>) {
                            if (response.isSuccessful) {
                                // Update LiveData or MutableState with the response data
                                // For example:
                                println("NIKOLA")
                                println(response.body())
                                val catFacts = response.body() ?: emptyList()
                                _catState.value = CatsState(cats = catFacts)

                            } else {
                                // Handle error
                                _catState.value = CatsState(error = "Failed to fetch cat facts")
                            }
                        }

                        override fun onFailure(call: Call<List<CatFact>>, t: Throwable) {
                            // Handle network error
                            _catState.value = CatsState(error = "Error: ${t.message}")
                        }
                    })
                }
            } catch (e: Exception) {
                _catState.value = CatsState(error = "Error: ${e.message}")
            } finally {
                _catState.value = _catState.value.copy(isLoading = false)
            }
        }
    }

    fun logIn() {
        _uiState.value = _uiState.value.copy(loggedIn = true)
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

data class CatsState(
    val cats: List<CatFact> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class HomeUIState(
    val emails: List<Email> = emptyList(),
    val loggedIn: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)