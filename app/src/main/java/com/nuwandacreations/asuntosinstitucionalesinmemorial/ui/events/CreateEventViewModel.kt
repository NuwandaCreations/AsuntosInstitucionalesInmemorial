package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Evento
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetEventFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.ERROR_UPDATING_EVENT
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.timestampToDate
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.toCamelCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

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
                if (event.id.isNullOrBlank() || event.nombre.isNullOrBlank() || event.fecha.isNullOrBlank() || event.lugar.isNullOrBlank()) {
                    _uiState.update { it.copy(errorType = CreateEventError.INCOMPLETE) }
                    onError()
                } else {
                    setEventFirestoreUseCase(event)
                    onSucces()
                }
            } catch (_: Exception) {
                _uiState.update { it.copy(errorType = CreateEventError.ANOTHER) }
                onError()
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateEventState(
        name: String? = null,
        eventDate: Long? = null,
        place: String? = null,
        isRelevo: Boolean? = null,
        description: String? = null
    ) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    val currentEvent = currentState.event ?: return@update currentState
                    val eventDateString = if (eventDate != null) {
                        timestampToDate(Timestamp(Date(eventDate)))
                    } else {
                        currentEvent.fecha
                    }

                    currentState.copy(
                        event = currentEvent.copy(
                            nombre = name ?: currentEvent.nombre,
                            id = name?.toCamelCase() ?: currentEvent.id,
                            fecha = eventDateString ?: currentEvent.fecha,
                            lugar = place ?: currentEvent.lugar,
                            esRelevoGuardia = isRelevo ?: currentEvent.esRelevoGuardia,
                            descripcion = description ?: currentEvent.descripcion
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

enum class CreateEventError {
    INCOMPLETE, ANOTHER
}

data class CreateEventUiState(
    val event: Evento? = Evento(),
    val errorType: CreateEventError = CreateEventError.ANOTHER,
    val isInputMode: Boolean = false,
    val isDatePickerShown: Boolean = false,
    val isLoading: Boolean = false,
    val isErrorShown: Boolean = false
)