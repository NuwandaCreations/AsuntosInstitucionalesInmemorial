package com.example.asuntosinstitucionalesinmemorial.ui.protocolstorage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.MaterialDao
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.RegalosDao
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.DownloadStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProtocolStorageViewModel(val downloadStorageUseCase: DownloadStorageUseCase, val regalosDao: RegalosDao, val materialDao: MaterialDao) : ViewModel() {
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

    fun addStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val regalo = RegalosEntity("regalito", 3, "regalito", "regalito", "regalito", "regalito", "regalito")
                regalosDao.addRegalo(regalo)
            } catch (e: Exception) {

            }
        }
    }

    fun getStorageFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val respuesta = regalosDao.getAllRegalos().collect { regalos ->
//                    _uiState.update { it.copy(storage = regalos) }
                }
            } catch (e: Exception) {

            }
        }
    }
}

data class ProtocolStorageUiState(
    val storage: ProtocolStorage = ProtocolStorage(),
    val internetConnection: Boolean = true
)