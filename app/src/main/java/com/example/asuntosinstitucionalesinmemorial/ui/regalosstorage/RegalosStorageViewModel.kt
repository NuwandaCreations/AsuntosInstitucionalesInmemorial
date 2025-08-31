package com.example.asuntosinstitucionalesinmemorial.ui.regalosstorage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.toDomain
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.roomusecases.AddRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebaseusecases.DeleteRegaloFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.roomusecases.DeleteRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.roomusecases.GetRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebaseusecases.GetRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebaseusecases.SetRegaloFirestoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegalosStorageViewModel(
    val addRegalosDBUseCase: AddRegalosDBUseCase,
    val getRegalosDBUseCase: GetRegalosDBUseCase,
    val deleteRegalosDBUseCase: DeleteRegalosDBUseCase,
    val setRegaloFirestoreUseCase: SetRegaloFirestoreUseCase,
    val getRegalosFirestoreUseCase: GetRegalosFirestoreUseCase,
    val deleteRegaloFirestoreUseCase: DeleteRegaloFirestoreUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegalosStorageUiState())
    val uiState: StateFlow<RegalosStorageUiState> = _uiState

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
                getRegalosFromDB()
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


    @Composable
    fun CircularProgressCountdown() {
        LaunchedEffect("circularProgress") {
            delay(20000)
            _uiState.update { it.copy(progressVisibility = false) }
        }
    }
}

data class RegalosStorageUiState(
    val storage: ProtocolStorage = ProtocolStorage(),
    val internetConnection: Boolean = true,
    val error: String = "Error",
    val progressVisibility: Boolean = true
)