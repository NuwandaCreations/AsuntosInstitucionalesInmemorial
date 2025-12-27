package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.events.toDomain
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Evento
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Invitados
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.GetEventGuestsStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.GetEventPhotoByIdStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.GetGuestsPhotosStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.GetRelevoGuestsStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventByIdFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventGuestsFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventsFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetRelevoGuestsFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetEventFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetGuestFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetRelevoGuestFirestoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.Normalizer

class EventsViewModel(
    val getEventsFirestoreUseCase: GetEventsFirestoreUseCase,
    val getEventByIdFirestoreUseCase: GetEventByIdFirestoreUseCase,
    val getEventGuestsStorageUseCase: GetEventGuestsStorageUseCase,
    val getRelevoGuestsStorageUseCase: GetRelevoGuestsStorageUseCase,
    val getEventGuestsFirestoreUseCase: GetEventGuestsFirestoreUseCase,
    val getRelevoGuestsFirestoreUseCase: GetRelevoGuestsFirestoreUseCase,
    val setEventFirestoreUseCase: SetEventFirestoreUseCase,
    val setGuestFirestoreUseCase: SetGuestFirestoreUseCase,
    val setRelevoGuestFirestoreUseCase: SetRelevoGuestFirestoreUseCase,
    val getGuestsPhotosStorageUseCase: GetGuestsPhotosStorageUseCase,
    val getEventPhotoByIdStorageUseCase: GetEventPhotoByIdStorageUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState

    fun getEventsFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            getEventsFirestoreUseCase().collect { events ->
                val sortedEvents = events.sortedBy { it.fecha }
                _uiState.update { it.copy(events = sortedEvents.map { evento -> evento.toDomain() }) }
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

    fun getEventPhotoByIdStorage(event: Evento) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                event.id?.let {
                    val photo = getEventPhotoByIdStorageUseCase(it)
                    if (photo.isNotEmpty()) {
                        setEventFirestoreUseCase(event.copy(imagen = photo))
                    }
                }
            } catch (e: Exception) {

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

    fun setGuestFirestore(event: String, invitado: Invitados) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setGuestFirestoreUseCase(event, invitado)
            } catch (_: Exception) {

            }
        }
    }

    fun setRelevoGuestFirestore(event: String, invitado: InvitadosRelevo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setRelevoGuestFirestoreUseCase(event, invitado)
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

    fun getRelevoGuests(event: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getRelevoGuestsFirestoreUseCase(event).collect { invitados ->
                    _uiState.update {
                        it.copy(
                            invitadosRelevo = invitados
                        )
                    }
                }
            } catch (_: Exception) {
            }
        }
    }

    fun getGuestsPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val photoNames = getGuestsPhotosStorageUseCase()
                _uiState.update { it.copy(guestsPhotos = photoNames) }
            } catch (_: Exception) {
            }
        }
    }

    fun searchPhoto(event: String, invitado: Invitados) {
        val upperName = invitado.nombre.uppercase()
        val normalized = Normalizer.normalize(upperName, Normalizer.Form.NFD)
        val photoName = normalized.replace("\\p{Mn}+".toRegex(), "")
        _uiState.value.guestsPhotos.forEach {
            if (photoName == it.first) {
                setGuestFirestore(event, invitado.copy(foto = it.second))
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
    val guestsPhotos: List<Pair<String, String>> = emptyList(),
    val isDialogShown: Boolean = false
)