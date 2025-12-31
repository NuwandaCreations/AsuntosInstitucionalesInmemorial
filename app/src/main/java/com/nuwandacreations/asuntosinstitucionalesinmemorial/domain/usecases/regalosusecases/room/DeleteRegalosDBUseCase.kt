package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos

class DeleteRegalosDBUseCase(val repository: Repository) {
    suspend operator fun invoke(vararg regalos: Regalos) {
        repository.deleteRegalosDB(*regalos)
    }
}