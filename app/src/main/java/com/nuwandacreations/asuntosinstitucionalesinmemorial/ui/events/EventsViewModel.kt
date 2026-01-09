package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.events

import android.util.Log
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
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.DeleteEventFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventByIdFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventGuestsFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventsFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetRelevoGuestsFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetEventFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetGuestFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetRelevoGuestFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.ERROR_GETTING_GUESTS
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
    val getEventPhotoByIdStorageUseCase: GetEventPhotoByIdStorageUseCase,
    val deleteEventFirestoreUseCase: DeleteEventFirestoreUseCase
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
            _uiState.update { it.copy(isLoading = true) }
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

            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteEventFirestore(eventId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteEventFirestoreUseCase(eventId)
            } catch (_: Exception) {
            }
        }
    }

    fun setGuestAccess(
        access: Boolean,
        isRelevo: Boolean,
        event: String
    ) {
        if (isRelevo) {
            _uiState.update {
                it.copy(
                    invitadoRelevoSelected = it.invitadoRelevoSelected.copy(
                        accedido = access
                    )
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    invitadoSelected = it.invitadoSelected.copy(
                        accedido = access
                    )
                )
            }
        }
        setGuestFirestore(
            event = event,
            esRelevoGuardia = isRelevo,
            invitadoRelevo = _uiState.value.invitadoRelevoSelected,
            invitado = _uiState.value.invitadoSelected
        )
    }

    fun setGuestFirestore(
        event: String,
        esRelevoGuardia: Boolean,
        invitado: Invitados? = null,
        invitadoRelevo: InvitadosRelevo? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (esRelevoGuardia) {
                    invitadoRelevo?.let {
                        setRelevoGuestFirestoreUseCase(event, it)
                    }
                } else {
                    invitado?.let {
                        setGuestFirestoreUseCase(event, it)
                    }
                }
            } catch (_: Exception) {

            }
        }
    }

    fun getEventGuests(event: String, esRelevoGuardia: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!esRelevoGuardia) {
                    getEventGuestsFirestoreUseCase(event).collect { invitados ->
                        _uiState.update {
                            it.copy(
                                invitados = invitados
                            )
                        }
                    }
                } else {
                    getRelevoGuestsFirestoreUseCase(event).collect { invitados ->
                        _uiState.update {
                            it.copy(
                                invitadosRelevo = invitados
                            )
                        }
                    }
                }

            } catch (e: Exception) {
                Log.e("GetEventGuests", ERROR_GETTING_GUESTS, e)
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

    fun searchPhoto(
        event: String,
        invitado: Invitados = Invitados(),
        invitadoRelevo: InvitadosRelevo = InvitadosRelevo(),
        isRelevo: Boolean
    ) {
        val selectGuest = if (isRelevo) invitadoRelevo.nombre else invitado.nombre
        val upperName = selectGuest.uppercase()
        val normalized = Normalizer.normalize(upperName, Normalizer.Form.NFD)
        val photoName = normalized.replace("\\p{Mn}+".toRegex(), "")
        _uiState.value.guestsPhotos.forEach {
            if (photoName == it.first) {
                setGuestFirestore(
                    event = event,
                    esRelevoGuardia = isRelevo,
                    invitado = invitado.copy(foto = it.second),
                    invitadoRelevo = invitadoRelevo.copy(foto = it.second)
                )
            }
        }
    }

    fun selectGuest(
        invitado: Invitados = Invitados(),
        invitadoRelevo: InvitadosRelevo = InvitadosRelevo(),
        isRelevo: Boolean,
        isShortClick: Boolean = false
    ) {
        _uiState.update {
            if (isRelevo) {
                it.copy(invitadoRelevoSelected = invitadoRelevo, isDialogShown = isShortClick)
            } else {
                it.copy(invitadoSelected = invitado, isDialogShown = isShortClick)
            }
        }
    }

    fun updateDialogType(dialogType: DialogType) {
        _uiState.update { it.copy(dialogType = dialogType) }
    }

    fun showDialog(isShown: Boolean) {
        _uiState.update { it.copy(isDialogShown = isShown) }
    }
}

data class EventsUiState(
    val events: List<Evento> = emptyList(),
    val event: Evento? = null,
    val invitados: List<Invitados> = emptyList(),
    val invitadoSelected: Invitados = Invitados(),
    val invitadosRelevo: List<InvitadosRelevo> = emptyList(),
    val invitadoRelevoSelected: InvitadosRelevo = InvitadosRelevo(),
    val guestsPhotos: List<Pair<String, String>> = emptyList(),
    val dialogType: DialogType? = null,
    val isDialogShown: Boolean = false,
    val isLoading: Boolean = false
)

enum class DialogType {
    SET_GUEST,
    DELETE_EVENT
}