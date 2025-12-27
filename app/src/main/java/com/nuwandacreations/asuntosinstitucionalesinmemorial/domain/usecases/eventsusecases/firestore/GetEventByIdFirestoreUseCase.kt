package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.events.EventsResponse
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import kotlinx.coroutines.flow.Flow

class GetEventByIdFirestoreUseCase(val repository: FirebaseRepository) {
    operator fun invoke(event: String): Flow<EventsResponse> {
        return repository.getEventByIdFirestore(event)
    }
}