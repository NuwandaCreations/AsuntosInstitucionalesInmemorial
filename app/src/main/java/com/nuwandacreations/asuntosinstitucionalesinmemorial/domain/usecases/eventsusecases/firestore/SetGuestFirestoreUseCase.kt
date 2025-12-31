package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Invitados

class SetGuestFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(evento: String, invitado: Invitados) {
        repository.setGuestsFirestore(evento, invitado)
    }
}