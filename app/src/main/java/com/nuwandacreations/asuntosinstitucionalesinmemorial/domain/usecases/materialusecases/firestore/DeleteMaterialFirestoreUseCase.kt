package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Material

class DeleteMaterialFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(material: Material) {
        firebaseRepository.deleteMaterialFirestore(material)
    }
}