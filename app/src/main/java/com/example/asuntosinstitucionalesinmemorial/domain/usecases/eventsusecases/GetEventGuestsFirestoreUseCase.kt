package com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Invitados
import kotlinx.coroutines.flow.Flow

class GetEventGuestsFirestoreUseCase(val repository: FirebaseRepository) {
    operator fun invoke(event: String): Flow<List<Invitados>> = repository.getGuestsFirestore(event)
}