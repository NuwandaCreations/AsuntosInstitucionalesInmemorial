package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos

class UpdateRegalosFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(regalos: List<Regalos>) = repository.updateRegalosFirestore(regalos)
}