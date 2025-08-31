package com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.roomusecases

import com.example.asuntosinstitucionalesinmemorial.domain.Repository

class DeleteAllMaterialDBUseCase(val repository: Repository) {
    suspend operator fun invoke() = repository.deleteAllMaterialDB()
}