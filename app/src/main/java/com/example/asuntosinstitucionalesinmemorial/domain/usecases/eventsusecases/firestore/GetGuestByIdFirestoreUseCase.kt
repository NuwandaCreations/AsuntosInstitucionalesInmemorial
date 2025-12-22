package com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Invitados
import kotlinx.coroutines.flow.Flow

class GetGuestByIdFirestoreUseCase(val repository: FirebaseRepository) {
    operator fun invoke(event: String, invitado: String): Flow<Invitados> =
        repository.getGuestByIdFirestore(event, invitado)
}