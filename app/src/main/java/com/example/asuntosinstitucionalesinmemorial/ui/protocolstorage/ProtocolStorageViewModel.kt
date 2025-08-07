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
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.AddMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.GetMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.GetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.SetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.AddRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteRegaloFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.GetRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.GetRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.SetRegaloFirestoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProtocolStorageViewModel(
    val downloadStorageUseCase: DownloadStorageUseCase,
    val addRegalosDBUseCase: AddRegalosDBUseCase,
    val getRegalosDBUseCase: GetRegalosDBUseCase,
    val deleteRegalosDBUseCase: DeleteRegalosDBUseCase,
    val addMaterialDBUseCase: AddMaterialDBUseCase,
    val getMaterialDBUseCase: GetMaterialDBUseCase,
    val deleteMaterialDBUseCase: DeleteMaterialDBUseCase,
    val setRegaloFirestoreUseCase: SetRegaloFirestoreUseCase,
    val getRegalosFirestoreUseCase: GetRegalosFirestoreUseCase,
    val deleteRegaloFirestoreUseCase: DeleteRegaloFirestoreUseCase,
    val setMaterialFirestoreUseCase: SetMaterialFirestoreUseCase,
    val getMaterialFirestoreUseCase: GetMaterialFirestoreUseCase,
    val deleteMaterialFirestoreUseCase: DeleteMaterialFirestoreUseCase,
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
                addRegalosDBUseCase(*regalos)
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "No se han podido añadir los regalos") }
            }
        }
    }

    fun getRegalosFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getRegalosDBUseCase().collect { regalos ->
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
                deleteRegalosDBUseCase(*regalos)
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "No se ha podido eliminar el regalo") }
            }
        }
    }

    fun addMaterialToDB(vararg material: Material) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                addMaterialDBUseCase(*material)
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "No se han podido añadir el material") }
            }
        }
    }

    fun getMaterialFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getMaterialDBUseCase().collect { material ->
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
                deleteMaterialDBUseCase(*material)
            } catch (_: Exception) {
                _uiState.update { it.copy(error = "No se ha podido eliminar el material") }
            }
        }
    }

    fun getRegalosFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getRegalosFirestoreUseCase().collect { regalos ->
                    _uiState.update {
                        it.copy(
                            storage = it.storage.copy(regalos = regalos)
                        )
                    }
                }
            } catch (_: Exception) {
                _uiState.update { it.copy(internetConnection = false) }
            }
        }
    }

    fun setRegaloFirestore(regalo: Regalos) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setRegaloFirestoreUseCase(regalo)
            } catch (_: Exception) {
                _uiState.update { it.copy(internetConnection = false) }
            }
        }
    }

    fun deleteRegalosFirestore(regalos: Regalos) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteRegaloFirestoreUseCase(regalos)
            } catch (_: Exception) {
                _uiState.update { it.copy(internetConnection = false) }
            }
        }
    }

    fun getMaterialFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getMaterialFirestoreUseCase().collect { material ->
                    _uiState.update {
                        it.copy(
                            storage = it.storage.copy(material = material)
                        )
                    }
                }
            } catch (_: Exception) {
                _uiState.update { it.copy(internetConnection = false) }
            }
        }
    }

    fun setMaterialFirestore(material: Material) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setMaterialFirestoreUseCase(material)
            } catch (_: Exception) {
                _uiState.update { it.copy(internetConnection = false) }
            }
        }
    }

    fun deleteMaterialFirestore(material: Material) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteMaterialFirestoreUseCase(material)
            } catch (_: Exception) {
                _uiState.update { it.copy(internetConnection = false) }
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