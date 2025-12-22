package com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo

class SetRelevoGuestFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(evento: String, invitado: InvitadosRelevo) {
        repository.setRelevoGuestsFirestore(evento, invitado)
    }
}