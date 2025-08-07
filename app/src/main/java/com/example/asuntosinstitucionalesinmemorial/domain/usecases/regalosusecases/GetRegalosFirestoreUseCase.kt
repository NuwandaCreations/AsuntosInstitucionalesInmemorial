package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import kotlinx.coroutines.flow.Flow

class GetRegalosFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    operator fun invoke(): Flow<List<Regalos>> {
        return firebaseRepository.getRegalosFirestore()
    }
}