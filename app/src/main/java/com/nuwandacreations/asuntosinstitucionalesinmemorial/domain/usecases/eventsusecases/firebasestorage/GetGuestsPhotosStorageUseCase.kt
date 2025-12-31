package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository

class GetGuestsPhotosStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(): List<Pair<String, String>>{
        return repository.getGuestsPhotosStorage()
    }
}