package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
import kotlinx.coroutines.flow.Flow

class GetGuestRelevoByIdFirestoreUseCase(val repository: FirebaseRepository) {
    operator fun invoke(event: String, invitado: String): Flow<InvitadosRelevo> =
        repository.getRelevoGuestByIdFirestore(event, invitado)
}