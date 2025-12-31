package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Material

class DeleteMaterialDBUseCase(val repository: Repository) {
    suspend operator fun invoke(vararg material: Material) {
        repository.deleteMaterialDB(*material)
    }
}