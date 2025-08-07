package com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material

class DeleteMaterialFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    operator fun invoke(material: Material) {
        firebaseRepository.deleteMaterialFirestore(material)
    }
}