package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import kotlinx.coroutines.flow.Flow

class GetRegaloByIdFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    operator fun invoke(regalo: String): Flow<Regalos> {
        return firebaseRepository.getRegaloByIdFirestore(regalo)
    }
}