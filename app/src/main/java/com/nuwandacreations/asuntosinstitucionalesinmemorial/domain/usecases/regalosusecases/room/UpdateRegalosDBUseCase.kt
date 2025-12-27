package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos

class UpdateRegalosDBUseCase(val repository: Repository) {
    suspend operator fun invoke(regalos: List<Regalos>) {
        repository.updateRegalosDB(*regalos.toTypedArray())
    }
}