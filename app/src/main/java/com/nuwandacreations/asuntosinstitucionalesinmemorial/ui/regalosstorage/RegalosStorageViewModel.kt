package com.nuwandacreations.asuntosinstitucionalesinmemorial.ui.regalosstorage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.model.toDomain
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage.GetAllPhotosStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage.GetPhotosStorageUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.DeleteRegaloFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.GetRegalosFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.SetRegaloFirestoreUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room.AddRegalosDBUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room.DeleteRegalosDBUseCase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room.GetRegalosDBUseCase
import kotlinx.coroutines.Dispatchers
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
    val deleteRegaloFirestoreUseCase: DeleteRegaloFirestoreUseCase,
    val getAllPhotosStorageUseCase: GetAllPhotosStorageUseCase,
    val getPhotosStorageUseCase: GetPhotosStorageUseCase
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
                    addRegalosToDB(*regalos.toTypedArray())
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

    fun getAllPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val urls = getAllPhotosStorageUseCase()
                _uiState.update { it.copy(photoUrl = urls) }
            } catch (_: Exception) {

            }
        }
    }

    fun getPhoto(regalo: Regalos) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = getPhotosStorageUseCase(regalo.objeto!!)
                if (url.isNotEmpty()) {
                    val newRegalo = regalo.copy(foto = url)
                    setRegaloFirestore(newRegalo)
                }
            } catch (_: Exception) { }
        }
    }
}

data class RegalosStorageUiState(
    val storage: ProtocolStorage = ProtocolStorage(),
    val internetConnection: Boolean = true,
    val photoUrl: MutableMap<String, String> = mutableMapOf(),
    val error: String = "Error",
)