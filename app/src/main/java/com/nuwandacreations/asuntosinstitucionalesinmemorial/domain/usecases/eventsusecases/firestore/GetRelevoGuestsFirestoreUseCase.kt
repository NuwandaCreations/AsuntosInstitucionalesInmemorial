package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
import kotlinx.coroutines.flow.Flow

class GetRelevoGuestsFirestoreUseCase(val repository: FirebaseRepository) {
    operator fun invoke(event: String): Flow<List<InvitadosRelevo>> =
        repository.getRelevoGuestsFirestore(event)
}