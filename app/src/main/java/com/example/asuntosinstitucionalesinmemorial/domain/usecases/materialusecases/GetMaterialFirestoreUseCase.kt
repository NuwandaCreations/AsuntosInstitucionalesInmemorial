package com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import kotlinx.coroutines.flow.Flow

class GetMaterialFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    operator fun invoke(): Flow<List<Material>> {
        return firebaseRepository.getMaterialFirestore()
    }
}