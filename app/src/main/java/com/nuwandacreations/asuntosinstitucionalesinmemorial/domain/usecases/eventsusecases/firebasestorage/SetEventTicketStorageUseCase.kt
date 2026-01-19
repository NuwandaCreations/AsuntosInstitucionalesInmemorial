package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository

class SetEventTicketStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(event: String, ticket: Pair<ByteArray, String>) {
        repository.setEventTicketsStorage(event = event, ticket = ticket)
    }
}