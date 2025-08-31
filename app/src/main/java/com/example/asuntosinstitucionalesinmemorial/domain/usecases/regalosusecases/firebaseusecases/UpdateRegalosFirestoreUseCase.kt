package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebaseusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

class UpdateRegalosFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(regalos: List<Regalos>) = repository.updateRegalosFirestore(regalos)
}