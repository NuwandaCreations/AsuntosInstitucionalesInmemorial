package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository

class GetPhotosStorageUseCase(val repository: FirebaseRepository) {
    suspend operator fun invoke(objeto: String): String = repository.getPhotosStorage(objeto)
}