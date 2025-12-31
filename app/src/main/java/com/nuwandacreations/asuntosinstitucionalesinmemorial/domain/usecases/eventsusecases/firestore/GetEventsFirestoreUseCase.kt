package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.events.EventsResponse
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import kotlinx.coroutines.flow.Flow

class GetEventsFirestoreUseCase(val repository: FirebaseRepository) {
    operator fun invoke(): Flow<List<EventsResponse>> {
        return repository.getEventsFirestore()
    }
}