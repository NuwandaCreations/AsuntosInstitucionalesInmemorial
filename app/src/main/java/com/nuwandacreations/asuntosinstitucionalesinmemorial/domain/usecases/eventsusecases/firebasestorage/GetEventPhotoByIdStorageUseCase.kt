package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository

class GetEventPhotoByIdStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke (id: String) : String {
        return repository.getEventPhotoByIdStorage(id) ?: ""
    }
}