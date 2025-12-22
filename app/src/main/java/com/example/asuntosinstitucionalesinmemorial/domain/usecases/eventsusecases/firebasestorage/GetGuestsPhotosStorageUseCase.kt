package com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository

class GetGuestsPhotosStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(): List<Pair<String, String>>{
        return repository.getGuestsPhotosStorage()
    }
}