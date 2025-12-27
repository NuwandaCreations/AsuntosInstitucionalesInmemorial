package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos

class AddRegalosDBUseCase(val repository: Repository) {
    suspend operator fun invoke(vararg regalos: Regalos) {
        repository.addRegalosDB(*regalos)
    }
}