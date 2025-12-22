package com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room

import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material

class UpdateMaterialDBUseCase(val repository: Repository) {
    suspend operator fun invoke(material: List<Material>) {
        repository.updateMaterialDB(*material.toTypedArray())
    }
}