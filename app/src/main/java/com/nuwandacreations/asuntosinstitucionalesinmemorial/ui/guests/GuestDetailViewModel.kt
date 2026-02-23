package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.guests

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Invitados
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetGuestByIdFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetGuestRelevoByIdFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetGuestFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetRelevoGuestFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.ERROR_LOADING_GUEST
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.ERROR_SETTING_ACCESS
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.ERROR_SETTING_GUEST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GuestDetailViewModel(
    val getGuestByIdFirestoreUseCase: GetGuestByIdFirestoreUseCase,
    val getGuestRelevoByIdFirestoreUseCase: GetGuestRelevoByIdFirestoreUseCase,
    val setGuestFirestoreUseCase: SetGuestFirestoreUseCase,
    val setRelevoGuestFirestoreUseCase: SetRelevoGuestFirestoreUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(GuestDetailUiState())
    val uiState: StateFlow<GuestDetailUiState> = _uiState

    fun getGuestFirestore(event: String, guest: String, isRelevo: Boolean, hasQrScanned: Boolean) {
        try {
            if (isRelevo) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        getGuestRelevoByIdFirestoreUseCase(event, guest).collect { invitadoRelevo ->
                            if (invitadoRelevo.nombre.isNotEmpty()) {
                                _uiState.update { it.copy(invitadoRelevo = invitadoRelevo) }
                                if (hasQrScanned) {
                                    setGuestAccess(event, access = true, isRelevo = true)
                                }
                            } else {
                                _uiState.update { it.copy(emptyQrScan = true) }
                            }
                        }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(emptyQrScan = true) }
                        Log.e("GetGuestFirestore", ERROR_LOADING_GUEST, e)
                    }
                }
            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    getGuestByIdFirestoreUseCase(event, guest).collect { invitado ->
                        if (invitado.nombre.isNotEmpty()) {
                            _uiState.update { it.copy(invitado = invitado) }
                            if (hasQrScanned) {
                                setGuestAccess(event, access = true, isRelevo = false)
                            }
                        } else {
                            _uiState.update { it.copy(emptyQrScan = true) }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(emptyQrScan = true) }
            Log.e("GetGuestFirestore", ERROR_LOADING_GUEST, e)
        }
    }

    fun setGuestFirestore(
        event: String,
        guest: Invitados,
        relevoGuest: InvitadosRelevo,
        isRelevo: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isRelevo) {
                    setRelevoGuestFirestoreUseCase(event, relevoGuest)
                } else {
                    setGuestFirestoreUseCase(event, guest)
                }
            } catch (e: Exception) {
                Log.e("SetGuestFirestore", ERROR_SETTING_GUEST, e)
            }
        }
    }

    fun setGuestAccess(event: String, access: Boolean?, isRelevo: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                if (isRelevo) {
                    _uiState.update { it.copy(invitadoRelevo = it.invitadoRelevo.copy(accedido = access)) }
                } else {
                    _uiState.update { it.copy(invitado = it.invitado.copy(accedido = access)) }
                }
                setGuestFirestore(
                    event,
                    _uiState.value.invitado,
                    _uiState.value.invitadoRelevo,
                    isRelevo
                )
            } catch (e: Exception) {
                Log.e("SetGuestAccess", ERROR_SETTING_ACCESS, e)
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
    val emptyQrScan: Boolean = false,
    val isDialogShown: Boolean = false
)