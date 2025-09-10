package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room

import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

class UpdateRegalosDBUseCase(val repository: Repository) {
    suspend operator fun invoke(regalos: List<Regalos>) {
        repository.updateRegalosDB(*regalos.toTypedArray())
    }
}