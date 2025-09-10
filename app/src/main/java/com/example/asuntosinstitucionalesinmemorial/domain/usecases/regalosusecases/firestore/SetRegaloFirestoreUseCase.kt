package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

class SetRegaloFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(regalo: Regalos) {
        firebaseRepository.setRegaloFirestore(regalo)
    }
}