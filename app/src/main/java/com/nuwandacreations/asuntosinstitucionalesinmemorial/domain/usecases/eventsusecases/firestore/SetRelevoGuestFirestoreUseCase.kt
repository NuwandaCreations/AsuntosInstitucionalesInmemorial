package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo

class SetRelevoGuestFirestoreUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(evento: String, invitado: InvitadosRelevo) {
        repository.setRelevoGuestsFirestore(evento, invitado)
    }
}