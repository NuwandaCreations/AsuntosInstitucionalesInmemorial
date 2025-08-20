package com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository

class DeleteAllMaterialFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke() = repository.deleteAllMaterialFirestore()

}