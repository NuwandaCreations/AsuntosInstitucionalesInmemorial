package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.guests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Invitados
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetGuestByIdFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetGuestRelevoByIdFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetGuestFirestoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuestDetailViewModel(
    val getGuestByIdFirestoreUseCase: GetGuestByIdFirestoreUseCase,
    val getGuestRelevoByIdFirestoreUseCase: GetGuestRelevoByIdFirestoreUseCase,
    val setGuestFirestoreUseCase: SetGuestFirestoreUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(GuestDetailUiState())
    val uiState: StateFlow<GuestDetailUiState> = _uiState

    fun getGuestFirestore(evento: String, invitado: String, esRelevo: Boolean) {
        try {
            if (esRelevo) {
                viewModelScope.launch(Dispatchers.IO) {
                    getGuestRelevoByIdFirestoreUseCase(evento, invitado).collect { invitadoRelevo ->
                        _uiState.update { it.copy(invitadoRelevo = invitadoRelevo) }
                    }
                }
            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    getGuestByIdFirestoreUseCase(evento, invitado).collect { invitado ->
                        _uiState.update { it.copy(invitado = invitado) }
                    }
                }
            }
        } catch (_: Exception) {

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

    fun showDialog(isShown: Boolean) {
        _uiState.update { it.copy(isDialogShown = isShown) }
    }
}

data class GuestDetailUiState(
    val invitadoRelevo: InvitadosRelevo = InvitadosRelevo(),
    val invitado: Invitados = Invitados(),
    val isDialogShown: Boolean = false
)