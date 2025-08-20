package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository

class DeleteAllRegalosFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke() = repository.deleteAllRegalosFirestore()
}