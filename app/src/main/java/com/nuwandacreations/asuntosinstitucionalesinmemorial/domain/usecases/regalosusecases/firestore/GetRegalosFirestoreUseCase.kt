package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos
import kotlinx.coroutines.flow.Flow

class GetRegalosFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    operator fun invoke(): Flow<List<Regalos>> {
        return firebaseRepository.getRegalosFirestore()
    }
}