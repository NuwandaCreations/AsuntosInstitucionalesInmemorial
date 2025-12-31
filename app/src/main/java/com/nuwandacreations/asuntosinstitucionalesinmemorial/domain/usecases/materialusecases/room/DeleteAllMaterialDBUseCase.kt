package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository

class DeleteAllMaterialDBUseCase(val repository: Repository) {
    suspend operator fun invoke() = repository.deleteAllMaterialDB()
}