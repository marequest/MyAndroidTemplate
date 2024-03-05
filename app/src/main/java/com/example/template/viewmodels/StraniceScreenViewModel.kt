package com.example.template.viewmodels

import androidx.lifecycle.ViewModel
import com.example.template.fakedata.Strana
import com.example.template.fakedata.StraniceDataProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

data class DnevnikUiState(
//    val dnevnikId: String? = null,
//    val stranicaId: String? = null,
    val strana: Strana? = null,  // Added the entire Strana state
    val loading: Boolean = false,
)

@HiltViewModel
class StraniceScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(DnevnikUiState(loading = true))
    val uiState: StateFlow<DnevnikUiState> = _uiState.asStateFlow()

    init {
        initialStranaState()

//        setDnevnikId(DnevniciDataProvider.getLastDnevnik().id.toString())
//        setStranicaId(StraniceDataProvider.getLastStranica().stranaId.toString())
    }

    private fun initialStranaState() {
        val strana = getInicijalnaStrana()
        _uiState.update { it.copy(strana = strana, loading = false) }
    }

    private fun getInicijalnaStrana(): Strana {
        return StraniceDataProvider.getLastStranica()
    }

    fun updateStrana(newStrana: Strana) {
        _uiState.update { it.copy(strana = newStrana, loading = false) }
    }
    fun updateStranaId(newStranaId: Long) {
        val newStrana = fetchStranaById(newStranaId)
        /// TODO Ovde moze provera ako je newStrana null da nista ne uradi
        _uiState.update { currentState ->
            currentState.copy(strana = newStrana, loading = false)
        }
        println("Promenjena stranica na: $newStranaId")
    }

    fun setDnevnikId(newDnevnikId: Long) {
        _uiState.update { currentState ->
            val newStrana = StraniceDataProvider.allStranice.lastOrNull {
                it.dnevnikId == newDnevnikId
            }

            newStrana?.let {
                println("Promenjena dnevnik na: $newDnevnikId, i stranica na ${it.stranaId}")
            }

            currentState.copy(strana = newStrana ?: currentState.strana, loading = false)
        }
    }

    fun updateStrana(newStranaId: Long, newDnevnikId: Long) {
        val newStrana = fetchStranaByIdAndDnevnikId(newStranaId, newDnevnikId)
        /// TODO Ovde moze provera ako je newStrana null da nista ne uradi
        _uiState.update { currentState ->
            currentState.copy(strana = newStrana, loading = false)
        }
    }

    fun updateStranaDate(newStranaDate: Date) {
        /// TODO Ovo treba da proveri ako postoji stranica sa datumom pa da predje na tu stranicu
        _uiState.update { currentState ->
            val matchingStrana = findStranaByDate(newStranaDate)

            if (matchingStrana != null) {
                println("Promenjen datum na: $newStranaDate")
                // Update the UI state to the found Strana
                currentState.copy(strana = matchingStrana, loading = false)
            } else {
                println("NE POSTOJI STRANA SA DATUMOM: $newStranaDate")
                // No matching Strana found, optionally handle this case, such as by showing an error or a default state
                currentState // Return the current state unchanged
            }
        }
    }
    private fun findStranaByDate(date: Date): Strana? {
        // Implement the logic to search for a Strana with the specified date
        // This is just a placeholder, replace with your actual data fetching/search logic

        val normalizedInputDate = date.toStartOfDay()

        return StraniceDataProvider.allStranice.find {
            it.datumStrane.toStartOfDay() == normalizedInputDate
        }    }

    // Extension function to normalize a Date to the start of the day
    private fun Date.toStartOfDay(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = this
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
    private fun fetchStranaById(stranaId: Long): Strana? {
        return StraniceDataProvider.getStranaById(stranaId)
    }

    private fun fetchStranaByIdAndDnevnikId(stranaId: Long, dnevnikId: Long): Strana? {
        return StraniceDataProvider.getStranaByIdAndDnevnikId(stranaId, dnevnikId)
    }
//    fun setDnevnikId(newId: String?) {
//        _uiState.update { it.copy(dnevnikId = newId, loading = true) }
//    }
//
//    fun setStranicaId(newId: String?) {
//        _uiState.update { it.copy(stranicaId = newId, loading = true) }
//    }

}