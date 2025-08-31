package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.roomusecases

import com.example.asuntosinstitucionalesinmemorial.domain.Repository

class DeleteAllRegalosDBUseCase(val repository: Repository) {
    suspend operator fun invoke() = repository.deleteAllRegalosDB()
}