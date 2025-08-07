package com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases

import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

class AddRegalosDBUseCase(val repository: Repository) {
    suspend operator fun invoke(vararg regalos: Regalos) {
        repository.addRegalosDB(*regalos)
    }
}