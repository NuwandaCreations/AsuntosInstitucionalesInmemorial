package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebaseusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

class DeleteRegaloFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    suspend operator fun invoke(regalo: Regalos) {
        firebaseRepository.deleteRegalosFirestore(regalo)
    }
}