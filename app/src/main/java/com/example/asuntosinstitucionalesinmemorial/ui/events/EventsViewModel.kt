package com.example.asuntosinstitucionalesinmemorial.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.data.network.response.toDomain
import com.example.asuntosinstitucionalesinmemorial.domain.model.Evento
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.GetEventByIdFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.GetEventsFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetEventGuestsStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventsViewModel(
    val getEventsFirestoreUseCase: GetEventsFirestoreUseCase,
    val getEventByIdFirestoreUseCase: GetEventByIdFirestoreUseCase,
    val getEventGuestsStorageUseCase: GetEventGuestsStorageUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState

    fun getEventsFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            getEventsFirestoreUseCase().collect { events ->
                _uiState.update { it.copy(events = events.map { evento -> evento.toDomain() }) }
            }
        }
    }

    fun getEventByIdFirestore(event: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getEventByIdFirestoreUseCase(event).collect { evento ->
                _uiState.update { it.copy(event = evento.toDomain()) }
            }
        }

    }
}

data class EventsUiState(
    val events: List<Evento> = emptyList(),
    val event: Evento? = null
)