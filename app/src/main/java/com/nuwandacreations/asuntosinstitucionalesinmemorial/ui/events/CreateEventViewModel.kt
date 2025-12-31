package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Evento
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetEventFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.ERROR_UPDATING_EVENT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateEventViewModel(
    val setEventFirestoreUseCase: SetEventFirestoreUseCase,
) : ViewModel() {
    val _uiState = MutableStateFlow(CreateEventUiState())
    val uiState: StateFlow<CreateEventUiState> = _uiState

    fun setEventFirestore(
        event: Evento,
        onSucces: () -> Unit = {},
        onError: () -> Unit = {}
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                setEventFirestoreUseCase(event)
                onSucces()
            } catch (_: Exception) {
                onError()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateEventState(
        name: String? = null,
        id: String? = null,
        date: String? = null,
        place: String? = null,
        isRelevo: Boolean? = null
    ) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    val currentEvent = currentState.event ?: return@update currentState

                    currentState.copy(
                        event = currentEvent.copy(
                            nombre = name ?: currentEvent.nombre,
                            id = id ?: currentEvent.id,
                            fecha = date ?: currentEvent.fecha,
                            lugar = place ?: currentEvent.lugar,
                            esRelevoGuardia = isRelevo ?: currentEvent.esRelevoGuardia
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("CreateEventViewModel", ERROR_UPDATING_EVENT, e)
            }
        }
    }

    fun inputMode(isInput: Boolean) {
        _uiState.update { it.copy(isInputMode = isInput) }
    }

    fun showDatePicker(isShown: Boolean) {
        _uiState.update { it.copy(isDatePickerShown = isShown) }
    }

    fun showError(isShown: Boolean) {
        _uiState.update { it.copy(isErrorShown = isShown) }
    }
}

data class CreateEventUiState(
    val event: Evento? = Evento(),
    val isInputMode: Boolean = false,
    val isDatePickerShown: Boolean = false,
    val isLoading: Boolean = false,
    val isErrorShown: Boolean = false
)