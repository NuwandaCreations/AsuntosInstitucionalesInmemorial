package com.example.asuntosinstitucionalesinmemorial.view.protocolStorage

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProtocolStorageViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProtocolStorageUiState())
    val uiState: StateFlow<ProtocolStorageUiState> = _uiState

    fun getStorage() {
        _uiState.update {
            it.copy( storage = "+1 elemento")
        }
    }
}

data class ProtocolStorageUiState (
    val storage: String = "vacío"
)