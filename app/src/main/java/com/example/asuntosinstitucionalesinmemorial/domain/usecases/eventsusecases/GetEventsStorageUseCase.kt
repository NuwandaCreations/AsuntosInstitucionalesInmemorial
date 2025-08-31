package com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository

class GetEventsStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(): List<String> {
        //TODO
        return listOf("", "")
    }
}