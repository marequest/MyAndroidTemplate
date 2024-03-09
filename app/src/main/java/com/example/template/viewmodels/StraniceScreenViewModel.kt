package com.example.template.viewmodels

import androidx.lifecycle.ViewModel
import com.example.template.fakedata.entities.Smena
import com.example.template.fakedata.entities.Strana
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
    val strana: Strana? = null,
    val loading: Boolean = false
)
@HiltViewModel
class StraniceScreenViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(DnevnikUiState(loading = true))
    val uiState: StateFlow<DnevnikUiState> = _uiState.asStateFlow()

    init {
        initialStranaState()
    }

    private fun initialStranaState() {
        val strana = getInicijalnaStrana()
        _uiState.update { it.copy(strana = strana, loading = false) }
    }

    private fun getInicijalnaStrana(): Strana {
        return StraniceDataProvider.getLastStranica()
    }

    fun goToThisStrana(newStrana: Strana) {
        _uiState.update { it.copy(strana = newStrana, loading = false) }
    }

    fun goToDnevnik(newDnevnikId: Long) {
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

    fun goToStrana(newStranaId: Long) {
        val newStrana = fetchStranaById(newStranaId)
        /// TODO Ovde moze provera ako je newStrana null da nista ne uradi
        _uiState.update { currentState ->
            if(newStrana != null) {
                currentState.copy(strana = newStrana, loading = false)
            } else {
                currentState
            }
        }
    }

    fun goToStranaAtDnevnik(newStranaId: Long, newDnevnikId: Long) {
        val newStrana = fetchStranaByIdAndDnevnikId(newStranaId, newDnevnikId)
        /// TODO Ovde moze provera ako je newStrana null da nista ne uradi
        _uiState.update { currentState ->
            if(newStrana != null) {
                currentState.copy(strana = newStrana, loading = false)
            } else {
                currentState
            }
        }
    }

    fun goToStranaByDate(newStranaDate: Date) {
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

    fun updateInfoORadnicima(kojaSmena: Int, smena: Smena) {
        _uiState.update { currentState ->
            val currentStrana = currentState.strana
            if (currentStrana != null) {
                if (kojaSmena in currentStrana.smenaRadnici.indices) {
                    val updatedSmenaRadnici = currentStrana.smenaRadnici.toMutableList().apply {
                        this[kojaSmena] = smena
                    }.toTypedArray()
                    val updatedStrana = currentStrana.copy(smenaRadnici = updatedSmenaRadnici)

                    currentState.copy(strana = updatedStrana, loading = false)
                } else {
                    println("GRESKA u updateInfoORadnicima")
                    currentState
                }
            } else {
                currentState
            }
        }
    }
    fun updateInfoOVremenu(sunacnoOblacnoKisa: String, brzinaVetra: String, nivoPodzemnihVoda: String) {
        _uiState.update { currentState ->
            val currentStrana = currentState.strana
            if (currentStrana != null) {
                val updatedStrana = currentStrana.copy(
                    sunacnoOblacnoKisa = sunacnoOblacnoKisa,
                    brzinaVetra = brzinaVetra,
                    nivoPodzemnihVoda = nivoPodzemnihVoda
                )
                currentState.copy(strana = updatedStrana, loading = false)
            } else {
                currentState
            }
        }
    }
    private fun findStranaByDate(date: Date): Strana? {
        val normalizedInputDate = date.toStartOfDay()

        return StraniceDataProvider.allStranice.find {
            it.datumStrane.toStartOfDay() == normalizedInputDate
        }
    }

    private fun fetchStranaById(stranaId: Long): Strana? {
        return StraniceDataProvider.getStranaById(stranaId)
    }

    private fun fetchStranaByIdAndDnevnikId(stranaId: Long, dnevnikId: Long): Strana? {
        return StraniceDataProvider.getStranaByIdAndDnevnikId(stranaId, dnevnikId)
    }

    private fun Date.toStartOfDay(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = this
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}