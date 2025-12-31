package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos
import kotlinx.coroutines.flow.Flow

class GetRegaloByIdFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    operator fun invoke(regalo: String): Flow<Regalos> {
        return firebaseRepository.getRegaloByIdFirestore(regalo)
    }
}