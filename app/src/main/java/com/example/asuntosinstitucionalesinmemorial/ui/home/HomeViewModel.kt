package com.example.asuntosinstitucionalesinmemorial.ui.home

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetMaterialStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetPhotosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetRegalosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.AddMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteAllMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteAllMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.SetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.AddRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteAllRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteAllRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.SetRegaloFirestoreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    val getRegalosStorageUseCase: GetRegalosStorageUseCase,
    val getMaterialStorageUseCase: GetMaterialStorageUseCase,
    val getPhotosStorageUseCase: GetPhotosStorageUseCase,
    val setRegalosFirestoreUseCase: SetRegaloFirestoreUseCase,
    val setMaterialFirestoreUseCase: SetMaterialFirestoreUseCase,
    val saveRegalosDBUseCase: AddRegalosDBUseCase,
    val saveMaterialDBUseCase: AddMaterialDBUseCase,
    val deleteAllRegalosDBUseCase: DeleteAllRegalosDBUseCase,
    val deleteAllMaterialDBUseCase: DeleteAllMaterialDBUseCase,
    val deleteAllRegalosFirestoreUseCase: DeleteAllRegalosFirestoreUseCase,
    val deleteAllMaterialFirestoreUseCase: DeleteAllMaterialFirestoreUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun getRegalosStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update { it.copy(isLoading = true) }
                var regalosList = emptyList<Regalos>()
                val job = launch {
                    regalosList = getRegalosStorageUseCase().toList()
                }
                //con esto esperamos a que acabe el use case y devuelva el storage
                job.join()
                val secondJob = launch {
                    deleteAllRegalosDB()
                    deleteAllRegalosFirestore()
                    saveRegalosDB(*regalosList.toTypedArray())
                    setRegalosFirestore(regalosList)
                }
                secondJob.join()
                _uiState.update {
                    it.copy(
                        storage = it.storage.copy(regalos = regalosList),
                        isLoading = false,
                        snackbarText = R.string.database_success,
                        snackbarColor = R.color.onSuccess
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        snackbarText = R.string.database_error,
                        snackbarColor = R.color.onError,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getMaterialStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.update { it.copy(isLoading = true) }
                var materialList = emptyList<Material>()
                val job = launch {
                    materialList = getMaterialStorageUseCase().toList()
                }
                //con esto esperamos a que acabe el use case y devuelva el storage
                job.join()
                val secondJob = launch {
                    deleteAllMaterialDB()
                    deleteAllMaterialFirestore()
                    saveMaterialDB(*materialList.toTypedArray())
                    setMaterialFirestore(materialList)
                }
                secondJob.join()
                _uiState.update {
                    it.copy(
                        storage = it.storage.copy(material = materialList),
                        isLoading = false,
                        snackbarText = R.string.database_success,
                        snackbarColor = R.color.onSuccess
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        snackbarText = R.string.database_error,
                        snackbarColor = R.color.onError,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun setRegalosFirestore(regalos: List<Regalos>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                regalos.forEach { setRegalosFirestoreUseCase(it) }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        snackbarText = R.string.firestore_error,
                        snackbarColor = R.color.onError
                    )
                }
            }
        }
    }

    fun setMaterialFirestore(material: List<Material>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                material.forEach { setMaterialFirestoreUseCase(it) }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        snackbarText = R.string.firestore_error,
                        snackbarColor = R.color.onError
                    )
                }
            }
        }
    }

    fun deleteAllRegalosDB() {
        viewModelScope.launch {
            try {
                deleteAllRegalosDBUseCase()
            } catch (_: Exception) {
            }
        }
    }

    fun deleteAllMaterialDB() {
        viewModelScope.launch {
            try {
                deleteAllMaterialDBUseCase()
            } catch (_: Exception) {
            }
        }
    }

    fun deleteAllRegalosFirestore() {
        viewModelScope.launch {
            try {
                deleteAllRegalosFirestoreUseCase()
            } catch (_: Exception) {
            }
        }
    }

    fun deleteAllMaterialFirestore() {
        viewModelScope.launch {
            try {
                deleteAllMaterialFirestoreUseCase()
            } catch (_: Exception) {
            }
        }
    }

    fun saveRegalosDB(vararg regalos: Regalos) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveRegalosDBUseCase(*regalos)
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        snackbarText = R.string.save_error,
                        snackbarColor = R.color.onError
                    )
                }
            }
        }
    }

    fun saveMaterialDB(vararg material: Material) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveMaterialDBUseCase(*material)
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        snackbarText = R.string.save_error,
                        snackbarColor = R.color.onError
                    )
                }
            }
        }
    }

    fun showSnackBar(snackbarHostState: SnackbarHostState, text: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(
                message = text,
                duration = SnackbarDuration.Long
            )
        }
    }
}

data class HomeUiState(
    val storage: ProtocolStorage = ProtocolStorage(),
    val snackbarText: Int? = null,
    val snackbarColor: Int? = null,
    val isLoading: Boolean = false
)