package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos

class SetRegaloFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(regalo: Regalos) {
        firebaseRepository.setRegaloFirestore(regalo)
    }
}