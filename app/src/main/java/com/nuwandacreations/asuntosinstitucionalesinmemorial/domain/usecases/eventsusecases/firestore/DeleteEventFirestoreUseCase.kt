package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository

class DeleteEventFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(eventId: String) {
        repository.deleteEventFirestore(eventId)
    }
}