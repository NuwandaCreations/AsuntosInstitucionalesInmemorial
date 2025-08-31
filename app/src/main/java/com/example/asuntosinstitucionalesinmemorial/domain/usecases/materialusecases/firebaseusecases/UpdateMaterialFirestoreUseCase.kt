package com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firebaseusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material

class UpdateMaterialFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(material: List<Material>) = repository.updateMaterialFirestore(material)

}