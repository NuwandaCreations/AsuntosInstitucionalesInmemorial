package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Evento
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.toData

class SetEventFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(event: Evento) {
        repository.setEventFirestore(event.toData())
    }
}