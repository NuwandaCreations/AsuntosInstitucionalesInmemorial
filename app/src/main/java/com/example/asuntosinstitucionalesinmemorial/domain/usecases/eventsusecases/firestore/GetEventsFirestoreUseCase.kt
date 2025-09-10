package com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.example.asuntosinstitucionalesinmemorial.data.network.response.events.EventsResponse
import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import kotlinx.coroutines.flow.Flow

class GetEventsFirestoreUseCase(val repository: FirebaseRepository) {
    operator fun invoke(): Flow<List<EventsResponse>> {
        return repository.getEventsFirestore()
    }
}