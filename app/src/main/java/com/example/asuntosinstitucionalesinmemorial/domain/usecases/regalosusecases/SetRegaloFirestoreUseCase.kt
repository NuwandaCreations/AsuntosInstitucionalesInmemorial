package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

class SetRegaloFirestoreUseCase(val firebaseRepository: FirebaseRepository) {
    operator fun invoke(regalo: Regalos) {
        firebaseRepository.setRegaloFirestore(regalo)
    }
}