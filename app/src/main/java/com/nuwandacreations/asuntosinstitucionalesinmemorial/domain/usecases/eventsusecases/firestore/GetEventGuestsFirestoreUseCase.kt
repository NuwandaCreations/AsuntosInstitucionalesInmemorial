package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Invitados
import kotlinx.coroutines.flow.Flow

class GetEventGuestsFirestoreUseCase(val repository: FirebaseRepository) {
    operator fun invoke(event: String): Flow<List<Invitados>> = repository.getGuestsFirestore(event)
}