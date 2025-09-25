package com.example.asuntosinstitucionalesinmemorial.ui.materialstorage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.toDomain
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage.GetAllPhotosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.DeleteMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.GetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.SetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room.AddMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room.DeleteMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room.GetMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage.GetPhotosStorageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MaterialStorageViewModel(
    val addMaterialDBUseCase: AddMaterialDBUseCase,
    val getMaterialDBUseCase: GetMaterialDBUseCase,
    val deleteMaterialDBUseCase: DeleteMaterialDBUseCase,
    val setMaterialFirestoreUseCase: SetMaterialFirestoreUseCase,
    val getMaterialFirestoreUseCase: GetMaterialFirestoreUseCase,
    val deleteMaterialFirestoreUseCase: DeleteMaterialFirestoreUseCase,
    val getAllPhotosStorageUseCase: GetAllPhotosStorageUseCase,
    val getPhotosStorageUseCase: GetPhotosStorageUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MaterialStorageUiState())
    val uiState: StateFlow<MaterialStorageUiState> = _uiState

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
                getMaterialFromDB()
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

    fun getAllPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val urls = getAllPhotosStorageUseCase()
                _uiState.update { it.copy(photoUrl = urls) }
            } catch (_: Exception) {

            }
        }
    }

    fun getPhoto(material: Material) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = getPhotosStorageUseCase(material.objeto!!)
                if (url.isNotEmpty()) {
                    val newMaterial = material.copy(foto = url)
                    setMaterialFirestore(newMaterial)
                }
            } catch (_: Exception) { }
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

data class MaterialStorageUiState(
    val storage: ProtocolStorage = ProtocolStorage(),
    val internetConnection: Boolean = true,
    val photoUrl: MutableMap<String, String> = mutableMapOf(),
    val error: String = "Error",
    val progressVisibility: Boolean = true
)