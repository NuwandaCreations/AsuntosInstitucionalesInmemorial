package com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room

import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Material

class UpdateMaterialDBUseCase(val repository: Repository) {
    suspend operator fun invoke(material: List<Material>) {
        repository.updateMaterialDB(*material.toTypedArray())
    }
}