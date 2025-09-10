package com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import kotlinx.coroutines.flow.Flow

class GetMaterialByIdFirestoreUseCase(val repository: FirebaseRepository) {
    operator fun invoke(material: String): Flow<Material> {
        return repository.getMaterialByIdFirestore(material)
    }
}