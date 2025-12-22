package com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Invitados

class SetGuestFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(evento: String, invitado: Invitados) {
        repository.setGuestsFirestore(evento, invitado)
    }
}