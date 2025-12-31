package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Material

class UpdateMaterialFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(material: List<Material>) = repository.updateMaterialFirestore(material)

}