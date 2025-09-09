package com.example.asuntosinstitucionalesinmemorial.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.data.network.response.toDomain
import com.example.asuntosinstitucionalesinmemorial.domain.model.Evento
import com.example.asuntosinstitucionalesinmemorial.domain.model.Invitados
import com.example.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.GetEventByIdFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.GetEventGuestsFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.GetEventsFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.GetRelevoGuestsStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.SetGuestFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.SetRelevoGuestFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetEventGuestsStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventsViewModel(
    val getEventsFirestoreUseCase: GetEventsFirestoreUseCase,
    val getEventByIdFirestoreUseCase: GetEventByIdFirestoreUseCase,
    val getEventGuestsStorageUseCase: GetEventGuestsStorageUseCase,
    val getRelevoGuestsStorageUseCase: GetRelevoGuestsStorageUseCase,
    val getEventGuestsFirestoreUseCase: GetEventGuestsFirestoreUseCase,
    val setGuestFirestoreUseCase: SetGuestFirestoreUseCase,
    val setRelevoGuestFirestoreUseCase: SetRelevoGuestFirestoreUseCase
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

    fun getEventGuestsStorage(event: String, esRelevoGuardia: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (esRelevoGuardia) {
                    val invitadosRelevo = getRelevoGuestsStorageUseCase(event).map { invitado ->
                        invitado.toDomain()
                    }
                    if (invitadosRelevo.isNotEmpty()) {
                        invitadosRelevo.forEach { invitado ->
                            if (invitado.nombre.isNotEmpty()) {
                                setRelevoGuestFirestoreUseCase(event, invitado)
                            }
                        }
                        _uiState.update { it.copy(invitadosRelevo = invitadosRelevo) }
                    }
                } else {
                    val invitados = getEventGuestsStorageUseCase(event).map { invitado ->
                        invitado.toDomain()
                    }
                    if (invitados.isNotEmpty()) {
                        invitados.forEach { invitado ->
                            if (invitado.nombre.isNotEmpty()) {
                                setGuestFirestoreUseCase(event, invitado)
                            }
                        }
                        _uiState.update { it.copy(invitados = invitados) }
                    }
                }
            } catch (_: Exception) {

            }
        }
    }

    fun setInvitadoFirestore(event: String, invitado: Invitados) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setGuestFirestoreUseCase(event, invitado)
            } catch (_: Exception) {

            }
        }
    }

    fun getEventGuests(event: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getEventGuestsFirestoreUseCase(event).collect { invitados ->
                    _uiState.update {
                        it.copy(
                            invitados = invitados
                        )
                    }
//                    addRegalosToDB(*invitados.toTypedArray())
                }
            } catch (_: Exception) {
//                getRegalosFromDB()
            }
        }
    }

    fun showDialog(isShown: Boolean) {
        _uiState.update { it.copy(isDialogShown = isShown) }
    }
}

data class EventsUiState(
    val events: List<Evento> = emptyList(),
    val event: Evento? = null,
    val invitados: List<Invitados> = emptyList(),
    val invitadosRelevo: List<InvitadosRelevo> = emptyList(),
    val isDialogShown: Boolean = false
)