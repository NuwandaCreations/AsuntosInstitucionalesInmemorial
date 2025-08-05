package com.example.asuntosinstitucionalesinmemorial.ui.protocolstorage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.toDomain
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.DownloadStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.AddMaterialUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteMaterialUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.GetMaterialUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.AddRegalosUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteRegalosUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.GetRegalosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProtocolStorageViewModel(
    val downloadStorageUseCase: DownloadStorageUseCase,
    val addRegalosUseCase: AddRegalosUseCase,
    val getRegalosUseCase: GetRegalosUseCase,
    val deleteRegalosUseCase: DeleteRegalosUseCase,
    val addMaterialUseCase: AddMaterialUseCase,
    val getMaterialUseCase: GetMaterialUseCase,
    val deleteMaterialUseCase: DeleteMaterialUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProtocolStorageUiState())
    val uiState: StateFlow<ProtocolStorageUiState> = _uiState

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

    fun addRegalosToDB(vararg regalos: Regalos) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addRegalosUseCase(*regalos)
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "No se han podido añadir los regalos") }
            }
        }
    }

    fun getRegalosFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getRegalosUseCase().collect { regalos ->
                    _uiState.update {
                        it.copy(
                            storage = it.storage.copy(regalos = regalos.map { regaloEntity -> regaloEntity.toDomain() })
                        )
                    }
                }
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "No se pudieron obtener los regalos guardados") }
            }
        }
    }

    fun deleteRegalosFromDB(vararg regalos: Regalos) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteRegalosUseCase(*regalos)
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "No se ha podido eliminar el regalo") }
            }
        }
    }

    fun addMaterialToDB(vararg material: Material) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addMaterialUseCase(*material)
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "No se han podido añadir el material") }
            }
        }
    }

    fun getMaterialFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getMaterialUseCase().collect { material ->
                    _uiState.update {
                        it.copy(
                            storage = it.storage.copy(material = material.map { materialEntity -> materialEntity.toDomain() })
                        )
                    }
                }
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "No se pudo obtener el material guardado") }
            }
        }
    }

    fun deleteMaterialFromDB(vararg material: Material) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteMaterialUseCase(*material)
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "No se ha podido eliminar el material") }
            }
        }
    }

    @Composable
    fun CircularProgressCountdown() {
        LaunchedEffect("circularProgress") {
            delay(20000)
            _uiState.update { it.copy(progressVisibility = false) }
        }
    }
}

data class ProtocolStorageUiState(
    val storage: ProtocolStorage = ProtocolStorage(),
    val internetConnection: Boolean = true,
    val error: String = "Error",
    val progressVisibility: Boolean = true
)