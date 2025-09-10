package com.example.asuntosinstitucionalesinmemorial.ui.storagedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage.GetPhotosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.GetMaterialByIdFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.SetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.GetRegaloByIdFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.SetRegaloFirestoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StorageDetailViewModel(
    val getRegaloByIdFirestoreUseCase: GetRegaloByIdFirestoreUseCase,
    val getMaterialByIdFirestoreUseCase: GetMaterialByIdFirestoreUseCase,
    val getPhotosStorageUseCase: GetPhotosStorageUseCase,
    val setRegaloFirestoreUseCase: SetRegaloFirestoreUseCase,
    val setMaterialFirestoreUseCase: SetMaterialFirestoreUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    fun getRegaloByIdFirestore(objeto: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getRegaloByIdFirestoreUseCase(objeto).collect { regalo ->
                    _uiState.update {
                        it.copy(
                            regalo = regalo,
                        )
                    }
                }
            } catch (_: Exception) {
                try {
                    //TODO get regalo in DB
                } catch (_: Exception) {
                    _uiState.update {
                        it.copy(
                            error = R.string.search_object_error,
                        )
                    }
                }
            }
        }
    }

    fun getMaterialByIdFirestore(objeto: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getMaterialByIdFirestoreUseCase(objeto).collect { material ->
                    _uiState.update {
                        it.copy(
                            material = material,
                        )
                    }
                }
            } catch (_: Exception) {
                try {
                    //TODO get material in DB
                } catch (_: Exception) {
                    _uiState.update {
                        it.copy(
                            error = R.string.search_object_error,
                        )
                    }
                }
            }
        }
    }

    fun getPhoto(objeto: String) {
        _uiState.update { it.copy(progressVisibility = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = getPhotosStorageUseCase(objeto)
                _uiState.update { it.copy(photoUrl = url, progressVisibility = false) }
            } catch (_: Exception) {
                _uiState.update { it.copy(progressVisibility = false) }
            }
        }
    }

    fun regaloPlusNumber(regalo: Regalos) {
        regalo.cantidad = regalo.cantidad?.plus(1) ?: 1
        viewModelScope.launch(Dispatchers.IO) {
            setRegaloFirestoreUseCase(regalo)
        }
        _uiState.update { it.copy(regalo = it.regalo?.copy(cantidad = it.regalo.cantidad?.plus(1))) }
    }

    fun regaloMinusNumber(regalo: Regalos) {
        regalo.cantidad = regalo.cantidad?.minus(1) ?: 0
        viewModelScope.launch(Dispatchers.IO) {
            setRegaloFirestoreUseCase(regalo)
        }
        _uiState.update { it.copy(regalo = it.regalo?.copy(cantidad = it.regalo.cantidad?.minus(1))) }
    }

    fun materialPlusNumber(material: Material) {
        material.cantidad = material.cantidad?.plus(1) ?: 1
        viewModelScope.launch(Dispatchers.IO) {
            setMaterialFirestoreUseCase(material)
        }
        _uiState.update {
            it.copy(
                material = it.material?.copy(
                    cantidad = it.material.cantidad?.plus(
                        1
                    )
                )
            )
        }
    }

    fun materialMinusNumber(material: Material) {
        material.cantidad = material.cantidad?.minus(1) ?: 0
        viewModelScope.launch(Dispatchers.IO) {
            setMaterialFirestoreUseCase(material)
        }
        _uiState.update {
            it.copy(
                material = it.material?.copy(
                    cantidad = it.material.cantidad?.minus(
                        1
                    )
                )
            )
        }
    }
}

data class DetailUiState(
    val regalo: Regalos? = null,
    val material: Material? = null,
    val photoUrl: String? = null,
    val error: Int = R.string.error,
    val progressVisibility: Boolean = false
)