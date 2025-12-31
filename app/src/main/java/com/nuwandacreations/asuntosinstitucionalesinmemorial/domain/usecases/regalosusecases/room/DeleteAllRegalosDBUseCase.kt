package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository

class DeleteAllRegalosDBUseCase(val repository: Repository) {
    suspend operator fun invoke() = repository.deleteAllRegalosDB()
}