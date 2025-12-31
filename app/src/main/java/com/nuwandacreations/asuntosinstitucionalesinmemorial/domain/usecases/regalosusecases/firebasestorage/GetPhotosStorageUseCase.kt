package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository

class GetPhotosStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(objeto: String): String = repository.getPhotosStorage(objeto)
}