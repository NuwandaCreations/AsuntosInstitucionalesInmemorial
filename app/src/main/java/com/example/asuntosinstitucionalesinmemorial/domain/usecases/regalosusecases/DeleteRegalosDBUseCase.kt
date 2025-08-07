package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases

import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

class DeleteRegalosDBUseCase(val repository: Repository) {
    suspend operator fun invoke(vararg regalos: Regalos) {
        repository.deleteRegalosDB(*regalos)
    }
}