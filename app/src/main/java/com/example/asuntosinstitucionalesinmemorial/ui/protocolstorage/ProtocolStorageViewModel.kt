package com.example.asuntosinstitucionalesinmemorial.ui.protocolstorage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.DownloadStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProtocolStorageViewModel(val downloadStorageUseCase: DownloadStorageUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(ProtocolStorageUiState())
    val uiState: StateFlow<ProtocolStorageUiState> = _uiState

    fun getStorage() {
        _uiState.update {
            //TODO download from firebase or room
            it
        }
    }

    fun downloadStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myStorage = downloadStorageUseCase()
                _uiState.update {
                    it.copy(storage = myStorage, internetConnection = true)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(internetConnection = false)
                }
            }
        }
    }
}

data class ProtocolStorageUiState(
    val storage: ProtocolStorage = ProtocolStorage(),
    val internetConnection: Boolean = true
)