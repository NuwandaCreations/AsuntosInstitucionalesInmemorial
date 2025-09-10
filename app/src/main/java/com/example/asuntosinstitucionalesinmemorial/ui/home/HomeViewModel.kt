package com.example.asuntosinstitucionalesinmemorial.ui.home

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asuntosinstitucionalesinmemorial.R
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firebasestorage.GetMaterialStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage.GetRegalosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.UpdateMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room.UpdateMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.UpdateRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room.UpdateRegalosDBUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    val getRegalosStorageUseCase: GetRegalosStorageUseCase,
    val getMaterialStorageUseCase: GetMaterialStorageUseCase,
    val updateRegalosFirestoreUseCase: UpdateRegalosFirestoreUseCase,
    val updateMaterialFirestoreUseCase: UpdateMaterialFirestoreUseCase,
    val updateRegalosDBUseCase: UpdateRegalosDBUseCase,
    val updateMaterialDBUseCase: UpdateMaterialDBUseCase
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
                job.join()

                if (regalosList.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            snackbarText = R.string.json_error,
                            snackbarColor = R.color.onError
                        )
                    }
                } else {
                    val secondJob = launch {
                        updateRegalosFirestoreUseCase(regalosList)
                        updateRegalosDBUseCase(regalosList)
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
                job.join()

                if (materialList.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            snackbarText = R.string.json_error,
                            snackbarColor = R.color.onError
                        )
                    }
                } else {
                    val secondJob = launch {
                        updateMaterialFirestoreUseCase(materialList)
                        updateMaterialDBUseCase(materialList)
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

    fun showSnackBar(snackbarHostState: SnackbarHostState, text: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(
                message = text,
                duration = SnackbarDuration.Long
            )
            _uiState.update { it.copy(snackbarText = null) }
        }
    }

    fun showDialog(isShown: Boolean) {
        _uiState.update { it.copy(isDialogShown = isShown) }
    }
}

data class HomeUiState(
    val storage: ProtocolStorage = ProtocolStorage(),
    val snackbarText: Int? = null,
    val snackbarColor: Int? = null,
    val isLoading: Boolean = false,
    val isDialogShown: Boolean = false
)

enum class ButtonAction {
    REGALOS,
    MATERIAL,
}